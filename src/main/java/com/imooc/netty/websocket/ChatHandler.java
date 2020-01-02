package com.imooc.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;

/**
 * @description: 处理消息的 handler
 * TextWebSocketFrame：在 netty 中，是用于为 websocket 专门处理文本的对象， frame 是消息的载体
 * @author: Panson
 **/

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 用于记录和管理所有客户端的 channel
     */
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String content = msg.text();
        System.out.println("接受到的数据：" + content);

        for (Channel channel : clients) {
            channel.writeAndFlush(new TextWebSocketFrame("[服务器接收到消息：]" + LocalDateTime.now() + "接收到消息，消息为：" + content));
        }

    }

    /**
     * 当客户端连接服务端之后（打开链接）
     * 获取客户端的 channel，并且放到 ChannelGroup 中去进行管理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发 handlerRemoved, ChannelGroup 会自动移除对应客户端的 channel
        System.out.println("客户端断开，channel 对应的长 id 为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开， channel 对应的短 id 为：" + ctx.channel().id().asShortText());
    }
}
