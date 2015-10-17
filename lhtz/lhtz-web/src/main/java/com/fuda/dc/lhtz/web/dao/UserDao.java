package com.fuda.dc.lhtz.web.dao;

import java.util.List;

import com.fuda.dc.lhtz.web.vo.User;

public interface UserDao {
	public List<User> findByName(String username);
}
