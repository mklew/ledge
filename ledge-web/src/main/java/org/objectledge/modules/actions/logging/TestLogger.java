// 
// Copyright (c) 2003-2005, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
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
package org.objectledge.modules.actions.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.web.mvc.builders.PolicyProtectedAction;
import org.objectledge.web.mvc.security.PolicySystem;

/**
 * Tests the logger settings by emmiting a message.
 *
 * @author <a href="mailto:rafal@caltha.pl">Rafal Krzewski</a>
 * @version $Id: TestLogger.java,v 1.2 2005-05-20 04:19:01 rafal Exp $
 */
public class TestLogger
    extends PolicyProtectedAction
{

    /**
     * Creates new UpdateLogger instance.
     * 
     * @param policySystem the PolicySystem component.
     */
    public TestLogger(PolicySystem policySystem)
    {
        super(policySystem);
    }

    /**
     * {@inheritDoc}
     */
    public void process(Context context)
        throws ProcessingException
    {
        RequestParameters requestParameters = RequestParameters.getRequestParameters(context);
        String id = requestParameters.get("id");
        Logger logger;
        if(id.equals("root"))
        {
            logger = LogManager.getRootLogger();
        }
        else
        {
            if(LogManager.exists(id) == null)
            {
                throw new ProcessingException("invalid logger id "+id);
            }
            logger = LogManager.getLogger(id);
        }
        String level = requestParameters.get("level", "ERROR");
        String message = requestParameters.get("message", "");

        if("FATAL".equals(level))
        {
            logger.fatal(message);
        }
        if("ERROR".equals(level))
        {
            logger.error(message);
        }
        else if("WARN".equals(level))
        {
            logger.warn(message);
        }
        else if("INFO".equals(level))
        {
            logger.info(message);
        }
        else if("DEBUG".equals(level))
        {
            logger.debug(message);
        }
    }
}
