package com.example.controller;


import com.example.service.JavaCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/java")
@RestController
@ResponseBody
public class JudgeController {
    @Autowired
    public JavaCodeService javaCodeService;

    @PostMapping("/runJavaClass")
    public String runJavaClass(@RequestBody String code) {
        return javaCodeService.runJavaClass(code);
    }
}

