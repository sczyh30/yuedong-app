package com.m1racle.yuedong;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.m1racle.yuedong.base.BaseApplication;
//import com.m1racle.yuedong.bean.Constants;
//import com.m1racle.yuedong.bean.User;
//import com.m1racle.yuedong.cache.DataCleanManager;
//import com.m1racle.yuedong.util.CyptoUtils;
//import com.m1racle.yuedong.util.MethodsCompat;
import com.m1racle.yuedong.dao.LocalUserDaoImpl;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.ApiHttpClient;
import com.m1racle.yuedong.util.StringUtils;
import com.m1racle.yuedong.util.LogUtil;
//import com.m1racle.yuedong.util.UIHelper;


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

    private int loginUid;

    private boolean login;
    LocalUserDaoImpl userDao = new LocalUserDaoImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        initLogin();
    }

    public static AppContext getContext() {
        return instance;
    }

    private void init() {
        // init the http client service
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore mCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(mCookieStore);
        ApiHttpClient.setHttpClient(client);
        ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));

        userDao = new LocalUserDaoImpl();
    }

    private void initLogin() {
        User user = getLoginUser();
        if (null != user && user.getId() > 0) {
            login = true;
            loginUid = user.getId();
        } else {
            this.cleanLoginInfo();
        }
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }


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
        return userDao.getUserInfo();
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.loginUid = 0;
        this.login = false;
        userDao.removeUserInfo();
    }

    public void saveUserInfo(User user) {
        userDao.saveUserInfo(user);
    }

    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

}
