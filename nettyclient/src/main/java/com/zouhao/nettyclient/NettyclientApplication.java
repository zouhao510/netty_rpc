package com.zouhao.nettyclient;

import com.zouhao.nettynetwork.client.EchoClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class NettyclientApplication {

    public static void main(String[] args) {

        SpringApplication.run(NettyclientApplication.class, args);
        try{
            Channel channel = EchoClient.start();
            start(channel);
        }catch (Exception e){

        }
    }

    public static void start(Channel channel){
        int index = 1;
        int interval = 1000;
        while (index <= 25) {
            try {
                ByteBuf delimiter = Unpooled.copiedBuffer(",".getBytes());
                System.out.println("the "+ index +" Msg send");
                // 生成协议中的信息

                // 当被通知Channel是活跃的时候，发送一条消息
                channel.writeAndFlush("xxxxxx"+ new Date().toGMTString());
                channel.writeAndFlush(delimiter);

//                channel.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
                index += 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
