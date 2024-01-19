package com.example.controller.judge;


import com.example.model.JsonResult;
import com.example.service.judge.JavaCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/java")
@RestController
@ResponseBody
public class JavaCodeController {
    @Autowired
    public JavaCodeService javaCodeService;

    @PostMapping("/runJavaCode")
    public JsonResult runJavaClass(String code, String demo) {
        if(!StringUtils.hasText(code)){
            return new JsonResult(null,"400","参数不能为空");
        }
        return new JsonResult(javaCodeService.runJavaCode(code,demo));
    }
}

