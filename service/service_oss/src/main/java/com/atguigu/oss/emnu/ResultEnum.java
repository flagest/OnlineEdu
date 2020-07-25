package com.atguigu.oss.emnu;

/**
 * @author wu on 2020/7/5 0005
 */

public enum ResultEnum {

    ERROR(201, "就是要失败没有原因！"),
    SUCESS(202, "就要要成功，没有原因！"),
    NAME_NOTNULL(203, "添加教师姓名不能为空！"),
    UPLOAD_ERROR(204, "图片上传失败！");

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
