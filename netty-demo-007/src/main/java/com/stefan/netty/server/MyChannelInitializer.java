package com.stefan.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @description:
 * @author: stefanyang
 * @date: 2023/2/20 16:12
 * @version: 1.0
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 解决半包粘包
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 字符串解码器
        ch.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 字符串编码器
        ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 自定义处理器
        ch.pipeline().addLast(new MyServerHandler());
    }
}
