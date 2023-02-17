package com.stefan.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @description: channel initializer
 * @author: stefanyang
 * @date: 2023/2/17 15:26
 * @version: 1.0
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));

        ch.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));

        ch.pipeline().addLast(new MyServerHandler());

    }
}
