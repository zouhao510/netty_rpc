package com.zouhao.nettynetwork.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

/**
 * 心跳检测服务器端
 */
public class HeartBeatServer {

    public static void main(String[] args)throws Exception{

        start(123456);
    }


    public static void start(int port)throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(3);

        ServerBootstrap b = new ServerBootstrap();
        b.group(group,worker)
                .localAddress(new InetSocketAddress(port))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(10,0,0))
                                .addLast(new LengthFieldBasedFrameDecoder(1024,0,4,-4,0))
                                .addLast(new HeartBeatServerHandler());
                    }
                });
        Channel channel = b.bind().channel();
        channel.closeFuture().sync();
    }


}
