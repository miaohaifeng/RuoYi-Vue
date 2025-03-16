package com.ruoyi.websocket.handler;

import com.ruoyi.websocket.enums.ExchangeEnum;
import com.ruoyi.websocket.enums.LanguageEnum;

public interface WebSocketHandler {
    void connect();
    void subscribe(String channel);
    void subscribe(String channel, LanguageEnum language);
    void disconnect();
    ExchangeEnum getExchange();
}