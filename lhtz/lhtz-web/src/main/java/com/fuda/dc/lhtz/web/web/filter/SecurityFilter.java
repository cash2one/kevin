package com.fuda.dc.lhtz.web.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.web.util.SessionUtil;

/**
 * 过滤器
 * 
 * @author liukai
 * 
 */
public class SecurityFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getContextPath();
        String loginUserName = SessionUtil.getUserName(request);
        LOG.info("login user name = {}, path = {}", loginUserName, path);

        if (StringUtils.isEmpty(loginUserName)) {
            ((HttpServletResponse) response).sendRedirect(path + "/login.htm");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
