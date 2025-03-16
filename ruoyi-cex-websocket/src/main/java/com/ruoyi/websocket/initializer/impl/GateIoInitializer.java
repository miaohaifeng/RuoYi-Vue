package com.ruoyi.websocket.initializer.impl;

import com.ruoyi.websocket.enums.LanguageEnum;
import com.ruoyi.websocket.handler.impl.GateIoWebSocketHandler;
import com.ruoyi.websocket.initializer.WebSocketInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GateIoInitializer implements WebSocketInitializer {
    
    private final GateIoWebSocketHandler handler;

    public GateIoInitializer(GateIoWebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void initialize() {
        try {
            handler.connect();
            handler.subscribe("announcement.summary_listing");
            handler.subscribe("announcement.summary_listing", LanguageEnum.EN);
            log.info("Gate.io WebSocket 初始化完成");
        } catch (Exception e) {
            log.error("Gate.io WebSocket 初始化失败", e);
        }
    }
}