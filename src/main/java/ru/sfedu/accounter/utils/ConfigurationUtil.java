package ru.sfedu.accounter.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration utility. Allows to get configuration properties from the
 * default configuration file
 *
 * @author Boris Jmailov
 */
public class ConfigurationUtil {

    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";
    private static final Properties configuration = new Properties();
    private static final Logger log = LogManager.getLogger(ConfigurationUtil.class);
    /**
     * Hides default constructor
     */
    public ConfigurationUtil() {
    }

    private static Properties getConfiguration() throws IOException {
        if(configuration.isEmpty()){
            loadConfiguration();
        } else {
            if (System.getProperty("path") != null) {
                String path = System.getProperty("path");
                File nf = new File(path);
                if (nf.exists()) {
                    InputStream in = new FileInputStream(nf);
                    configuration.load(in);
                    in.close();
                } else {
                    log.error("Your .properties file not found. Default loaded.");
                    loadConfiguration();
                }
            }
        }
        return configuration;
    }

    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     * @throws IOException In case of the configuration file read failure
     */
    private static void loadConfiguration() throws IOException{
        File nf = new File(DEFAULT_CONFIG_PATH);
        // DEFAULT_CONFIG_PATH.getClass().getResourceAsStream(DEFAULT_CONFIG_PATH);
        try (InputStream in = new FileInputStream(nf)) {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    /**
     * Gets configuration entry value
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public static String getConfigurationEntry(String key) throws IOException{
        return getConfiguration().getProperty(key);
    }
    
}
