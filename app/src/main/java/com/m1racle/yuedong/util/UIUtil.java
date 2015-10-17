package com.m1racle.yuedong.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.huawei.huaweiwearable.data.DataAlarm;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.base.UtilActivityPage;
import com.m1racle.yuedong.ui.DialogUtil;
import com.m1racle.yuedong.ui.activity.LoginActivity;
import com.m1racle.yuedong.ui.activity.UtilActivity;

/**
 * A very important UI util class
 * @author sczyh30
 * @since 0.1
 */
public class UIUtil {

    public static void openSysBrowser(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToastShort("无法浏览此网页");
        }
    }

    public static void showUserCenter(Context context, int uid,
                                      String username) {
        if (uid == 0 && username.equalsIgnoreCase("匿名")) {
            ToastUtil.toast("提醒你，该用户为非会员");
            return;
        }
        Bundle args = new Bundle();
        args.putInt("uid", uid);
        args.putString("username", username);
        //showActivity(context, UtilActivityPage.USER_CENTER, args);
    }

    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void showActivity(Context context, UtilActivityPage page) {
        Intent intent = new Intent(context, UtilActivity.class);
        intent.putExtra(UtilActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    public static void showActivity(Context context, UtilActivityPage page,
                                    Bundle args) {
        Intent intent = new Intent(context, UtilActivity.class);
        intent.putExtra(UtilActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(UtilActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    public static void showActivitiesDetail(Context context, int maid) {
        if(maid == 0) {
            ToastUtil.toast("获取信息失败");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("ma_id", maid);
        showActivity(context, UtilActivityPage.MOTION_ACTIVITIES_DETAIL, bundle);
    }

    public static void sendAppCrashReport(final Context context) {

        DialogUtil.getConfirmDialog(context, "程序发生异常", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(-1);
            }
        }).show();
    }

    public static void showAboutYD(Context context) {
        showActivity(context, UtilActivityPage.ABOUT_YD);
    }

    public static void showMyInformation(Context context, int uid, String username) {
        if (uid == 0 && username.equalsIgnoreCase("匿名")) {
            ToastUtil.toast("提醒你，该用户为非会员");
            return;
        }
        Bundle args = new Bundle();
        args.putInt("uid", uid);
        args.putString("username", username);
        showActivity(context, UtilActivityPage.MY_INFORMATION, args);
    }

    public static void showDeviceUserSet(Context context) {
        showActivity(context, UtilActivityPage.DEVICE_USER_INFO_SET);
    }

    public static void showSettingNotification(Context context) {
        showActivity(context, UtilActivityPage.SETTING_NOTIFICATION);
    }

    public static void showDeviceAlarmSet(Context context, int max) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", max);
            showActivity(context, UtilActivityPage.DEVICE_ALARM_SET, bundle);

    }

    public static void showDeviceAlarmSet(Context context, DataAlarm data) {
        if (data != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("enable", data.isAlarm_enable());
            bundle.putInt("time", data.getAlarm_time());
            bundle.putInt("cycle", data.getAlarm_cycle());
            bundle.putString("name", data.getAlarm_name());
            bundle.putInt("index", data.getAlarm_index());
            showActivity(context, UtilActivityPage.DEVICE_ALARM_SET, bundle);
        } else {
            showActivity(context, UtilActivityPage.DEVICE_ALARM_SET);
        }
    }

    public static void showHealthAdvice(Context context) {
        showActivity(context, UtilActivityPage.HEALTH_MOTION_ADVICE);
    }

    public static void showSetting(Context context) {
        showActivity(context, UtilActivityPage.SETTING);
    }

    public static void showTemperature(Context context) {
        showActivity(context, UtilActivityPage.TEMPERATURE_DETAIL);
    }

    public static void showDeviceMotionData(Context context) {
        showActivity(context, UtilActivityPage.DEVICE_MOTION_DATA);
    }

    public static void showMotionGoal(Context context) {
        showActivity(context, UtilActivityPage.DEVICE_MOTION_GOAL);
    }

    //TODO:这里需要传入一些数据
    public static void showMotionGoalSet(Context context) {
        showActivity(context, UtilActivityPage.DEVICE_MOTION_GOAL_SET);
    }

    public static void showSleepObserver(Context context) {
        showActivity(context, UtilActivityPage.SLEEP_OBSERVER);
    }

    public static void showDeviceAlarm(Context context) {
        showActivity(context, UtilActivityPage.DEVICE_ALARM);
    }

    public static void showSocialDetail(Context context) {
        showActivity(context, UtilActivityPage.SOCIAL_DETAIL);
    }

    public static void showDeviceUserInfo(Context context) {
        showActivity(context, UtilActivityPage.DEVICE_USER_INFO);
    }

    public static void showEverydayMotion(Context context) {
        showActivity(context, UtilActivityPage.DEVICE_EVERYDAY_MOTION);
    }

    public static void clearAppCache(Activity activity) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ToastUtil.toast("缓存清除成功");
                } else {
                    ToastUtil.toast("缓存清除失败");
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    AppContext.getContext().clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }



}
