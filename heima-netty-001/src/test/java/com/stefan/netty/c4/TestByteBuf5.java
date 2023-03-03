package com.stefan.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
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
public class TestByteBuf5 {
    public static void main(String[] args) {

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();

        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});

        ByteBuf slice = buf.slice();
        slice.retain();
        log(slice);

        ByteBuf s1 = buf.slice(0, 5);
        s1.retain();
        log(s1);

        ByteBuf s2 = buf.slice(5, 5);
        s2.retain();
        log(s2);

        buf.release();

        log(slice);
        log(s1);
        log(s2);

        slice.release();
//        s1.release();
//        s2.release();

        System.out.println("====================================================");

        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();

        ByteBuf b1 = ByteBufAllocator.DEFAULT.buffer().writeBytes(new byte[]{'a', 'b', 'c'});
        ByteBuf b2 = ByteBufAllocator.DEFAULT.buffer().writeBytes(new byte[]{'d', 'e', 'f', 'g'});

        compositeByteBuf.addComponents(true, b1, b2);
        log(compositeByteBuf);

        ByteBuf duplicate = compositeByteBuf.duplicate();

        log(duplicate);
        duplicate.setByte(1, 'x');
        log(compositeByteBuf);

        ByteBuf copy = compositeByteBuf.copy();
        log(copy);
        copy.setByte(1, 'b');
        log(compositeByteBuf);
        log(copy);

        System.out.println("====================================================");

        ByteBuf byteBuf = Unpooled.wrappedBuffer(s1, s2);
        log(byteBuf);
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
