package com.graudata.space.jmeter.samplers;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.graudata.space.jmeter.drivers.JmeterUtil;

public class RecursiveFileSampler extends CmisSamplerBase {
    private static final String FILESIZE = "filesize";
    private static final String FILE_NUM = "filenumber";
    private static final String FOLDER_NUM = "foldernumber";
    private static final String DEPTH = "depth";
    private static final String BASE = "basename";

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = super.getDefaultParameters();
        args.addArgument(FILESIZE, "1", "in KiB");
        args.addArgument(FILE_NUM, "10", "Files per subfolder");
        args.addArgument(FOLDER_NUM, "1", "Folders per subfolder");
        args.addArgument(DEPTH, "1", "Depth of folder creation");
        args.addArgument(BASE, "test", "Name of the BaseFolder");
        return args;
    }

    @Override
    public SampleResult doRunTest(JavaSamplerContext ctx) {
        String base = ctx.getParameter(BASE);
        int depth = Integer.parseInt(ctx.getParameter(DEPTH));
        long folderNum = Long.parseLong(ctx.getParameter(FOLDER_NUM));
        long fileNum = Long.parseLong(ctx.getParameter(FILE_NUM));
        long fileSize = Long.parseLong(ctx.getParameter(FILESIZE))*1024;
        
        SampleResult result = new SampleResult();
        result.sampleStart();
        JmeterUtil.createRecursiveFoldersAndFiles(session, base, depth, folderNum, fileNum, fileSize);
        result.sampleEnd();
        result.setResponseOK();
        return result;
    }
}
