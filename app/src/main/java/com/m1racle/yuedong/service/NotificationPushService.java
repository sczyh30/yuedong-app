package com.m1racle.yuedong.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.m1racle.yuedong.service.receiver.NPSBroadcastReceiver;
import com.m1racle.yuedong.util.AlarmUtil;
import com.m1racle.yuedong.util.NotificationUtil;

public class NotificationPushService extends Service {

    private final int period = 60 * 10 * 10 * 1000;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NotificationUtil.doNotify("朋友，今天悦动了吗？", "今天天气很好，出去运动一下吧~");
            }
        }).start();
        AlarmUtil.setAlarmByBC(this, NPSBroadcastReceiver.class, period);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}


