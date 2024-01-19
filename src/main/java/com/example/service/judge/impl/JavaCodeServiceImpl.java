package com.example.service.judge.impl;

import com.example.service.judge.JavaCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Service
public class JavaCodeServiceImpl implements JavaCodeService {
    public String runJavaCode(String code, String demo) {
        try {
            // 保存 Java 代码到临时文件
            String fileName = "Main.java";
            String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
            saveCodeToFile(code, filePath);

            // 编译 Java 代码
            if (compileCode(filePath)) {
                // 运行编译后的类
                return runCompiledCode("Main",demo);
            } else {
                return "Compilation Error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    private void saveCodeToFile(String code, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.write(code);
        }
    }

    private boolean compileCode(String filePath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(filePath));
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            return task.call();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String runCompiledCode(String className, String input) {
        try {
            log.info("11111111");
            URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(System.getProperty("java.io.tmpdir")).toURI().toURL()});
            Class<?> compiledClass = classLoader.loadClass(className);

            Method mainMethod = compiledClass.getMethod("main", String[].class);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);

            PrintStream originalOut = System.out;
            InputStream originalIn = System.in;

            System.setOut(printStream);

            // 重定向输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            mainMethod.invoke(null, (Object) new String[]{});

            // 恢复原始输出流和输入流
            System.setOut(originalOut);
            System.setIn(originalIn);

            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during code execution: " + e.getMessage();
        }
    }
}
