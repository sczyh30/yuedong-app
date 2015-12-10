package com.m1racle.yuedong.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.database.EverydayMotionDBHelper;
import com.m1racle.yuedong.database.UserDBHelper;
import com.m1racle.yuedong.database.WeightDBHelper;

/**
 * Base Dao Impl
 * SQLite 3 Transaction and Execution Util
 * @author sczyh30
 */
abstract class BaseDaoImpl {

    /**
     * Get the database object by db name
     * @param name db name
     * @param type database type; if true, return the writable database;
     *             else, return the read-only database.
     * @return the database object
     */
    protected SQLiteDatabase getDatabase(String name, boolean type) {
        SQLiteOpenHelper helper;
        switch (name) {
            case "user":
                helper = new UserDBHelper(AppContext.getContext(), "UserLogin.db", null, 2);
                break;
            case "weight_table":
                helper = new WeightDBHelper(AppContext.getContext(), "Weight.db", null, 2);
                break;
            default:
                throw new RuntimeException("No the certain database~");
        }
        if(type)
            return helper.getWritableDatabase();
        else
            return helper.getReadableDatabase();
    }

    protected SQLiteDatabase getUserDB(boolean type) {
        return getDatabase("user", type);
    }

    protected SQLiteDatabase getWeightDB(boolean type) {
        return getDatabase("weight_table", type);
    }

    @Deprecated
    protected SQLiteDatabase getEverydayMotionDB(boolean type) {
        EverydayMotionDBHelper helper = new EverydayMotionDBHelper(AppContext.getContext(), "EverydayMotion.db", null, 2);
        if(type)
            return helper.getWritableDatabase();
        else
            return helper.getReadableDatabase();
    }
}
