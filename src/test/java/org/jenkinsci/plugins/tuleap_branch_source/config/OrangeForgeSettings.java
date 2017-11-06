package org.jenkinsci.plugins.tuleap_branch_source.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl;

/**
 * Holds config info to acces OrangeForge. To populate itself, it looks for a
 * .jenkinsfaasbranchsource/orangeForge.properties in user home dir as a config file.
 */
public class OrangeForgeSettings {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrangeForgeSettings.class);
    private String username, password;
    private String apiBaseUrl, gitBaseUrl;
    private String FaaSProjectId;

    /*public OrangeForgeSettings() throws IOException {
        this(fromHomeDir());
    }

    public OrangeForgeSettings(String propPath) throws IOException {
        this(fromPath(propPath));
    }*/

    public OrangeForgeSettings()throws IOException  {
        Properties props = new Properties();
        FileInputStream thePropIn = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File propFile = new File(classLoader.getResource("orangeForge.properties").getFile());
            //File propFile = new File(homeDir, "orangeForge.properties");
            String propPath =  propFile.getPath();
            LOGGER.info("Fetch config in orangeForge.properties from path : {}", propPath);
            thePropIn = new FileInputStream(propPath);
            props.load(thePropIn);
        } finally {
            IOUtils.closeQuietly(thePropIn);
        }
        this.username = props.getProperty("orangeforge.username");
        this.password = props.getProperty("orangeforge.password");
        this.apiBaseUrl = props.getProperty("orangeforge.api-base-url");
        this.FaaSProjectId = props.getProperty("orangeforge.project-id");
        this.gitBaseUrl = props.getProperty("orangeforge.git-base-url");
    }

    public Optional<StandardCredentials> credentials(){
        return Optional.of(new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, "orangeForge", "FaaS-viewer",
                                                               getUsername(), getPassword()));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getFaaSProjectId() {
        return FaaSProjectId;
    }

    public void setFaaSProjectId(String faaSProjectId) {
        FaaSProjectId = faaSProjectId;
    }

    public String getGitBaseUrl() {
        return gitBaseUrl;
    }

    public void setGitBaseUrl(String gitBaseUrl) {
        this.gitBaseUrl = gitBaseUrl;
    }

    private String fromHomeDir() {
        LOGGER.info("Fetch config in .jenkinsfaasbranchsource/orangeForge.properties from user.home property system");
        ClassLoader classLoader = getClass().getClassLoader();
        File propFile = new File(classLoader.getResource("orangeForge.properties").getFile());
        //File propFile = new File(homeDir, "orangeForge.properties");
        return propFile.getPath();
    }

    private static Properties fromPath(String propPath) throws IOException {
        LOGGER.info("Fetch config in orangeForge.properties from path : {}", propPath);
        Properties result = new Properties();
        FileInputStream thePropIn = null;
        try {
            thePropIn = new FileInputStream(propPath);
            result.load(thePropIn);
        } finally {
            IOUtils.closeQuietly(thePropIn);
        }
        return result;
    }
}
