package com.graudata.space.jmeter.samplers;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.graudata.space.jmeter.drivers.JmeterUtil;

public abstract class CmisSamplerBase extends AbstractJavaSamplerClient {

    protected Session session;

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = new Arguments();
        args.addArgument("host", "https://demo.dataspace.cc/");
        args.addArgument("user", "user");
        args.addArgument("pw", "****");
        return args;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext ctx) {

        if (session == null) {
            SampleResult result = new SampleResult();
            result.setResponseCode("Not Authorized");
            result.setResponseData("Unable to get Session", "UTF-8");
            return result;
        }

        return doRunTest(ctx);

    }

    public abstract SampleResult doRunTest(JavaSamplerContext ctx);

    @Override
    public void setupTest(JavaSamplerContext ctx) {
        try {
            session = JmeterUtil.getSession(ctx.getParameter("host"),
                    ctx.getParameter("user"), ctx.getParameter("pw"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
