package com.jing.study.service;


import com.jing.study.entity.UsersLogin;

/**
 * @author zhangning
 * @date 2020/7/9
 */
public interface IUserService {

    //登陸
    UsersLogin login(String username, String password);

}