package com.stefan.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @description: ByteBuf
 * @author: stefanyang
 * @date: 2023/3/3 09:44
 * @version: 1.0
 */
@Slf4j
public class TestByteBuf3 {
    public static void main(String[] args) {
//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();  // 使用直接内存
        // 参数 -Dio.netty.allocator.type= unpooled | pooled  4.1版本后默认开启  Android平台默认不开启
//        ByteBuf buf = ByteBufAllocator.DEFAULT.directBuffer();  // 使用直接内存
        ByteBuf buf = ByteBufAllocator.DEFAULT.heapBuffer();  // 使用堆内存
        log.debug("{}", buf);
        log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
        log(buf);
    }

    private static void log(ByteBuf buf) {
        int length = buf.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder sb = new StringBuilder(rows * 80 * 2)
                .append("read index: ").append(buf.readerIndex())
                .append(" write index: ").append(buf.writerIndex())
                .append(" capacity: ").append(buf.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(sb, buf);
        System.out.println(sb);
    }

}
