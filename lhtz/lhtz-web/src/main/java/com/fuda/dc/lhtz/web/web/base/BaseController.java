package com.fuda.dc.lhtz.web.web.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseController, controller的基类
 * 
 * @author liukai
 * 
 */
public class BaseController {

    protected static Logger log = LoggerFactory.getLogger(BaseController.class);

    /**
     * redirect跳转方法
     * 
     *@param response   HttpServletResponse
     *@param suc   用户名 
     */
    public void redirect(HttpServletResponse response, String suc) {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        log.debug("===========BaseController===========redirect==============");

        StringBuffer sb = new StringBuffer();
        PrintWriter writer = null;
        try {
            sb.append(suc);
            writer = response.getWriter();
            writer.write(sb.toString());
            response.flushBuffer();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
