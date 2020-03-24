package com.mcw.scw.webui.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class UserProjectVo implements Serializable {

    //项目表

	@ApiModelProperty(value = "项目名字")
	private String name ;

//	@ApiModelProperty(value = "项目状态  0-草稿  2-审核中 ....")
//	private Byte status ;

	@ApiModelProperty(value = "筹资金额")
	private Long money ;

	@ApiModelProperty(value = "筹资天数")
	private Integer day ;

	@ApiModelProperty(value = "发布日期")
	private String deploydate ;

    @ApiModelProperty(value = "当前项目已经支持金额")
    private Long supportmoney ;

	private Integer completion; //完成度

    private Long daysRemaining;//剩余天数

	//订单表
	@ApiModelProperty(value = "项目id")
	private Integer id ;

	@ApiModelProperty(value = "订单编号")
	private String orderId ;

	@ApiModelProperty(value = "支持日期")
	private String orderCreateTime ;

	@ApiModelProperty(value = "我支持的回报id")
	private Integer returnId ;

	@ApiModelProperty(value = "我支持的金额")
	private Integer orderMoney ;

	@ApiModelProperty(value = "我支持的回报数量")
	private Integer returnNum ;

	@ApiModelProperty(value = "交易状态/订单状态")
	private String orderStatus ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getDeploydate() {
		return deploydate;
	}

	public void setDeploydate(String deploydate) {
		this.deploydate = deploydate;
	}

	public Long getSupportmoney() {
		return supportmoney;
	}

	public void setSupportmoney(Long supportmoney) {
		this.supportmoney = supportmoney;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Integer getReturnId() {
		return returnId;
	}

	public void setReturnId(Integer returnId) {
		this.returnId = returnId;
	}

	public Integer getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Integer orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Integer getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}

	public String  getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String  orderStatus) {
		this.orderStatus = orderStatus;
	}

    public Integer getCompletion() {
        return completion;
    }

    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public Long getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(Long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    @Override
    public String toString() {
        return "UserProjectVo{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", day=" + day +
                ", deploydate='" + deploydate + '\'' +
                ", supportmoney=" + supportmoney +
                ", completion=" + completion +
                ", daysRemaining=" + daysRemaining +
                ", id=" + id +
                ", orderId='" + orderId + '\'' +
                ", orderCreateTime='" + orderCreateTime + '\'' +
                ", returnId=" + returnId +
                ", orderMoney=" + orderMoney +
                ", returnNum=" + returnNum +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
