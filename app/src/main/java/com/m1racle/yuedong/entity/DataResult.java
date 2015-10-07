package com.m1racle.yuedong.entity;

/**
 * Data request result (ok or error)
 */
public class DataResult {

    private int errorCode;
    private String errorMsg;

    public boolean isOK() {
        return errorCode == 886;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
