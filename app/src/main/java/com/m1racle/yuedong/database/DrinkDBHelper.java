package com.m1racle.yuedong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m1racle.yuedong.util.LogUtil;

/**
 * Yuedong app
 * Everyday Motion database helper
 * @author sczyh30
 * @since v1.40
 */
public class DrinkDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_SQL = "CREATE TABLE drink (" +
            "did INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "cup integer NOT NULL DEAULT 0," +
            ")";

    private Context context;

    public DrinkDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
        LogUtil.log("DrinkWaterDB onCreate : CREATE TABLE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
