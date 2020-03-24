package com.mcw.scw.webui.vo.req;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mcw 2019\12\16 0016-14:54
 */
@Data
@ToString
public class BaseVo implements Serializable {

    private String accessToken;
}
