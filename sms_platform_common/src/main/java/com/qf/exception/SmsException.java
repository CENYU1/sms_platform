package com.qf.exception;

import com.qf.enums.InterfaceExceptionEnums;
import lombok.Getter;

import java.util.Set;

@Getter
public class SmsException extends RuntimeException {
    private Integer code;

    private Set<String> data;

    public SmsException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public SmsException(InterfaceExceptionEnums enums) {
        super(enums.getMessage());
        this.code = enums.getCode();
    }
    public SmsException(InterfaceExceptionEnums enums,Set<String> data) {
        super(enums.getMessage());
        this.code = enums.getCode();
        this.data = data;
    }
}
