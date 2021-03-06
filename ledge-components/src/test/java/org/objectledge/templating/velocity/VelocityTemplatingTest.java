// 
//Copyright (c) 2003, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
//All rights reserved. 
//   
//Redistribution and use in source and binary forms, with or without modification,  
//are permitted provided that the following conditions are met: 
//   
//* Redistributions of source code must retain the above copyright notice,  
//this list of conditions and the following disclaimer. 
//* Redistributions in binary form must reproduce the above copyright notice,  
//this list of conditions and the following disclaimer in the documentation  
//and/or other materials provided with the distribution. 
//* Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.  
//nor the names of its contributors may be used to endorse or promote products  
//derived from this software without specific prior written permission. 
// 
//THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"  
//AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED  
//WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
//IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,  
//INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,  
//BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
//OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,  
//WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)  
//ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE  
//POSSIBILITY OF SUCH DAMAGE. 
//

package org.objectledge.templating.velocity;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.impl.Log4JLogger;
import org.objectledge.context.Context;
import org.objectledge.filesystem.ClasspathFileSystemProvider;
import org.objectledge.filesystem.FileSystem;
import org.objectledge.filesystem.FileSystemProvider;
import org.objectledge.filesystem.LocalFileSystemProvider;
import org.objectledge.logging.LedgeDOMConfigurator;
import org.objectledge.pipeline.SimplePipeline;
import org.objectledge.pipeline.Valve;
import org.objectledge.templating.MergingException;
import org.objectledge.templating.Template;
import org.objectledge.templating.TemplateNotFoundException;
import org.objectledge.templating.Templating;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.templating.TemplatingContextLoaderValve;
import org.objectledge.templating.tools.ContextToolFactory;
import org.objectledge.templating.tools.ContextToolPopulatorValve;
import org.objectledge.templating.tools.ContextToolRecyclerValve;
import org.objectledge.templating.tools.ContextTools;
import org.objectledge.test.LedgeTestCase;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Velocity Templating test.
 * 
 * @author <a href="mailto:pablo@caltha.pl">Pawel Potempski</a>
 */
public class VelocityTemplatingTest extends LedgeTestCase
{
    private Templating templating;

    protected void setUp() throws Exception
    {
        super.setUp();
        FileSystem fs = getFileSystem();
		try
		{
            InputSource source = new InputSource(fs.getInputStream(
                "config/org.objectledge.logging.LoggingConfigurator.xml"));
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document logConfig = builder.parse(source);
            LedgeDOMConfigurator configurator = new LedgeDOMConfigurator(fs);
            configurator.doConfigure(logConfig.getDocumentElement(), 
                LogManager.getLoggerRepository());

            Configuration config = getConfig(fs,
                "config/org.objectledge.templating.velocity.VelocityTemplating.xml");
                
			Logger logger = Logger.getLogger(VelocityTemplating.class);
			templating = new VelocityTemplating(config, new Log4JLogger(logger), fs);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
    }
    
    protected void tearDown() throws Exception
    {
        super.tearDown();
        templating = null;
    }

    /**
    	 * CreateContext method test. 
     */
    public void testCreateContext()
    {
        assertNotNull(templating);
        TemplatingContext context = templating.createContext();
        assertNotNull(context);
        context.put("foo","bar");
        assertEquals("bar",context.get("foo"));
        assertTrue(context.containsKey("foo"));
        assertEquals(1, context.getKeys().length);
        context.remove("foo");
        assertNull(context.get("foo"));
		assertEquals(0, context.getKeys().length);
    }

    /**
     * CreateContext method test. 
     */
    public void testTemplateExists()
    {
    	assertEquals(templating.templateExists("foo"), false);
    	assertEquals(templating.templateExists("bar"), true);
    }

    /**
     * CreateContext method test. 
     */
    public void testGetTemplate()
    {
    	try
    	{
    		assertNotNull(templating.getTemplate("bar"));
    		// cache test
			assertNotNull(templating.getTemplate("bar"));
    	}
    	catch(TemplateNotFoundException e)
    	{
    		fail(e.getMessage());
    	}
		try
		{
			templating.getTemplate("foo");
			fail("Should throw TemplateNotFoundException");
		}
		catch(TemplateNotFoundException e)
		{
			//do nothing
		}
    }

    /**
     * CreateContext method test. 
     */
    public void testEvaluate()
    {
		try
		{
			TemplatingContext context = templating.createContext();
			context.put("foo","bar");
			StringWriter target = new StringWriter();
			StringReader source = new StringReader("foo $foo");
			templating.merge(context,source,target,"test"); 
			assertEquals("foo bar",target.toString());
		}
		catch(MergingException e)
		{
			fail(e.getMessage());
		}
    }

    /**
     * CreateContext method test. 
     */
    public void testMerge()
    {
		try
		{
			Template template = templating.getTemplate("bar");
			TemplatingContext context = templating.createContext();
			context.put("foo","bar");
			assertEquals("foo bar",template.merge(context));
		}
		catch(TemplateNotFoundException e)
		{
			fail(e.getMessage());
		}
		catch(MergingException e)
		{
			fail(e.getMessage());
		}
    }

    /**
     * CreateContext method test. 
     */
    public void testGetTemplateEncoding()
    {
    	assertEquals("UTF-8", templating.getTemplateEncoding());
    }
    
	public void testPipelineComponents()
	{
		try
		{
		    Context context = new Context();
	    	Valve[] valves = new Valve[3];
	    	ContextTools contextTools = new ContextTools(new ContextToolFactory[0]);
	    	valves[0] = new TemplatingContextLoaderValve(templating);
			valves[1] = new ContextToolPopulatorValve(contextTools);
			valves[2] = new ContextToolRecyclerValve(contextTools);
		
			Valve pipe = new SimplePipeline(valves);
			pipe.process(context);
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
    }
}
