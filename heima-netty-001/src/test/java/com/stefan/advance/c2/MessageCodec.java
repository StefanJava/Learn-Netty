package com.stefan.advance.c2;

import com.stefan.netty.chat.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @description: 消息编解码器
 * @author: stefanyang
 * @date: 2023/3/7 11:32
 * @version: 1.0
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // 编码   由msg -> buf
        // 魔数 - 4个字节
        out.writeBytes(new byte[]{1, 2, 3, 4});
        // 版本号 - 1个字节
        out.writeByte(1);
        // 序列化种类 - 1个字节
        out.writeByte(0);
        // 指令类型 - 1个字节
        out.writeByte(msg.getMessageType());
        // 消息序号 - 4个字节
        out.writeInt(msg.getSequenceId());
        // 无意义，对齐填充
        out.writeByte(0xff);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        // 内容长度 - 4个字节
        out.writeInt(bytes.length);
        // 写入内容
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 解码  由 buf -> msg
        // 魔数 - 4个字节
        int magicNumber = in.readInt();
        // 版本号 - 1个字节
        int version = in.readByte();
        // 序列化种类 - 1个字节
        int serialType = in.readByte();
        // 指令类型 - 1个字节
        int messageType = in.readByte();
        // 消息序号 - 4个字节
        int sequenceId = in.readInt();
        // 无意义，对齐填充
        in.readByte();
        // 内容长度
        int length = in.readInt();
        // 内容
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
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
