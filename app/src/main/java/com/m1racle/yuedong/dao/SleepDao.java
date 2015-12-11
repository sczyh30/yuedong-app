package com.m1racle.yuedong.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m1racle.yuedong.entity.SleepData;

import java.util.ArrayList;
import java.util.List;

/**
 * Yuedong app
 * Sleep Data Dao Service Impl
 * @author sczyh30
 * @since v1.40
 */
public class SleepDao extends BaseDaoImpl implements BaseDao<SleepData> {

    // I need a fucking ORM!

    @Override
    public boolean save(SleepData data) {
        SQLiteDatabase db = getSleepDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getValues(data);
            db.insert("sleep_table", null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public SleepData get(int id) {
        return null;
    }

    public SleepData getLatest() {
        SleepData sleepData = new SleepData();
        final String SQL = "SELECT * FROM sleep_table WHERE aid = (SELECT MAX(sid) FROM sleep_table)";
        SQLiteDatabase db = getSleepDB(false);
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(SQL, null);
            if(cursor.moveToFirst()) {
                sleepData.setDate(cursor.getString(cursor.getColumnIndex("s_date")));
                sleepData.setDeep(cursor.getInt(cursor.getColumnIndex("deep")));
                sleepData.setLight(cursor.getInt(cursor.getColumnIndex("light")));
                sleepData.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return sleepData;
    }

    public List<SleepData> getAllData() {
        List<SleepData> list = new ArrayList<>();
        final String SQL = "SELECT * FROM sleep_table";
        SQLiteDatabase db = getSleepDB(false);
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(SQL, null);
            if(cursor.moveToFirst()) {
                do {
                    SleepData sleepData = new SleepData();
                    sleepData.setDate(cursor.getString(cursor.getColumnIndex("s_date")));
                    sleepData.setDeep(cursor.getInt(cursor.getColumnIndex("deep")));
                    sleepData.setLight(cursor.getInt(cursor.getColumnIndex("light")));
                    sleepData.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
                    list.add(sleepData);
                } while(cursor.moveToNext());
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    @Override
    public boolean update(SleepData sleepData, int id) {
        SQLiteDatabase db = getSleepDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getValues(sleepData);
            db.update("sleep_table", values, "sid = ?", new String[] {Integer.toString(id)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    public boolean updateByDate(SleepData sleepData, String date) {
        SQLiteDatabase db = getSleepDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getValues(sleepData);
            db.update("sleep_table", values, "s_date = ?", new String[]{date});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    private ContentValues getValues(SleepData data) {
        ContentValues values = new ContentValues();
        values.put("s_date", data.getDate());
        values.put("light", data.getLight());
        values.put("deep", data.getDeep());
        return values;
    }
}
