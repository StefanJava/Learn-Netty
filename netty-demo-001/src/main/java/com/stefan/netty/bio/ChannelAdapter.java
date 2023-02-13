package com.stefan.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @description: 适配器
 * @author: stefanyang
 * @date: 2023/2/13 14:14
 * @version: 1.0
 */
public abstract class ChannelAdapter extends Thread {

    private Socket socket;
    private ChannelHandler channelHandler;
    private Charset charset;

    public ChannelAdapter(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
        while (!socket.isConnected()) {
            break;
        }
        channelHandler = new ChannelHandler(socket, charset);
        channelActive(channelHandler);
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String str = null;
            while ((str = input.readLine()) != null) {
                channelRead(channelHandler, str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述:链接通知抽象方法
     * @param: ch 处理对象
     * @return: void 返回值
     * @auther: stefanyang
     * @date: 2023/2/13 17:05
     */
    public abstract void channelActive(ChannelHandler ch);

    /**
     * 功能描述:连接通知抽象方法
     * @param: ch 处理对象处理对象
     * @param: msg 消息
     * @return: void
     * @auther: stefanyang
     * @date: 2023/2/13 17:07
     */
    public abstract void channelRead(ChannelHandler ch, Object msg);
    
}
