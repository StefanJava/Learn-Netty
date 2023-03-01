package com.stefan.nio.c4;

import com.stefan.nio.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 多线程多路复用
 * @author: stefanyang
 * @date: 2023/3/1 13:22
 * @version: 1.0
 */
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(9092));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

//            Worker worker = new Worker("worker - 0");
            Worker[] workers = new Worker[2];
            for (int i = 0; i < workers.length; i++) {
                workers[i] = new Worker("worker - " + i);
            }
            AtomicInteger atomicInteger = new AtomicInteger();
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();
                        // 调用工作线程处理
                        if (atomicInteger.get() >= Integer.MAX_VALUE) {
                            atomicInteger.set(0);
                        }
                        int index = atomicInteger.getAndIncrement() % workers.length;
                        log.info("worker - " + index);
                        workers[index].register(sc);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Worker extends Thread {


        private Selector selector;
        private String name;

        private volatile Boolean flag = false;

        private volatile ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();


        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel socketChannel) {

            try {
                if (!flag) {
                    this.selector = Selector.open();
                    this.setName(name);
                    this.start();
                    flag = true;
                }
                queue.add(() -> {
                    try {
                        log.info("thread {}", this.getName());
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                selector.wakeup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                    if (!queue.isEmpty() && queue.peek() != null) {
                        Runnable runnable = queue.poll();
                        runnable.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            while (sc.read(buffer) > 0) {
                                buffer.flip();
                                ByteBufferUtil.debugRead(buffer);
                                buffer.clear();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
