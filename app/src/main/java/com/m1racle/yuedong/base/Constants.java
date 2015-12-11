package com.m1racle.yuedong.base;

/**
 * Yuedong app
 * Constant class
 */
public class Constants {

    public static final int APP_DEV_STATUS = 0; // 0 DEBUG, 1 RELEASE, 2 EB

    // intent
    public static final String INTENT_ACTION_USER_CHANGE = "com.m1racle.yuedong.action.USER_CHANGE";

    public static final String INTENT_ACTION_COMMENT_CHANGED = "ncom.m1racle.yuedong.action.COMMENT_CHANGED";

    public static final String INTENT_ACTION_NOTICE = "com.m1racle.yuedong.action.APPWIDGET_UPDATE";

    public static final String INTENT_ACTION_LOGOUT = "com.m1racle.yuedong.action.LOGOUT";

    public static final String INTENT_ON_WEIGHT_GOAL_CHANGE = "com.m1racle.yuedong.action.ON_WEIGHT_GOAL_CHANGE";

    public static final String INTENT_ON_WEIGHT_PRESENT_CHANGE = "com.m1racle.yuedong.action.ON_WEIGHT_PRESENT_CHANGE";

    // message type
    public static final int MESSAGE_DONGTAI = 1;
    public static final int MESSAGE_NOTICE = 2;
}
