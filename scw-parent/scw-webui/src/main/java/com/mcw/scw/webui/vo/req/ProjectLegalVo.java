package com.mcw.scw.webui.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author mcw 2019\12\22 0022-17:32
 */
@ToString
@ApiModel
@Data
public class ProjectLegalVo extends BaseVo {
    private String projectToken;// 项目的临时token

    @ApiModelProperty("易付宝企业账号")
    private String entaccount; //易付宝企业账号

    @ApiModelProperty("法人身份证号")
    private String idnumber; //法人身份证号
}
