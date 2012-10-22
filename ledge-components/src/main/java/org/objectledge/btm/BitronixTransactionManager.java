package org.objectledge.btm;


import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.jcontainer.dna.ConfigurationException;
import org.picocontainer.Startable;

import bitronix.tm.TransactionManagerServices;


public class BitronixTransactionManager
    implements Startable
{
    private bitronix.tm.BitronixTransactionManager btm;

    private final TransactionTracingConfig tracing = new TransactionTracingConfig();

    private final Map<String, DataSource> dataSources = new HashMap<>();

    private final Map<String, ConnectionFactory> connectionFactories = new HashMap<>();

    public BitronixTransactionManager(org.jcontainer.dna.Configuration config)
        throws ConfigurationException
    {
        ConfigurationHandler.configure(dataSources, connectionFactories, tracing, config);
        btm = TransactionManagerServices.getTransactionManager();
    }

    TransactionTracingConfig getTracingConfiguration()
    {
        return tracing;
    }

    UserTransaction getUserTransaction()
    {
        return btm;
    }

    TransactionManager getTransactionManager()
    {
        return btm;
    }

    DataSource getDataSource(String uniqueName)
    {
        return dataSources.get(uniqueName);
    }

    ConnectionFactory getConnectionFactory(String uniqueName)
    {
        return connectionFactories.get(uniqueName);
    }

    @Override
    public void start()
    {
    }

    @Override
    public void stop()
    {
        btm.shutdown();
    }
}