package com.stefan.netty.c3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @description: jdk future
 * @author: stefanyang
 * @date: 2023/3/2 18:17
 * @version: 1.0
 */
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 提交一个任务
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                try {
                    log.debug("执行任务。。。");
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 500;
            }
        });

        log.debug("等待结果");
        // 接收结果
        log.debug("接收结果: {}", future.get());

        executorService.shutdown();
    }
}
