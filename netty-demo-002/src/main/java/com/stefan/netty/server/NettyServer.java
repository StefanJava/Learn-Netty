package com.stefan.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description: netty server
 * @author: stefanyang
 * @date: 2023/2/16 16:10
 * @version: 1.0
 */
public class NettyServer {
    public static void main(String[] args) {
        new NettyServer().bing(8082);
    }

    private void bing(int port) {
        // 配置服务端NIO线程组
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)  // 非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());
            ChannelFuture f = bootstrap.bind(port).sync();
            System.out.println("server start done.");
            Channel channel = f.channel();
            ChannelFuture channelFuture = channel.closeFuture();
            channelFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
