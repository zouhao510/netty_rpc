package com.zouhao.nettynetwork.server;

import com.zouhao.nettynetwork.client.HeartBeatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class EchoServer {

    public static Channel start()throws Exception{
        EventLoopGroup group = null;
        EventLoopGroup worker = null;
        try{
            group = new NioEventLoopGroup(3);
            worker = new NioEventLoopGroup(3);
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group,worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(new InetSocketAddress(7979));
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS))
                            .addLast(new StringDecoder())
                            .addLast(new HeartBeatServerHandler());
                }
            });
            // 绑定结果
            ChannelFuture f = bootstrap.bind().sync();


            f.channel().closeFuture().sync();
            return f.channel();
        }finally {
            group.close();
            group.shutdownGracefully().sync();
            worker.close();
            worker.shutdownGracefully().sync();
        }
    }
}
