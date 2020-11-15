package com.jing.study.dao;

import com.jing.study.entity.UsersLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhangning
 * @date 2020/7/9
 */
@Repository("IUserDao")
@Mapper
public interface IUserDao {

    //登录
    @Select("SELECT * FROM user_login WHERE username=#{username} AND password=#{password}")
    UsersLogin login(@Param("username") String username, @Param("password") String password);

}