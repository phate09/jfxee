package com.zenjava.jfxcontact.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JfxContactConfig
{
    private static final Logger log = LoggerFactory.getLogger(JfxContactConfig.class);

    private String serverUrl;
    private Integer serverTimeout;

    public JfxContactConfig(String propertiesFile)
    {
        try
        {
            loadProperties(propertiesFile);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("Unable to load properties from: " + propertiesFile, e);
        }
    }

    public String getServerUrl()
    {
        return serverUrl;
    }

    public Integer getServerTimeout()
    {
        return serverTimeout;
    }

    protected void loadProperties(String fileName) throws IOException
    {
        Properties props = new Properties();
        InputStream stream = getClass().getResourceAsStream(fileName);
        if (stream != null)
        {
            props.load(stream);
            serverUrl = (String) props.get("server.host");
            String value = (String) props.get("server.timeout");
            serverTimeout = value != null ? Integer.parseInt(value) : null;
            log.info("Using server at '{}' (timeout={})", new Object[]{serverUrl, serverTimeout});
        }
        else
        {
            log.warn("Unable to find properties: {}", fileName);
        }
    }
}
