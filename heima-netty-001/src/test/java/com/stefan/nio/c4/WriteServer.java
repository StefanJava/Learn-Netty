package com.stefan.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @description: 写数据
 * @author: stefanyang
 * @date: 2023/2/27 17:34
 * @version: 1.0
 */
public class WriteServer {
    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(9091));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    // 连接事件
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);

                        // 向客户端发送数据

                        ByteBuffer buffer = StandardCharsets.UTF_8.encode("a".repeat(30000000));
                        while (buffer.hasRemaining()) {
                            int write = sc.write(buffer);
                            System.out.println(write);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
