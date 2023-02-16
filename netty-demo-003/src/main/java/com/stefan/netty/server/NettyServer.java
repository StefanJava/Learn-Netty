package com.stefan.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description: netty server
 * @author: stefanyang
 * @date: 2023/2/16 16:32
 * @version: 1.0
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bing(8083);
    }

    private void bing(int port) {

        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("server start done.");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }

}
