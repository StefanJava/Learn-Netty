package com.stefan.netty.client;

import com.stefan.netty.server.NettyServer;

/**
 * @description: netty client
 * @author: stefanyang
 * @date: 2023/2/20 16:28
 * @version: 1.0
 */
public class NettyClient {
    public static void main(String[] args) {
        new NettyServer().connect("127.0.0.1", 8087);
    }
}
