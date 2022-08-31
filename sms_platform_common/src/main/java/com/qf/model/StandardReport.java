package com.qf.model;

import lombok.Data;

import java.io.Serializable;

/**
 *  状态推送报告 , 发送的es信息.
 */
@Data
public class StandardReport implements Serializable {
    /**
     *  唯一的序列标识
     */
    private String sequenceId;

    /**
     * 目的号码
     */
    private String mobile;

    /**
     * 定长4个字节 发送状态      "0 发送成功"       "1 等待发送"       "2 发送失败"
     */
    private int state;   // 2.

    /**
     * 具体发送状态
     */
    private String errorCode;

    /**
     * 客户侧唯一序列号
     */
    private long srcID;

    /**
     * 客户ID
     */
    private long clientID;

    /**
     * 响应返回的运营商消息编号,
     */
    private String msgId;   // 网关模块给了.

    /**
     * 状态报告推送次数  TTL
     */
    private int sendCount = 0;   // 1,2,3,4,5    立即推送,10s之后， 30s之后， 1min， 5min

    /**
     * 回调地址
     */
    private String receiveStatusUrl;
}
