package com.stefan.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @description: server
 * @author: stefanyang
 * @date: 2023/3/1 18:15
 * @version: 1.0
 */
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws IOException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                    // 异步非阻塞 main 发起调用 另一个线程执行连接 nio线程
                }).connect(new InetSocketAddress(9094));

        // sync 同步 保证已经同服务器建立连接
            /*Channel channel = channelFuture.sync().channel();
            log.info("{}", channel);
            System.out.println("hello world");*/

        // addListener 异步处理 交给建立连接的那个线程去处理，当建立连接后会调用
        channelFuture.addListener((ChannelFutureListener) future -> {
            Channel channel = future.channel();
            log.info("{}", channel);
            channel.writeAndFlush("hello world");
        });

        log.info("main end");
    }
}
