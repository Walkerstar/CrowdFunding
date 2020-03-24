package com.mcw.scw.webui.vo.req;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mcw 2019\12\20 0020-14:24
 */
@ToString
@Data
public class OrderFormInfoSubmitVo implements Serializable {

    private String address;//收货地址id
    private Byte invoice;//0代表不要  1-代表要
    private String invoictitle;//发票抬头
    private String remark;//订单的备注
}
