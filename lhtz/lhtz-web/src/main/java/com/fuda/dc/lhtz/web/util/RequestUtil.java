package com.fuda.dc.lhtz.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析requet请求中的参数
 * 
 * @author liukai
 * 
 */
public class RequestUtil {

    /**
     * 私有化构造函数，防止实例化
     */
    private RequestUtil() {

    }

    /**
     * 从HttpServletRequest中解析short类型的数据
     * 
     * @param request  HttpServletRequest
     * @param key	Request的key
     * @param defaultValue	默认值
     * @return	Request的key对应的value
     */
    public static short getShort(HttpServletRequest request, String key, short defaultValue) {
        String str = request.getParameter(key);
        return BaseParamUtil.getShort(str, defaultValue);
    }

    /**
     * 从HttpServletRequest中解析int类型的数据
     * 
     * @param request	HttpServletRequest
     * @param key	Request的key
     * @param defaultValue	默认值
     * @return	Request的key对应的value
     */
    public static int getInt(HttpServletRequest request, String key, int defaultValue) {
        String str = request.getParameter(key);
        return BaseParamUtil.getInt(str, defaultValue);
    }

    /**
     * 从HttpServletRequest中解析long类型的数据
     * 
     * @param request	HttpServletRequest
     * @param key	Request的key
     * @param defaultValue	默认值
     * @return	Request的key对应的value
     */
    public static long getLong(HttpServletRequest request, String key, long defaultValue) {
        String str = request.getParameter(key);
        return BaseParamUtil.getLong(str, defaultValue);
    }

    /**
     * 从HttpServletRequest中解析String类型的数据
     * 
     * @param request	HttpServletRequest
     * @param key	Request的key
     * @param defaultValue	默认值
     * @return	Request的key对应的value
     */
    public static String getString(HttpServletRequest request, String key, String defaultValue) {
        String str = request.getParameter(key);
        return BaseParamUtil.getString(str, defaultValue);
    }

}