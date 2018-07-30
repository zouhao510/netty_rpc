package com.zouhao.nettyserver;

import com.zouhao.nettynetwork.server.EchoServer;
import io.netty.channel.Channel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyserverApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(NettyserverApplication.class);

            // 启动netty server
            Channel channel = EchoServer.start();

            run(channel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run(Channel channel) throws Exception {
        // do nothing
        channel.closeFuture().sync();
        System.out.println("server run");
    }


}
