package com.graudata.space.jmeter.samplers;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.graudata.space.jmeter.drivers.JmeterUtil;

public class GetDescendantsSampler extends CmisSamplerBase {

    private static final String DEPTH = "depth";
    private static final String BASE = "basename";

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = super.getDefaultParameters();
        args.addArgument(DEPTH, "10", "Depth of folder scannign");
        args.addArgument(BASE, "test", "Name of the BaseFolder");
        return args;
    }

    @Override
    public SampleResult doRunTest(JavaSamplerContext ctx) {
        String base = ctx.getParameter(BASE);
        int depth = Integer.parseInt(ctx.getParameter(DEPTH));
        
        SampleResult result = new SampleResult();
        result.sampleStart();
        CmisObject obj = JmeterUtil.findChildByName(session.getRootFolder(), base);
        long desc = JmeterUtil.countDescendants((Folder) obj, depth);
        result.sampleEnd();
        result.setResponseData("count" + desc, "UTF-8");
        result.setResponseOK();
        return result;
    }
}
