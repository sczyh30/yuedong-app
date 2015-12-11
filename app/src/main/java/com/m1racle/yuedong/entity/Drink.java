package com.m1racle.yuedong.entity;

/**
 * Drink entity
 */
public class Drink {

    public Drink() {}

    public Drink(int cup, String date) {
        this.cup = cup;
        this.date = date;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getCup() {
        return cup;
    }

    public void setCup(int cup) {
        this.cup = cup;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private int did;
    private int cup;
    private String date;
}
