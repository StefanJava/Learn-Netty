package com.stefan.netty.bio.client;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @description: bio客户端
 * @author: stefanyang
 * @date: 2023/2/13 14:02
 * @version: 1.0
 */
public class BioClient {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 7398);
            System.out.println("BIO client start done");
            BioClientHandler handler = new BioClientHandler(socket, StandardCharsets.UTF_8);
            handler.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
