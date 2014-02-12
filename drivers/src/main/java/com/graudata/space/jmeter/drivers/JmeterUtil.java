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
package com.graudata.space.jmeter.drivers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.api.Tree;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

public class JmeterUtil {
    public static String randomName() {
        return UUID.randomUUID().toString();
    }

    public static Session getSession(String host, String user, String pw)
            throws Exception {
        // default factory implementation
        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameter = new HashMap<>();

        // user credentials
        parameter.put(SessionParameter.USER, user);
        parameter.put(SessionParameter.PASSWORD, pw);

        // connection settings
        parameter.put(SessionParameter.ATOMPUB_URL, host + "/cmis/atom");
        parameter.put(SessionParameter.BINDING_TYPE,
                BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.REPOSITORY_ID, "my");
        parameter.put(SessionParameter.COOKIES, "true");

        List<Repository> repositories = factory.getRepositories(parameter);
        // create session
        for (Repository rep : repositories) {
            if (rep.getName().equals("my"))
                return rep.createSession();
        }
        throw new Exception();

    }

    public static void createRecursiveFoldersAndFiles(Session session,
            String base, int depth, long folderNum, long fileNum, long fileSize) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        properties.put(PropertyIds.NAME, base);

        Folder root = session.getRootFolder().createFolder(properties);
        createRecursiveFoldersAndFiles(root, depth, folderNum, fileNum,
                fileSize);
    }

    private static void createRecursiveFoldersAndFiles(Folder base, int depth,
            long folderNum, long fileNum, long fileSize) {
        for (int i = 0; i < fileNum; i++) {
            createDoc(base, fileSize);
        }
        if (depth > 0) {
            for (int i = 0; i < fileNum; i++) {
                Map<String, Object> properties = new HashMap<>();
                properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
                properties.put(PropertyIds.NAME, randomName());
                Folder subfolder = base.createFolder(properties);
                createRecursiveFoldersAndFiles(subfolder, depth - 1, folderNum,
                        fileNum, fileSize);
            }
        }

    }

    private static void createDoc(Folder base, long fileSize) {
        String name = randomName();
        Map<String, Object> properties = new HashMap<>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, name);

        // content
        Random random = new Random();
        byte[] content = new byte[(int) fileSize];
        random.nextBytes(content);
        InputStream stream = new ByteArrayInputStream(content);
        ContentStream contentStream = new ContentStreamImpl(name,
                BigInteger.valueOf(content.length), "text/plain", stream);

        // create a major version
        base.createDocument(properties, contentStream, VersioningState.MAJOR);
    }

    public static void deleteFolderRecursive(Session session, String base) {
        CmisObject object;
        object = findChildByName(session.getRootFolder(), base);
        if (null != object && object.getBaseTypeId() == BaseTypeId.CMIS_FOLDER) {
            Folder folder = (Folder) object;
            folder.deleteTree(true, UnfileObject.DELETE, true);
        }

    }

    public static CmisObject findChildByName(Folder folder, String base) {
        for (CmisObject child : folder.getChildren()) {
            if (child.getName().equals(base)) {
                return child;
            }
        }
        return null;
    }
    
    public static long countDescendants(Folder folder, int depth) {
        long count = 0;
        for(Tree<FileableCmisObject> tree : folder.getDescendants(depth)){
            count +=1;
            count += countDescendants(tree);
        }
        return count;
    }
    
    private static long countDescendants(Tree<FileableCmisObject> tree) {
        long count = 0;
        for(Tree<FileableCmisObject> subtree : tree.getChildren()){
            count += 1;
            count += countDescendants(subtree);
        }
        return count;
    }
}
