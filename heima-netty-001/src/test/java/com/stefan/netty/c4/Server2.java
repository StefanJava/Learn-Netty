package com.stefan.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.stefan.netty.c1.ByteBufferUtil.debugRead;

/**
 * @description: server
 * @author: stefanyang
 * @date: 2023/2/24 12:49
 * @version: 1.0
 */
@Slf4j
public class Server2 {

    static ByteBuffer buffer = ByteBuffer.allocate(16);

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            // 设置成非阻塞模式
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(9090));
            List<SocketChannel> channelList = new ArrayList<>();
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    log.info("建立连接 {}", socketChannel);
                    socketChannel.configureBlocking(false);
                    channelList.add(socketChannel);
                }
                for (SocketChannel channel : channelList) {
                    while (channel.read(buffer) > 0) {
                        log.info("读取数据 {}", channel);
                        // 切换成读模式
                        buffer.flip();
                        debugRead(buffer);
                        buffer.clear();
                        log.info("读取结束 {}", channel);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
