package com.m1racle.yuedong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Yuedong app
 * Weight Database Helper
 * SQLite 3 support
 * @author sczyh30
 * @since v1.22
 */
public class WeightDBHelper extends SQLiteOpenHelper{

    private static final String CREATE_SQL = "CREATE TABLE weight (" +
            "wid int NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "weight float NOT NULL," +
            "height float NOT NULL," +
            "index float NOT NULL," +
            "w_time varchar(40) NOT NULL" +
            "tip varchar(255) NOT NULL" +
            ")";

    private Context context;

    public WeightDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(CREATE_SQL);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no need
    }

}
