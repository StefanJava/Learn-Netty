package com.stefan.advance;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @description: test redis
 * @author: stefanyang
 * @date: 2023/3/6 10:12
 * @version: 1.0
 */
public class TestRedis {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        final byte[] LINE = new byte[]{13, 10};
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
                                    buf.writeBytes("*3".getBytes(StandardCharsets.UTF_8));
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("set".getBytes(StandardCharsets.UTF_8));
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("$4".getBytes(StandardCharsets.UTF_8));
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("name".getBytes(StandardCharsets.UTF_8));
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("$10".getBytes(StandardCharsets.UTF_8));
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("StefanYang".getBytes(StandardCharsets.UTF_8));
                                    buf.writeBytes(LINE);
                                    ch.writeAndFlush(buf);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf buf = (ByteBuf) msg;
                                    System.out.println(buf.toString(StandardCharsets.UTF_8));
                                }
                            });
                        }
                    }).connect(new InetSocketAddress(6379));
            future.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
