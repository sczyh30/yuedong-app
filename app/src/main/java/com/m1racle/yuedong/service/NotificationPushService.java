package com.m1racle.yuedong.service;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.m1racle.yuedong.AppConfig;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.entity.BaseNotice;
import com.m1racle.yuedong.service.receiver.NPSBroadcastReceiver;
import com.m1racle.yuedong.ui.activity.MainActivity;
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
        AlarmUtil.setAlarmByBC(this, NPSBroadcastReceiver.class, period);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void doNotify(BaseNotice notice) {

        Intent intent = new Intent(this, MainActivity.class);
        //TODO:此处要总结
        PendingIntent pi = PendingIntent.getActivity(this, 1000, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setTicker(notice.getContentTitle())
                .setContentTitle(notice.getContentTitle())
                .setContentText(notice.getContentBody())
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentIntent(pi);

        if(AppContext.get(AppConfig.KEY_NOTIFICATION_VIBRATION, true)) {
            long[] vibrate = {1000};
            builder.setVibrate(vibrate);
        }

        if(AppContext.get(AppConfig.KEY_NOTIFICATION_SOUND, true)) {
            //builder.setSound()
        }

        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(667, notification);
    }


}


