package com.stefan.netty.c2;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @description: EventLoopGroup
 * @author: stefanyang
 * @date: 2023/3/1 17:37
 * @version: 1.0
 */
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2);  // IO任务 普通任务 定时任务
//        EventLoopGroup defaultGroup = new DefaultEventLoopGroup(2);  // 处理 普通任务 定时任务

        log.info("{}", group.next());
        log.info("{}", group.next());

        // 普通任务
        group.next().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                log.info("task {}", Thread.currentThread());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        log.info("main ... {}", Thread.currentThread());

        // 定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.info("fix fix fix");
        }, 0, 2, TimeUnit.SECONDS);

        log.info("endding");
    }
}
