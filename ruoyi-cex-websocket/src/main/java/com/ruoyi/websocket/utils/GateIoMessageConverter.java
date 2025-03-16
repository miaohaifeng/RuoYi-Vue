
package com.ruoyi.websocket.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.websocket.domain.CexAnn;
import com.ruoyi.websocket.enums.ExchangeEnum;

import java.util.Date;

public class GateIoMessageConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static CexAnn convertAnnouncementMessage(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            JsonNode result = root.get("result");

            if (result == null) {
                return null;
            }

            CexAnn cexAnn = new CexAnn();

            // 设置基本信息
            cexAnn.setExchangeId(ExchangeEnum.GATE_IO.getName());
            cexAnn.setChannel(root.path("channel").asText());
            cexAnn.setTimeMs(root.path("time_ms").asLong());
            cexAnn.setEvent(root.path("event").asText());
            cexAnn.setRawData(message);

            // 设置公告具体内容
            cexAnn.setLang(result.path("lang").asText());
            cexAnn.setOriginUrl(result.path("origin_url").asText());
            cexAnn.setTitle(result.path("title").asText());
            cexAnn.setBrief(result.path("brief").asText());

            // 设置时间相关字段
            cexAnn.setPublishedAt(String.valueOf(root.path("time").asLong()));
            cexAnn.setCreateTime(new Date());
            cexAnn.setUpdateTime(new Date());

            return cexAnn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isAnnouncementMessage(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            String channel = root.path("channel").asText();
            return "announcement.summary_listing".equals(channel);
        } catch (Exception e) {
            return false;
        }
    }
}