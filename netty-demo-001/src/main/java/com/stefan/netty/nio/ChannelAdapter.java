package com.stefan.netty.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @description: 适配器
 * @author: stefanyang
 * @date: 2023/2/14 09:42
 * @version: 1.0
 */
public abstract class ChannelAdapter extends Thread {

    private ChannelHandler channelHandler;

    private Selector selector;

    private Charset charset;

    public ChannelAdapter(Selector selector, Charset charset) {
        this.selector = selector;
        this.charset = charset;
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    handleInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) {
        if (!key.isValid()) {
            return;
        }
        Class<?> superclass = key.channel().getClass().getSuperclass();

        // 客户端SocketChannel
        if (superclass == SocketChannel.class) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                try {
                    if (socketChannel.finishConnect()) {
                        channelHandler = new ChannelHandler(socketChannel, charset);
                        channelActive(channelHandler);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else {
                        System.exit(-1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 服务端ServerSocketChannel
        if (superclass == ServerSocketChannel.class) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            if (key.isAcceptable()) {
                try {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);

                    channelHandler = new ChannelHandler(socketChannel, charset);
                    channelActive(channelHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try {
                int readBytes = socketChannel.read(byteBuffer);
                if (readBytes > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    channelRead(channelHandler, new String(bytes, charset));
                } else {
                    key.cancel();
                    socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 链接通知
     * @param channelHandler handler
     */
    public abstract void channelActive(ChannelHandler channelHandler);

    /**
     * 读数据
     * @param channelHandler handler
     * @param msg msg
     */
    public abstract void channelRead(ChannelHandler channelHandler, Object msg);
}
