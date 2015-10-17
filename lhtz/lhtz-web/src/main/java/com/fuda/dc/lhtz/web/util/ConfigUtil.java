package com.fuda.dc.lhtz.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载config.properties配置文件的工具类
 * 
 * @author xiayanming
 */
public class ConfigUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);

    private static final String CONF_FILE = "config.properties";

    public static final String ZOOKEEPER_SERVER_KEY = "zookeeper.server";
    public static final String ZOOKEEPER_CLIENT_TIMEOUR_KEY = "zookeeper.clientTimeout";

    private static Properties properties = new Properties();

    private static ConfigUtil instance = new ConfigUtil();

    public static ConfigUtil getInstance() {
        return instance;
    }

    /**
     * 单例私有构造函数
     */
    private ConfigUtil() {
        init();
    }

    /**
     * 获取service.domain参数的值
     * 
     * @return  service.domain参数的值
     */
    public String getServiceDomain() {
        return System.getProperty("service.domain");
    }

    /**
     * 将config中的string值转为boolean的值
     * 
     * @param key   配置参数的key
     * @param defaultValue  boolean的默认值
     * @return  key对应的boolean值
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key, null);
        return value == null ? defaultValue : Boolean.valueOf(value).booleanValue();
    }

    /**
     * 将config中的string值转为int的值
     * 
     * @param key   配置参数的key
     * @param defaultValue  int的默认值
     * @return  key对应的int值
     */
    public int getInt(String key, int defaultValue) {
        String value = getString(key, null);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * 获取config中的string值
     * 
     * @param key   配置参数的key
     * @param defaultValue  默认值
     * @return  key对应的值
     */
    public String getString(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null) {
            value = properties.getProperty(key, defaultValue);
        }
        return value;
    }

    /**
     * 初始化
     */
    private void init() {
        LOG.info("==  ConfigUtil init start .  ");
        InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream(CONF_FILE);
        properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
            LOG.info("init message properties file failed�� ");
        }
    }

}
