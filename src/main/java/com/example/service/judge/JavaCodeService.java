package com.example.service.judge;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface JavaCodeService {
    String runJavaCode(String code, String demo);
}
