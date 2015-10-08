package com.m1racle.yuedong.service;

import android.app.Service;

/**
 * Huawei Service Config
 * this abstract class provides constants
 * in next version this abstract class should be
 * replaced by the enum class
 * @author sczyh30
 * @since 0.2
 */
public abstract class HWServiceConfig extends Service {

    public static final int JAR_GET_DEVICE_CONNECT_STATUS = 0;
    public static final int JAR_GET_DEVICE_BATTERY = 1;
    public static final int JAR_CONNECT_DEVICE = 12;
    public final static int HUAWEI_TALKBAND_B2 = 1;

}
