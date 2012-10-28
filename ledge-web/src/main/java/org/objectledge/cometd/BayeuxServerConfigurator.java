package org.objectledge.cometd;

import org.cometd.bayeux.server.BayeuxServer;

/**
 * Triggers configuration of Bayeux server by providing it to processors.
 * Some of the work is done already by pico container during initialisation
 * of sequence of parameters.
 * Every BayeuxService is already done so that channels and services are created already.
 *
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/28/12
 *        Time: 10:49 PM
 */
public class BayeuxServerConfigurator
{
    public BayeuxServerConfigurator(BayeuxServer bayeuxServer, BayeuxServerValve [] valves, BayeuxService [] services)
    {
        for(BayeuxServerValve valve : valves)
        {
            valve.process(bayeuxServer);
        }
    }
}
