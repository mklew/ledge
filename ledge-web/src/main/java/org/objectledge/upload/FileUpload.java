// 
// Copyright (c) 2003, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
// All rights reserved. 
//   
// Redistribution and use in source and binary forms, with or without modification,  
// are permitted provided that the following conditions are met: 
//   
// * Redistributions of source code must retain the above copyright notice,  
//   this list of conditions and the following disclaimer. 
// * Redistributions in binary form must reproduce the above copyright notice,  
//   this list of conditions and the following disclaimer in the documentation  
//   and/or other materials provided with the distribution. 
// * Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.  
//   nor the names of its contributors may be used to endorse or promote products  
//   derived from this software without specific prior written permission. 
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

package org.objectledge.upload;

import java.util.Map;

import org.jcontainer.dna.Configuration;
import org.objectledge.context.Context;

/**
 * An application access point to the HTML form file upload functionality.
 *
 * <p>Created on Jan 14, 2004</p>
 * @author <a href="Rafal.Krzewski">rafal@caltha.pl</a>
 * @version $Id: FileUpload.java,v 1.5 2004-02-03 11:30:39 pablo Exp $
 */
public class FileUpload
{
    // constants ////////////////////////////////////////////////////////////////////////////////
    
    /** context key to store the upload map */
    public static final String UPLOAD_CONTEXT_KEY = "org.objectledge.upload.FileUpload.uploadMap";
    
    /** the default upload limit */
    public static final int DEFAULT_UPLOAD_LIMIT = 4194304;

    // instance variables ///////////////////////////////////////////////////////////////////////
    
    /** the thread's processing context. */
    private Context context;

    /** the upload size limit */
    private int uploadLimit;

    // initialization ///////////////////////////////////////////////////////////////////////////

    /**
     * Creates a FileUpload component.
     *
     * @param config the configuration. 
     * @param context the context.
     */
    public FileUpload(Configuration config, Context context)
    {
        uploadLimit = config.getChild("upload_limit").getValueAsInteger(DEFAULT_UPLOAD_LIMIT);
        this.context = context;
    }
    
    // public API ///////////////////////////////////////////////////////////////////////////////

    /**
     * Retrieve the upload container.
     *
     * @param name the name of the item.
     * @return the upload container, or <code>null</code> if not available.
     */
    public UploadContainer getContainer(String name) 
    {
        Map map =(Map)context.getAttribute(UPLOAD_CONTEXT_KEY);
        if (map == null) 
        {
            return null;
        }
        else
        {
            return (UploadContainer)map.get(name);
        }
    }
    
    /**
     * Get the upload size limit. 
     *
     * @return the upload limit. 
     */
    public int getUploadLimit()
    {
        return uploadLimit;
    }    

}
