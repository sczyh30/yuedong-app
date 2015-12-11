package com.m1racle.yuedong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Yuedong app
 * Sleep Database Helper
 * @author sczyh30
 * @since v1.40
 */
public class SleepDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_SQL = "CREATE TABLE sleep_table (" +
            "sid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "s_date VARCHAR(50) NOT NULL," +
            "light INTEGER NOT NULL DEFAULT 0," +
            "deep INTEGER NOT NULL DEFAULT 0" +
            ")";

    public SleepDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
