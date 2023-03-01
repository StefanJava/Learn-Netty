package com.stefan.netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @description: 客户端
 * @author: stefanyang
 * @date: 2023/3/1 13:23
 * @version: 1.0
 */
public class TestClient {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress(9092));
            socketChannel.write(StandardCharsets.UTF_8.encode("hello world123343"));
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
