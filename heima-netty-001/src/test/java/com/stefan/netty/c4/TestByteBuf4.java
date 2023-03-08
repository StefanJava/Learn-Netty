package com.stefan.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @description: ByteBuf
 * @author: stefanyang
 * @date: 2023/3/3 09:44
 * @version: 1.0
 */
@Slf4j
public class TestByteBuf4 {
    public static void main(String[] args) {
//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();  // 使用直接内存
        // 参数 -Dio.netty.allocator.type= unpooled | pooled  4.1版本后默认开启  Android平台默认不开启
//        ByteBuf buf = ByteBufAllocator.DEFAULT.directBuffer();  // 使用直接内存
        ByteBuf buf = ByteBufAllocator.DEFAULT.heapBuffer();  // 使用堆内存

        buf.writeByte('a');

        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f'});
        System.out.println((char) buf.readByte());
//        buf.markReaderIndex();
        System.out.println((char) buf.readByte());
        System.out.println((char) buf.readByte());
        System.out.println((char) buf.readByte());
        System.out.println((char) buf.readByte());
        log(buf);
//        buf.resetReaderIndex();
        System.out.println((char) buf.readByte());
        System.out.println((char) buf.readByte());
        log(buf);
        buf.writeInt(24);
        buf.writeInt(25);
        buf.writeInt(26);
        log(buf);
        buf.writeIntLE(27);
        buf.writeIntLE(28);
        log(buf);

        ByteBuf byteBuf = buf.writerIndex(19);
        log(byteBuf);
        buf.writeByte('b');
        System.out.println(buf.writerIndex());
        System.out.println(byteBuf.writerIndex());
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
