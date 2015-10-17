package com.fuda.dc.lhtz.test;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.fuda.dc.lhtz.web.dao.UserDao;

public class MyTest {

	public static void main(String[] args) {
		WebApplicationContext wac = new XmlWebApplicationContext();
		UserDao userDao = (UserDao) wac.getBean("UserDao");
		userDao.findByName("a");
	}

}
