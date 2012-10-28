package org.objectledge.cometd;

import org.cometd.bayeux.server.BayeuxServer;

/**
 * One time processing valve of Bayeux Server.
 * Necessary configuration should be done in valves.
 * Services should extend BayeuxService class, not this class
 *
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/28/12
 *        Time: 11:17 PM
 */
public interface BayeuxServerValve
{
    void process(BayeuxServer bayeuxServer);
}
