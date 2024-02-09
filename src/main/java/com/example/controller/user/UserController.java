package com.example.controller.user;

import com.example.model.JsonResult;
import com.example.model.user.User;
import com.example.service.user.userImpl.UserServiceImpl;
import com.example.utils.State;
import com.example.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/user")
@RestController
@ResponseBody
public class UserController {
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/registerUser")
    public JsonResult register(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public JsonResult login(@RequestBody User user){
        User login = userService.login(user);
        if(login.getId() == 0){
            return new JsonResult(null, State.FAILURE,"登陆失败!");
        }
        String token = tokenUtil.getToken(user.getStudentId(),login.getRole());
        login.setToken(token);

        return new JsonResult(login,State.SUCCESS,"登陆成功!");
    }

    @PostMapping("/getToken")
    public JsonResult getToken(HttpServletRequest request){
        String token = request.getHeader("token");

        return new JsonResult(tokenUtil.parseToken(token),State.SUCCESS);
    }

    @PostMapping("/changeUserInfo")
    public JsonResult changeUserInfo(@RequestBody User user){

        return new JsonResult(userService.changeUserInfo(user),State.SUCCESS,"操作成功!");
    }

    @GetMapping("/loadUserInfo/{uid}")
    public JsonResult loadUserInfo(@PathVariable long uid){
        return new JsonResult(userService.loadUserInfo(uid),State.SUCCESS,"操作成功!");
    }

    @GetMapping("/getUserList/{page}")
    public JsonResult getUserList(@PathVariable Integer page){
        return new JsonResult(userService.getUserList(page),State.SUCCESS,"获取成功!");
    }

}
