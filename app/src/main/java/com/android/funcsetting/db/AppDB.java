package com.android.funcsetting.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.funcsetting.AppInfo;
import com.android.funcsetting.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
public class AppDB {
    private static AppDB db;
    private static SQLiteDatabase sqLiteDatabase;
    public final int DEFAULT_APPS = 1;
    public final int APPS = 3;
    public final String DEFAULT_APPS_TABLE = "appinfo_default_list";
    public final String APPS_TABLE = "appinfo_list";
    public final String APP_DB_NAME = "app_info_db";
    private static final String DB_PATH = "/data/data/com.android.funcsetting/database/app_info_db.db";

    public static AppDB getInstance(Context context) {
        if (db == null) {
//            synchronized (AppDB.class) {
//                if (db == null) {
                    db = new AppDB();
                    sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
//                }
            }

//        }
        return db;
    }

    public ArrayList<AppInfo> getDefaultApp(String table) {
        return getAllApp(table);
    }

    public ArrayList<AppInfo> getApp(String table) {
        return getAllApp(table);
    }

    private ArrayList<AppInfo> getAllApp(String table) {
        ArrayList<AppInfo> list = new ArrayList<AppInfo>();

        Cursor cursor = null;
        try {
//            String sql = "select * from "+table+" GROUP BY app_name"; //去掉重复的
            String sql = "select * from " + table + "  package_name";
            cursor = sqLiteDatabase.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                AppInfo appInfo = new AppInfo();
                appInfo.setPackerName(cursor.getString(cursor.getColumnIndex("package_name")));
                appInfo.setIntroduction(cursor.getString(cursor.getColumnIndex("introduction")));
                appInfo.setIndex(cursor.getInt(cursor.getColumnIndex("index")));
                list.add(appInfo);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    /**
     * 删除全部包括自增长id
     */
    public void delete() {
        sqLiteDatabase.execSQL("delete  from " + DEFAULT_APPS_TABLE);
//        sqLiteDatabase.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = '"+DEFAULT_APPS_TABLE+"'");
        sqLiteDatabase.execSQL("delete  from " + APPS_TABLE);
//        sqLiteDatabase.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = '"+APPS_TABLE+"'");
//        sqLiteDatabase.close();
    }

    public void addAll(List<AppInfo> list) {
//        ContentValues values = new ContentValues();
        for (AppInfo info : list) {
            if (info.getIndex() == DEFAULT_APPS || info.getIndex() == APPS ) {
                if (sqLiteDatabase .isOpen()) {
                    sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
                }
//                values.put("package_name", info.getPackerName());
//                values.put("index", info.getIndex());
//                values.put("introduction", info.getIntroduction());
                String sq="";
                if (info.getIndex() == DEFAULT_APPS) {
                     sq="INSERT INTO "+DEFAULT_APPS_TABLE+" VALUES ('"+info.getIntroduction()+"','"+info.getIndex()+"','"+ info.getPackerName()+"', '"+info.getAppName()+"')";


//                   long i= sqLiteDatabase.insert(DEFAULT_APPS_TABLE, null, values);
                } else if (info.getIndex() == APPS) {
                     sq="INSERT INTO "+APPS_TABLE+" VALUES ('"+info.getIntroduction()+"','"+info.getIndex()+"','"+ info.getPackerName()+"', '"+info.getAppName()+"')";
//                    sqLiteDatabase.insert(APPS_TABLE, null, values);
                }
                sqLiteDatabase.execSQL(sq);
            }
        }
        sqLiteDatabase.close();

    }

    public void add(AppInfo info) {
        ContentValues values = new ContentValues();
        values.put("package_name", info.getPackerName());
        values.put("index", info.getIndex());
        values.put("introduction", info.getIntroduction().isEmpty());
        if (sqLiteDatabase == null) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        }
        if (info.getIndex() == DEFAULT_APPS) {
            sqLiteDatabase.insert(DEFAULT_APPS_TABLE, null, values);
        } else if (info.getIndex() == APPS) {
            sqLiteDatabase.insert(APPS_TABLE, null, values);
        }

    }
    public void closeDB(){
        sqLiteDatabase.close();
        db=null;
    }
}
