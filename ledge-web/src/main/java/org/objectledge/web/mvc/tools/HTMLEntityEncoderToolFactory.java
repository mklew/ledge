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

package org.objectledge.web.mvc.tools;

import org.objectledge.context.Context;
import org.objectledge.encodings.HTMLEntityEncoder;
import org.objectledge.templating.tools.ContextToolFactory;
import org.objectledge.web.HttpContext;

/**
 * Context tool factory component for html entity encoder tool.
 * 
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: HTMLEntityEncoderToolFactory.java,v 1.4 2004-08-18 09:04:38 zwierzem Exp $
 */
public class HTMLEntityEncoderToolFactory implements ContextToolFactory
{
	private Context context;
	private HTMLEntityEncoder htmlEntityEncoder;
	
	/**
	 * Component constructor.
	 * @param context application context
	 * @param htmlEntityEncoder encoder used by encoder tools.
 	 */
	public HTMLEntityEncoderToolFactory(Context context, HTMLEntityEncoder htmlEntityEncoder)
	{
        this.context = context;
		this.htmlEntityEncoder = htmlEntityEncoder;
	}
	
    /**
	 * {@inheritDoc}
	 */
	public Object getTool()
	{
		return new HTMLEntityEncoderTool(htmlEntityEncoder,
					HttpContext.getHttpContext(context).getEncoding());
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void recycleTool(Object tool)
	{
		// these simple objects do not need recycling
	}

	/**
	 * {@inheritDoc}
	 */
	public String getKey()
	{
		return "htmlEncoder";
	}    
}
