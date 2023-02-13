package com.stefan.netty.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @description: BIO Server
 * @author: stefanyang
 * @date: 2023/2/13 17:16
 * @version: 1.0
 */
public class BioServer extends Thread {

    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        BioServer bioServer = new BioServer();
        bioServer.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(7398);
            System.out.println("bio server start done");
            while (true) {
                Socket socket = serverSocket.accept();
                BioServerHandler handler = new BioServerHandler(socket, StandardCharsets.UTF_8);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
