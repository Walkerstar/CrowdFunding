package com.mcw.scw.project.bean;

public class TAccount {
    private Integer id;

    private String entaccount;

    private String idnumber;

    private Integer memberid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntaccount() {
        return entaccount;
    }

    public void setEntaccount(String entaccount) {
        this.entaccount = entaccount == null ? null : entaccount.trim();
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    @Override
    public String toString() {
        return "TAccount{" +
                "id=" + id +
                ", entaccount='" + entaccount + '\'' +
                ", idnumber='" + idnumber + '\'' +
                ", memberid=" + memberid +
                '}';
    }
}