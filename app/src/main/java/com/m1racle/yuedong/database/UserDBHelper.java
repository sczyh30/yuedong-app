package com.m1racle.yuedong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

/**
 * User Login Database(Local) Helper class
 * SQLite 3 support
 */
public class UserDBHelper extends SQLiteOpenHelper {

    public static final String CREATE_SQL = "CREATE TABLE user (" +
            "id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "password varchar(60) NOT NULL," +
            "username varchar(50) NOT NULL UNIQUE," +
            "face varchar(255) NOT NULL," +
            "account varchar(50) NOT NULL UNIQUE," +
            "location varchar(50) NOT NULL," +
            "gender integer NOT NULL," +
            "followers integer NOT NULL," +
            "fans integer NOT NULL," +
            "score integer NOT NULL," +
            "rememberMe integer NOT NULL" +
            ")";

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
        onCreate(sqLiteDatabase);
    }
}
