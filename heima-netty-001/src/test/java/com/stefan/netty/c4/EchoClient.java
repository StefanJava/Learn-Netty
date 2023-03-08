package com.stefan.netty.c4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @description: Echo Client
 * @author: stefanyang
 * @date: 2023/3/3 13:52
 * @version: 1.0
 */
@Slf4j
public class EchoClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture future = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

//                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {

                                    while (true) {
                                        Scanner sc = new Scanner(System.in);
                                        String str = sc.nextLine();
                                        if ("exit".equals(str)) {
                                            ch.close();
                                            break;
                                        }
                                        ch.writeAndFlush(str);
                                    }

                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    log.debug("服务器发来: {}", msg);
                                    super.channelRead(ctx, msg);
                                }
                            });
                            pipeline.addLast(new StringEncoder());
                        }
                    }).connect(new InetSocketAddress(9096));
            future.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        /*future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
                while (true) {
                    Scanner sc = new Scanner(System.in);
                    String str = sc.nextLine();
                    if ("exit".equals(str)) {
                        channel.close();
                        break;
                    }
                    channel.writeAndFlush(str);
                }

                future.addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        group.shutdownGracefully();
                    }
                });
            }
        });*/

    }
}
