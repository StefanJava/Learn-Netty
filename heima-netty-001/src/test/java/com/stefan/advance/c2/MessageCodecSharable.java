package com.stefan.advance.c2;

import com.stefan.netty.chat.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @description: 可共享 ** 必须和黏包半包处理器(io.netty.handler.codec.LengthFieldBasedFrameDecoder)结合使用
 * @author: stefanyang
 * @date: 2023/3/7 11:33
 * @version: 1.0
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        // 编码   由msg -> buf
        ByteBuf buf = ctx.alloc().buffer();
        // 魔数 - 4个字节
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        // 版本号 - 1个字节
        buf.writeByte(1);
        // 序列化种类 - 1个字节
        buf.writeByte(0);
        // 指令类型 - 1个字节
        buf.writeByte(msg.getMessageType());
        // 消息序号 - 4个字节
        buf.writeInt(msg.getSequenceId());
        // 无意义，对齐填充
        buf.writeByte(0xff);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        // 内容长度 - 4个字节
        buf.writeInt(bytes.length);
        // 写入内容
        buf.writeBytes(bytes);
        out.add(buf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 解码  由 buf -> msg
        // 魔数 - 4个字节
        int magicNumber = msg.readInt();
        // 版本号 - 1个字节
        int version = msg.readByte();
        // 序列化种类 - 1个字节
        int serialType = msg.readByte();
        // 指令类型 - 1个字节
        int messageType = msg.readByte();
        // 消息序号 - 4个字节
        int sequenceId = msg.readInt();
        // 无意义，对齐填充
        msg.readByte();
        // 内容长度
        int length = msg.readInt();
        // 内容
        byte[] bytes = new byte[length];
        msg.readBytes(bytes, 0, length);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) ois.readObject();
        log.debug("魔数: {}", magicNumber);
        log.debug("版本号: {}", version);
        log.debug("序列化种类: {}", serialType);
        log.debug("指令类型: {}", messageType);
        log.debug("消息序号: {}", sequenceId);
        log.debug("内容长度: {}", length);
        log.debug("消息内容: {}", message);
    }
}
