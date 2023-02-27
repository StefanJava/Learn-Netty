package com.stefan.netty.c4;

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
public class WriteServerPro {
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
                        SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ);

                        // 向客户端发送数据
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < 30000000; i++) {
                            sb.append("a");
                        }

                        ByteBuffer buffer = StandardCharsets.UTF_8.encode(sb.toString());
                        int write = sc.write(buffer);
                        System.out.println(write);
                        if (buffer.hasRemaining()) {
                            // 如果buffer中还有数据没写完，关注可写事件,不阻塞
                            scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                            scKey.attach(buffer);
                        }
                    }

                    if (key.isWritable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int write = sc.write(buffer);
                        System.out.println(write);
                        if (!buffer.hasRemaining()) {
                            key.attach(null);
                            key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
