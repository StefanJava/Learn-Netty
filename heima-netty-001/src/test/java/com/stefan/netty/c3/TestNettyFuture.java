package com.stefan.netty.c3;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: netty future
 * @author: stefanyang
 * @date: 2023/3/2 18:23
 * @version: 1.0
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup(2);

        Future<Integer> future = group.submit(() -> {
            log.debug("执行任务");
            TimeUnit.MILLISECONDS.sleep(1000);
            return 500;
        });

        /*log.debug("接收结果: {}", future.get());

        group.shutdownGracefully();*/

        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("接收结果: {}", future.get());
                group.shutdownGracefully();
            }
        });

        log.debug("main end");
    }
}
