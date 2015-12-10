package com.m1racle.yuedong.entity;

/**
 * Motion activities entity
 */
public class MotionActivitiesDetail extends Entity {

    private int ma_id;
    private String title;
    private String name;
    private String body;
    private String img_id;
    private String author;
    private String createTime;
    private String startTime;
    private String endTime;
    private String location;
    private int status;

    public int getMaId() {
        return ma_id;
    }

    public void setMaId(int ma_id) {
        this.ma_id = ma_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImgId() {
        return img_id;
    }

    public void setImgId(String img_id) {
        this.img_id = img_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
