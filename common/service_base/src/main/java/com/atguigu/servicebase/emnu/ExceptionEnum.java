package com.atguigu.servicebase.emnu;

/**
 * @author wu on 2020/7/5 0005
 */

public enum ExceptionEnum {


    DUPLICATE_ENTRY(301,"该值已经重复！");
    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
