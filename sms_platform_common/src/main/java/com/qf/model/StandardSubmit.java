package com.qf.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口模块校验通过之后，将当前对象实例，发送到RabbitMQ的队列中
 */
@Data
public class StandardSubmit implements Serializable {
    /**
     * 当前Submit的唯一标识 
     */
    private String sequenceId;

    /**
     * 客户端ID
     */
    private Integer clientID;    // ok

    /**
     * 下发的源号码      10698888888    1069777777    号段补全策略实现   需要使用spNumber（channel表） + extendNumber（client_channel表）
     */
    private String srcNumber;     // 号段补全

    /**
     * 消息的优先级 0 最低 --- 3 最高
     */
    private short messagePriority;  // 可能涉及不到.  // ok

    /**
     * 客户侧唯一序列号
     */
    private Long srcSequenceId;    // srcID  // ok

    /**
     * 下发网关ID号   1   （通道ID）
     */
    private Integer gatewayID;     //  当可以发送短信后,指定网关的id,不同的网关对接不同的运营商,号段补全策略实现

    /**
     * 目的手机号
     */
    private String destMobile;      //  手机号// ok

    /**
     * 短信内容
     */
    private String messageContent;      // 短信内容// ok

    /**
     * 响应返回的运营商消息编号,
     */
    private String msgid;               // 哪个运营商返回的消息// ok

    /**
     * 手机接收的状态值  0 成功 1 等待 2 失败
     */
    private Integer reportState;        //....  // ok

    /**
     * 状态的错误码
     */
    private String errorCode;           // 指定出现问题时的错误码

    /**
     * 短信发送时间
     */
    private Date sendTime;          // ....// ok

    /**
     * 运营商     0 - 未知,1 - 移动, 2 - 联通 ,3 - 电信
     */
    private Integer operatorId;         // 策略模块中去查询出来,   号段补全策略实现

    /**
     * 省
     */
    private Integer provinceId;         // 策略模块中去查询出来,  号段补全策略实现

    /**
     * 市
     */
    private Integer cityId;             // 策略模块中去查询出来,   号段补全策略实现

    /**
     * 发送方式 1 HTTP 2 WEB
     */
    private Integer source;         // 发送方式.// ok

    /**
     *  当前短信费用, 为了让fee策略扣费时,可以直接获取到费用信息
     */
    private Long fee;   //  当前选择通道的费用
}
