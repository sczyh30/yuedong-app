package com.m1racle.yuedong.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m1racle.yuedong.entity.Weight;

import java.util.ArrayList;
import java.util.List;

/**
 * Yuedong app
 * Weight Dao Service Impl
 * @author sczyh30
 * @since v1.22
 */
public class WeightDaoImpl extends BaseDaoImpl implements BaseDao<Weight> {

    @Override
    public boolean save(Weight weight) {
        SQLiteDatabase db = getUserDB(true);
        try {
            ContentValues values = getValues(weight);
            db.insert("weight", null, values);
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
    public boolean remove(int wid) {
        return false;
    }

    //TODO:代码重复率过高，应通过AOP思想对重复部分进行抽象并注入！

    @Override
    public Weight get(int wid) {
        Weight weight = new Weight();
        final String SQL = "SELECT * FROM weight WHERE wid = " + wid;
        SQLiteDatabase db = getUserDB(false);
        try {
            Cursor cursor = db.rawQuery(SQL, null);
            if(cursor.moveToFirst()) {
                weight.setWid(cursor.getInt(cursor.getColumnIndex("wid")));
                weight.setIndex(cursor.getFloat(cursor.getColumnIndex("index")));
                weight.setHeight(cursor.getFloat(cursor.getColumnIndex("height")));
                weight.setWeight(cursor.getFloat(cursor.getColumnIndex("weight")));
                weight.setTip(cursor.getString(cursor.getColumnIndex("tip")));
                weight.setwTime(cursor.getString(cursor.getColumnIndex("w_time")));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return weight;
    }

    /**
     * Get the latest weight data from the database
     * order by wid.
     * @return the latest weight data
     */
    public Weight getLatest() {
        Weight weight = new Weight();
        final String SQL = "SELECT * FROM weight WHERE wid = (SELECT MAX(wid) FROM weight)";
        SQLiteDatabase db = getUserDB(false);
        try {
            Cursor cursor = db.rawQuery(SQL, null);
            if(cursor.moveToFirst()) {
                weight.setWid(cursor.getInt(cursor.getColumnIndex("wid")));
                weight.setIndex(cursor.getFloat(cursor.getColumnIndex("index")));
                weight.setHeight(cursor.getFloat(cursor.getColumnIndex("height")));
                weight.setWeight(cursor.getFloat(cursor.getColumnIndex("weight")));
                weight.setTip(cursor.getString(cursor.getColumnIndex("tip")));
                weight.setwTime(cursor.getString(cursor.getColumnIndex("w_time")));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return weight;
    }

    /**
     * Get all weight data from database
     * @return weight data list
     */
    public List<Weight> getAll() {
        List<Weight> list = new ArrayList<>();
        final String SQL = "SELECT * FROM weight";
        SQLiteDatabase db = getUserDB(false);
        try {
            Cursor cursor = db.rawQuery(SQL, null);
            if(cursor.moveToFirst()) {
                do {
                    Weight weight = new Weight();
                    weight.setWid(cursor.getInt(cursor.getColumnIndex("wid")));
                    weight.setIndex(cursor.getFloat(cursor.getColumnIndex("index")));
                    weight.setHeight(cursor.getFloat(cursor.getColumnIndex("height")));
                    weight.setWeight(cursor.getFloat(cursor.getColumnIndex("weight")));
                    weight.setTip(cursor.getString(cursor.getColumnIndex("tip")));
                    weight.setwTime(cursor.getString(cursor.getColumnIndex("w_time")));
                    list.add(weight);
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
    public boolean update(Weight weight, int id) {
        return false;
    }

    private ContentValues getValues(Weight weight) {
        ContentValues values = new ContentValues();
        values.put("wid", weight.getWid());
        values.put("weight", weight.getWeight());
        values.put("height", weight.getHeight());
        values.put("index", weight.getIndex());
        values.put("w_time", weight.getwTime());
        values.put("tip", weight.getTip());
        return values;
    }
}
