package com.example.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * WebSocket通道连接初始化
 */
@Component
public class NioWebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WebSocketProperties webSocketProperties;
    @Autowired
    private NioWebSocketHandler nioWebSocketHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                //添加了一个 HTTP 服务器编解码器，将请求和响应消息编码或解码为 HTTP 消息。
                .addLast(new HttpServerCodec())
                //添加了一个用于处理大数据流的处理器，支持异步发送大的数据块，例如文件传输。
                .addLast(new ChunkedWriteHandler())
                // 添加了一个 HTTP 消息聚合器，将多个消息部分聚合成一个完整的 `FullHttpRequest` 或 `FullHttpResponse`，解决了分块传输的问题。
                .addLast(new HttpObjectAggregator(8192))
                //- 添加了自定义的 `NioWebSocketHandler` 处理器，用于处理 WebSocket 相关的业务逻辑。负责处理连接建立、消息收发等WebSocket相关的操作。
                .addLast(nioWebSocketHandler)
                //添加了一个 WebSocket 服务器协议处理器，用于处理 WebSocket 握手、心跳以及关闭帧。
                .addLast(new WebSocketServerProtocolHandler(webSocketProperties.getPath(), null, true, 65536));
    }
}
