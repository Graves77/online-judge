package com.example.mapper.user;

import com.example.model.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    boolean addUser(User user);

    int existUser(String studentId);

    User getUser(String studentId);


    List<User> getUserList(int offset,Integer page);

    User getUserById(long id);
    boolean changeUserResolve(String difficulty,long id);


    boolean changeUserInfo(User user);

    boolean updateUserBanState(String uid,Integer ban);

    Integer userCount();

}
