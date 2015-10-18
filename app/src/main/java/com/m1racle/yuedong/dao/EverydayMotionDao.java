package com.m1racle.yuedong.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huawei.huaweiwearable.data.DataTotalMotion;

/**
 * Everyday Motion DAO Impl
 * @author sczyh30
 * 用数据库太麻烦，因此废弃
 */
@Deprecated
public class EverydayMotionDao extends BaseDaoImpl {

    public DataTotalMotion getMotion(int type) {
        DataTotalMotion data = new DataTotalMotion();
        String sql = "SELECT * FROM emd WHERE motion_type = ? AND timestamp = (SELECT MAX(timestamp) FROM emd)";
        SQLiteDatabase db = getEverydayMotionDB(false);
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(sql,new String[]{Integer.toString(type)});
            if(cursor.moveToFirst()) {
                data.setMotion_type(type);
                data.setCalorie(getInt(cursor, "calorie"));
                data.setDistance(getInt(cursor, "distance"));
                data.setSleep_time(getInt(cursor, "sleep_time"));
                data.setStep(getInt(cursor, "step"));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return null;
    }

    private int getInt(Cursor cursor, String s) {
        return cursor.getInt(cursor.getColumnIndex(s));
    }

    public void updateInfo(final DataTotalMotion motion, int type) {
        SQLiteDatabase db = getEverydayMotionDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getValues(motion);
            final String sql = "UPDATE emd" +
                    "SET";
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void saveInfo(final DataTotalMotion motion, int type) {
        SQLiteDatabase db = getEverydayMotionDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getValues(motion);
            db.insert("emd", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void removeInfo(final int type) {
        SQLiteDatabase db = getEverydayMotionDB(true);
        db.beginTransaction();
        try {
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private ContentValues getValues(DataTotalMotion data) {
        ContentValues values = new ContentValues();
        values.put("motion_type", data.getMotion_type());
        values.put("step", data.getStep());
        values.put("calorie", data.getCalorie());
        values.put("distance", data.getDistance());
        values.put("sleep_time", data.getSleep_time());
        values.put("timestamp", System.currentTimeMillis());
        return values;
    }
}
