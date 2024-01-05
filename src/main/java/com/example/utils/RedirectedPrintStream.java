package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class RedirectedPrintStream extends PrintStream {

    // 使用ThreadLocal为每个线程存储输出流
    private static final ThreadLocal<OutputStream> OUTPUT_HOLDER = ThreadLocal.withInitial(ByteArrayOutputStream::new);

    // 构造函数，用于包装现有的OutputStream
    public RedirectedPrintStream(OutputStream out) {
        super(out);
    }

    // 静态方法，重定向 System.out
    public static void redirectSystemOut() {
        // 创建一个新的PrintStream，该PrintStream包装了一个用于 System.out 的 RedirectedOutputStream
        System.setOut(new PrintStream(new RedirectedOutputStream(System.out)));
    }

    // 静态方法，重定向 System.err
    public static void redirectSystemErr() {
        // 创建一个新的PrintStream，该PrintStream包装了一个用于 System.err 的 RedirectedOutputStream
        System.setErr(new PrintStream(new RedirectedOutputStream(System.err)));
    }

    // 静态方法，用于获取捕获的输出
    public static String getOutput() {
        return OUTPUT_HOLDER.get().toString();
    }

    // 嵌套类，用于处理输出重定向的逻辑
    private static class RedirectedOutputStream extends OutputStream {
        private final OutputStream original;

        // 构造函数，接收原始的OutputStream
        public RedirectedOutputStream(OutputStream original) {
            this.original = original;
        }

        // 重写write方法，实现输出的重定向
        @Override
        public void write(int b) {
            try {
                // 将输出写入 ThreadLocal 存储的 ByteArrayOutputStream 中
                OUTPUT_HOLDER.get().write(b);
                // 同时将输出写入原始的 OutputStream 中
                original.write(b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
