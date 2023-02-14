package com.stefan.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @description: nio server
 * @author: stefanyang
 * @date: 2023/2/14 09:41
 * @version: 1.0
 */
public class NioServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public static void main(String[] args) {
        new NioServer().bind(8080);
    }

    private void bind(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("nio server start done.");
            new NioServerHandler(selector, StandardCharsets.UTF_8).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
