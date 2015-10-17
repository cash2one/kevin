package com.fuda.dc.lhtz.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆用户session管理
 * 
 * @author xiayanming
 *
 */
public class SessionUtil {

    // session key
    private static final String SESSION_KEY = "username";

    /**
    * 将当前登陆的用户放到session中
    * 
    * @param request	HttpServletRequest
    * @param userName	当前登陆用户名
    */
    public static void setUserName(HttpServletRequest request, String userName) {
        request.getSession().setAttribute(SESSION_KEY, userName);
    }

    /**
    * 获取session中当前登陆的用户
    * 
    * @param request	HttpServletRequest
    * @return	当前登陆用户名
    */
    public static String getUserName(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(SESSION_KEY);
    }

}
