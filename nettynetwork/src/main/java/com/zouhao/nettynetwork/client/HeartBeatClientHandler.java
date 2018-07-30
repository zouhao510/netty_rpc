package com.zouhao.nettynetwork.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

public class HeartBeatClientHandler extends ChannelHandlerAdapter {

    private int beatTime = 10;
    private int curTime = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        System.out.println("客户端循环心跳监测发送：" + new Date());
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.WRITER_IDLE){
                if(curTime < beatTime){
                    curTime++;
                    ctx.writeAndFlush("biubiu");
                }
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        curTime++;
    }
}
