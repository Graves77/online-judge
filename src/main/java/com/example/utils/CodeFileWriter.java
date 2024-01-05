package com.example.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeFileWriter {

    public static void writeCodeToFile(String code, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(code);
        }
    }
}
