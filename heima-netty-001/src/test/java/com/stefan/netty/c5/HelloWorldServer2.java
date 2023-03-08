package com.stefan.netty.c5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @description: server
 * @author: stefanyang
 * @date: 2023/3/3 15:25
 * @version: 1.0
 */
public class HelloWorldServer2 {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ChannelFuture future = new ServerBootstrap()
                    .group(boss, worker)
                    // 调整系统tcp接收缓冲区
//                    .option(ChannelOption.SO_RCVBUF, 10)
                    // 调整系统的接收缓冲区 ByteBuf
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(16, 16, 16))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new FixedLengthFrameDecoder(10));
                            pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        }
                    }).bind(9097);
            future.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
