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

import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.service.NotificationPushService;
import com.m1racle.yuedong.ui.activity.MainActivity;

/**
 * Yuedong App Common Library
 * Notification Util
 * @author sczyh30
 * @since 0.1.6
 */
public class NotificationUtil {


    public static void doNotify(String ticker, String text) {
        doNotify(ticker, text, System.currentTimeMillis(), R.mipmap.ic_launcher);
    }

    public static void doNotify(String ticker, String text, long time) {
        doNotify(ticker, text, time, R.mipmap.ic_launcher);
    }

    public static void doNotify(String ticker, String text, long time, int icon, long viberate, long sound) {

    }

    public static void doNotify(String ticker, String text, long time, int icon) {
        Context context = AppContext.getContext();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        Bitmap big_icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(icon).setLargeIcon(big_icon)
                .setWhen(time)
                .setTicker(ticker)
                .setContentTitle("悦动")
                .setContentText(text)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        notificationManager.notify(0, notification);
        LogUtil.log("Yuedong => (DEBUG MODE) Notification executed");
    }

    /** public static void doTest(Context context) {
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
    } */
}
