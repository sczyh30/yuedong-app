package com.m1racle.yuedong.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huawei.huaweiwearable.data.DataTotalMotion;
import com.m1racle.yuedong.entity.StepDayData;

import java.util.ArrayList;
import java.util.List;

/**
 * Everyday Motion Dao Impl
 * @author sczyh30
 * @version 2-rf
 * @since v1.40
 */
public class EverydayMotionDao extends BaseDaoImpl implements BaseDao<StepDayData>{

    @Override
    public boolean save(StepDayData data) {
        SQLiteDatabase db = getEverydayMotionDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getValues(data);
            db.insert("em_table", null, values);
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
    public StepDayData get(int eid) {
        StepDayData data = new StepDayData();
        final String SQL = "SELECT * FROM em_table WHERE eid = " + eid;
        SQLiteDatabase db = getEverydayMotionDB(false);
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(SQL, null);
            if(cursor.moveToFirst()) {
                data.setCalorie(cursor.getInt(cursor.getColumnIndex("calorie")));
                data.setDistance(cursor.getInt(cursor.getColumnIndex("distance")));
                data.setEdate(cursor.getString(cursor.getColumnIndex("edate")));
                data.setEtime(cursor.getInt(cursor.getColumnIndex("etime")));
                data.setStep(cursor.getInt(cursor.getColumnIndex("step")));
                data.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return data;
    }

    public StepDayData getLatest() {
        StepDayData data = new StepDayData();
        final String SQL = "SELECT * FROM em_table WHERE eid = (SELECT MAX(eid) FROM em_table)";
        SQLiteDatabase db = getEverydayMotionDB(false);
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(SQL, null);
            if(cursor.moveToFirst()) {
                data.setCalorie(cursor.getInt(cursor.getColumnIndex("calorie")));
                data.setDistance(cursor.getInt(cursor.getColumnIndex("distance")));
                data.setEdate(cursor.getString(cursor.getColumnIndex("edate")));
                data.setEtime(cursor.getInt(cursor.getColumnIndex("etime")));
                data.setStep(cursor.getInt(cursor.getColumnIndex("step")));
                data.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return data;
    }

    @Override
    public boolean update(StepDayData stepDayData, int id) {
        return false;
    }

    public boolean updateByDate(StepDayData stepDayData, String date) {
        return false;
    }

    public List<StepDayData> getAll() {
        List<StepDayData> list = new ArrayList<>();
        final String SQL = "SELECT * FROM em_table";
        SQLiteDatabase db = getEverydayMotionDB(false);
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(SQL, null);
            if(cursor.moveToFirst()) {
                do {
                    StepDayData data = new StepDayData();
                    data.setCalorie(cursor.getInt(cursor.getColumnIndex("calorie")));
                    data.setDistance(cursor.getInt(cursor.getColumnIndex("distance")));
                    data.setEdate(cursor.getString(cursor.getColumnIndex("edate")));
                    data.setEtime(cursor.getInt(cursor.getColumnIndex("etime")));
                    data.setStep(cursor.getInt(cursor.getColumnIndex("step")));
                    data.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
                    list.add(data);
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

    private ContentValues getValues(StepDayData data) {
        ContentValues values = new ContentValues();
        values.put("step", data.getStep());
        values.put("calorie", data.getCalorie());
        values.put("distance", data.getDistance());
        values.put("etime", data.getEtime());
        values.put("edate", data.getEdate());
        return values;
    }
}
