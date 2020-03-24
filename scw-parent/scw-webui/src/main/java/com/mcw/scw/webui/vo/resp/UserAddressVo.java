package com.mcw.scw.webui.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class UserAddressVo implements Serializable {

	@ApiModelProperty("地址id")
	private Integer id;

	@ApiModelProperty("会员地址")
	private String address ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
