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

package org.objectledge.web.mvc;

import org.objectledge.context.Context;
import org.objectledge.parameters.Parameters;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.Valve;
import org.objectledge.web.WebConfigurator;

/**
 * Pipeline processing valve that initializes the MVC parameters (view and action) by means of the
 * {@link org.objectledge.web.mvc.MVCContext} object.
 *
 * @author <a href="mailto:pablo@caltha.pl">Pawel Potempski</a>
 * @version $Id: MVCInitializerValve.java,v 1.9 2005-07-22 17:25:50 pablo Exp $
 */
public class MVCInitializerValve 
    implements Valve
{
	/** the web configuration component */
	private WebConfigurator webConfigurator;
	
	/**
	 * Constructor.
	 * 
	 * @param webConfigurator the web configuration component.
	 */
	public MVCInitializerValve(WebConfigurator webConfigurator)
	{
		this.webConfigurator = webConfigurator;
	}
	
    /**
     * Run the pipeline valve - initialize and store the pipeline context.
     * 
     * @param context the context.
     */
    public void process(Context context)
    {
    	MVCContext mvcContext = new MVCContext();
        Parameters requestParamters = RequestParameters.getRequestParameters(context);
        mvcContext.setAction(requestParamters.get(webConfigurator.getActionToken(), null));
        mvcContext.setView(requestParamters.get(webConfigurator.getViewToken(), null));
    	context.setAttribute(MVCContext.class, mvcContext);
    }
}
