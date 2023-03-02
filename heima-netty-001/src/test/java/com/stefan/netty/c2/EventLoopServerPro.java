package com.stefan.netty.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @description: server
 * @author: stefanyang
 * @date: 2023/3/1 18:15
 * @version: 1.0
 */
@Slf4j
public class EventLoopServerPro {
    public static void main(String[] args) {
        // 创建一个独立的EventLoopGroup专门用来处理耗时较长的handler
        EventLoopGroup group = new DefaultEventLoopGroup();
        new ServerBootstrap()
                // boss worker
                // boss 只负责 ServerSocketChannel 上 accept 事件   worker 负责 SocketChannel 上的读写
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast("handler-1", new ChannelInboundHandlerAdapter() {
                            // 一个worker会处理多个channel，一个channel耗时长，会影响其他channel
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info(buf.toString(StandardCharsets.UTF_8));
                                ctx.fireChannelRead(msg);  // 将消息传递给下一个handler
                            }
                        }).addLast(group, "handler-2", new ChannelInboundHandlerAdapter() {
                            // 一个worker会处理多个channel，一个channel耗时长，会影响其他channel
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info(buf.toString(StandardCharsets.UTF_8));
                            }
                        });
                    }
                }).bind(9094);
    }
}
