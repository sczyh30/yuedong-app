package com.m1racle.yuedong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m1racle.yuedong.util.LogUtil;

/**
 * Yuedong app
 * Weight Database Helper
 * SQLite 3 support
 * @author sczyh30
 * @since v1.22
 */
public class WeightDBHelper extends SQLiteOpenHelper{

    private static final String CREATE_SQL = "CREATE TABLE weight_table (" +
            "wid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "weight float NOT NULL," +
            "height float NOT NULL," +
            "bmi float NOT NULL," +
            "w_time varchar(40) NOT NULL," +
            "tip varchar(60)" +
            ")";

    private Context context;

    public WeightDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.beginTransaction();
        try {
            db.execSQL(CREATE_SQL);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.endTransaction();*/
        db.execSQL(CREATE_SQL);
        LogUtil.log("WeightDB onCreate : CREATE TABLE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE weight_table");
        //onCreate(db);
        // no need
    }

}
