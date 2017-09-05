/*
 * Copyright (c) 2017. 方燚
 */

package com.android.funcsetting.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBManager {
    public static final String DB_NAME = "app_info_db.db"; //保存的数据库文件名
    public static final String DB_PATH = "/data/data/com.android.funcsetting/database/";
    private Context context;
   public DBManager(Context context) {
        this.context = context;
    }
    public  boolean openDatabase() {
        boolean b = false;
        if (!(new File(DB_PATH + DB_NAME).exists())) {
            File file = new File(DB_PATH);
            if (!(file.exists())) {
                file.mkdir();
            }
            try {
                InputStream is = context.getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] buffer = new byte[1024];
                while ((is.read(buffer)) > 0) {
                    os.write(buffer);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            b = true;
        }
        return b;

    }
}
