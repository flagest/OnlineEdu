package com.atguigu.eduservice.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author 蒲勇君
 * @date 2020-01-07 16:27
 */
public final class ValidationUtil {

    public static String validation(BindingResult result){

        if (result.hasErrors()){

            FieldError fieldError = result.getFieldError();

            String field = fieldError.getField();
            String msg = fieldError.getDefaultMessage();

            return field + ":" +msg;
        }

        return "success";
    }
}
