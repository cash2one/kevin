package com.fuda.dc.lhtz.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件reader
 * 
 * @author kevin
 * @date 2015.09.10
 */
public class PropertiesReader {
	private static final Logger LOG = LoggerFactory.getLogger(PropertiesReader.class);
    private static Map<String, Properties> filePropMapping = new WeakHashMap<String, Properties>();

    public static String getValue(String fileName, String key) {
    	Properties properties = null;
    	if (filePropMapping.containsKey(fileName)) {
    		properties = filePropMapping.get(fileName);
    	} else {
            if (!fileName.endsWith(".properties")) {
                fileName = fileName + ".properties";
            }
            
    		properties = getProperties(fileName);
    		if (properties != null) {
    			filePropMapping.put(fileName, properties);
    		}
		}
    	
        String value = properties.getProperty(key);
        return value.trim();
    }

    public static int getIntValue(String fileName, String key, int defaultValue) throws MissingResourceException {
        Properties properties = null;
    	if (filePropMapping.containsKey(fileName)) {
    		properties = filePropMapping.get(fileName);
    	} else {
            if (!fileName.endsWith(".properties")) {
                fileName = fileName + ".properties";
            }
            
    		properties = getProperties(fileName);
    		if (properties != null) {
    			filePropMapping.put(fileName, properties);
    		}
		}
    	
        String value = properties.getProperty(key);
        try {
        	return Integer.valueOf(value.trim());
        } catch(NumberFormatException e) {
        	return defaultValue;
        }
    }
    
    private static Properties getProperties(String fileName) {
    	InputStream is = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName);
    	if (is == null) {
    		LOG.error("{}.properties file not exists.", fileName);
    		return null;
    	}

    	Properties properties = new Properties();
    	try {
    		properties.load(is);
    		filePropMapping.put(fileName, properties);
    	} catch (IOException e1) {
    		throw new RuntimeException("load properties file error", e1);
    	} finally {
    		try {
    			if (is != null) {
    				is.close();
    			}
    		} catch (IOException e) {
    			throw new RuntimeException("load properties file error", e);
    		}
    	}

    	return properties;
    }

}