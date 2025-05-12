package com.hexaware.util;

import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    /**
     * Loads the specified properties file from the classpath.
     * 
     * @param fileName the properties file name (e.g. db.properties)
     * @return Properties object loaded from the file
     * @throws Exception if file not found or IO error occurs
     */
    public static Properties loadProperties(String fileName) throws Exception {
        Properties props = new Properties();
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new Exception("Properties file '" + fileName + "' not found in classpath");
            }
            props.load(input);
        }
        return props;
    }
}
