package com.m1racle.yuedong.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.TestActivity;
import com.m1racle.yuedong.base.BaseApplication;
import com.m1racle.yuedong.service.NotificationPushService;

/**
 * Yuedong App Common Library
 * Notification Util
 * @author sczyh30
 * @since 1.0.6
 */
public class NotificationUtil {


    public void doNotify() {

    }

    public static void doTest(Context context) {
        //context = BaseApplication.getContext();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        Intent i = new Intent(context, TestActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(icon)
                .setWhen(System.currentTimeMillis())
                .setTicker("同学，该运动啦~")
                .setContentTitle("悦动")
                .setContentText("今天貌似没有运动呢？出去运动一下吧~")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        notificationManager.notify(0, notification);
        notification.ledARGB = Color.GREEN;
        notification.ledOnMS = 1000;
        notification.ledOffMS = 1000;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        LogUtil.log("Yuedong => (DEBUG MODE) NPS Service executed");
    }
}
