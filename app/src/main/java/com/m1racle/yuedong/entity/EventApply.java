package com.m1racle.yuedong.entity;

/**
 * Yuedong App
 * Event Apply Entity
 */
public class EventApply extends Entity {

    private String act_name;
    private int uid;
    private int maid;
    private String name;
    private int gender;
    private String mobile;
    private String address;

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMaid() {
        return maid;
    }

    public void setMaid(int maid) {
        this.maid = maid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
