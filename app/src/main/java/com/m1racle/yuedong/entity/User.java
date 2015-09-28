package com.m1racle.yuedong.entity;

import java.util.List;

/**
 * User Entity
 */
public class User extends Entity {

    private int id;
    private String account;
    private String username;
    private TempToken temp_token;
    private boolean isRememberMe;

    private int fans;
    private int followers;
    private String portrait;
    private int score;

    private String location;
    private String reg_time;
    private String gender;
    private List<String> like_sport;
    private String my_tip;

    public User(){}
    public User(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TempToken getTemp_token() {
        return temp_token;
    }

    public void setTemp_token(TempToken temp_token) {
        this.temp_token = temp_token;
    }

    public boolean isRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getLike_sport() {
        return like_sport;
    }

    public void setLike_sport(List<String> like_sport) {
        this.like_sport = like_sport;
    }

    public String getMy_tip() {
        return my_tip;
    }

    public void setMy_tip(String my_tip) {
        this.my_tip = my_tip;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User Entity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fans=" + fans +
                ", followers=" + followers +
                ", location='" + location + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", gender='" + gender + '\'' +
                ", like_sport=" + like_sport +
                ", my_tip='" + my_tip + '\'' +
                '}';
    }
}
