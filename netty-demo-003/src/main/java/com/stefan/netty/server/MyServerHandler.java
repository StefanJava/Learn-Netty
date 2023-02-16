package com.stefan.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @description:
 * @author: stefanyang
 * @date: 2023/2/16 16:42
 * @version: 1.0
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收msg
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.print(new Date() + "接收到消息：");
        System.out.println(new String(bytes, Charset.forName("GBK")));
    }
}
