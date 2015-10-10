package com.m1racle.yuedong.entity;

import java.util.List;

/**
 * User info detail entity
 */
public class UserDetail {

    private int uid;
    private String reg_time;
    private List<String> sportList;
    private String my_tip;
    private String location;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<String> getSportList() {
        return sportList;
    }

    public void setSportList(List<String> sportList) {
        this.sportList = sportList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegTime() {
        return reg_time;
    }

    public void setRegTime(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getMyTip() {
        return my_tip;
    }

    public void setMyTip(String my_tip) {
        this.my_tip = my_tip;
    }

    public String getSports() {
        StringBuilder sports = new StringBuilder("");
        for(String sport : sportList) {
            sports.append(sport).append(" ");
        }
        return sports.toString();
    }

}
