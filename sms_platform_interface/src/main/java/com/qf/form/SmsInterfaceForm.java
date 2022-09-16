package com.qf.form;

import lombok.Data;

/**
 * 客户项目发送到本模块的数据, 实体类
 * @author cenyu
 */
@Data
public class SmsInterfaceForm {

    //客户ID
    private String clientID;

    //下行编号
    private String srcID;

    //目的手机号, 非空，多个号请用英文逗号隔开，最多100个号码
    private String mobile;

    //消息的内容,内容长度<=500字符，以utf-8的方式编码
    private String content;

    //密码  ,由服务方提供,格式：MD5(password)32位大写
    private String pwd;

    //ip地址, 用户没有直接携带的参数,需要自己手动获取
    private String ip;
}
