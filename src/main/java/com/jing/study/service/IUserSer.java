package com.jing.study.service;

import com.jing.study.dto.ResponseUser;
import com.jing.study.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangning
 * @date 2020/8/14
 */
//分库分表业务类
public interface IUserSer {

    ResponseUser addUser(User user);

    List<User> queryUsersByName(User user);

    User updataUser(User user);

    List<User> queryUsersByNameAndDate(User object,String begin,String end);

    //分页查询
    List<User> queryUsersByQueryVo(User object,String begin,String end, Integer firstResult, Integer MaxResults);
}
