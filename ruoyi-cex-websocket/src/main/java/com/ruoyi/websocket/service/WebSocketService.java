package com.ruoyi.websocket.service;

import com.ruoyi.websocket.enums.ExchangeEnum;
import com.ruoyi.websocket.enums.WebSocketTypeEnum;
import com.ruoyi.websocket.handler.WebSocketHandler;
import com.ruoyi.websocket.handler.impl.GateIoWebSocketHandler;
import com.ruoyi.websocket.enums.LanguageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class WebSocketService {
    private final Map<ExchangeEnum, Map<WebSocketTypeEnum, WebSocketHandler>> handlers = new ConcurrentHashMap<>();

    public void initHandler(ExchangeEnum exchange, WebSocketTypeEnum type) {
        GateIoWebSocketHandler handler = new GateIoWebSocketHandler(type);
        handlers.computeIfAbsent(exchange, k -> new ConcurrentHashMap<>())
                .put(type, handler);
        handler.connect();
    }

    // 重命名方法以避免混淆
    public void subscribeChannelWithLang(ExchangeEnum exchange, WebSocketTypeEnum type, String channel, LanguageEnum language) {
        WebSocketHandler handler = handlers.get(exchange).get(type);
        if (handler != null) {
            handler.subscribe(channel, language);
        } else {
            log.error("Handler not found for exchange: {}, type: {}", exchange, type);
        }
    }

    // 基础订阅方法
    public void subscribeChannel(ExchangeEnum exchange, WebSocketTypeEnum type, String channel) {
        WebSocketHandler handler = handlers.get(exchange).get(type);
        if (handler != null) {
            handler.subscribe(channel);
        } else {
            log.error("Handler not found for exchange: {}, type: {}", exchange, type);
        }
    }

    // 带交易对的订阅方法
    public void subscribeChannelWithSymbol(ExchangeEnum exchange, WebSocketTypeEnum type, String channel, String symbol) {
        WebSocketHandler handler = handlers.get(exchange).get(type);
        if (handler != null) {
            handler.subscribe(channel + ":" + symbol);
        } else {
            log.error("Handler not found for exchange: {}, type: {}", exchange, type);
        }
    }
}