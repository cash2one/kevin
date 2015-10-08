package com.fuda.dc.lhtz.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fuda.dc.lhtz.web.service.IUserService;
import com.fuda.dc.lhtz.web.util.RequestUtil;
import com.fuda.dc.lhtz.web.util.SessionUtil;


/**
 * 登录Controller类
 * 
 * @author liukai
 * @date 2015.09.03
 */
@Controller
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IUserService userService;
	
    @RequestMapping("/test")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "test";
    }
	
    /**
     * 打开登陆页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }
	
    /**
     * 登录 
     */
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	private String doLogin(HttpServletRequest request, HttpServletResponse response) {
        String userName = RequestUtil.getString(request, "username", "");
        String password = RequestUtil.getString(request, "password", "");

        boolean validResult = userService.doLogin(userName, password);
        if (validResult) {
            SessionUtil.setUserName(request, userName);
            return "redirect:index.jsp";
        } else {
        	LOG.warn("");
            return "redirect:/login.jsp";
        }
    }
	
    /**
     * 权限验证失败
     */
    @RequestMapping(value = "/validfail", method = RequestMethod.GET)
    public String validfail(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("noPermission", true);
        return "/login";
    }
		
}
