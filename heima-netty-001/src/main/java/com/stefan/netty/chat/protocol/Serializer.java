package com.stefan.netty.chat.protocol;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @description: 序列化接口
 * @author: stefanyang
 * @date: 2023/3/9 15:57
 * @version: 1.0
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param obj 序列化对象
     * @return 序列化结果
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     *
     * @param clazz 对象类型
     * @param bytes 字节数组
     * @return 反序列化结果
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    enum Algorithm implements Serializer {

        /**
         * 使用 jdk 序列化方法
         */
        JAVA {
            @Override
            public <T> byte[] serialize(T obj) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(obj);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败", e);
                }
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败", e);
                }
            }
        },

        /**
         * 使用 Google Gson 序列化方法
         */
        GSON {
            @Override
            public <T> byte[] serialize(T obj) {
                String str = new Gson().toJson(obj);
                return str.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                return new Gson().fromJson(json, clazz);
            }
        }

    }

}
