package com.graudata.space;

import static org.junit.Assert.assertTrue;

import org.apache.chemistry.opencmis.client.api.Session;
import org.junit.Test;

import com.graudata.space.jmeter.drivers.JmeterUtil;

public class UtilTest {
    private static String HOST = System.getProperty("test.host");
    private static String USER = System.getProperty("test.user");
    private static String PASSWORD = System.getProperty("test.password");

    @SuppressWarnings("static-method")
    @Test
    public void testApp() throws Exception
    {

            Session session = JmeterUtil.getSession(HOST, USER, PASSWORD);
            assertTrue(session.getRootFolder().isRootFolder());

    }
}
