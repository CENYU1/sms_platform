package com.qf.service;

import com.qf.form.SmsInterfaceForm;

import java.util.Map;

/**
 * @author cenyu
 */
public interface SmsInterfaceService {

    /**
     * 校验参数
     * @param interfaceForm
     * @return
     */
    public Map<String, String> checkInterfaceForm(SmsInterfaceForm interfaceForm);
}