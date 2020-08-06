package com.atguigu.educenter.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wu on 2020/8/6 0006
 */
@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginVO implements Serializable {
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

}
