package com.m1racle.yuedong.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Yuedong App Common Library
 * Alarm Task Util
 * @author sczyh30
 * @since 1.0.6
 */
public class AlarmUtil {

    public static boolean setAlarmByBC(Context context, Class<?> bc_class, int period) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager == null)
            return false;
        long triggerAtTime = SystemClock.elapsedRealtime() + period;
        Intent i = new Intent(context, bc_class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, triggerAtTime, pi);
        return true;
    }

}
