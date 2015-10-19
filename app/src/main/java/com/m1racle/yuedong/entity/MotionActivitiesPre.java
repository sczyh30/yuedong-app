package com.m1racle.yuedong.entity;

/**
 * Motion activities pre entity
 */
public class MotionActivitiesPre extends Entity {

    private int ma_id;
    private String title;
    private String img_id;
    private String summary;
    private String author;
    private String createTime;

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

    public String getImgId() {
        return img_id;
    }

    public void setImgId(String img_id) {
        this.img_id = img_id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
}
