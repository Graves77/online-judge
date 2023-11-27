package com.example.controller;

import com.example.model.JsonResult;
import com.example.model.User;
import com.example.service.impl.UserServiceImpl;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RequestMapping("/user")
@RestController
@ResponseBody
public class UserController {
    @Autowired
    private UserServiceImpl userService;



    @PostMapping("/login")//登录
    public JsonResult login(String name, String password){
        try{
            if(StringUtils.isEmpty(name)) return new JsonResult("用户名不能为空","400","fail");
            if(StringUtils.isEmpty(password)) return new JsonResult("密码不能为空","400","fail");
            User user=userService.selectUserByName(name);
            if(user ==null){
                return new JsonResult("登录失败，不存在该用户","400","fail");
            }
            //2022.8.24 新增或修改部分   by:Orall
            if(password.equals(user.getPassword())){
                //生成token
                JwtUtils jwt = JwtUtils.getInstance();
                String token = jwt
                        .setClaim("name",name)
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
    public JsonResult register(String name,String password,String confirm){
        try{
            if(StringUtils.isEmpty(name)) return new JsonResult("用户名不能为空","400","fail");
            if(StringUtils.isEmpty(password)) return new JsonResult("密码不能为空","400","fail");
            if(!password.equals(confirm)){return new JsonResult("两次密码不同","400","fail");}
            System.out.println("!1!!!!!212");
            User user=userService.selectUserByName(name);
            if(user !=null){
                return new JsonResult("注册失败，该用户已经存在","400","fail");
            }
            if(userService.insertUser(name, password)==1){
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
    public JsonResult updateUser(String name,String  email,String phone){
        try{
            if(StringUtils.isEmpty(name)) return new JsonResult("用户名不能为空","400","fail");
            if(StringUtils.isEmpty(email)) return new JsonResult("邮箱不能为空","400","fail");
            if(StringUtils.isEmpty(phone)) return new JsonResult("电话不能为空","400","fail");
            User user=userService.selectUserByName(name);
            if(user ==null){
                return new JsonResult("更新失败，该用户不存在","400","fail");
            }
            if(userService.updateUser(name, email, phone)==1){
                return new JsonResult("信息补充成功","201");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("服务器内部出错","500","fail");
        }
        return new JsonResult("信息补充失败","400","fail");
    }

    //@Limit(key = "test", permitsPerSecond = 1, timeout = 500, msg = "请求过于频繁,请稍后再试！")
    @GetMapping("/test")
    public String test1(){
        return "测试成功";
    }


    @PostMapping("/updatepassword")//修改密码
    public JsonResult updatePassword(String name, String password,String confirm){
        try{
            if(StringUtils.isEmpty(name)) return new JsonResult("用户名不能为空","400","fail");
            if(StringUtils.isEmpty(password)) return new JsonResult("密码不能为空","400","fail");
            if(!password.equals(confirm)){return new JsonResult("两次密码不同","400","fail");}
            User user=userService.selectUserByName(name);
            if(user ==null){
                return new JsonResult("修改失败，该用户不存在","400","fail");
            }
            if(userService.updatePassword(name, password)==1){
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
    public JsonResult information(String name){
        User user = userService.information(name);
        if(user == null){
            return new JsonResult("不存在该对象","400","失败");
        }
        else{
            return new JsonResult(user,"200");
        }
    }


}
