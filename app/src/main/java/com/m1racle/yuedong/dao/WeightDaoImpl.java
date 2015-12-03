package com.m1racle.yuedong.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.m1racle.yuedong.entity.Weight;

/**
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
    public boolean remove(int id) {
        return false;
    }

    @Override
    public Weight get(int id) {
        return null;
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
