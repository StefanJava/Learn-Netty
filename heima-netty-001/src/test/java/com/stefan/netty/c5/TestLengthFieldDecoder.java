package com.stefan.netty.c5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @description: LengthFieldDecoder
 * @author: stefanyang
 * @date: 2023/3/3 17:17
 * @version: 1.0
 */
public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        ChannelHandler h1 = new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 4);
        ChannelHandler h2 = new LoggingHandler(LogLevel.DEBUG);
        EmbeddedChannel channel = new EmbeddedChannel(h1, h2);

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

        create(buffer, "hello world");
        create(buffer, "zhangsan");
        create(buffer, "are you ok?");

        channel.writeInbound(buffer);
    }

    private static void create(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes();
        int len = bytes.length;
        buffer.writeInt(len);
        buffer.writeByte(17);
        buffer.writeBytes(bytes);
    }
}
