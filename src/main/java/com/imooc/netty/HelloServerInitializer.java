package com.imooc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，channel 注册后，会执行里面的相应的初始化方法
 * 每一个 channel 由多个 handler 共同组成管道（pipeline）
 * @author panson
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过 SocketChannel 去获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();
        // 通过管道，添加 handler
        // HttpServerCodec 是由 netty 自己提供的助手类，可以理解为拦截器
        // 当请求到服务端，我们需要做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 添加自定义的助手类，返回 "hello netty~ "
        pipeline.addLast("customHandler", new CustomHandler());

    }
}
