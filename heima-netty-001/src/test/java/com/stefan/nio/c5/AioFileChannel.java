package com.stefan.nio.c5;

import com.stefan.nio.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @description: 文件AIO
 * @author: stefanyang
 * @date: 2023/2/28 20:17
 * @version: 1.0
 */
@Slf4j
public class AioFileChannel {
    public static void main(String[] args) {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("heima-netty-001/data.txt"), StandardOpenOption.READ)) {

            ByteBuffer buffer = ByteBuffer.allocate(16);

            log.info("read start");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.info("reading... {}", result);
                    attachment.flip();
                    ByteBufferUtil.debugAll(attachment);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {

                }
            });
            log.info("read end");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
