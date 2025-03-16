package com.ruoyi.websocket.config;

import com.ruoyi.websocket.enums.WebSocketTypeEnum;

public class BinanceConfig {
    private static final String WS_BASE_URL = "wss://stream.binance.com:9443/ws";
    
    public static String getWsUrlByType(WebSocketTypeEnum type) {
        return WS_BASE_URL;
    }
}