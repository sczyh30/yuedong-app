package com.m1racle.yuedong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

/**
 * UserDBHelper
 */
public class UserDBHelper extends SQLiteOpenHelper {

    public static final String CREATE_SQL = "CREATE TABLE db_test1("
            + "id integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "username varchar(255) NOT NULL )";

    private Context context;

    public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SQL);
        LogUtil.log("UserDB onCreate : CREATE TABLE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS db_test1");
        onCreate(sqLiteDatabase);
    }
}
