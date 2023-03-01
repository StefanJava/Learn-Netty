package com.stefan.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @description: client
 * @author: stefanyang
 * @date: 2023/2/24 12:57
 * @version: 1.0
 */
public class Client2 {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            // 连接服务器
            socketChannel.connect(new InetSocketAddress("192.168.31.147", 9090));
            if (socketChannel.isConnected()) {
                socketChannel.write(StandardCharsets.UTF_8.encode("hello world"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
