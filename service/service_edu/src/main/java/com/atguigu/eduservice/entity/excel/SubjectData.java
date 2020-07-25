package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author wu on 2020/7/16 0016
 */
@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjet;
    @ExcelProperty(index = 1)
    private String twoSubject;
}
