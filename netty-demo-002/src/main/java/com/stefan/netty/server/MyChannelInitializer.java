package com.stefan.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @author: stefanyang
 * @date: 2023/2/16 16:22
 * @version: 1.0
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + ch.remoteAddress().getHostString());
        System.out.println("链接报告Port:" + ch.remoteAddress().getPort());
        System.out.println("链接报告完毕");
    }
}
