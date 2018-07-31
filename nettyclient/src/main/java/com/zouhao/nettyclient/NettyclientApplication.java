package com.zouhao.nettyclient;

import com.zouhao.nettynetwork.client.HeartBeatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyclientApplication {

    public static void main(String[] args) {

        SpringApplication.run(NettyclientApplication.class, args);

        //启动nettyclien
        HeartBeatClient client = new HeartBeatClient();
        try{
            client.start();
            client.sendData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
