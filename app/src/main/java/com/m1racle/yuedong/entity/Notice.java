package com.m1racle.yuedong.entity;

/**
 * Notice Entity
 * @author sczyh30
 * @since 0.3.1
 */
public class Notice {

    public final static int TYPE_AT_ME = 1;
    public final static int TYPE_MESSAGE = 2;
    public final static int TYPE_COMMENT = 3;
    public final static int TYPE_NEW_FAN = 4;
    public final static int TYPE_NEW_ZAN = 5;

    private int atMeCount;
    private int messageCount;
    private int commentCount;
    private int newFansCount;
    private int newZansCount;

    public int getAtMeCount() {
        return atMeCount;
    }

    public void setAtMeCount(int atMeCount) {
        this.atMeCount = atMeCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getNewFansCount() {
        return newFansCount;
    }

    public void setNewFansCount(int newFanCount) {
        this.newFansCount = newFanCount;
    }

    public int getNewZansCount() {
        return newZansCount;
    }

    public void setNewZansCount(int newZanCount) {
        this.newZansCount = newZanCount;
    }
}
