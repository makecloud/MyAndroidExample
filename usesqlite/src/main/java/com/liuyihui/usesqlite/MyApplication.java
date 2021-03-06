package com.liuyihui.usesqlite;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liuyihui.usesqlite.repository.MyDatabaseHelper;

/**
 * Created by liuyi on 2017/12/20.
 */

public class MyApplication extends Application {

    private static Context context;
    private static SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 获取数据库实例,放在application中,方便在各地方使用
     * <p>
     * 有必要放到Application来让MyDatabaseHelper更容易找到吗？没有。
     *
     * @return
     */
    public static SQLiteDatabase getDatabase() {
        if (database == null) {
            database = MyDatabaseHelper.getInstance().getWritableDatabase();
        }
        return database;
    }
}
