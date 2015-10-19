package com.m1racle.yuedong.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.huawei.huaweiwearable.data.DataHealthGoal;
import com.huawei.huaweiwearable.data.DataUserInfo;
import com.m1racle.yuedong.AppContext;

/**
 * Yuedong app
 * Xml Cache Manager
 * @author sczyh30
 */
public class XmlCacheManager {

    private static Context mContext = AppContext.getContext();

    public static void saveGoal(int type, DataHealthGoal goal) {
        SharedPreferences cache = mContext.getSharedPreferences("motion_goal_t" + type, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cache.edit();
        editor.putInt("type", goal.getMotion_type());
        editor.putInt("gtype", goal.getGoal_type());
        editor.putInt("step", goal.getStep_goal());
        editor.putInt("energy", goal.getEnergy_burn_goal());
        editor.putInt("distance", goal.getSport_distance_goal());
        editor.putInt("dur", goal.getGoal_duration());
        editor.apply();
    }

    public static DataHealthGoal readGoal(int type) {
        SharedPreferences cache = mContext.getSharedPreferences("motion_goal_t" + type, Context.MODE_PRIVATE);
        DataHealthGoal goal = new DataHealthGoal();
        goal.setMotion_type(cache.getInt("type", 1));
        goal.setEnergy_burn_goal(cache.getInt("energy", 500));
        goal.setGoal_duration(cache.getInt("dur", 1));
        goal.setGoal_type(cache.getInt("gtype", 1));
        goal.setSport_distance_goal(cache.getInt("distance", 1000));
        goal.setStep_goal(cache.getInt("step", 2000));
        return goal;
    }

    public static void saveDeviceUser(DataUserInfo user) {
        SharedPreferences cache = mContext.getSharedPreferences("device_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cache.edit();
        editor.putInt("age", user.getAge());
        editor.putInt("birthday", user.getBirthday());
        editor.putInt("height", user.getHeight());
        editor.putInt("weight", user.getWeight());
        editor.putInt("gender", user.getGender());
        editor.putInt("walk", user.getWalk_step_length());
        editor.putInt("run", user.getRun_step_length());
        editor.apply();
    }

    public static DataUserInfo readDeviceUser() {
        SharedPreferences cache = mContext.getSharedPreferences("device_user", Context.MODE_PRIVATE);
        DataUserInfo user = new DataUserInfo();
        user.setAge(cache.getInt("age", 0));
        user.setBirthday(cache.getInt("birthday", 0));
        user.setHeight(cache.getInt("height", 0));
        user.setWeight(cache.getInt("weight", 0));
        user.setGender(cache.getInt("gender", 0));
        user.setWalk_step_length(cache.getInt("walk", 0));
        user.setRun_step_length(cache.getInt("run", 0));
        return user;
    }

}
