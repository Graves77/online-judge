package com.example.controller;

import com.example.service.CppCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class CppCodeController {

    @Autowired
    public CppCodeService cppCodeService;

    @PostMapping("/runCppCode")
    public String runCppCode(@RequestBody String code) {
        return cppCodeService.runCppCode(code);
    }
}
