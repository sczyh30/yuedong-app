package com.m1racle.yuedong.entity;

/**
 * Yuedong app
 * Base message entity
 */
public class BaseMessage extends Entity {

    private int uid;
    private String username;
    private String portrait;
    private String message;
    private String time;
    private int zans;
    private int comments;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getZans() {
        return zans;
    }

    public void setZans(int zans) {
        this.zans = zans;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
