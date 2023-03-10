package com.stefan.nio.c4;

import com.stefan.nio.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: server
 * @author: stefanyang
 * @date: 2023/2/24 12:49
 * @version: 1.0
 */
@Slf4j
public class Server {

    static ByteBuffer buffer = ByteBuffer.allocate(16);

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(9090));
            List<SocketChannel> channelList = new ArrayList<>();
            while (true) {
                log.info("======连接前======");
                SocketChannel socketChannel = serverSocketChannel.accept();
                log.info("连接后 {}", socketChannel);
                channelList.add(socketChannel);
                for (SocketChannel channel : channelList) {
                    log.info("读取数据 {}", channel);
                    while (channel.read(buffer) > -1) {
                        // 切换成读模式
                        buffer.flip();
                        ByteBufferUtil.debugRead(buffer);
                        buffer.clear();
                    }
                    log.info("读取结束 {}", channel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
