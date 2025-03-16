package com.ruoyi.websocket.enums;

public enum LanguageEnum {
    EN("en"),
    ZH("cn");

    private final String code;

    LanguageEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}