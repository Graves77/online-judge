package com.example.service.user.userImpl;


import com.example.mapper.user.UserMapper;
import com.example.model.user.User;
import com.example.service.user.UserService;
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
    public int insertUser(String userId,String password) {
        return userMapper.saveUser(userId,password);
    }

    @Override
    public int updateUser(String name, String phone) {
        return userMapper.updateUser(name, phone);
    }

    @Override
    public int updatePassword(String userId, String password) {
        return userMapper.updatePassword(userId, password);
    }

    @Override
    public String findPassword(String name) {
        return userMapper.findPassword(name);
    }

    @Override
    public User information(String userId) {
        return userMapper.information(userId);
    }
}
