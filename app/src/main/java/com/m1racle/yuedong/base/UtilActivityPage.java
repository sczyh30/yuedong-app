package com.m1racle.yuedong.base;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.ui.fragment.*;

/**
 * The enum class designed for UtilActivity
 * provide the index for UI Manager class
 * @see com.m1racle.yuedong.ui.activity.UtilActivity
 * @see com.m1racle.yuedong.util.UIUtil
 */
public enum UtilActivityPage {

    SETTING (1, R.string.actionbar_title_setting, SettingsFragment.class),
    ABOUT_YD (2, R.string.actionbar_title_about, AboutYDFragment.class),
    MY_INFORMATION (3, R.string.actionbar_title_my_information,
            MySocialInfoFragment.class),
    SETTING_NOTIFICATION (4, R.string.actionbar_title_setting_notification,
            SettingsNotificationFragment.class),
    SOCIAL_DETAIL(5,R.string.actionbar_title_my_information_detail, MySocialDetailFragment.class),
    TEMPERATURE_DETAIL(6,R.string.actionbar_title_temperature_detail, TemperatureViewFragment.class),
    DEVICE_USER_INFO(7,R.string.actionbar_title_device_user_info, DeviceUserFragment.class),
    DEVICE_EVERYDAY_MOTION(8,R.string.actionbar_title_device_everyday_motion, EverydayMotionFragment.class),
    DEVICE_ALARM(9,R.string.actionbar_title_device_alarm, DeviceAlarmFragment.class),
    DEVICE_MOTION_DATA(10,R.string.actionbar_title_device_motion_data, DeviceMotionDataFragment.class),
    DEVICE_MOTION_GOAL(11,R.string.actionbar_title_device_motion_goal, MotionGoalFragment.class),
    SLEEP_OBSERVER(12,R.string.actionbar_title_device_sleep_observer, SleepObserverFragment.class),
    DEVICE_USER_INFO_SET(13,R.string.set_device_user_info, DeviceUserSetFragment.class),
    HEALTH_MOTION_ADVICE(14,R.string.actionbar_title_motion_advice, HealthAdviceFragment.class),
    DEVICE_ALARM_SET(15,R.string.actionbar_title_device_alarm_set, DeviceAlarmSetFragment.class),
    DEVICE_MOTION_GOAL_SET(16,R.string.actionbar_title_device_motion_goal_set, MotionGoalSetFragment.class),
    MOTION_ACTIVITIES_DETAIL(17,R.string.actionbar_title_motion_activities_detail, ActivitiesDetailFragment.class);

    private int title;
    private Class<?> clz;
    private int value;

    UtilActivityPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static UtilActivityPage getPageByValue(int val) {
        for (UtilActivityPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }
}
