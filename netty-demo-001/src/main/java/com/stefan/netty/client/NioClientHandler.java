package com.stefan.netty.client;

import com.stefan.netty.ChannelAdapter;
import com.stefan.netty.ChannelHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: nio client 处理器
 * @author: stefanyang
 * @date: 2023/2/14 09:42
 * @version: 1.0
 */
public class NioClientHandler extends ChannelAdapter {

    public NioClientHandler(Selector selector, Charset charset) {
        super(selector, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            System.out.println("链接报告LocalAddress:" + channelHandler.socketChannel().getLocalAddress());
            channelHandler.writeAndFlush("hi! 今天guangdong很冷 NioClient to msg for you \r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        channelHandler.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }
}
