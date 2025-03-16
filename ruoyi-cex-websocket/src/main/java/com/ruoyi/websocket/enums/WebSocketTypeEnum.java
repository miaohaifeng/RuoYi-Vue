package com.ruoyi.websocket.enums;

public enum WebSocketTypeEnum {
    ANNOUNCEMENT("announcement"),
    FUTURES("futures");

    private final String type;

    WebSocketTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}