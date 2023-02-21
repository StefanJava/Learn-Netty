package com.stefan.netty.c1;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.stefan.netty.c1.ByteBufferUtil.debugAll;

/**
 * @description: ByteBuffer <-> String
 * @author: stefanyang
 * @date: 2023/2/21 17:02
 * @version: 1.0
 */
public class TestByteBufferString {
    public static void main(String[] args) {
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("你好".getBytes(Charset.forName("GBK")));
        debugAll(buffer1);

//        ByteBuffer buffer2 = Charset.forName("GBK").encode("hello");
//        debugAll(buffer2);

        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("你好");
        debugAll(buffer2);

        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer3);

        buffer1.flip();
//        String str1 = StandardCharsets.UTF_8.decode(buffer1).toString();
        String str1 = Charset.forName("GBK").decode(buffer1).toString();
        System.out.println(str1);

        String str2 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str2);
    }
}
