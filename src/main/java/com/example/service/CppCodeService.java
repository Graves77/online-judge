package com.example.service;


import org.springframework.stereotype.Service;

@Service
public interface CppCodeService {
    public String runCppCode(String code);

}
