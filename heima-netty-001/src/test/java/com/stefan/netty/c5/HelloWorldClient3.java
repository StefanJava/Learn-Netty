package com.stefan.netty.c5;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Random;

/**
 * @description: Echo Client
 * @author: stefanyang
 * @date: 2023/3/3 13:52
 * @version: 1.0
 */
@Slf4j
public class HelloWorldClient3 {
    public static void main(String[] args) {
        send();
        System.out.println("finished");
    }

    private static void send() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture future = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                            pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf buf = ctx.alloc().buffer();
                                    Random random = new Random();
                                    char c = '0';
                                    for (int i = 0; i < 10; i++) {
                                        byte[] bytes = makeString(c, random.nextInt(256) + 1);
                                        c++;
                                        buf.writeBytes(bytes);
                                    }
                                    ch.writeAndFlush(buf);
                                    ctx.channel().close();
                                }
                            });
                        }
                    }).connect(new InetSocketAddress(9098));
            future.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static byte[] makeString(char c, int len) {
        byte[] bytes = new byte[len + 1];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) c;
        }
        bytes[len] = '\n';
        System.out.println(new String(bytes));
        return bytes;
    }
}
