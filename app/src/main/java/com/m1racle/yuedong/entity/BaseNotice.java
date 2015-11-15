package com.m1racle.yuedong.entity;

/**
 * Yuedong App
 * Base Notification Entity
 */
public class BaseNotice extends Entity {

    private String contentTitle;
    private String contentBody;
    private int contentType;

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
