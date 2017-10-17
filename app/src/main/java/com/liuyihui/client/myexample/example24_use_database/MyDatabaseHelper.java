package com.liuyihui.client.myexample.example24_use_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuyh on 2017/6/22.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "myexample.MyDatabaseHelper";
    private final static String DB_NAME = "book.db";
    public static final String CREATE_TABLE_SQL = "create table Book (" + "id integer primary key autoincrement, " + "author text, " + "price real, " + "page integer, " + "name text"
            + ")";
    
    public MyDatabaseHelper(Context context, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, DB_NAME, cursorFactory, version);
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建Book表
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
        
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        
    }
}
