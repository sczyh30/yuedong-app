package com.m1racle.yuedong.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.m1racle.yuedong.service.NotificationPushService;

/**
 * Alarm Broadcast Receiver
 */
public class NPSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationPushService.class);
        context.startService(i);
    }

}
