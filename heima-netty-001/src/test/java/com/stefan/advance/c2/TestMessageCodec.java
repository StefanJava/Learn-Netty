package com.stefan.advance.c2;

import com.stefan.netty.chat.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @description: 测试消息编解码器
 * @author: stefanyang
 * @date: 2023/3/7 11:36
 * @version: 1.0
 */
public class TestMessageCodec {

    public static void main(String[] args) throws Exception {
//        MessageCodec messageCodec = new MessageCodec();
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                loggingHandler,
                messageCodec
        );

        LoginRequestMessage loginRequestMessage = new LoginRequestMessage("Stefan", "123456");

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,loginRequestMessage, buf);

        ByteBuf buf1 = buf.slice(0, 50);
        ByteBuf buf2 = buf.slice(50, buf.readableBytes() - 50);
        buf1.retain();
        buf2.retain();
        channel.writeInbound(buf1);
        channel.writeInbound(buf2);
//        channel.writeInbound(buf);
    }

}
