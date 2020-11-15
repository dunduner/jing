package com.jing.study.controller;

import com.jing.study.dto.QueryVo;
import com.jing.study.dto.ResponseUser;
import com.jing.study.entity.User;
import com.jing.study.service.IUserSer;
import com.jing.study.service.IUserSer2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author zhangning
 * @date 2020/8/19
 */

@RestController
public class UserConFenKuFenBiao {

    @Autowired
    private IUserSer iUserSer;
    @Autowired
    private IUserSer2 iUserSer2;
    @Autowired
    private ResponseUser responseUser;

    /**
     * 模拟插入数据
     */
    User userdemo = new User();

    /**
     * 初始化插入数据,@PostConstruct这个注解代表类实施事就执行这个方法，类似于构造函数（仅仅是我的理解）
     */
    @PostConstruct
    private void getData() {
        SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userdemo = new User(3, "dadada", 18, "2025-05-15 14:39:18");//会发现被存入第二个库中tab_user0表中
        //userList=new User(3,"dadada", 298,sfEnd.format(new Date()));当前时间2020年，会发现被存入第一个库中tab_user0表中
        System.out.println("测试~" + userdemo.toString());
    }

    /**
     * 添加数据，
     */
    @RequestMapping("/save-user")
    public Object saveUser(@RequestBody User userCanshu) {
        if (userCanshu != null) {
            return iUserSer.addUser(userCanshu);
        }
        return iUserSer.addUser(userdemo);
    }

    /**
     * 根据名字查询数据，
     */
    @RequestMapping("/query-user-name")
    public List<User> queryUserByName(@RequestBody User userCanshu) {
        if (userCanshu != null) {
//            return iUserSer.queryUsersByName(userCanshu);
            //用第二个数据源查询
            return iUserSer2.queryUsersByName(userCanshu);
        }
        return null;
    }

    /**
     * 根据时间范围查询数据，
     */
    @RequestMapping("/query-date")
    public List<User> queryUserByNameAndDate(@RequestBody QueryVo<User> queryVo) {
        User user = queryVo.getObject();
        if (user != null) {
            return iUserSer.queryUsersByNameAndDate(user, queryVo.getBegin(), queryVo.getEnd());
//            return iUserSer2.queryUsersByNameAndDate(user, queryVo.getBegin(), queryVo.getEnd());
        }
        return null;
    }

    /**
     * 分页查询
     */
    @RequestMapping("/query-fenye")
    public List<User> queeryFen(@RequestBody QueryVo<User> queryVo) {
        User user = queryVo.getObject();
        if (user != null) {
            return iUserSer.queryUsersByQueryVo(user, queryVo.getBegin(), queryVo.getEnd(), queryVo.getFirstResult(), queryVo.getMaxResults());
        }
        return null;
    }


    /**
     * 修改user
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseUser updataUser(@RequestBody User userCanshu) {
        if (userCanshu != null) {
            User userRes = iUserSer.updataUser(userCanshu);
            if (userRes == null) {
                responseUser.setAll(2, "修改失敗", null);
            } else {
                responseUser.setAll(1, "修改user成功", iUserSer.updataUser(userCanshu));
            }
            return responseUser;
        }
        return responseUser;
    }

}
