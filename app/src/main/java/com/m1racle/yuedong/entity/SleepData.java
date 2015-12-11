package com.m1racle.yuedong.entity;

/**
 * Sleep data entity
 */
public class SleepData {

    private int sid;
    private String date;
    private int light;
    private int deep;

    public SleepData() {}

    public SleepData(String date, int light, int deep) {
        this.date = date;
        this.light = light;
        this.deep = deep;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }
}
