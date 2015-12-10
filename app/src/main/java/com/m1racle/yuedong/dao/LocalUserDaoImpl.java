package com.m1racle.yuedong.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.database.UserDBHelper;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.crypt.MD5Util;

/**
 * Yuedong app
 * Local User Dao Service Impl
 * @author sczyh30
 */
public class LocalUserDaoImpl extends BaseDaoImpl implements UserDao {

    /**
     * Save the user info to the database
     * @param user User entity
     */
    @Override
    public void saveUserInfo(final User user) {
        SQLiteDatabase db = getUserDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getUserValues(user);
            db.insert("user", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

    }

    public void updateUserInfo(final User user) {
        SQLiteDatabase db = getUserDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getUserValues(user);
            db.update("user", values, "rememberMe != ?", new String[]{Integer.toString(0)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public void updateUserInfo(final User user, int id) {
        SQLiteDatabase db = getUserDB(true);
        db.beginTransaction();
        try {
            ContentValues values = getUserValues(user);
            db.update("user", values, "id = ?", new String[] {Integer.toString(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public User getUserInfo() {
        User user = new User();
        final String sql = "SELECT * FROM user";
        SQLiteDatabase db = getUserDB(false);
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToFirst()) {
                user.setAccount(cursor.getString(cursor.getColumnIndex("account")));
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                user.setPortrait(cursor.getString(cursor.getColumnIndex("face")));
                user.setGender(cursor.getInt(cursor.getColumnIndex("gender")));
                user.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                user.setRememberMe(cursor.getInt(cursor.getColumnIndex("rememberMe")) > 0);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return user;
    }

    @Deprecated
    public User getUserInfo(int id) {
        User user = new User();
        return user;
    }

    @Override
    public void removeUserInfo() {
        SQLiteDatabase db = getUserDB(true);
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM user where rememberMe = 1");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * A common method
     * intended to get the ContentValues entity
     * in a common way
     * @param user User entity
     * @return the ContentValues entity
     */
    private ContentValues getUserValues(User user) {
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("account", user.getAccount());
        values.put("username", user.getUsername());
        values.put("password", MD5Util.MD5(user.getPassword()));
        values.put("face", user.getPortrait());
        values.put("location", user.getLocation());
        values.put("gender", user.getGender());
        values.put("followers", user.getFollowers());
        values.put("fans", user.getFans());
        values.put("score", user.getScore());
        values.put("rememberMe", 1);
        return values;
    }
}
