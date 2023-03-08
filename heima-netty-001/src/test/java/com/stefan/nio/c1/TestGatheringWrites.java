package com.stefan.nio.c1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @description: 集中写
 * @author: stefanyang
 * @date: 2023/2/21 17:35
 * @version: 1.0
 */
public class TestGatheringWrites {
    public static void main(String[] args) {
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("你好");
        try (FileChannel fileChannel = new RandomAccessFile("heima-netty-001/words2.txt", "rw").getChannel()) {
            fileChannel.write(new ByteBuffer[]{buffer1, buffer2, buffer3});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
