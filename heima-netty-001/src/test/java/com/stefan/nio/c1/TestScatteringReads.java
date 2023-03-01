package com.stefan.nio.c1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.stefan.nio.c1.ByteBufferUtil.debugAll;

/**
 * @description: 分散读
 * @author: stefanyang
 * @date: 2023/2/21 17:31
 * @version: 1.0
 */
public class TestScatteringReads {
    public static void main(String[] args) {
        try (FileChannel channel = new RandomAccessFile("heima-netty-001/words.txt", "rw").getChannel()){
            ByteBuffer buffer1 = ByteBuffer.allocate(3);
            ByteBuffer buffer2 = ByteBuffer.allocate(3);
            ByteBuffer buffer3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{buffer1, buffer2, buffer3});
            debugAll(buffer1);
            debugAll(buffer2);
            debugAll(buffer3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
