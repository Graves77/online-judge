package com.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Netty服务端
 */
@Slf4j
@Component
public class NioWebSocketServer implements InitializingBean, DisposableBean {

    @Autowired
    private WebSocketProperties webSocketProperties;
    @Autowired
    private NioWebSocketChannelInitializer webSocketChannelInitializer;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ChannelFuture channelFuture;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // 创建 boss 和 work 线程池，用于处理连接请求和处理业务逻辑
            bossGroup = new NioEventLoopGroup(webSocketProperties.getBoss());
            workGroup = new NioEventLoopGroup(webSocketProperties.getWork());

            // 创建 Netty 服务器引导类
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 配置服务器参数
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(webSocketProperties.getPort())
                    .childHandler(webSocketChannelInitializer);

            // 绑定端口并启动服务器
            log.info("Netty Port : {}",webSocketProperties.getPort());
            channelFuture = serverBootstrap.bind().sync();
        } finally {
            // 在 finally 块中确保资源正确关闭
            if (channelFuture != null && channelFuture.isSuccess()) {
                log.info("Netty server startup on port: {} (websocket) with context path '{}'", webSocketProperties.getPort(), webSocketProperties.getPath());
            } else {
                log.error("Netty server startup failed.");

                // 如果启动失败，关闭线程池
                if (bossGroup != null)
                    bossGroup.shutdownGracefully().sync();
                if (workGroup != null)
                    workGroup.shutdownGracefully().sync();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("Shutting down Netty server...");
        if (bossGroup != null)
            bossGroup.shutdownGracefully().sync();
        if (workGroup != null)
            workGroup.shutdownGracefully().sync();
        if (channelFuture != null)
            channelFuture.channel().closeFuture().syncUninterruptibly();
        log.info("Netty server shutdown.");
    }
}
