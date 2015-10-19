package com.m1racle.yuedong.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m1racle.yuedong.entity.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Json Resolver Util
 * @author sczyh30
 */
public class JsonUtil {

    private JsonUtil(){}

    private static Gson gson = new Gson();
    public static <T> ArrayList<T> resolveJson(String jsonData) {
        Type listType = new TypeToken<ArrayList<T>>(){}.getType();
        return gson.fromJson(jsonData, listType);
    }

    public static ArrayList<User> resolveUsers(String jsonData) {
        Type listType = new TypeToken<ArrayList<User>>(){}.getType();
        return gson.fromJson(jsonData, listType);
    }

    public static Object resolveSingle(String jsonData, Class<?> cls) {
        return gson.fromJson(jsonData, cls);
    }

    public static User resolveSingleUser(String jsonData) {
        try {
            return gson.fromJson(jsonData, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.toast("解析用户数据时出现错误");
            return null;
        }
    }

    public static DataResult resolveDataResult(String jsonData) {
        return gson.fromJson(jsonData, DataResult.class);
    }

    public static LoginResult resolveLoginResult(String jsonData) {
        return gson.fromJson(jsonData, LoginResult.class);
    }

    public static MotionActivitiesPre resolveMAs(String jsonData) {
        return gson.fromJson(jsonData, MotionActivitiesPre.class);
    }

    public static ArrayList<MotionActivitiesPre> resolveMAList(String jsonData) {
        try {
            Type listType = new TypeToken<ArrayList<MotionActivitiesPre>>(){}.getType();
            return gson.fromJson(jsonData, listType);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.toast("解析数据时出现错误");
            return null;
        }

    }

    public static UserDetail resolveUserDetail(String jsonData) {
        return gson.fromJson(jsonData, UserDetail.class);
    }

    public static Update resolveUpdate(String jsonData) {
        return gson.fromJson(jsonData, Update.class);
    }

    public static BaseFriend resolveBaseFriend(String jsonData) {
        return gson.fromJson(jsonData, BaseFriend.class);
    }
}
