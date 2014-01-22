package com.graudata.space.jmeter.samplers;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.graudata.space.jmeter.drivers.JmeterUtil;

/**
 * Hello world!
 *
 */
public class JavaSamplerTest extends AbstractJavaSamplerClient
{

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = new Arguments();
        args.addArgument("host", "https://bunker.deutsche-wolke.de/");
        args.addArgument("user", "user");
        args.addArgument("pw", "****");
        return args;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext ctx) {
        SampleResult result = new SampleResult();
        result.sampleStart();
        
        try {
            JmeterUtil.getSession(ctx.getParameter("host"), ctx.getParameter("user"), ctx.getParameter("pw"));
        } catch (Exception e) {
            result.sampleEnd();
            result.setResponseCode("Not Authorized");
            result.setResponseData("Unable to get Session", "UTF-8");
            return result;
        }

        result.sampleEnd();
        result.setSampleLabel("TestSample");
        result.setResponseOK();
        
        return result;
    }

}
