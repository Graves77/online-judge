package com.example.model.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private int id;
    private String name;
    private String password;

    //学号
    private String userId;

    //通过的题目数量
    private int passQuestion;

    //是否为管理员
    private boolean isAdmin;
}
