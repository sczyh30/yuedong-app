package com.m1racle.yuedong.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.util.LogUtil;

import java.lang.ref.WeakReference;

/**
 * Huawei Wearable API Service
 * @author sczyh30
 * @since 0.2
 */
public class HWService extends Service {

    private HuaweiWearableManager manager = null;
    private Intent notifyIntent;
    private Bundle bundle;

    public static final int JAR_GET_DEVICE_CONNECT_STATUS = 0;
    public static final int JAR_GET_DEVICE_BATTERY = 1;
    public static final int JAR_CONNECT_DEVICE = 12;
    public final static int HUAWEI_TALKBAND_B2 = 1;

    private void init() {
        manager = HuaweiWearableManager.getInstance(AppContext.getContext());
        if(manager != null) {
            manager.registerConnectStateCallback(stateCallBack);
        }
    }

    private IDeviceConnectStatusCallback stateCallBack = new IDeviceConnectStatusCallback() {

        @Override
        public void onConnectStatusChange(int deviceType, String macAddress, int status, int err_code) {
            switch (status) {
                case 0:
                    Message message0 = Message.obtain();
                    message0.what = JAR_CONNECT_DEVICE;
                    message0.obj = "未知";
                    mHandler.sendMessage(message0);
                    break;
                case 1:
                    Message message1 = Message.obtain();
                    message1.what = JAR_CONNECT_DEVICE;
                    message1.obj = "正在连接";
                    mHandler.sendMessage(message1);
                    break;
                case 2:
                    Message message2 = Message.obtain();
                    message2.what = JAR_CONNECT_DEVICE;
                    message2.obj = "已连接";
                    mHandler.sendMessage(message2);
                    break;
                case 3:
                    Message message3 = Message.obtain();
                    message3.what = JAR_CONNECT_DEVICE;
                    message3.obj = "断开连接";
                    mHandler.sendMessage(message3);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * In case of causing memory leak
     * use the nested class instead
     * the WeakReference could solve this issue
     */
    private static class MyHandler extends Handler {
        private final WeakReference<HWService> mService;
        private Bundle bundle = null;

        public MyHandler(HWService service) {
            mService = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case JAR_GET_DEVICE_CONNECT_STATUS:
                    int state = (Integer)object;
                    mService.get().bundle.putInt("conn_state", state);
                    break;
                case JAR_GET_DEVICE_BATTERY:
                    int send_data_battery = msg.arg1;
                    break;
                case JAR_CONNECT_DEVICE:
                    String str = (String)msg.obj;
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * Send the broadcast to notify the UI layer
     */
    private void notifyStatusChanged() {
        notifyIntent.putExtras(bundle);
        sendBroadcast(notifyIntent);
    }

    private final MyHandler mHandler = new MyHandler(this);

    public HWService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getBasicStatus() {
        int connectState = 0;

        if(null != manager){
            connectState = manager.getConnectStatus(HUAWEI_TALKBAND_B2);
        }

        LogUtil.log("getConnectState => connectState = " + connectState);
        Message message = Message.obtain();
        message.what = JAR_GET_DEVICE_CONNECT_STATUS;
        message.obj = connectState;
        message.arg1 = HUAWEI_TALKBAND_B2;
        mHandler.sendMessage(message);
    }
}
