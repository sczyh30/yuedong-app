package com.m1racle.yuedong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m1racle.yuedong.util.LogUtil;

/**
 * Yuedong app
 * Everyday Motion Database Helper
 * @author sczyh30
 * @since v1.40 (refactored)
 */
public class EverydayMotionDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_SQL = "CREATE TABLE emd (" +
            "motion_type integer NOT NULL," +
            "step integer NOT NULL," +
            "calorie integer NOT NULL," +
            "distance integer NOT NULL," +
            "sleep_time integer NOT NULL," +
            "timestamp integer NOT NULL" +
            ")";

    private Context context;

    public EverydayMotionDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
        LogUtil.log("EverydayMotionDB onCreate : CREATE TABLE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
