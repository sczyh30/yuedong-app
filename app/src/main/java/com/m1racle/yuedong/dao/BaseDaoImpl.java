package com.m1racle.yuedong.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.database.DrinkDBHelper;
import com.m1racle.yuedong.database.EverydayMotionDBHelper;
import com.m1racle.yuedong.database.SleepDBHelper;
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
            case "sleep":
                helper = new SleepDBHelper(AppContext.getContext(), "sleep_data.db", null, 3);
                break;
            case "drink_water":
                helper = new DrinkDBHelper(AppContext.getContext(), "drink_data.db", null, 3);
                break;
            case "everyday_step":
                helper = new EverydayMotionDBHelper(AppContext.getContext(), "em_step_data.db", null, 3);
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

    protected SQLiteDatabase getDrinkDB(boolean type) {
        return getDatabase("drink_water", type);
    }

    protected SQLiteDatabase getSleepDB(boolean type) {
        return getDatabase("sleep", type);
    }

    protected SQLiteDatabase getEverydayMotionDB(boolean type) {
        return getDatabase("everyday_step", type);
    }
}
