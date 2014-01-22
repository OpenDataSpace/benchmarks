package com.graudata.space;

import static org.junit.Assert.assertTrue;

import org.apache.chemistry.opencmis.client.api.Session;
import org.junit.Ignore;
import org.junit.Test;

import com.graudata.space.jmeter.drivers.JmeterUtil;

/**
 * Unit test for simple App.
 */
public class UtilTest {

    @SuppressWarnings("static-method")
    @Test
    @Ignore
    public void testApp() throws Exception
    {

            Session session = JmeterUtil.getSession("https://devel.dataspace.cc/", "jenkinsjmeter", "Jmeter123");
            assertTrue(session.getRootFolder().isRootFolder());

    }
}
