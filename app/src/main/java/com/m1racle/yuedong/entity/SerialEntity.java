package com.m1racle.yuedong.entity;

/**
 * Yuedong App
 * Base Serializable Entity
 */
public abstract class SerialEntity implements java.io.Serializable {

    protected Notice notice;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
