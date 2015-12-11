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
    public StepDayData get(int id) {
        return null;
    }

    @Override
    public boolean update(StepDayData stepDayData, int id) {
        return false;
    }

    public List<StepDayData> getAll() {
        List<StepDayData> list = new ArrayList<>();
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
