package com.stefan.nio.c1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @description: transfer to
 * @author: stefanyang
 * @date: 2023/2/23 14:40
 * @version: 1.0
 */
public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (FileChannel from = new RandomAccessFile("heima-netty-001/data.txt", "rw").getChannel();
             FileChannel to = new RandomAccessFile("heima-netty-001/to.txt", "rw").getChannel();
        ) {
            long size = from.size();
            for (long left = size; left > 0; ) {
                left -= from.transferTo((size - left), left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
