package com.fuda.dc.lhtz.web.service;

import org.springframework.stereotype.Service;

/**
 * 用户管理
 * 
 * @author liukai
 */
public interface IUserService {

    public boolean doLogin(String userName, String password);

    /**
     * 该用户所能访问的资源列表(zookeeper 资源的列表)
     * @param userName  用户名
     * @return
     */
    public String getAccessList(String userName);

}
