// 
// Copyright (c) 2003-2005, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
// All rights reserved. 
//   
// Redistribution and use in source and binary forms, with or without modification,  
// are permitted provided that the following conditions are met: 
//   
// * Redistributions of source code must retain the above copyright notice,  
// this list of conditions and the following disclaimer. 
// * Redistributions in binary form must reproduce the above copyright notice,  
// this list of conditions and the following disclaimer in the documentation  
// and/or other materials provided with the distribution. 
// * Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.  
// nor the names of its contributors may be used to endorse or promote products  
// derived from this software without specific prior written permission. 
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

package org.objectledge.modules.actions.im;

import java.security.Principal;

import org.objectledge.authentication.UserManager;
import org.objectledge.context.Context;
import org.objectledge.im.InstantMessaging;
import org.objectledge.im.InstantMessagingContact;
import org.objectledge.parameters.Parameters;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.parameters.directory.DirectoryParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.pipeline.Valve;

/**
 * Removes an IM contact from user's personal data.
 * 
 * @author <a href="rafal@caltha.pl">Rafał Krzewski</a>
 * @version $Id: RemoveContact.java,v 1.1 2005-08-01 09:45:25 rafal Exp $
 */
public class RemoveContact
    implements Valve
{
    private final UserManager userManager;

    private final InstantMessaging instantMessaging;

    /**
     * Creates a new RemoveContact action instance.
     * 
     * @param messaging the instant messaging component.
     * @param manager the user manager component.
     */
    public RemoveContact(InstantMessaging messaging, UserManager manager)
    {
        instantMessaging = messaging;
        userManager = manager;
    }

    /**
     * {@inheritDoc}
     */
    public void process(Context context)
        throws ProcessingException
    {
        RequestParameters parameters = RequestParameters.getRequestParameters(context);
        String user = parameters.get("user");
        InstantMessagingContact contact = InstantMessagingContact.fromString(parameters
            .get("contact"), instantMessaging);
        Parameters personalData;
        try
        {
            Principal principal = userManager.getUserByLogin(user);
            personalData = new DirectoryParameters(userManager.getPersonalData(principal));
        }
        catch(Exception e)
        {
            throw new ProcessingException("failed to access user's personal data", e);
        }
        try
        {
            instantMessaging.removeContact(personalData, contact);
        }
        catch(Exception e)
        {
            throw new ProcessingException("failed to remove contact", e);
        }
    }
}
