package com.example.mapper.user;

import com.example.model.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {


    @Insert("INSERT INTO user(userId,password,isAdmin) VALUES(#{userId},#{password},0) ")//添加新用户
    int saveUser(@Param("userId") String userId, @Param("password") String password);


    @Select("select * from user where userId=#{userId}")
    User selectUserByName(@Param("userId")String userId);

    @Select("select * from user where id=#{id}")
    User selectUserById(@Param("id")String id);

    @Update("UPDATE user set name=#{name} WHERE userId =#{userId}")
    int updateUser(@Param("name")String name,@Param("userId")String userId);

    @Update("UPDATE `user`   set `password`=#{password}  WHERE `userId`=#{userId}")//更改密码
    int updatePassword(@Param("userId")String userId,@Param("password")String password);

    @Select("SELECT `password`  from `user`  where name=#{name}")
    String findPassword(@Param("name") String name);

    @Select("SELECT *  from `user`  where userId=#{userId}")
    User information(@Param("userId") String userId);
}
