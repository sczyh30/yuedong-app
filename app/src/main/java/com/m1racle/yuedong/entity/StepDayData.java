package com.m1racle.yuedong.entity;

/**
 * Step Day Data Entity
 */
public class StepDayData {

    private int eid;
    private int calorie;
    private int step;
    private int distance;
    private int etime;
    private String edate;

    public StepDayData() {}

    public StepDayData(int step, int calorie, int distance, String edate) {
        this.step = step;
        this.calorie = calorie;
        this.distance = distance;
        this.edate = edate;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getEtime() {
        return etime;
    }

    public void setEtime(int etime) {
        this.etime = etime;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }
}
