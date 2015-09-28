package com.m1racle.yuedong.service;

import android.os.Handler;
import android.os.Message;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.constant.DeviceType;
import com.huawei.huaweiwearable.data.DataGMTTime;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
/**
 * Huawei API Service Basic
 */
public class HWService {

    private static HWService hwService = new HWService();

    private HWService(){}

    public static HWService getService() {
        return hwService;
    }

    public IDeviceConnectStatusCallback getStateCallBack(final Handler handler) {
        if(handler != null) {
            return new IDeviceConnectStatusCallback() {

                @Override
                public void onConnectStatusChange(int deviceType, String macAddress, int status, int err_code) {
                    switch (status) {
                        case 0:
                            Message message0 = Message.obtain();
                            message0.what = HWServiceConfig.JAR_CONNECT_DEVICE;
                            message0.obj = "未知";
                            handler.sendMessage(message0);
                            break;
                        case 1:
                            Message message1 = Message.obtain();
                            message1.what = HWServiceConfig.JAR_CONNECT_DEVICE;;
                            message1.obj = "正在连接";
                            handler.sendMessage(message1);
                            break;
                        case 2:
                            Message message2 = Message.obtain();
                            message2.what = HWServiceConfig.JAR_CONNECT_DEVICE;;
                            message2.obj = "已连接";
                            handler.sendMessage(message2);
                            break;
                        case 3:
                            Message message3 = Message.obtain();
                            message3.what = HWServiceConfig.JAR_CONNECT_DEVICE;;
                            message3.obj = "断开连接";
                            handler.sendMessage(message3);
                            break;
                        default:
                            break;
                    }
                }
            };
        }
            return null;
    }


}
