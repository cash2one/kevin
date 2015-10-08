/**
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Baidu company (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.fuda.dc.lhtz.crawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 需要在JAVA_OPTS加入-D参数
 * 例如
 * -Dserver.id=MonitorServer
 * 
 * @author xiayanming
 *
 */
public class SystemConfigUtil {

    public static final Logger LOG = LoggerFactory.getLogger(SystemConfigUtil.class);

    /**
     * 获取系统启动参数
     * @param   key  jvm -D的参数key
     * @return jvm -D的参数key对应的value
     */
    public static String getVmArguments(String key) {
        return System.getProperty(key);
    }

}
