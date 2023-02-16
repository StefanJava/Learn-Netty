package com.stefan.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ServerChannel;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @author: stefanyang
 * @date: 2023/2/16 16:38
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

        // 在管道中添加我们自己接收数据的实现方法
        ch.pipeline().addLast(new MyServerHandler());
    }
}
