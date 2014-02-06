/**
 *
 * Copyright (c) 2014 GRAU DATA AG
 *
 * This file is part of the OpenDataSpace CMIS Benchmark.
 *
 * The OpenDataSpace CMIS Benchmark is free software: you can redistribute
 * it and/or modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, version 3 of the License
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
