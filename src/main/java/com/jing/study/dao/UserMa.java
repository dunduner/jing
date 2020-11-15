package com.jing.study.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author zhangning
 * @date 2020/8/14
 */
@Repository("UserMa")
@Mapper
public interface UserMa {
    /**
     * 添加，只需向逻辑表中添加即可，
     * 此处tab_user不含有数字 0 1 2 3 4
     */
    @Insert({
            " INSERT INTO tab_user(id,name,age,create_time) ",
            " VALUES ( " ,
            "#{id,jdbcType=INTEGER}, ",
            "#{name,jdbcType=VARCHAR}, ",
            "#{age,jdbcType=INTEGER}, ",
            "#{create_time,jdbcType=VARCHAR})"
    })
    int addUser(@Param("id")  Integer id,
                @Param("name") String name,
                @Param("age") Integer age,
                @Param("create_time") String create_time);
}
