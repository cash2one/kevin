package com.fuda.dc.lhtz.web.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fuda.dc.lhtz.web.service.IUserService;
import com.fuda.dc.lhtz.web.util.SessionUtil;

/**
 * 获取配置中心的树形菜单
 * 
 * @author liukai
 */
public class ConfigTreeServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigTreeServlet.class);
    private static final long serialVersionUID = -8385928024851037494L;

    @Autowired
    private IUserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String ppath = request.getParameter("path");
        String userName = SessionUtil.getUserName(request);
        LOG.info("[ConfigTreeServlet] userName={}, path = {}", userName, ppath);
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(ppath)) {
            LOG.error("[ConfigTreeServlet] username:{} or path:{} is null", userName, ppath);
            return;
        }

        String accessList = userService.getAccessList(userName);
        if (ppath.indexOf(accessList) != -1) {
            PrintWriter p = response.getWriter();
            p.close();
        } else {
            LOG.error("[ConfigTreeServlet] username:{} no {} permissions", userName, ppath);
        }
    }
}
