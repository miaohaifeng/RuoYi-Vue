package com.ruoyi.websocket.enums;

public enum ExchangeEnum {
    GATE_IO("gate.io"),
    BINANCE("binance"),
    OKX("okx");

    private final String name;

    ExchangeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}