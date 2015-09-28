package com.m1racle.yuedong.entity;

/**
 * Motion activities entity
 */
public class MotionActivities {

    private int ma_id;
    private String title;
    private String body;
    private String img_id;

    public int getMAid() {
        return ma_id;
    }

    public void setMAid(int ma_id) {
        this.ma_id = ma_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }
}
