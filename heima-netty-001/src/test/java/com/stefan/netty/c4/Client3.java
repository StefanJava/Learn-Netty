package com.stefan.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static com.stefan.netty.c1.ByteBufferUtil.debugRead;

/**
 * @description: selector client
 * @author: stefanyang
 * @date: 2023/2/27 14:27
 * @version: 1.0
 */
@Slf4j
public class Client3 {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {

            socketChannel.connect(new InetSocketAddress("192.168.31.147", 9090));
            SocketAddress address = socketChannel.getLocalAddress();
            socketChannel.write(StandardCharsets.UTF_8.encode("hello\nworld6784728284"));
            socketChannel.write(StandardCharsets.UTF_8.encode("helloworld6784728284\n"));
            System.in.read();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
