package com.stefan.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: handler
 * @author: stefanyang
 * @date: 2023/2/17 15:26
 * @version: 1.0
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始 {公众号：bugstack虫洞栈 >获取学习源码}");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.remoteAddress().getHostString());
        System.out.println("链接报告Port:" + channel.remoteAddress().getPort());
        System.out.println("链接报告完毕");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("断开报告开始 {公众号：bugstack虫洞栈 >获取学习源码}");
        System.out.println("断开报告信息：有一客户端断开链接到本服务端");
        System.out.println("报告IP:" + channel.remoteAddress().getHostString());
        System.out.println("断开报告Port:" + channel.remoteAddress().getPort());
        System.out.println("断开报告完毕");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "接收到消息: " + msg);
    }
}
