package com.mcw.scw.webui.vo.req;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用于保存订单的数据
 * @author mcw 2019\12\20 0020-15:05
 */
@Data
@ToString
public class OrderInfoSubmitVo implements Serializable {

    private String accessToken;
    private Integer projectid;
    private Integer returnid;
    private Integer rtncount;

    private String address;
    private Byte invoice;
    private String invoictitle;
    private String remark;
}
