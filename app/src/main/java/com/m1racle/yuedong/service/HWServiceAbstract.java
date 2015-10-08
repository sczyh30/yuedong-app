package com.m1racle.yuedong.service;

/**
 * Huawei Wearable API Service Function Interface
 * @author sczyh30
 * @since 0.2
 */
public interface HWServiceAbstract {

    void getConnectState();

    void getBlueToothBattery();

    void getDeviceTime();
}
