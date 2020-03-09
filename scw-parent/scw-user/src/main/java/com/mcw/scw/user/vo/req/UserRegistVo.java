package com.mcw.scw.user.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册提交的数据VO
 * @author mcw 2019\12\13 0013-15:06
 */
@ApiModel
@Data
public class UserRegistVo implements Serializable {

    @ApiModelProperty("手机号")
    private String loginacct;

    @ApiModelProperty("密码")
    private String userpswd;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("用户类型: 1-个人 0-企业")
    private String usertype;
}
