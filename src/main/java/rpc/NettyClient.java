package rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈虎
 * @description
 * @date 2022-10-13 10:39
 */
public class NettyClient {
    private static final int nThread=Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor executor=new ThreadPoolExecutor(nThread,nThread,0L, TimeUnit.MICROSECONDS,new LinkedBlockingDeque<>());
    private static NettyClientHandler clientHandler;

    public static Object getBean(final Class<?> serviceClass){
        return Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, (proxy, method, args) -> {
            if(clientHandler==null){
                initClient();
            }
            String params=String.format(RpcConstants.PREFIX,method.getName(),args[0].toString());
            clientHandler.setParams(params);
            return executor.submit(clientHandler).get();
        });
    }

    public static void initClient(){
        clientHandler=new NettyClientHandler();
        NioEventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(clientHandler);
                        }
                    });
            bootstrap.connect("localhost",8899).sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloService helloService= (HelloService) getBean(HelloService.class);
        System.out.println("RPC result: "+helloService.hello("chenhu"));
        System.out.println("RPC result: "+helloService.log("chenhu"));
    }
}
