package org.objectledge.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.pipeline.Valve;
import org.objectledge.templating.TemplatingContext;

/**
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/28/12
 *        Time: 4:11 PM
 */
public class ShiroSubjectValve implements Valve
{
    @Override
    public void process(Context context) throws ProcessingException
    {
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated())
        {
            throw new AuthenticationException("You must be logged in");
        }
        TemplatingContext templatingContext = (TemplatingContext) context.getAttribute(TemplatingContext.class);

        templatingContext.put("currentUser", currentUser);
        templatingContext.put("username", currentUser.getPrincipal());
    }
}
