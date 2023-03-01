package com.stefan.netty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @description: 处理器
 * @author: stefanyang
 * @date: 2023/2/14 09:41
 * @version: 1.0
 */
public class ChannelHandler {

    private SocketChannel socketChannel;

    private Charset charset;

    public ChannelHandler(SocketChannel socketChannel, Charset charset) {
        this.socketChannel = socketChannel;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg) {
        try {
            byte[] bytes = msg.toString().getBytes(charset);
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketChannel socketChannel() {
        return this.socketChannel;
    }
}
