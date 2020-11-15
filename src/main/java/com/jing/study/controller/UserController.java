package com.jing.study.controller;

import com.jing.study.entity.UsersLogin;
import com.jing.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangning
 * @date 2020/7/9
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //植入对象
    @Autowired
    private IUserService iUserService;

    /*返回頁面*/
    @RequestMapping("/getlogin")
    public String getlogin(){
        return "login";
    }

    /*登录*/
    @RequestMapping("/login")
    public ModelAndView login(UsersLogin user, ModelAndView mv, HttpServletRequest request, Model model){
        UsersLogin login = iUserService.login(user.getUserName(),user.getPassword());
        System.out.println(login);
        if (login!=null){
            request.getSession().setAttribute("login",login);
            System.out.println("成功！！");
            mv.setViewName("index");
        }else{
            System.out.println("失败！！");
            mv.addObject("res","账号或密码不正确！");
            mv.setViewName("login");
        }
        return mv;
    }

}