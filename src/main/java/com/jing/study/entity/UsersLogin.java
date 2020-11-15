package com.jing.study.entity;

//demo 实体类 不是分库分表类
public class UsersLogin {
    private Integer uid;
    private String userName;

    private String password;

    private String realName;

    public UsersLogin(Integer uid, String username, String password, String realname) {
        this.uid = uid;
        this.userName = username;
        this.password = password;
        this.realName = realname;
    }

    public UsersLogin() {
        super();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "UsersLogin{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}