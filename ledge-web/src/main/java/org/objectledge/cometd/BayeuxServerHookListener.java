package org.objectledge.cometd;

import org.cometd.bayeux.server.BayeuxServer;
import org.objectledge.web.LedgeServletContextListener;
import org.picocontainer.MutablePicoContainer;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * Listens for initialisation of Cometd servlet and waits for BayeuxServer.
 * Puts it into container which is instantiated by definition because
 * servlets are not yet initialised when container is created.
 * Pulls BayeuxServerConfigurator which configures bayeux server.
 * This is kind of delayed start mechanics.
 *
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/28/12
 *        Time: 9:57 PM
 */
public class BayeuxServerHookListener implements ServletContextAttributeListener
{

    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent)
    {
        if (BayeuxServer.ATTRIBUTE.equals(servletContextAttributeEvent.getName()))
        {
            BayeuxServer bayeuxServer = (BayeuxServer) servletContextAttributeEvent.getValue();
            MutablePicoContainer mutablePicoContainer = (MutablePicoContainer) servletContextAttributeEvent.getServletContext().getAttribute(LedgeServletContextListener.CONTAINER_CONTEXT_KEY);
            mutablePicoContainer.registerComponentInstance(bayeuxServer);
            mutablePicoContainer.getComponentInstanceOfType(BayeuxServerConfigurator.class);  // start configuration
        }
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent)
    {

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent)
    {

    }
}
