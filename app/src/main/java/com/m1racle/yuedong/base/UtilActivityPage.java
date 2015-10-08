package com.m1racle.yuedong.base;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.ui.fragment.AboutYDFragment;
import com.m1racle.yuedong.ui.fragment.MyInformationFragment;
import com.m1racle.yuedong.ui.fragment.MySocialDetailFragment;
import com.m1racle.yuedong.ui.fragment.SettingsFragment;
import com.m1racle.yuedong.ui.fragment.SettingsNotificationFragment;
import com.m1racle.yuedong.ui.fragment.TestDBFragment;

public enum UtilActivityPage {

    SETTING (1, R.string.actionbar_title_setting, SettingsFragment.class),
    ABOUT_YD (2, R.string.actionbar_title_about, AboutYDFragment.class),
    MY_INFORMATION (3, R.string.actionbar_title_my_information,
            MyInformationFragment.class),
    SETTING_NOTIFICATION (4, R.string.actionbar_title_setting_notification,
            SettingsNotificationFragment.class),
    SOCIAL_DETAIL(5,R.string.actionbar_title_my_information_detail, MySocialDetailFragment.class),
    DB_TEST(666,R.string.actionbar_title_setting_notification, TestDBFragment.class);

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
