package com.graudata.space.jmeter.drivers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

public class JmeterUtil {
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

        List<Repository> repositories = factory.getRepositories(parameter);
        // create session
        for (Repository rep : repositories) {
            if (rep.getName().equals("my"))
                return rep.createSession();
        }
        throw new Exception();

    }
}
