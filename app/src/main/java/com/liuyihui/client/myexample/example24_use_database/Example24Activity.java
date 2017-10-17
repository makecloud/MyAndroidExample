package com.liuyihui.client.myexample.example24_use_database;

import static com.liuyihui.client.myexample.MyApplication.getAppPathOnSDCard;
import static com.liuyihui.client.myexample.example24_use_database.MyDatabaseHelper.CREATE_TABLE_SQL;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.liuyihui.client.myexample.R;

import java.io.File;

public class Example24Activity extends AppCompatActivity {
    private final String TAG = "myexample.Example24Activity";
    private SQLiteOpenHelper sqLiteOpenHelper;
    private Button initDBButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        //初始化数据库帮助类
//                sqLiteOpenHelper = new MyDatabaseHelper(Example24Activity.this, null, 1);
        setContentView(R.layout.activity_example24);
        
        
        initDBButton = (Button) findViewById(R.id.initDB);
        
        initDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SQLiteDatabase db1 = sqLiteOpenHelper.getWritableDatabase();
                SQLiteDatabase db2 = SQLiteDatabase.openOrCreateDatabase(getAppPathOnSDCard() + File.separator + "book.db", null);
                db2.execSQL(CREATE_TABLE_SQL);
            }
        });
    }
    
    
}
