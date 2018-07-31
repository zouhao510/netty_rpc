package com.zouhao.nettyserver;

import com.zouhao.nettynetwork.client.HeartBeatClient;
import com.zouhao.nettynetwork.server.HeartBeatServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyserverApplication {

    public static void main(String[] args) {

        SpringApplication.run(NettyserverApplication.class);

        //启动nettyclien
        HeartBeatServer server = new HeartBeatServer();
        try {
            server.start(12345);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
