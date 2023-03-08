package com.stefan.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @description: client
 * @author: stefanyang
 * @date: 2023/3/1 15:43
 * @version: 1.0
 */
public class HelloClient {
    public static void main(String[] args) {
        try {
            new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline().addLast(new StringEncoder());
                        }
                    }).connect(new InetSocketAddress(9093)).sync().channel().writeAndFlush("hello, world");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
