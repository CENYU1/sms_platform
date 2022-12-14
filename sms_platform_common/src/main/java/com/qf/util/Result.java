package com.qf.util;


import com.qf.exception.SmsException;

import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", 0);
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static Result error(SmsException ex) {
        Result result = new Result();
        result.put("code", ex.getCode());
        result.put("msg", ex.getMessage());
        return result;
    }

    public static Result errorData(SmsException ex) {
        Result result = new Result();
        result.put("code", ex.getCode());
        result.put("msg", ex.getMessage());
        result.put("data", ex.getData());
        return result;
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.put("code", 0);
        result.put("msg", msg);
        return result;
    }

    public static Result ok(Map<String, Object> map) {
        Result result = new Result();
        result.putAll(map);
        return result;
    }

    public static Result ok() {
        return new Result();
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
