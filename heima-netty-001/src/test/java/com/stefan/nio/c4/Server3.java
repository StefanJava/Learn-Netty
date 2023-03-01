package com.stefan.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

import static com.stefan.nio.c1.ByteBufferUtil.debugAll;

/**
 * @description: selector
 * @author: stefanyang
 * @date: 2023/2/27 14:27
 * @version: 1.0
 */
@Slf4j
public class Server3 {
    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(9090));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    // 连接事件
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        log.info("连接建立  {}", sc);
                        ByteBuffer buffer = ByteBuffer.allocate(4);
                        sc.register(selector, SelectionKey.OP_READ, buffer);
                    }

                    // 读事件
                    if (key.isReadable()) {
                        try {
                            SocketChannel sc = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            int read;
                            while ((read = sc.read(buffer)) > 0) {
                                split(buffer);
                                if (buffer.position() == buffer.limit()) {
                                    ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                    buffer.flip();
                                    newBuffer.put(buffer);
                                    buffer = newBuffer;
                                    key.attach(buffer);
                                }
                            }
                            if (read == -1) {
                                key.cancel();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            key.cancel();
                        }
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();

        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int len = i + 1 - source.position();
                ByteBuffer buffer = ByteBuffer.allocate(len);
                for (int j = 0; j < len; j++) {
                    buffer.put(source.get());
                }
                debugAll(buffer);
            }

        }

        source.compact();
    }
}
