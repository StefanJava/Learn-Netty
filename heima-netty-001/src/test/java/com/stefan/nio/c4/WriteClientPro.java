package com.stefan.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @description: 读数据
 * @author: stefanyang
 * @date: 2023/2/27 17:34
 * @version: 1.0
 */
public class WriteClientPro {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress("192.168.31.147", 9091));

            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            long count = 0L;
            while (true) {
                count += socketChannel.read(buffer);
                System.out.println(count);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
