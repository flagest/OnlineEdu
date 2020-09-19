package com.atguigu.servicebase.exceptionhandler;


import com.atguigu.commonutils.R;
import com.atguigu.servicebase.emnu.ExceptionEnum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理..");
    }

    @ExceptionHandler(GuLiException.class)
    @ResponseBody
    public R guliException(GuLiException e) {
        e.printStackTrace();
        log.error(e.getMessage()+e.getMsg()+e.getCode());
        return R.error().code(e.getCode()).message(e.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public R constraintViolationException(DataIntegrityViolationException e){
        e.printStackTrace();
        log.error(e.getMessage()+ExceptionEnum.DUPLICATE_ENTRY.code()+ExceptionEnum.DUPLICATE_ENTRY.message());
        return R.error().code(ExceptionEnum.DUPLICATE_ENTRY.code()).message(ExceptionEnum.DUPLICATE_ENTRY.message());
//        return R.error().code(ExceptionEnum.DUPLICATE_ENTRY.code()).message(e.getMessage());

    }
}
