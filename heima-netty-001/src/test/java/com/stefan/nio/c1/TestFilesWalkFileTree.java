package com.stefan.nio.c1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: Files.walkFileTree()
 * @author: stefanyang
 * @date: 2023/2/23 15:10
 * @version: 1.0
 */
public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {



    }

    private static void m3() throws IOException {
        Path path = Paths.get("/Volumes/Cynthia/Stefan/Netty网络编程");
        Files.walkFileTree(path, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("========>" + dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    private static void m2() throws IOException {
        AtomicInteger jarCount = new AtomicInteger();

        Files.walkFileTree(Paths.get("/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home"), new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".dylib")) {
                    jarCount.incrementAndGet();
                }
                System.out.println(file);
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("jar count: " + jarCount);
    }

    private static void m1() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home"), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dirCount.incrementAndGet();
                System.out.println("=======>" + dir);
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileCount.incrementAndGet();
                System.out.println(file);
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("dir count: " + dirCount);
        System.out.println("file count: " + fileCount);
    }
}
