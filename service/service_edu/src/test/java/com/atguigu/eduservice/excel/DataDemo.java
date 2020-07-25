package com.atguigu.eduservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author wu on 2020/7/14 0014
 */
@Data
public class DataDemo {

    @ExcelProperty("学生编号")
    private Integer SNo;

    @ExcelProperty("学生姓名")
    private String SName;

}
