package com.example.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;
}
