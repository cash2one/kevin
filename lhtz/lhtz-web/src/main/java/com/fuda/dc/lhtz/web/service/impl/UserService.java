package com.fuda.dc.lhtz.web.service.impl;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Service;

import com.fuda.dc.lhtz.web.dao.UserDao;
import com.fuda.dc.lhtz.web.redis.RedisClient;
import com.fuda.dc.lhtz.web.redis.dao.RedisDao;
import com.fuda.dc.lhtz.web.service.IUserService;
import com.fuda.dc.lhtz.web.util.ConfigUtil;
import com.fuda.dc.lhtz.web.vo.User;

/**
 * 用户管理
 * 
 * @author liukai
 */
@Service
public class UserService implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RedisDao redisDao;

    /**
     * 验证用户登录
     * 
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public boolean doLogin(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            LOG.info("do login, valid username and password failed, [username={}, password={}]", userName, password);
            return false;
        }
        
/*    	RedisClient.getInstance().set("name", "minmin");
    	String value = RedisClient.getInstance().get("name");*/
        redisDao.set("a", "b");
        String b = redisDao.get("a");
        
        List<User> users = userDao.findByName(userName);
        if (users == null || users.size() == 0) {
        	return false;
        } 
        
        return true;
    }

    /**
     * 该用户所能访问的资源列表(zookeeper 资源的列表)
     * @param userName  用户名
     * @return
     */
    public String getAccessList(String userName) {
        String result = null;
        if (StringUtils.isEmpty(userName)) {
            LOG.info("getAccessList by username={}]", userName);
            return result;
        }

        result = ConfigUtil.getInstance().getString(userName, null);
        return result;
    }

}
