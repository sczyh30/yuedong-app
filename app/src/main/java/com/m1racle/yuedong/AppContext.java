package com.m1racle.yuedong;

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
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.cache.DataCleanManager;
import com.m1racle.yuedong.dao.LocalUserDaoImpl;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.ApiHttpClient;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.service.NotificationPushService;
import com.m1racle.yuedong.util.StringUtils;

import org.kymjs.kjframe.KJBitmap;

import java.util.Properties;
import java.util.UUID;

import static com.m1racle.yuedong.AppConfig.KEY_FIRST_START;
import static com.m1racle.yuedong.AppConfig.KEY_LOAD_IMAGE;
import static com.m1racle.yuedong.AppConfig.KEY_NIGHT_MODE_SWITCH;
import static com.m1racle.yuedong.AppConfig.KEY_NOTIFICATION_ACCEPT;
import static com.m1racle.yuedong.AppConfig.KEY_NOTIFICATION_SOUND;
import static com.m1racle.yuedong.AppConfig.KEY_NOTIFICATION_VIBRATION;

/**
 * Yuedong App
 * The global context class
 * very significant
 * @since 0.1
 * @author sczyh30
 */
public class AppContext extends BaseApplication {

    private static AppContext instance;

    private int loginUid;

    private boolean login;
    LocalUserDaoImpl userDao = new LocalUserDaoImpl();

    //private RequestQueue mQueue = Volley.newRequestQueue(this);

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
        initNewAPI();
        isFirstStart();
        userDao = new LocalUserDaoImpl();
    }

    private void initNewAPI() {
        //ApiRequestClient.setQueue(mQueue);
        YuedongAPI.setCookie(YuedongAPI.getCookie(this));
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


    public static boolean isFirstStart() {
        return getPreferences().getBoolean(KEY_FIRST_START, true);
    }

    public static void setFirstStart(boolean first) {
        set(KEY_FIRST_START, first);
    }

    //夜间模式
    public static boolean getNightModeSwitch() {
        return getPreferences().getBoolean(KEY_NIGHT_MODE_SWITCH, false);
    }

    public static void setNotificationAccept(boolean b) {
        set(KEY_NOTIFICATION_ACCEPT, b);
    }

    public static boolean getNotificationAccept() {
        return getPreferences().getBoolean(KEY_NOTIFICATION_ACCEPT, false);
    }

    public static void setNotificationSound(boolean b) {
        set(KEY_NOTIFICATION_SOUND, b);
    }

    public static boolean getNotificationSound() {
        return getPreferences().getBoolean(KEY_NOTIFICATION_VIBRATION, false);
    }

    public static void setNotificationViberate(boolean b) {
        set(KEY_NOTIFICATION_SOUND, b);
    }

    public static boolean getNotificationViberate() {
        return getPreferences().getBoolean(KEY_NOTIFICATION_VIBRATION, false);
    }

    public static void startNotification() {
        if(getNotificationAccept()) {
            if(getNotificationSound() && getNotificationViberate()) {
                Intent intent = new Intent(instance, NotificationPushService.class);
                instance.startService(intent);
            }
        }
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
     * Clear the login info
     */
    public void cleanLoginInfo() {
        this.loginUid = 0;
        this.login = false;
        userDao.removeUserInfo();
    }

    /**
     * Logout method
     */
    public void logout() {
        cleanLoginInfo();
        ApiHttpClient.cleanCookie();
        this.cleanCookie();
        this.login = false;
        this.loginUid = 0;
        // notify the app
        Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
        sendBroadcast(intent);
    }

    public void saveUserInfo(User user) {
        this.loginUid = user.getId();
        this.login = true;
        userDao.saveUserInfo(user);
    }

    public void updateUserInfo(final User user) {
        userDao.updateUserInfo(user);
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
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUE_ID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUE_ID, uniqueID);
        }
        return uniqueID;
    }

    public static void setLoadImage(boolean flag) {
        set(KEY_LOAD_IMAGE, flag);
    }

    //Clear cache methods
    public void cleanCookie() {
        removeProperty(AppConfig.CONF_COOKIE);
    }

    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // clear data cache
        DataCleanManager.cleanInternalCache(this);
        DataCleanManager.cleanCustomCache(this.getExternalCacheDir());
        // clear temporary content
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
        new KJBitmap().cleanCache();
    }

}
