package com.imooc.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocket00FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.catalina.Pipeline;

/**
 * @description: websocket 初始化器
 * @author: Panson
 **/

public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline channelPipeline = ch.pipeline();

        // websocket 基于 HTTP 协议，所以要有 HTTP 编解码器
        channelPipeline.addLast(new HttpServerCodec());

        // 对写大数据流的支持
        channelPipeline.addLast(new ChunkedWriteHandler());

        // 对 HttpMessage 进行聚合， 聚合成 FullHttpRequest 或 FullHttpResponse
        // 几乎在 netty 中的编程，都会使用到此 Handler
        channelPipeline.addLast(new HttpObjectAggregator(1024 * 64));


        // websocket 服务器处理的协议，用于指定给客户端连接访问的路由：/ws
        // 本 handler 会帮你处理一些繁重复杂的事
        // 会帮你处理握手动作：handshaking(close, ping, pong), ping + pong = 心跳
        // 对于 websocket 来讲，都是以 frames 来传输的，不同的数据类型对应的 frames 也不同
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));


        // 自定义的 handler
        channelPipeline.addLast(null);





    }
}
