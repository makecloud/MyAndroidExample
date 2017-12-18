package com.liuyihui.logapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用slf4j + logback android 组合,实现日志打印到文件
 * <p>
 * logback android 有扩展的appender 如sqlite appender实现将日志写到sqlite数据库,=待学习使用
 *
 * @author liuyi 2017年11月30日16:10:47
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger logger = LoggerFactory.getLogger(MainActivity.class);
        logger.info("logback printed");
    }
}
