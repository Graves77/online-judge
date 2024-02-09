package com.example.service.user;

import com.example.model.JsonResult;
import com.example.model.user.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    JsonResult registerUser(User user);

    User login(User user);

    boolean changeUserInfo(User user);

    User loadUserInfo(long id);


    Map<String,Object> getUserList(Integer page);

}
