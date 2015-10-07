package com.m1racle.yuedong.entity;

/**
 * Login request result
 */
public class LoginResult {

    private DataResult dataResult;

    private User user;

    public LoginResult(User user, DataResult dataResult) {
        this.user = user;
        this.dataResult = dataResult;
    }

    public DataResult getDataResult() {
        return dataResult;
    }

    public void setDataResult(DataResult dataResult) {
        this.dataResult = dataResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
