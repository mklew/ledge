package org.objectledge.cometd;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.server.AbstractService;

/**
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/28/12
 *        Time: 11:10 PM
 */
public abstract class BayeuxService extends AbstractService
{
    public BayeuxService(BayeuxServer bayeux, String name)
    {
        super(bayeux, name);
    }

    public BayeuxService(BayeuxServer bayeux, String name, int maxThreads)
    {
        super(bayeux, name, maxThreads);
    }
}
