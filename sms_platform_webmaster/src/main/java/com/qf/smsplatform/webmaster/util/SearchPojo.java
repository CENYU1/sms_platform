package com.qf.smsplatform.webmaster.util;

import lombok.Data;

/**
 * @author menglili
 * 调用搜索服务用到的对象，与搜索服务中的数据格式对应
 */
@Data
public class SearchPojo {
    //匹配的短信内容
    private String keyword;
    //客户id
    private Integer clientID;
    //手机号
    private String mobile;
    //开始时间
    private Long startTime;
    //结束时间
    private Long endTime;

    //从第几条开始查询
    private Integer start;
    //每页显示条数
    private Integer rows;
    //高亮前缀
    private String highLightPreTag;
    //高亮后缀
    private String highLightPostTag;

    //短信内容
    private String messageContent;

}
