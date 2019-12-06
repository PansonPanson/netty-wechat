package com.imooc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实现客户端发送一个请求，服务器返回 hello netty
 * @author panson
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {

        // 定义一对线程组，用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
        // 主线程组
        ServerBootstrap serverBootstrap = null;
            EventLoopGroup bossGroup = new NioEventLoopGroup();

            // 从线程组,老板线程组会把任务丢给他，让手下线程组去做任务
            EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // netty 服务器的创建，ServerBootstrap 是一个启动类
            serverBootstrap = new ServerBootstrap();
            // 设置主从线程
            serverBootstrap.group(bossGroup, workerGroup)
                    // 设置 nio 的双向通道
                    .channel(NioServerSocketChannel.class)
                    // 子处理器，用于处理 workerGroup
                    .childHandler(new HelloServerInitializer());
            // 启动 server, 并且设置 8088 为启动的端口号，同时设置启动方式为同步
            ChannelFuture channelFuture =   serverBootstrap.bind(8088).sync();
            // 监听关闭的 channel，设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }







    }
}
