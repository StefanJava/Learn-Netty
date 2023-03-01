package com.stefan.netty.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @description: nio client
 * @author: stefanyang
 * @date: 2023/2/14 09:35
 * @version: 1.0
 */
public class NioClient {

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            boolean connect = socketChannel.connect(new InetSocketAddress("192.168.31.147", 8080));
            if (connect) {
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
            System.out.println("nio client start done. ");
            new NioClientHandler(selector, Charset.forName("UTF-8")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
