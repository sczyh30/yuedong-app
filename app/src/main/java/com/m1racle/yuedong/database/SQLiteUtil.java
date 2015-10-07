package com.m1racle.yuedong.database;

import android.database.sqlite.SQLiteDatabase;

import com.m1racle.yuedong.AppContext;

/**
 * SQLite 3 Transaction and Execution Util
 * should be replaced by the BaseDaoImpl Class
 * @see com.m1racle.yuedong.dao.BaseDaoImpl
 */
@Deprecated
public class SQLiteUtil {

    public static SQLiteDatabase getUserDB(boolean type) {
        UserDBHelper helper = new UserDBHelper(AppContext.getContext(), "UserLogin.db", null, 2);
        if(type)
            return helper.getWritableDatabase();
        else
            return helper.getReadableDatabase();
    }


}
