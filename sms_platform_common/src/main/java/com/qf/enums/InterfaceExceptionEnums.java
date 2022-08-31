package com.qf.enums;

import lombok.Getter;

@Getter
public enum InterfaceExceptionEnums {
    INTERFACE_CLIENT_ID_ERROR(101,"认证错误：clientId错误"),
    INTERFACE_PWD_ERROR(102,"密码错误"),
    INTERFACE_IP_ERROR(103,"IP校验错误"),
    INTERFACE_CONTENT_ERROR(104,"消息长度错，为空或超长（目前定为500个字）"),
    INTERFACE_MOBILE_ERROR(105,"手机号错误"),
    INTERFACE_SRC_ID_ERROR(106,"下行编号（srcID）错误"),

    SEARCH_SAVE_LOG_ERROR(303,"保存日志到ES失败!"),
    SEARCH_UPDATE_LOG_ERROR(304,"修改日志到ES失败!"),
    ;

    private Integer code;

    private String message;

    InterfaceExceptionEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
