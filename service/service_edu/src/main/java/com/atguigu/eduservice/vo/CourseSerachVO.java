package com.atguigu.eduservice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程查询列表封装数据
 *
 * @author wu on 2020/7/25 0025
 */
@Data
public class CourseSerachVO {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
