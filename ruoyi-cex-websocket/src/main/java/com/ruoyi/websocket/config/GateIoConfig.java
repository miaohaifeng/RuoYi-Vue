package com.ruoyi.websocket.config;

import com.ruoyi.websocket.enums.WebSocketTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateIoConfig {
    public static final String ANNOUNCEMENT_WS_URL = "wss://api.gateio.ws/ws/v4/ann";
    public static final String FUTURES_WS_URL = "wss://fx-ws.gateio.ws/v4/ws/usdt";
    
    public static String getWsUrlByType(WebSocketTypeEnum type) {
        switch (type) {
            case ANNOUNCEMENT:
                return ANNOUNCEMENT_WS_URL;
            case FUTURES:
                return FUTURES_WS_URL;
            default:
                throw new IllegalArgumentException("Unknown WebSocket type: " + type);
        }
    }
}