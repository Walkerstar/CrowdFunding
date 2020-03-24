package com.mcw.scw.project.vo.resp;

import java.io.Serializable;


import lombok.Data;

//首页，热点项目
@Data
public class ProjectVo implements Serializable {
	
	private Integer memberid;// 会员id

    private Integer id;
	private String name;// 项目名称
	private String remark;// 项目简介

	private String headerImage;// 项目头部图片

	// 发起人信息：自我介绍，详细自我介绍，联系电话，客服电话
	

}
