package com.stefan.nio.c1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description: 多级目录拷贝
 * @author: stefanyang
 * @date: 2023/2/23 15:48
 * @version: 1.0
 */
public class TestFilesCopy {
    public static void main(String[] args) throws IOException {
        String source = "/Volumes/Cynthia/Stefan/资料-并发编程";
        String target = "/Volumes/Cynthia/Stefan/资料-并发编程001";
        Files.walk(Paths.get(source)).forEach(path -> {

            try {
                Path targetPath = Paths.get(path.toString().replace(source, target));
                if (Files.isDirectory(path)) {
                    // 创建
                    Files.createDirectory(targetPath);
                } else if (Files.isRegularFile(path)) {
                    // copy
                    Files.copy(path, targetPath);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
