package com.example.service.impl;


import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User selectUserById(String id){return userMapper.selectUserById(id);}

    @Override
    public User selectUserByName(String name){return userMapper.selectUserByName(name);}

    @Override
    public int insertUser(String name,String password) {
        return userMapper.saveUser(name,password);
    }

    @Override
    public int updateUser(String name, String email, String phone) {
        return userMapper.updateUser(name, email, phone);
    }

    @Override
    public int updatePassword(String name, String password) {
        return userMapper.updatePassword(name, password);
    }

    @Override
    public String findPassword(String name) {
        return userMapper.findPassword(name);
    }

    @Override
    public User information(String name) {
        return userMapper.information(name);
    }
}
