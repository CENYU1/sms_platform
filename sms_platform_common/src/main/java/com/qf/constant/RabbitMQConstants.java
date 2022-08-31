package com.qf.constant;

public class RabbitMQConstants {
    // 发送短信的消息队列名  ---  接口模块发送消息到策略模块
    public final static String TOPIC_PRE_SEND = "pre_send_sms_topic";

    public final static String BINDING = "#";

    //下发日志TOPIC   ---   写日志到ES的队列名
    public final static String TOPIC_SMS_SEND_LOG = "sms_send_log_topic";

    //推送状态报告TOPIC    ---      让接口模块给客户端一个推送.
    public final static String TOPIC_PUSH_SMS_REPORT = "push_sms_report_topic";

    //状态报告更新TOPIC    ----  修改日志到ES
    public final static String TOPIC_UPDATE_SMS_REPORT = "report_update_topic";

    //待发送短信网关队列 + 网关ID号   ---  根据手机号码情况不同,发送给不用的网关模块处理
    public final static String TOPIC_SMS_GATEWAY = "sms_send_gateway_";

    //过滤器更新TOPIC
    public final static String TOPIC_FILTER_UPDATE = "sms_filters_update_exchange";
}
