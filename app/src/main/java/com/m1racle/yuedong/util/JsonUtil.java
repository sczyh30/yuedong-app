package com.m1racle.yuedong.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m1racle.yuedong.entity.DataResult;
import com.m1racle.yuedong.entity.LoginResult;
import com.m1racle.yuedong.entity.MotionActivities;
import com.m1racle.yuedong.entity.User;

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
        return gson.fromJson(jsonData, User.class);
    }

    public static DataResult resolveDataResult(String jsonData) {
        return gson.fromJson(jsonData, DataResult.class);
    }

    public static LoginResult resolveLoginResult(String jsonData) {
        return gson.fromJson(jsonData, LoginResult.class);
    }

    public static MotionActivities resolveMAs(String jsonData) {
        return gson.fromJson(jsonData, MotionActivities.class);
    }

    public static ArrayList<MotionActivities> resolveMAList(String jsonData) {
        Type listType = new TypeToken<ArrayList<MotionActivities>>(){}.getType();
        return gson.fromJson(jsonData, listType);
    }
}
