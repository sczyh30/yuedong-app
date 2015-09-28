package com.m1racle.yuedong.util;

import android.util.Log;
import android.widget.Toast;

import com.m1racle.yuedong.base.BaseApplication;

/**
 * Yuedong App Common Library
 * Log Util Class
 * @author sczyh30
 * when the app is in production
 * set the DEBUG false
 */
public class LogUtil {

	public static final String LOG_TAG = "Yuedong-DEBUG";
	public static boolean DEBUG = true;

	public static void error(String log) {
		if (DEBUG)
			Log.e(LOG_TAG, "" + log);
	}

	public static void log(String log) {
		if (DEBUG)
			Log.i(LOG_TAG, log);
	}

	public static void log(String tag, String log) {
		if (DEBUG)
			Log.i(tag, log);
	}

	public static void v(String log) {
		if (DEBUG)
			Log.v(LOG_TAG, log);
	}

	public static void warn(String log) {
		if (DEBUG)
			Log.w(LOG_TAG, log);
	}

    public static void toast(String log) {
        if (DEBUG)
            Toast.makeText(BaseApplication.getContext(), log, Toast.LENGTH_SHORT).show();
    }
}
