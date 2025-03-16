package com.ruoyi.websocket;

import com.ruoyi.websocket.enums.ExchangeEnum;
import com.ruoyi.websocket.enums.LanguageEnum;
import com.ruoyi.websocket.enums.WebSocketTypeEnum;
import com.ruoyi.websocket.service.WebSocketService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
@SpringBootApplication(scanBasePackages = {"com.ruoyi"})
@MapperScan({"com.ruoyi.**.mapper"})
public class WebSocketApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebSocketApplication.class, args);

        WebSocketService webSocketService = context.getBean(WebSocketService.class);

        webSocketService.initHandler(ExchangeEnum.GATE_IO, WebSocketTypeEnum.ANNOUNCEMENT);
        // 基础订阅
        webSocketService.subscribeChannel(ExchangeEnum.GATE_IO, WebSocketTypeEnum.ANNOUNCEMENT, "announcement.summary_listing");

        // 带语言订阅
        webSocketService.subscribeChannelWithLang(ExchangeEnum.GATE_IO, WebSocketTypeEnum.ANNOUNCEMENT, "announcement.summary_listing", LanguageEnum.EN);
    }
}