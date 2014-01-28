package com.graudata.space.jmeter.samplers;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.graudata.space.jmeter.drivers.JmeterUtil;

public class DeleteFolderSampler extends CmisSamplerBase {

    private static final String BASE = "basename";

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = super.getDefaultParameters();
        args.addArgument(BASE, "test", "Name of the BaseFolder");
        return args;
    }

    @Override
    public SampleResult doRunTest(JavaSamplerContext ctx) {
        String base = ctx.getParameter(BASE);
        
        SampleResult result = new SampleResult();
        result.sampleStart();
        Folder obj = (Folder) JmeterUtil.findChildByName(session.getRootFolder(), base);
        obj.deleteTree(true, UnfileObject.DELETE, true);
        result.sampleEnd();
        result.setResponseOK();
        return result;
    }
}
