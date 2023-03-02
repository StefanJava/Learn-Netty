package com.stefan.netty.c3;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @description: netty Promise
 * @author: stefanyang
 * @date: 2023/3/2 18:30
 * @version: 1.0
 */
@Slf4j
public class TestNettyPromise {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2);
        DefaultPromise<Integer> promise = new DefaultPromise<>(group.next());

        new Thread(() -> {
            try {
                log.debug("执行任务");
                TimeUnit.MILLISECONDS.sleep(1000);
                promise.setSuccess(500);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();

        // 异步接收
        promise.addListener(future -> {
            log.debug("接收结果: {}", future.get());
            group.shutdownGracefully();
        });

        log.debug("main end");
    }
}
