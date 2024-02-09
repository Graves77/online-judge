package com.example.service.user.userImpl;


import com.example.mapper.user.UserMapper;
import com.example.model.JsonResult;
import com.example.model.user.User;
import com.example.service.user.UserService;
import com.example.utils.SnowflakeIdWorker;
import com.example.utils.State;
import com.example.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public JsonResult registerUser(User user) {
        if(userMapper.existUser(user.getStudentId()) == 1){
            return new JsonResult(null,State.FAILURE,"此学号已经注册!");
        }
//
        user.setId(SnowflakeIdWorker.snowFlow.nextId());
        user.setRole("普通用户");
        user.setUrl(null);
        user.setGender("保密");
        user.setPassword(
//                加密密码
                DigestUtils.md5DigestAsHex(user.getPassword().getBytes())
        );

        boolean addUser = userMapper.addUser(user);
        return  new JsonResult(null,addUser ? State.SUCCESS : State.FAILURE,addUser ? "注册成功！" : "注册失败!");
    }

    @Override
    public User login(User user) {
        User loginUser = userMapper.getUser(user.getStudentId());

        if(loginUser == null || !DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(loginUser.getPassword())){
            return user;
        }
        return loginUser;
    }

    @Override
    public boolean changeUserInfo(User user) {
        return userMapper.changeUserInfo(user);
    }

    @Override
    public User loadUserInfo(long id) {
        User userById = userMapper.getUserById(id);

        userById.setToken(tokenUtil.getToken(userById.getStudentId(),userById.getRole()));
        return userById;
    }

    @Override
    public Map<String,Object> getUserList(Integer page) {
        Map<String,Object> userMap = new HashMap<String,Object>();
        List<User> userList = userMapper.getUserList(page);
        Integer userCount = userMapper.userCount();
        userMap.put("userList",userList);
        userMap.put("userCount",userCount);
        return userMap;
    }
}
