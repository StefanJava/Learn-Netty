package com.stefan.nio.c1;

import java.nio.ByteBuffer;

import static com.stefan.nio.c1.ByteBufferUtil.debugAll;

/**
 * @description: 黏包半包
 * @author: stefanyang
 * @date: 2023/2/21 18:17
 * @version: 1.0
 */
public class TestByteBufferExam {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,World\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int len = i + 1 - source.position();
                ByteBuffer buffer = ByteBuffer.allocate(len);
                for (int j = 0; j < len; j++) {
                    buffer.put(source.get());
                }
                debugAll(buffer);
            }

        }
        source.compact();
    }
}
