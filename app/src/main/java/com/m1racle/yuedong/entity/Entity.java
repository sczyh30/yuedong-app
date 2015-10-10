package com.m1racle.yuedong.entity;

/**
 * The common entity abstract class
 */
public abstract class Entity extends SerialEntity {

    protected int id;
    protected String cacheKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}
