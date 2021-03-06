// 
//Copyright (c) 2003-2005, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
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
import org.objectledge.parameters.RequestParameters;
import org.objectledge.templating.tools.ContextToolFactory;
import org.objectledge.web.mvc.MVCContext;

/**
 * Context tool factory component to build the parameters tool.
 * 
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: ParametersToolFactory.java,v 1.2 2005-03-03 13:26:57 zwierzem Exp $
 */
public class ParametersToolFactory implements ContextToolFactory
{
    private Context context;

    /**
     * Component constructor.
     *
     * @param context the context.
     */
    public ParametersToolFactory(Context context)
    {
        this.context = context;
    }

    /**
	 * {@inheritDoc}
	 */
	public Object getTool()
	{
		return new ParametersTool(
            MVCContext.getMVCContext(context),
            RequestParameters.getRequestParameters(context));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void recycleTool(Object tool)
	{
		//do nothing ParametersTool is too simple object to be pooled
	}

	/**
	 * {@inheritDoc}
	 */
	public String getKey()
	{
		return "parametersTool";
	}    
}
