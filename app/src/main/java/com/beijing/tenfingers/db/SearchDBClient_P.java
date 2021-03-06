package com.beijing.tenfingers.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Torres on 2016/12/22.
 */

public class SearchDBClient_P extends BaseDBHelper {

    private static SearchDBClient_P mClient;

    private String tableName = SYS_CASCADE_DISCOVER_SEARCH;

    public SearchDBClient_P(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public static SearchDBClient_P get(Context context) {
        return mClient == null ? mClient = new SearchDBClient_P(context)
                : mClient;
    }

    /**
     * 插入一条记录
     *
     * @param 
     * @return
     */
    public boolean insert(String search) {
        SQLiteDatabase db = getWritableDatabase();
        boolean success = true;
        try {
            db.execSQL(
                    ("insert into " + tableName + "(searchname) " + "values (?)"),
                    new Object[] { search });
        } catch (SQLException e) {
            success = false;
            Log.w("insert", "insert e=" + e);
        }
        db.close();
        return success;
    }

    /**
     * 清空
     */
    public void clear() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("delete from " + tableName);
        } catch (SQLiteException e) {
        } catch (SQLException e) {
        }
        db.close();
    }

    /**
     * 判断表是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        int num = cursor.getCount();
        cursor.close();
        db.close();
        return 0 == num;
    }

    /**
     * 获取
     *
     * @return
     */
    public ArrayList<String> select() {
        ArrayList<String> counts = null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from " + tableName, null);
            if (cursor != null && cursor.getCount() > 0) {
                counts = new ArrayList<String>();
                cursor.moveToFirst();
                String count;
                for (int i = 0; i < cursor.getCount(); i++) {
                    count = cursor.getString(0);
                    counts.add(0, count);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.w("select", "select e=" + e);
        }
        if (cursor != null)
            cursor.close();
        db.close();
        return counts;
    }

    public boolean delete(String searchname) {
        boolean success;
        String conditions = " where searchname='" + searchname + "'";
        String sql = "delete from " + tableName + conditions;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
            success = true;
        } catch (Exception e) {
            success = false;
        }
        db.close();
        return success;
    }
}
