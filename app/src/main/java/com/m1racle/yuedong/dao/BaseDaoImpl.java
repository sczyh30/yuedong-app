package com.m1racle.yuedong.dao;

import android.database.sqlite.SQLiteDatabase;

import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.database.UserDBHelper;

/**
 * Base Dao Impl
 * SQLite 3 Transaction and Execution Util
 */
abstract class BaseDaoImpl {

    protected SQLiteDatabase getUserDB(boolean type) {
        UserDBHelper helper = new UserDBHelper(AppContext.getContext(), "UserLogin.db", null, 2);
        if(type)
            return helper.getWritableDatabase();
        else
            return helper.getReadableDatabase();
    }
}
