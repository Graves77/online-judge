package com.example.netty;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * WebSocket 配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "chat.websocket")
public class WebSocketProperties {
    /**
     * 监听端口
     */
    private Integer port = 8080;
    /**
     * 请求路径
     */
    private String path = "/ws"; // 请求路径
    private Integer boss = 2; // bossGroup线程数
    private Integer work = 2; // workGroup线程数

}
