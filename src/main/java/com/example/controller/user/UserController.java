package com.example.controller.user;

import com.example.model.JsonResult;
import com.example.model.user.User;
import com.example.service.user.userImpl.UserServiceImpl;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/user")
@RestController
@ResponseBody
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")//登录
    public JsonResult login(String userId, String password){
        try{
            if(!StringUtils.hasText(userId)||!StringUtils.hasText(password))
                return new JsonResult("用户信息不能为空","400","fail");
            User user=userService.selectUserByName(userId);
            if(user ==null){
                return new JsonResult("登录失败，不存在该用户","400","fail");
            }
            if(password.equals(user.getPassword())){
                //生成token
                JwtUtils jwt = JwtUtils.getInstance();
                String token = jwt
                        .setClaim("userId",userId)
                        .setClaim("id",user.getId())
                        .generateToken();
                Map<String,String> tmp = new HashMap<>();
                tmp.put("token",token);
                return new JsonResult(tmp);
            }
            else return new JsonResult("登录失败，密码错误","401","fail");

        }
        catch (Exception e){
            e.printStackTrace();
            return new JsonResult("服务器内部出错","500","fail");
        }
    }


    @PostMapping("/register")//注册：姓名和密码
    public JsonResult register(String userId,String password,String confirm){
        try{
            if(!StringUtils.hasText(userId)) return new JsonResult("学号不能为空","400","fail");
            if(!StringUtils.hasText(password)) return new JsonResult("密码不能为空","400","fail");
            if(!password.equals(confirm)){return new JsonResult("两次密码不同","400","fail");}
            User user=userService.selectUserByName(userId);
            if(user !=null){
                return new JsonResult("注册失败，该用户已经存在","400","fail");
            }
            if(userService.insertUser(userId, password)==1){
                return new JsonResult("注册成功","201");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new JsonResult("服务器内部出错","500","fail");
        }
    return new JsonResult("注册失败","400","fail");
    }


    @PostMapping("/updateUser")//更新信息，添加邮箱和电话，需要提供姓名
    public JsonResult updateUser(String name,String userId){
        try{
            if(!StringUtils.hasText(name)) return new JsonResult("用户名不能为空","400","fail");
            if(!StringUtils.hasText(userId)) return new JsonResult("学号不能为空","400","fail");
            User user=userService.selectUserByName(userId);
            if(user ==null){
                return new JsonResult("更新失败，该用户不存在","400","fail");
            }
            if(userService.updateUser(name, userId)==1){
                return new JsonResult("信息补充成功","201");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("服务器内部出错","500","fail");
        }
        return new JsonResult("信息补充失败","400","fail");
    }

    @PostMapping("/updatepassword")//修改密码
    public JsonResult updatePassword(String userId, String password,String confirm){
        try{
            if(!StringUtils.hasText(userId)) return new JsonResult("学号不能为空","400","fail");
            if(!StringUtils.hasText(password)) return new JsonResult("密码不能为空","400","fail");
            if(!password.equals(confirm)){return new JsonResult("两次密码不同","400","fail");}
            User user=userService.selectUserByName(userId);
            if(user ==null){
                return new JsonResult("修改失败，该用户不存在","400","fail");
            }
            if(userService.updatePassword(userId, password)==1){
                return new JsonResult("密码修改成功","201");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new JsonResult("服务器内部出错","500","fail");
        }

        return new JsonResult("修改失败","400","fail");
    }

    @PostMapping("/information")
    public JsonResult information(String userId){
        User user = userService.information(userId);
        if(user == null){
            return new JsonResult("不存在该对象","400","失败");
        }
        else{
            return new JsonResult(user,"200");
        }
    }


}
