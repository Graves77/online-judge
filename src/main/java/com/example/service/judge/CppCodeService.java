package com.example.service.judge;


import org.springframework.stereotype.Service;

@Service
public interface CppCodeService {
    String runCppCode(String code,String demo);

}
