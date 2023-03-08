package com.stefan.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: test ByteBuffer
 * @author: stefanyang
 * @date: 2023/2/20 20:03
 * @version: 1.0
 */
@Slf4j
public class TestByteBuffer {

    public static void main(String[] args) {

        try (FileChannel fileChannel = new FileInputStream("heima-netty-001/data.txt").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10);

            int read;
            while ((read = fileChannel.read(buffer)) != -1) {
                log.info("读取到的字节数: {}", read);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.info("读取字符: {}", (char) b);
                }
//                buffer.clear();  position -> 0  limit -> cap
                buffer.compact();  // position -> remaining number  limit -> cap
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
