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
package com.graudata.space;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import com.graudata.space.jmeter.drivers.JmeterUtil;

@SuppressWarnings("static-method")
public class UtilTest {
    private static String HOST = System.getProperty("test.host");
    private static String USER = System.getProperty("test.user");
    private static String PASSWORD = System.getProperty("test.password");

    private static String TESTFOLDER = "testfolder";

    @After
    public void tearDown() throws Exception {
        Session session = JmeterUtil.getSession(HOST, USER, PASSWORD);
        JmeterUtil.deleteFolderRecursive(session, TESTFOLDER);
    }

    @Test
    public void testGetSession() throws Exception {
        Session session = JmeterUtil.getSession(HOST, USER, PASSWORD);
        assertTrue(session.getRootFolder().isRootFolder());
    }
    
    @Test
    @Ignore
    public void testCreateFolderTreeBigFile() throws Exception {
        long filesize = 1024*1024*1024;
        int depth = 0;
        long filenum = 1;
        long foldernum = 0;
        Session session = JmeterUtil.getSession(HOST, USER, PASSWORD);
        JmeterUtil.createRecursiveFoldersAndFiles(session, TESTFOLDER, depth,
                foldernum, filenum, filesize);

        assertStructureCreated(filesize, depth, filenum, foldernum, session);
    }

    @Test
    public void testCreateFolderTree() throws Exception {
        long filesize = 1;
        int depth = 2;
        long filenum = 2;
        long foldernum = 2;
        Session session = JmeterUtil.getSession(HOST, USER, PASSWORD);
        JmeterUtil.createRecursiveFoldersAndFiles(session, TESTFOLDER, depth,
                foldernum, filenum, filesize);

        assertStructureCreated(filesize, depth, filenum, foldernum, session);
    }

    private void assertStructureCreated(long filesize, int depth, long filenum,
            long foldernum, Session session) {
        CmisObject object = JmeterUtil.findChildByName(session.getRootFolder(),
                TESTFOLDER);
        assertNotNull("Folder not created", object);
        assertEquals(BaseTypeId.CMIS_FOLDER, object.getBaseTypeId());
        Folder folder = (Folder) object;
        assertEquals(filenum + foldernum, folder.getChildren()
                .getTotalNumItems());

        for (CmisObject obj : folder.getChildren()) {
            if (BaseTypeId.CMIS_DOCUMENT == obj.getBaseTypeId()) {
                assertEquals(filesize,
                        ((Document) obj).getContentStreamLength());
                break;
            }
        }
        long filesInRoot = filenum;
        long subfolders =  foldernum*(long)(Math.pow(foldernum, depth)-1)/(foldernum-1);
        long desc = JmeterUtil.countDescendants(folder, depth+1);
        assertEquals(filesInRoot + subfolders * (1 + filenum), desc);
    }



}
