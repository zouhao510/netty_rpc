package com.zouhao.nettynetwork.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatServerHandler extends ChannelHandlerAdapter {

    private int lossConnectCount = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state() == IdleState.READER_IDLE){
                lossConnectCount++;
                if(lossConnectCount > 2){
                    System.out.println("关闭这个不活跃的通道");
                    ctx.channel().close();
                }
            }
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        lossConnectCount = 0;
        System.out.println("client says: " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
