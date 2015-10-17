package com.fuda.dc.lhtz.web.util;

/**
 * 常量类
 * 
 * @author xiayanming
 *
 */
public class Constants {

    /**
     * zookeeper根路径，以此区分线上环境和测试环境
     */

    public static final String ROOTPATH = "/";

    // 线上正式环境的根路径
    public static final String DEFAULT_ROOTPATH = "/live";
    public static final String MONITOR_PATH = "/monitors";

    // zookeeper服务默认地址
    public static final String DEDAULT_CONNECT_STRING = "127.0.0.1:2181";

}
