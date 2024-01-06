package com.example.service.impl;

import com.example.service.JavaCodeService;
import com.example.utils.CodeFileWriter;
import com.example.utils.RedirectedPrintStream;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

@Service
public class JavaCodeServiceImpl implements JavaCodeService {

    public String runJavaClass(String code){
        try {
            // 保存用户提交的代码到临时文件
            String className = "UserSubmittedClass";
            String filePath = System.getProperty("java.io.tmpdir") + "/" + className + ".java";
            CodeFileWriter.writeCodeToFile(code, filePath);

            // 编译代码
            if (compileCode(filePath)) {
                // 运行代码
                return runCompiledCode(className);
            } else {
                return "Compilation Error";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private boolean compileCode(String filePath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        return compiler.run(null, null, null, filePath) == 0;
    }

    private String runCompiledCode(String className) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        // 创建URLClassLoader，加载编译后的类
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(System.getProperty("java.io.tmpdir")).toURI().toURL()});
        Class<?> compiledClass = classLoader.loadClass(className);

        // 调用编译后的类的方法
        Method mainMethod = compiledClass.getMethod("main", String[].class);

        // 重定向System.out和System.err
        System.setOut(new RedirectedPrintStream(System.out));
        System.setErr(new RedirectedPrintStream(System.err));

        try {
            // 运行main方法
            mainMethod.invoke(null, (Object) new String[]{});

            // 返回捕获的输出
            return RedirectedPrintStream.getOutput();
        } finally {
            // 还原System.out和System.err
            System.setOut(System.out);
            System.setErr(System.err);
        }
    }
}
