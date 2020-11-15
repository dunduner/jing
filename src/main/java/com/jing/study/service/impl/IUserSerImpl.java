package com.jing.study.service.impl;

import com.jing.study.dao.UserMapper;
import com.jing.study.dto.ResponseUser;
import com.jing.study.entity.User;
import com.jing.study.service.IUserSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangning
 * 分库分表业务实现类
 * @date 2020/8/14
 */

@Service
public class IUserSerImpl implements IUserSer {

    @Autowired
    private ResponseUser resUser;
    @Autowired
    private UserMapper userDao;

    @Override
    public ResponseUser addUser(User user) {
        int count = userDao.addUser(
                user.getId(), user.getName(), user.getAge(), user.getCreate_time());
        if (count == 0)
            resUser.setAll(1, "添加失败", null);
        else
            resUser.setAll(0, "添加成功", count);
        return resUser;
    }

    @Override
    public List<User> queryUsersByName(User user) {
        List<User> userList = userDao.queryUsersByName(user);
        return userList;
    }

    @Override
    public User updataUser(User user) {
        int i = userDao.updataUser(user);
        if (i > 0) return user;
        return null;
    }

    @Override
    public List<User> queryUsersByNameAndDate(User user,String begin,String end) {
        List<User> users = userDao.queryUsersByNameAndDate(user,begin,end);
        if (users != null){
            return users;
        }
        return null;
    }

    /**
     * 分页查询
     * @param object
     * @param begin
     * @param end
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Override
    public List<User> queryUsersByQueryVo(User object, String begin, String end, Integer firstResult, Integer maxResults) {
        List<User> users = userDao.queryUsersByQueryVo(object,begin,end,firstResult,maxResults);
        if (users != null){
            return users;
        }
        return null;
    }
}
