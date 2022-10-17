package com.chenhu.learning.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈虎
 * @description
 * @date 2022-10-13 10:22
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<Object> {
    private final Lock lock=new ReentrantLock();
    private final Condition condition=lock.newCondition();
    private ChannelHandlerContext ctx;
    private String result;
    private String params;
    void setParams(String params){
        this.params=params;
    }

    @Override
    public Object call() throws Exception {
        lock.lock();
        try{
            ctx.writeAndFlush(params);
            condition.await();
            return result;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx=ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        lock.lock();
        try{
            result=msg.toString();
            condition.signalAll();
        }  finally {
            lock.unlock();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
