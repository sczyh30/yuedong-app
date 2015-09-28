package com.m1racle.yuedong.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.m1racle.yuedong.database.UserDBHelper;
import com.m1racle.yuedong.util.LogUtil;

public class DBTestService extends IntentService {

    public DBTestService() {
        super("DBTestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        UserDBHelper helper = new UserDBHelper(this, "UserTest.db", null, 2);
        helper.getWritableDatabase();
    }

    @Override
    public void onDestroy() {
        LogUtil.log("DBTestService => destroy");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        LogUtil.log("DBTestService => create");
        super.onCreate();
    }
}
