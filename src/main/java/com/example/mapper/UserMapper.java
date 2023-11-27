package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {


    @Insert("INSERT INTO user(name,password) VALUES(#{name},#{password}) ")//添加新用户
    int saveUser(@Param("name") String name, @Param("password") String password);


    @Select("select * from user where name=#{name}")
    User selectUserByName(@Param("name")String name);

    @Select("select * from user where id=#{id}")
    User selectUserById(@Param("id")String id);

    @Update("UPDATE user set email=#{email},phone=#{phone} WHERE name =#{name}")
    int updateUser(@Param("name")String name,@Param("email")String email,@Param("phone")String phone);

    @Update("UPDATE `user`   set `password`=#{password}  WHERE `name`=#{name}")//更改密码
    int updatePassword(@Param("name")String name,@Param("password")String password);

    @Select("SELECT `password`  from `user`  where name=#{name}")
    String findPassword(@Param("name") String name);

    @Select("SELECT *  from `user`  where name=#{name}")
    User information(@Param("name") String name);
}
