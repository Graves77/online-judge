package com.example.controller;

import com.example.service.CppCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RequestMapping("/cpp")
@RestController
@ResponseBody
public class CppCodeController {

    @Autowired
    public CppCodeService cppCodeService;
    @PostMapping("/runCppCode")
    public String runCppCode(String code) {
        return cppCodeService.runCppCode(code);
    }
}
