package net.artemisia.dev.api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private final File file;
    public FileUtils(File file) {
        this.file = file;
    }
    public List<File> getFiles() {
        return getFiles(file);
    }

    public List<File> getFiles(File f) {
        File[] files = f.listFiles();
        List<File> fileList = new ArrayList<File>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file);
                } else if (file.isDirectory()) {
                   fileList.addAll(this.getFiles(file));
                }
            }
        }
        return fileList;
    }


    public String readLines() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n"); // 添加每一行的内容到 StringBuilder
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
