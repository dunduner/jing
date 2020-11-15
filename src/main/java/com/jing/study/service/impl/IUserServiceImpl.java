package com.jing.study.service.impl;

import com.jing.study.dao.IUserDao;
import com.jing.study.entity.UsersLogin;
import com.jing.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangning
 * @date 2020/7/9
 */
@Service(value = "IUserServiceImpl")
public class IUserServiceImpl implements IUserService {

    @Resource(name="IUserDao")
    private IUserDao iUserDao;


    @Override
    public UsersLogin login(String username, String password) {

        return iUserDao.login(username, password);
    }
}
