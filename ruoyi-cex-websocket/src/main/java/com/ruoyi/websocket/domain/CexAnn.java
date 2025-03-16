package com.ruoyi.websocket.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 cex_ann
 *
 * @author ruoyi
 * @date 2025-03-15
 */
@Data
public class CexAnn extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     *
     */
    @Excel(name = "")
    private String exchangeId;

    /**
     *
     */
    @Excel(name = "")
    private String channel;

    /**
     *
     */
    @Excel(name = "")
    private long timeMs;

    /**
     *
     */
    @Excel(name = "")
    private String event;

    /**
     *
     */
    @Excel(name = "")
    private String rawData;

    /**
     *
     */
    @Excel(name = "")
    private String lang;

    /**
     *
     */
    @Excel(name = "")
    private String originUrl;

    /**
     *
     */
    @Excel(name = "")
    private String title;

    /**
     *
     */
    @Excel(name = "")
    private String brief;

    /**
     *
     */
    @Excel(name = "")
    private String publishedAt;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createType;

}
