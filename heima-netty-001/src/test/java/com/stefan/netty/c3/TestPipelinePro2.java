package com.stefan.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @description: Pipeline
 * @author: stefanyang
 * @date: 2023/3/2 20:03
 * @version: 1.0
 */
@Slf4j
public class TestPipelinePro2 {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast("h1", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("h1");
                                ByteBuf buf = (ByteBuf) msg;
                                String name = buf.toString(StandardCharsets.UTF_8);
                                super.channelRead(ctx, name);
                            }
                        });

                        pipeline.addLast("h2", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("h2, name = {}", msg);
                                Student student = new Student(msg.toString());
                                super.channelRead(ctx, student);  // ctx.fireChannelRead(student);
                            }
                        });

                        pipeline.addLast("h4", new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("h4");
                                super.write(ctx, msg, promise);
                            }
                        });

                        pipeline.addLast("h3", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("h3, student = {}, class={}", msg, msg.getClass());
                                // 从尾部handler往前查找出栈handler
//                                ch.writeAndFlush(ch.alloc().buffer().writeBytes("hello world".getBytes(StandardCharsets.UTF_8)));
                                // 从当前handler往前查找出栈handler
                                ctx.writeAndFlush(ch.alloc().buffer().writeBytes("hello world".getBytes(StandardCharsets.UTF_8)));
                            }
                        });

                        pipeline.addLast("h5", new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("h5");
                                super.write(ctx, msg, promise);
                            }
                        });

                        pipeline.addLast("h6", new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("h6");
                                super.write(ctx, msg, promise);
                            }
                        });

                    }
                }).bind(9095);
    }

    @Data
    @AllArgsConstructor
    static class Student {
        private String name;
    }
}
