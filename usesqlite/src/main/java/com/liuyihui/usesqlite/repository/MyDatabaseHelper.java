package com.liuyihui.usesqlite.repository;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.liuyihui.usesqlite.MyApplication;

/**
 * 一个库的 sqlite DB helper类
 * <p>
 * 需要什么表,定义一个建表sql常量
 * <p>
 * 有app运行时创建新表的需求?没有的话,就不需要在运行时让DB更新建表
 *
 * @author liuyi
 * @date 2017/12/20
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "MyDatabaseHelper";
    private final static String DB_FILE_NAME = "BookStore.db";
    /** 数据库版本号,修改版本号,重启app加载此类,则会导致数据库更新 */
    private static final int DB_VERSION = 1001;


    /**
     * 获取单例
     *
     * @return
     */
    public static MyDatabaseHelper getInstance() {
        return MyDatabaseHelperSingletonHolder.MY_DATABASE_HELPER;
    }

    private static class MyDatabaseHelperSingletonHolder {
        //helper的实例化,传入要建的库的信息
        private static final MyDatabaseHelper MY_DATABASE_HELPER =
                new MyDatabaseHelper(MyApplication.getContext(), DB_FILE_NAME, null, DB_VERSION);
    }

    /**
     * 构造方法1
     *
     * @param context
     * @param name    数据库名
     * @param factory
     * @param version 数据库版本
     */
    private MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 构造方法2
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     * @param errorHandler
     */
    private MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    /**
     * 创建数据库回调
     * <p>
     * 仅当db文件不存在，时创建db文件再回调(猜测)
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, TAG + " onCreate called");
        //建表
        for (String createTableSql : Sqls.getTables()) {
            try {
                sqLiteDatabase.execSQL(createTableSql);
            } catch (Exception e) {
                Log.e(TAG, "sql执行失败:", e);
            }
        }
    }
    

    /**
     * 升级回调
     * <p>
     * 在实例化helper发现版本参数变化时回调(猜测)
     * <p>
     * 升级回调时建新表. 但有在运行时升级的需求吗?
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, TAG + " onUpgrade called");
        //回调创建周期
        onCreate(sqLiteDatabase);
        try {
            sqLiteDatabase.execSQL(Sqls.bookData);
        } catch (Exception e) {
            Log.e(TAG, "sql执行失败:", e);
        }
    }
}
