package com.fuda.dc.lhtz.crawler.util;

import java.util.Map;
import java.util.MissingResourceException;
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
    private static Map<String, ResourceBundle> filePropMapping = new WeakHashMap<String, ResourceBundle>();

    public static String getValue(String fileName, String key) throws MissingResourceException {
        if (!fileName.endsWith(".properties")) {
            fileName = fileName + ".properties";
        }
        
    	ResourceBundle res = null;
    	if (filePropMapping.containsKey(fileName)) {
    		res = filePropMapping.get(fileName);
    	} else {
    		res = ResourceBundle.getBundle(fileName);
		}
    	
        String value = res.getString(key);
        return value.trim();
    }
    
    public static int getIntValue(String fileName, String key, int defaultValue) throws MissingResourceException {
        if (!fileName.endsWith(".properties")) {
            fileName = fileName + ".properties";
        }
    	
    	ResourceBundle res = null;
    	if (filePropMapping.containsKey(fileName)) {
    		res = filePropMapping.get(fileName);
    	} else {
    		res = ResourceBundle.getBundle(fileName);
		}
    	
        String value = res.getString(key);
        try {
        	return Integer.valueOf(value);
        } catch(NumberFormatException e) {
        	return defaultValue;
        }
    }

}