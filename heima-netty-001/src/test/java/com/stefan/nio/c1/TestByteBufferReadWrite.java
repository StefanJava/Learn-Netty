package com.stefan.nio.c1;

import java.nio.ByteBuffer;

import static com.stefan.nio.c1.ByteBufferUtil.debugAll;

/**
 * @description: ByteBuffer read write
 * @author: stefanyang
 * @date: 2023/2/21 16:16
 * @version: 1.0
 */
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        debugAll(buffer);
//        System.out.println(buffer.get());
        buffer.flip();  // limit -> position  position -> 0
        debugAll(buffer);
        System.out.println((char) buffer.get());  // position -> 1 limit不变
        debugAll(buffer);
        buffer.compact();  // position -> limit - position   limit -> cap
        debugAll(buffer);
        buffer.put(new byte[]{0x65, 0x66});
        debugAll(buffer);
    }
}
