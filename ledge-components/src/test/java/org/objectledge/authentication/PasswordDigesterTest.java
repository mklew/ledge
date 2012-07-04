package org.objectledge.authentication;

import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

import org.objectledge.ComponentInitializationError;

public class PasswordDigesterTest
    extends TestCase
{

    public void testAlgorigthms()
        throws NoSuchAlgorithmException
    {
        for(String scheme : PasswordDigester.SUPPORTED_SCHEMES)
        {
            PasswordDigester pd = new PasswordDigester(scheme);
            String password = "PASSWORD";
            String encoded = pd.generateDigest(password);
            // System.out.println(encoded);
            assertTrue(pd.validateDigest(password, encoded));
        }
    }

    public void testInvalidScheme()
    {
        try
        {
            @SuppressWarnings("unused")
            PasswordDigester pd = new PasswordDigester("SHA224");
            fail("should throw exception");
        }
        catch(ComponentInitializationError e)
        {
            // e.printStackTrace();
            assertTrue(e.getCause() instanceof NoSuchAlgorithmException);
        }
    }

    public void testInvalidFormat()
    {
        try
        {
            PasswordDigester pd = new PasswordDigester("SHA");
            pd.validateDigest("PASSWORD", "ESu3kTBHkd3PaS4p/VzxSbNf6jc="); // missing scheme part
            fail("should throw exception");
        }
        catch(Exception e)
        {
            // e.printStackTrace();
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    public void testMissingSalt()
    {
        try
        {
            PasswordDigester pd = new PasswordDigester("SHA");
            pd.validateDigest("PASSWORD", "{SSHA256}XC57dmqpSl6RTKT4mc9XKFA5TWRPKzawjEzQPBchK1s=");
            fail("should throw exception");
        }
        catch(Exception e)
        {
            // e.printStackTrace();
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    public void testTruncated()
    {
        try
        {
            PasswordDigester pd = new PasswordDigester("SHA");
            pd.validateDigest("PASSWORD", "{SHA256}C+ZK6J3dJOIlQ03pXVAX");
            fail("should throw exception");
        }
        catch(Exception e)
        {
            // e.printStackTrace();
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
}
