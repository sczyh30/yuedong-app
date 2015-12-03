package com.m1racle.yuedong.entity;

import java.util.Date;

/**
 * Weight Data Entity
 * @author sczyh30
 * @since v1.22
 */
public class Weight {

    public int wid;

    public float weight;
    public float height;
    public float index;

    public String wTime;
    public String tip;

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getIndex() {
        return index;
    }

    public void setIndex(float index) {
        this.index = index;
    }

    public String getwTime() {
        return wTime;
    }

    public void setwTime(String wTime) {
        this.wTime = wTime;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "Weight -> {" +
                "wid=" + wid +
                ", weight=" + weight +
                ", height=" + height +
                ", index=" + index +
                ", wTime=" + wTime +
                ", tip='" + tip + '\'' +
                '}';
    }
}
