package com.fuda.dc.lhtz.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fuda.dc.lhtz.web.dao.UserDao;
import com.fuda.dc.lhtz.web.vo.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findByName(String username) {
		return userDao.findByName(username);
	}
	
}
