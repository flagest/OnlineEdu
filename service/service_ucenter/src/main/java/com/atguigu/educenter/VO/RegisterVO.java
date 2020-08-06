package com.atguigu.educenter.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author wu on 2020/8/5 0005
 */
@Data
@ApiModel(value = "注册对象", description = "注册对象")
public class RegisterVO {

    @NotNull(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @NotNull
    @Pattern(regexp = "^[1-9]{1}[0-9]{5,8}$")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @NotNull
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull
    @ApiModelProperty(value = "验证码")
    private String code;
}
