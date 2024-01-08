package com.example.controller;

import com.example.model.JsonResult;
import com.example.service.CppCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public JsonResult runCppCode(String code, String demo) {
        if(StringUtils.hasText(code)){
            return new JsonResult(null,"400","参数为空");
        }
        return new JsonResult(cppCodeService.runCppCode(code,demo));
    }
}
