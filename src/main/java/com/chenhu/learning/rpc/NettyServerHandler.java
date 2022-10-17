package com.chenhu.learning.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * @author 陈虎
 * @description
 * @date 2022-10-13 9:30
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client msg is:"+msg);
        //调用方法
        String[] a=msg.toString().split("#");
        if(a.length!=3){
            return;
        }
        if(!"HelloService".equals(a[0])){
            return;
        }
        HelloService helloService=new HelloServiceImpl();
        String methodName=a[1];
        String params=a[2];
        Method m=helloService.getClass().getMethod(methodName,String.class);
        if(ObjectUtils.isEmpty(m)){
            return;
        }
        ctx.writeAndFlush(m.invoke(helloService,params));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
