package com.stefan.netty.c1;

import java.nio.ByteBuffer;

import static com.stefan.netty.c1.ByteBufferUtil.debugAll;

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
        debugAll(buffer);
        buffer.flip();
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        debugAll(buffer);
//        buffer.rewind();  // position -> 0
//        debugAll(buffer);
//        System.out.println((char) buffer.get());
        debugAll(buffer);
        System.out.println((char) buffer.get());
        buffer.mark();
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        debugAll(buffer);
        buffer.reset();
        debugAll(buffer);
        System.out.println((char) buffer.get());
        debugAll(buffer);

        System.out.println((char) buffer.get(0));
        debugAll(buffer);
    }
}
