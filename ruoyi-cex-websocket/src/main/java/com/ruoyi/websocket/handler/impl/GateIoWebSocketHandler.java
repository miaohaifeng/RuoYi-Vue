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
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GateIoWebSocketHandler extends TextWebSocketHandler implements WebSocketHandler {

    private final WebSocketTypeEnum type;
    private final String wsUrl;
    private WebSocketSession session;
    private final CexAnnMapper cexAnnMapper;
    private final WebSocketClient webSocketClient;

    public GateIoWebSocketHandler(CexAnnMapper cexAnnMapper, WebSocketClient webSocketClient) {
        this.cexAnnMapper = cexAnnMapper;
        this.webSocketClient = webSocketClient;
        this.type = WebSocketTypeEnum.ANNOUNCEMENT;
        this.wsUrl = GateIoConfig.getWsUrlByType(type);
    }

    @Override
    public void connect() {
        try {
            session = webSocketClient.doHandshake(this, wsUrl).get();
            log.info("Connected to Gate.io WebSocket server: {}", wsUrl);
        } catch (Exception e) {
            log.error("Failed to connect to Gate.io WebSocket server: {}", wsUrl, e);
        }
    }

    @Override
    public void subscribe(String channel) {
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

                if (language != null) {
                    subscribeMessage.put("payload", new String[]{language.getCode()});
                } else {
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

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connection established with Gate.io");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (cexAnnMapper == null) {
            throw new IllegalStateException("CexAnnMapper 未正确注入");
        }

        log.info("Received message from Gate.io: {}", message.getPayload());
        CexAnn cexAnn = GateIoMessageConverter.convertAnnouncementMessage((String) message.getPayload());
        if (cexAnn != null) {
            CexAnn old = cexAnnMapper.selectCexAnnByExchangeIdAndChannelAndTimeMs(
                    ExchangeEnum.GATE_IO.getName(),
                    cexAnn.getChannel(),
                    cexAnn.getTimeMs()
            );
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