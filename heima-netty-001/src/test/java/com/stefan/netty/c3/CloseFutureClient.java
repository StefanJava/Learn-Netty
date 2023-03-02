package com.stefan.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @description: 在channel关闭后，处理一些事情
 * @author: stefanyang
 * @date: 2023/3/2 15:31
 * @version: 1.0
 */
@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                    // 异步非阻塞 main 发起调用 另一个线程执行连接 nio线程
                }).connect(new InetSocketAddress(9095));

        // addListener 异步处理 交给建立连接的那个线程去处理，当建立连接后会调用
        channelFuture.addListener((ChannelFutureListener) future -> {
            Channel channel = future.channel();
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String line = scanner.nextLine();
                    if ("q".equals(line)) {
                        channel.close();
                        break;
                    }
                    channel.writeAndFlush(line);
                }
            }, "input").start();
            ChannelFuture closeFuture = channel.closeFuture();
            closeFuture.addListener((ChannelFutureListener) future1 -> {
                log.info("{}", future1);
                log.info("channel关闭之后的操作");
                group.shutdownGracefully();
            });
        });

        log.info("main end");
    }
}
