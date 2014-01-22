package com.graudata.space.jmeter.samplers;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class RecursiveFileSampler extends CmisSamplerBase {
    private static final String FILESIZE = "filesize";
    private static final String FILE_NUM = "filenumber";
    private static final String FOLDER_NUM = "foldernumber";
    private static final String DEPTH = "depth";
    private static final String BASE = "basename";
    
    @Override
    public Arguments getDefaultParameters() {
        Arguments args = super.getDefaultParameters();
        args.addArgument(FILESIZE, "1","in MiB");
        args.addArgument(FILE_NUM,"10","Files per subfolder");
        args.addArgument(FOLDER_NUM,"1","Folders per subfolder");
        args.addArgument(DEPTH,"1","Depth of folder creation");
        args.addArgument(BASE,"test","Name of the BaseFolder");
        return args;
    }

    @Override
    public SampleResult doRunTest(JavaSamplerContext ctx) {
        // TODO Auto-generated method stub
        return null;
    }

}
