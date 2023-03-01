package com.stefan.nio.c1;

import java.nio.ByteBuffer;

/**
 * @description: ByteBuffer read
 * @author: stefanyang
 * @date: 2023/2/21 16:43
 * @version: 1.0
 */
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        ByteBufferUtil.debugAll(buffer);
        buffer.flip();
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        debugAll(buffer);
//        buffer.rewind();  // position -> 0
//        debugAll(buffer);
//        System.out.println((char) buffer.get());
        ByteBufferUtil.debugAll(buffer);
        System.out.println((char) buffer.get());
        buffer.mark();
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        ByteBufferUtil.debugAll(buffer);
        buffer.reset();
        ByteBufferUtil.debugAll(buffer);
        System.out.println((char) buffer.get());
        ByteBufferUtil.debugAll(buffer);

        System.out.println((char) buffer.get(0));
        ByteBufferUtil.debugAll(buffer);
    }
}
