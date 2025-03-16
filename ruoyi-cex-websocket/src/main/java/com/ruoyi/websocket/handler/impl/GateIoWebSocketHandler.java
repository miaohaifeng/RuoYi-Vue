package com.ruoyi.websocket.handler.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.websocket.config.GateIoConfig;
import com.ruoyi.websocket.domain.CexAnn;
import com.ruoyi.websocket.enums.ExchangeEnum;
import com.ruoyi.websocket.enums.LanguageEnum;
import com.ruoyi.websocket.enums.WebSocketTypeEnum;
import com.ruoyi.websocket.handler.WebSocketHandler;
import com.ruoyi.websocket.mapper.CexAnnMapper;
import com.ruoyi.websocket.utils.GateIoMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GateIoWebSocketHandler implements WebSocketHandler, org.springframework.web.socket.WebSocketHandler {
    private final WebSocketTypeEnum type;
    private final String wsUrl;
    private final String lang = "en";
    private WebSocketSession session;

    @Autowired
    private CexAnnMapper cexAnnMapper;

    public GateIoWebSocketHandler(WebSocketTypeEnum type) {
        this.type = type;
        this.wsUrl = GateIoConfig.getWsUrlByType(type);
    }

    public GateIoWebSocketHandler() {
        this.type = WebSocketTypeEnum.ANNOUNCEMENT;
        this.wsUrl = GateIoConfig.getWsUrlByType(type);
    }

    // 删除 String 类型参数的构造函数
    
    @Override
    public void connect() {
        try {
            WebSocketClient client = new StandardWebSocketClient();
            session = client.doHandshake(this, wsUrl).get();
            log.info("Connected to Gate.io WebSocket server: {}", wsUrl);
        } catch (Exception e) {
            log.error("Failed to connect to Gate.io WebSocket server: {}", wsUrl, e);
        }
    }

    @Override
    public void subscribe(String channel) {
        // 默认订阅中英文
        subscribe(channel, null);
    }

    @Override
    public void subscribe(String channel, LanguageEnum language) {
        if (session != null && session.isOpen()) {
            try {
                Map<String, Object> subscribeMessage = new HashMap<>();
                subscribeMessage.put("time", System.currentTimeMillis() / 1000);
                subscribeMessage.put("channel", channel);
                subscribeMessage.put("event", "subscribe");
                
                // 如果指定了语言，则只订阅指定语言
                if (language != null) {
                    subscribeMessage.put("payload", new String[]{language.getCode()});
                } else {
                    // 默认订阅中英文
                    subscribeMessage.put("payload", new String[]{LanguageEnum.EN.getCode(), LanguageEnum.ZH.getCode()});
                }

                session.sendMessage(new TextMessage(JSON.toJSONString(subscribeMessage)));
                log.info("Subscribed to channel: {} with language: {}", channel, 
                    language != null ? language.getCode() : "en,zh");
            } catch (Exception e) {
                log.error("Failed to subscribe to channel: {}", channel, e);
            }
        }
    }

    @Override
    public void disconnect() {
        if (session != null && session.isOpen()) {
            try {
                session.close();
                log.info("Disconnected from Gate.io WebSocket server");
            } catch (Exception e) {
                log.error("Failed to disconnect from Gate.io WebSocket server", e);
            }
        }
    }

    @Override
    public ExchangeEnum getExchange() {
        return ExchangeEnum.GATE_IO;
    }

    // Spring WebSocketHandler 接口必需的方法
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connection established with Gate.io");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("Received message from Gate.io: {}", message.getPayload());
        CexAnn cexAnn = GateIoMessageConverter.convertAnnouncementMessage((String) message.getPayload());
        if (cexAnn != null) {
            CexAnn old =   cexAnnMapper.selectCexAnnByExchangeIdAndChannelAndTimeMs(ExchangeEnum.GATE_IO.getName(),cexAnn.getChannel(),cexAnn.getTimeMs());
            if (old == null) {
                cexAnnMapper.insertCexAnn(cexAnn);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Transport error occurred", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("Connection closed with status: {}", closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}