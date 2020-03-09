package com.mcw.scw.webui.vo.resp;

import java.io.Serializable;

/**
 * @author mcw 2019\12\27 0027-20:01
 */
public class UserFollowProjectVo implements Serializable {

    private Integer id;//项目ID

    private String proName;//项目的名字

    private Long supportmoney;//项目已筹集的金额

    private Long money;//项目要筹集的金额

    private Integer completion; //完成度

    private String deploydate; //发布的时间

    private Integer day; //筹集的天数

    private Integer supporter; //支持的人数

    private Integer follower; //关注的人数

    private Long daysRemaining;//剩余天数

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Long getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Long supportmoney) {
        this.supportmoney = supportmoney;
    }

    public Integer getCompletion() {
        return completion;
    }

    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public String getDeploydate() {
        return deploydate;
    }

    public void setDeploydate(String deploydate) {
        this.deploydate = deploydate;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getSupporter() {
        return supporter;
    }

    public void setSupporter(Integer supporter) {
        this.supporter = supporter;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(Long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserFollowProjectVo{" +
                "id=" + id +
                ", proName='" + proName + '\'' +
                ", supportmoney=" + supportmoney +
                ", money=" + money +
                ", completion=" + completion +
                ", deploydate='" + deploydate + '\'' +
                ", day=" + day +
                ", supporter=" + supporter +
                ", follower=" + follower +
                ", daysRemaining=" + daysRemaining +
                '}';
    }
}
