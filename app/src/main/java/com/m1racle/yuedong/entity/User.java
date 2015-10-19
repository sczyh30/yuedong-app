package com.m1racle.yuedong.entity;

/**
 * User Entity
 * very complex
 * @author sczyh30
 */
public class User extends Entity {

    private int id;
    private String account;
    private String username;
    private String password;
    private String portrait;
    private String tips;

    private String temp_token;
    //private TempToken temp_token;
    private int isRememberMe;

    private int fans;
    private int followers;
    private int score;
    private int activities_number;

    private int rank;
    private int step;

    private String location;
    private int gender;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String  getTempToken() {
        return temp_token;
    }

    public void setTempToken(String temp_token) {
        this.temp_token = temp_token;
    }

    public boolean isRememberMe() {
        return isRememberMe > 0;
    }

    public void setRememberMe(boolean isRememberMe) {
        if(isRememberMe)
            this.isRememberMe = 1;
        else
            this.isRememberMe = 0;
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

    public int getActivitiesNumber() {
        return activities_number;
    }

    public void setActivitiesNumber(int activities_number) {
        this.activities_number = activities_number;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "User Entity => {" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fans=" + fans +
                ", followers=" + followers +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
