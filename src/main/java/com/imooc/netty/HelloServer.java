package com.imooc.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 实现客户端发送一个请求，服务器返回 hello netty
 * @author panson
 */
public class HelloServer {

    public static void main(String[] args) {

        // 定义一对线程组
        // 主线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
    }
}
