// 
// Copyright (c) 2003,2004 , Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
// All rights reserved. 
// 
// Redistribution and use in source and binary forms, with or without modification,  
// are permitted provided that the following conditions are met: 
//  
// * Redistributions of source code must retain the above copyright notice,  
//	 this list of conditions and the following disclaimer. 
// * Redistributions in binary form must reproduce the above copyright notice,  
//	 this list of conditions and the following disclaimer in the documentation  
//	 and/or other materials provided with the distribution. 
// * Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.  
//	 nor the names of its contributors may be used to endorse or promote products  
//	 derived from this software without specific prior written permission. 
// 
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"  
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED  
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
// IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,  
// INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,  
// BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
// OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,  
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)  
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE  
// POSSIBILITY OF SUCH DAMAGE. 
// 
package org.objectledge.filesystem;

import java.io.InputStream;
import java.io.OutputStream;

import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;
import org.objectledge.ComponentInitializationError;
import org.picocontainer.Startable;

/**
 * Extracts contents of the spcified directory in some FileSystemProviders into another 
 * FileSystemProvider.
 * 
 * <p>This compoenent is typically used to extracts web content (images, css) from jars/war
 * into a working directory of the application that is going to be served by an external web 
 * server.</p>
 *
 * @author <a href="mailto:rafal@caltha.pl">Rafal Krzewski</a>
 * @version $Id: ContentExtractor.java,v 1.2 2004-12-22 08:58:38 rafal Exp $
 */
public class ContentExtractor
	implements Startable
{
    /**
     * Creates new ContentExtractor instance.
     * 
     * @param fileSystem the filesystem to operate on.
     * @param config component configuration.
     * @throws ConfigurationException if there is a problem accessing the configuration.
     */
    public ContentExtractor(FileSystem fileSystem, Configuration config) 
    	throws ConfigurationException
    {
        Configuration providers = config.getChild("providers");
        Configuration[] source = providers.getChildren("source");
        extractContentFrom = new String[source.length];
        for(int i = 0; i < source.length; i++)
        {
            extractContentFrom[i] = source[i].getValue();
        }
        extractContentTo = providers.getChild("target").getValue();
        extractContentDirectory = config.getChild("directory").getValue();
        this.fileSystem = fileSystem;
    }
    
    /** The FileSystem. */
    protected FileSystem fileSystem;
    
    /** The directory used for content extraction. */
    protected String extractContentDirectory;
    
    /** Names of the providers to read content from in the desired processing 
     * order.*/
    protected String[] extractContentFrom;
    
    /** Name of the provider to write content to. */
    protected String extractContentTo;
    
    private void extractContent()
    {
        String updateMarker = extractContentDirectory+"/.updated"; 
        try
        {
            FileSystemProvider out = fileSystem.getProvider(extractContentTo);
            if(out.exists(updateMarker))
            {
                return;
            }
            FileSystemProvider in;
            byte[] buffer = new byte[65536];
            for(int i=0; i<extractContentFrom.length; i++)
            {
                in = fileSystem.getProvider(extractContentFrom[i]);
                extractDirectory(extractContentDirectory, in, out, buffer);
            }
            fileSystem.write(updateMarker, new byte[0]);
        }
        catch(Exception e)
        {
            throw new ComponentInitializationError("failed to extract content", e);
        }
    }
    
    private void extractDirectory(String name, FileSystemProvider in, FileSystemProvider out, 
        byte[] buffer)
        throws Exception
    {
        if(!in.exists(name) || !in.isDirectory(name))
        {
            return;
        }
        String[] items = in.list(name);
        for(int i=0; i<items.length; i++)
        {
            String item = name+"/"+items[i];
            if(in.isDirectory(item))
            {
                extractDirectory(item, in, out, buffer);
            }
            else
            {
                InputStream is = in.getInputStream(item);
                if(!out.exists(FileSystem.directoryPath(item)))
                {
                    out.mkdirs(FileSystem.directoryPath(item));
                }
                OutputStream os = out.getOutputStream(item, false);
                if(is != null && os != null)
                {
                    int count = 0;
                    while(count >= 0)
                    {
                        count = is.read(buffer, 0, buffer.length);
                        if(count > 0)
                        {
                            os.write(buffer, 0, count);                            
                        }
                    }
                    os.flush();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void start()
    {
        extractContent();
    }

    /**
     * {@inheritDoc}
     */
    public void stop()
    {
    }
}