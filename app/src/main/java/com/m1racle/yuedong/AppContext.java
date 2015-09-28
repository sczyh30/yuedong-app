package com.m1racle.yuedong;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.PersistentCookieStore;

//import com.m1racle.yuedong.api.ApiHttpClient;
import com.m1racle.yuedong.base.BaseApplication;
//import com.m1racle.yuedong.bean.Constants;
//import com.m1racle.yuedong.bean.User;
//import com.m1racle.yuedong.cache.DataCleanManager;
//import com.m1racle.yuedong.util.CyptoUtils;
//import com.m1racle.yuedong.util.MethodsCompat;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.util.StringUtils;
import com.m1racle.yuedong.util.LogUtil;
//import com.m1racle.yuedong.util.UIHelper;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.Properties;
import java.util.UUID;

import static com.m1racle.yuedong.AppConfig.KEY_FRITST_START;
import static com.m1racle.yuedong.AppConfig.KEY_LOAD_IMAGE;
import static com.m1racle.yuedong.AppConfig.KEY_NIGHT_MODE_SWITCH;
import static com.m1racle.yuedong.AppConfig.KEY_TWEET_DRAFT;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 */
public class AppContext extends BaseApplication {

    public static final int PAGE_SIZE = 20;// 默认分页大小

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppContext getContext() {
        return instance;
    }

    private int loginUid;

    private boolean login;

    public static boolean isFristStart() {
        return getPreferences().getBoolean(KEY_FRITST_START, true);
    }

    public static void setFristStart(boolean frist) {
        set(KEY_FRITST_START, frist);
    }

    //夜间模式
    public static boolean getNightModeSwitch() {
        return getPreferences().getBoolean(KEY_NIGHT_MODE_SWITCH, false);
    }

    // 设置夜间模式
    public static void setNightModeSwitch(boolean on) {
        set(KEY_NIGHT_MODE_SWITCH, on);
    }

    public int getLoginUid() {
        return loginUid;
    }

    public boolean isLogin() {
        return login;
    }

    public User getLoginUser() {
        User user = new User();
        user.setId(StringUtils.toInt(getProperty("user.uid"), 0));
        user.setUsername(getProperty("user.username"));
        user.setPortrait(getProperty("user.face"));
        user.setAccount(getProperty("user.account"));
        user.setLocation(getProperty("user.location"));
        user.setFollowers(StringUtils.toInt(getProperty("user.followers"), 0));
        user.setFans(StringUtils.toInt(getProperty("user.fans"), 0));
        user.setScore(StringUtils.toInt(getProperty("user.score"), 0));
        user.setRememberMe(StringUtils.toBool(getProperty("user.isRememberMe")));
        user.setGender(getProperty("user.gender"));
        return user;
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.loginUid = 0;
        this.login = false;
        removeProperty("user.uid", "user.username", "user.face", "user.location",
                "user.followers", "user.fans", "user.score",
                "user.isRememberMe", "user.gender");
    }

    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }


}
