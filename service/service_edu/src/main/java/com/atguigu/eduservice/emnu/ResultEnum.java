package com.atguigu.eduservice.emnu;

import lombok.Data;

/**
 * @author wu on 2020/7/5 0005
 */

public enum ResultEnum {

    ERROR(101, "就是要失败没有原因！"),
    SUCESS(102, "就要要成功，没有原因！"),
    NAME_NOTNULL(103, "添加教师姓名不能为空！");
    private int code;
    private String message;

    ResultEnum(int code, String message) {
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
