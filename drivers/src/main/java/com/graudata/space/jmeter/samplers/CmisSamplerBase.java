package com.graudata.space.jmeter.samplers;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

        try {
            session = JmeterUtil.getSession(ctx.getParameter("host"),
                    ctx.getParameter("user"), ctx.getParameter("pw"));
            return doRunTest(ctx);
        } catch (Throwable e) {
            SampleResult result = new SampleResult();
            result.setResponseCode(e.getMessage());
            String st = ExceptionUtils.getStackTrace(e);
            result.setResponseData(e.getMessage() + "\n" + st, "UTF-8");
            return result;
        }


    }

    public abstract SampleResult doRunTest(JavaSamplerContext ctx);

}
