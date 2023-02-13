package com.stefan.netty.bio.client;

import com.stefan.netty.bio.ChannelAdapter;
import com.stefan.netty.bio.ChannelHandler;

import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 消息处理器
 * @author: stefanyang
 * @date: 2023/2/13 14:11
 * @version: 1.0
 */
public class BioClientHandler extends ChannelAdapter {
    public BioClientHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler ch) {
        System.out.println("链接报告LocalAddress: " + ch.socket().getLocalAddress());
        ch.writeAndFlush("hi! 今天天气很好 BioClient to msg for you \r\n");
    }

    @Override
    public void channelRead(ChannelHandler ch, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ch.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }
}
