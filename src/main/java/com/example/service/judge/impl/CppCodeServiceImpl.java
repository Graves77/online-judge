package com.example.service.judge.impl;

import com.example.service.judge.CppCodeService;
import com.example.utils.CodeFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class CppCodeServiceImpl implements CppCodeService {
    public String runCppCode(String code,String demo) {
        try {
            // 保存C++代码到临时文件
            String fileName = "CppDemo.cpp";
            //String filePath = System.getProperty("java.io.tmpdir") + "/" + fileName;
            String filePath = "C:\\Users\\Graves\\Desktop\\" + fileName;

            CodeFileWriter.writeCodeToFile(code, filePath);

            // 编译C++代码
            if (compileCppCode(filePath)) {
                // 运行生成的可执行文件
                return runCompiledCode(filePath,demo);
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
            //log.info("success");
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String runCompiledCode(String executablePath,String demo) {
        try {
            //编辑exe文件名
            String exeFileName = executablePath.substring(executablePath.lastIndexOf("\\")+1, executablePath.lastIndexOf("."));

            //对cpp文件进行编译生成exe文件
            Runtime.getRuntime().exec("g++ " + executablePath + " -o " + "C:\\Users\\Graves\\Desktop\\" +exeFileName  + ".exe");

            Thread.sleep(1000);
            //运行exe文件
            Process process = new ProcessBuilder("C:\\Users\\Graves\\Desktop\\" +exeFileName + ".exe").start();

            // 获取标准输入流，用于向子进程写入数据
            OutputStream outputStream = process.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            //传入到控制台
            writer.write(demo);
            writer.newLine();
            writer.flush();
            // 关闭标准输入流，表示输入结束
            outputStream.close();

            // 获取子进程的输出
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
