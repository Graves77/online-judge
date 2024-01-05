package com.example.service.impl;

import com.example.service.CppCodeService;
import com.example.utils.CodeFileWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CppCodeServiceImpl implements CppCodeService {
    public String runCppCode(String code) {
        try {
            // 保存C++代码到临时文件
            String fileName = "UserSubmittedCode.cpp";
            String filePath = System.getProperty("java.io.tmpdir") + "/" + fileName;
            CodeFileWriter.writeCodeToFile(code, filePath);

            // 编译C++代码
            if (compileCppCode(filePath)) {
                // 运行生成的可执行文件
                return runCompiledCode(filePath);
            } else {
                return "Compilation Error";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private boolean compileCppCode(String filePath) {
        try {
            // 调用系统命令进行编译，这里假设系统中有g++编译器
            Process process = new ProcessBuilder("g++", filePath, "-o", "output").start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String runCompiledCode(String executablePath) {
        try {
            // 运行生成的可执行文件，并捕获输出
            Process process = new ProcessBuilder(executablePath).start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            return output.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error during code execution: " + e.getMessage();
        }
    }
}
