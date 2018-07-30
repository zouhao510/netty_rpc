package com.zouhao.nettynetwork.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class EchoClient {

    public static Channel start()throws Exception{
        EventLoopGroup group = null;
        try{
            group = new NioEventLoopGroup(3);
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(new InetSocketAddress("127.0.0.1", 7979));
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(0,4,0, TimeUnit.SECONDS))
                            .addLast(new StringDecoder())
                            .addLast(new HeartBeatClientHandler());
                }
            });
            final ChannelFuture f = bootstrap.connect().sync();
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (f.isSuccess()) {
                        System.out.println("success");
                    } else {
                        Throwable cause = f.cause();
                        throw new RuntimeException(cause);
                    }
                }
            });

            return f.channel();
        }finally {
            group.close();
            group.shutdownGracefully().sync();
        }
    }
}
