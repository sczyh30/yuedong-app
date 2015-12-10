package com.m1racle.yuedong.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Yuedong app
 * A service that auto updates the weather data
 * @author sczyh30
 * @since v1.30
 */
public class UpdateWeatherService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {

    }
}
