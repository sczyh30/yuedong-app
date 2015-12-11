package com.m1racle.yuedong.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Yuedong App
 * Date and TimeZone Transform Util
 */
public class DateUtil {

	public static String getToday() {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sFormat.format(new Date());
    }

	/**
	 * 判断用户的设备时区是否为GMT+08
	 */
	public static boolean isInEasternEightZones() {
		boolean defaultVaule = true;
        defaultVaule = TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08");
		return defaultVaule;
	}

	/**
	 * 根据不同时区，转换时间
	 */
	public static Date transformTime(Date date, TimeZone oldZone, TimeZone newZone) {
		Date finalDate = null;
		if (date != null) {
			int timeOffset = oldZone.getOffset(date.getTime())
					- newZone.getOffset(date.getTime());
			finalDate = new Date(date.getTime() - timeOffset);
		}
		return finalDate;
	}

	public static String getCurrentTime() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return formater.format(date);
    }
}
