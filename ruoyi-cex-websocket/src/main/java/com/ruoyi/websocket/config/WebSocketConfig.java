package com.ruoyi.websocket.config;
import com.ruoyi.websocket.handler.impl.GateIoWebSocketHandler;
import com.ruoyi.websocket.initializer.impl.GateIoInitializer;
import com.ruoyi.websocket.mapper.CexAnnMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class WebSocketConfig {
    
    private final GateIoInitializer gateIoInitializer;

    public WebSocketConfig(@Lazy GateIoInitializer gateIoInitializer) {
        this.gateIoInitializer = gateIoInitializer;
    }
    
    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }
    
    @Bean
    public GateIoWebSocketHandler gateIoWebSocketHandler(CexAnnMapper cexAnnMapper, WebSocketClient webSocketClient) {
        return new GateIoWebSocketHandler(cexAnnMapper, webSocketClient);
    }

    @PostConstruct
    public void init() {
        try {
            gateIoInitializer.initialize();
            log.info("Gate.io WebSocket 连接和订阅初始化完成");
        } catch (Exception e) {
            log.error("Gate.io WebSocket 初始化失败", e);
        }
    }
}