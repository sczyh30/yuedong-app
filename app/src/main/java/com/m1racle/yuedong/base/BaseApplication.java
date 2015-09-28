package com.m1racle.yuedong.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.m1racle.yuedong.service.listener.AppLocationListener;

/**
 * Yuedong App
 * Base Application Class
 * @author sczyh30
 * @since 0.1.4
 */
public class BaseApplication extends Application {

    private static Context context;
    static Resources resources;
    private static String PREF_NAME = "creativelocker.pref";

    private static boolean FUCKING_GB;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            FUCKING_GB = true;
        }
    }
    /**
     * LocationClient is the Baidu Location API instance
     */
    public LocationClient mLocationClient;
    public BDLocationListener myListener;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        resources = context.getResources();
        initBDLocation();

    }
    public static Context getContext() {
        return context;
    }

    public void initBDLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        myListener = new AppLocationListener();
        mLocationClient.registerLocationListener(myListener);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context.getSharedPreferences(PREF_NAME,
                Context.MODE_MULTI_PROCESS);
        return pre;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences(String prefName) {
        return context.getSharedPreferences(prefName,
                Context.MODE_MULTI_PROCESS);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        if (FUCKING_GB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

}
