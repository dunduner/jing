package com.jing.study.dao2;

import com.jing.study.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangning
 * @date 2020/8/14
 */
@Repository("UserMapper2")
@Mapper
public interface UserMapper2 {
    /**
     * 添加，只需向逻辑表中添加即可，
     * 此处tab_user不含有数字 0 1 2 3 4
     */
    @Insert({
            " INSERT INTO tab_user (id,name,age,create_time) ",
            " VALUES ( ",
            "#{id,jdbcType=INTEGER}, ",
            "#{name,jdbcType=VARCHAR}, ",
            "#{age,jdbcType=INTEGER}, ",
            "#{create_time,jdbcType=VARCHAR})"
    })
    int addUser(@Param("id") Integer id, @Param("name") String name, @Param("age") Integer age, @Param("create_time") String create_time);


    @Select({"<script>",
            "select id,name,age,create_time from tab_user where 1=1 ",
            " <if test='id !=0'>",
            "and id = #{id,jdbcType=INTEGER} ",
            "</if>",
            " <if test='name !=null'>",
            "and name = #{name,jdbcType=VARCHAR} ",
            "</if>",
            " <if test='create_time !=null'>",
            "and create_time = #{create_time,jdbcType=VARCHAR}",
            "</if>",
            "</script>"
    })
    List<User> queryUsersByName(User user);

    //修改
    @Update("UPDATE  tab_user SET NAME = #{name,jdbcType=VARCHAR}, age = #{age,jdbcType=INTEGER}, create_time = #{create_time,jdbcType=VARCHAR}  WHERE id =#{id,jdbcType=INTEGER}")
    int updataUser(User user);



    @Select({"<script>",
            "select id,name,age,create_time from tab_user where 1=1 ",
            " <if test='user.id !=0'>",
            "and id = #{user.id,jdbcType=INTEGER} ",
            "</if>",
            " <if test='user.name !=null'>",
            "and name = #{user.name,jdbcType=VARCHAR} ",
            "</if>",
            " <if test='user.create_time !=null'>",
            "and create_time = #{user.create_time,jdbcType=VARCHAR}",
            "</if>",
            " <if test='begin !=null and end != null'>",
            "and date_format( create_time,'%Y-%m-%d')  between #{begin} and #{end}",
            "</if>",
            "</script>"
    })
    List<User> queryUsersByNameAndDate(@Param("user") User user, @Param("begin") String begin, @Param("end") String end);



    @Select({"<script>",
            "select id,name,age,create_time from tab_user where 1=1 ",
            " <if test='user.id !=0'>",
            "and id = #{user.id,jdbcType=INTEGER} ",
            "</if>",
            " <if test='user.name !=null'>",
            "and name = #{user.name,jdbcType=VARCHAR} ",
            "</if>",
            " <if test='user.create_time !=null'>",
            "and create_time = #{user.create_time,jdbcType=VARCHAR}",
            "</if>",
            " <if test='begin !=null and end != null'>",
            "and date_format( create_time,'%Y-%m-%d')  between #{begin} and #{end}",
            "</if>",
            "limit #{firstResult},#{maxResults}",
            "</script>"
    })
    List<User> queryUsersByQueryVo(@Param("user") User user, @Param("begin") String begin, @Param("end") String end, @Param("firstResult") Integer firstResult, @Param("maxResults") Integer maxResults);
}
