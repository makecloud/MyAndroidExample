package com.liuyihui.usesqlite.repository;

import android.database.Cursor;
import android.util.Log;

import com.liuyihui.usesqlite.repository.annotations.ColumnName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sqlite 查询结果cursor对象映射到实体对象的工具类
 *
 * @author liuyi
 * @date 2017/12/25
 */

public class CursorMapper {

    /**
     * cursor对象映射到一个实体对象
     *
     * @param cursor cursor
     * @param cls class
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T mapObject(Cursor cursor, Class<T> cls) throws Exception {
        if (cursor.getCount() > 1) {
            throw new Exception("multiResult cursor , should choose list mappter");
        }
        //创建实体实例
        T entityInstance = cls.newInstance();
        if (cursor.getCount() < 1) {
            Log.e(CursorMapper.class.getSimpleName(), "cursor is Empty !");
            return cls.newInstance();
        }
        if (cursor.moveToFirst()) {
            try {
                //<注解列名,字段> map
                Map<String, Field> fieldColumnMap = new HashMap<>();
                for (Field field : cls.getDeclaredFields()) {
                    ColumnName columnName = field.getAnnotation(ColumnName.class);
                    if (columnName != null) {
                        fieldColumnMap.put(columnName.value(), field);
                    }
                }

                //遍历cursor中列,填充实例
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    //cursor列名
                    String columnName = cursor.getColumnName(i);
                    if (fieldColumnMap.containsKey(columnName)) {
                        Field field = fieldColumnMap.get(columnName);
                        if (String.class.equals(field.getType())) {//string
                            String s = cursor.getString(i);//test line
                            field.set(entityInstance, cursor.getString(i));
                        } else if (Integer.class.equals(field.getType())) {//int
                            int x = cursor.getInt(i);//test line
                            field.set(entityInstance, cursor.getInt(i));
                        } else if (Long.class.equals(field.getType())) {//long
                            field.set(entityInstance, cursor.getLong(i));
                        } else if (Double.class.equals(field.getType())) {//double
                            double x = cursor.getDouble(i);//test line
                            field.set(entityInstance, cursor.getDouble(i));
                        } else if (Float.class.equals(field.getType())) {//float
                            field.set(entityInstance, cursor.getFloat(i));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("cursor mapper Exception!");
            }
        }
        //返回实例
        return entityInstance;
    }

    /**
     * cursor对象映射到一个列表集合
     *
     * @param cursor
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> mapList(Cursor cursor, Class<T> cls) throws Exception {
        //创建实体实例
        List<T> tList = new ArrayList<>();
        if (cursor.getCount() < 1) {
            Log.e(CursorMapper.class.getSimpleName(), "cursor is Empty !");
            return tList;
        }
        if (cursor.moveToFirst()) {
            try {
                do {
                    //创建一个实体对象
                    T t = cls.newInstance();

                    //<注解列名,字段> map
                    Map<String, Field> fieldColumnMap = new HashMap<>();
                    for (Field field : cls.getDeclaredFields()) {
                        ColumnName columnName = field.getAnnotation(ColumnName.class);
                        if (columnName != null) {
                            fieldColumnMap.put(columnName.value(), field);
                        }
                    }

                    //遍历cursor中列
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        //cursor列名
                        String columnName = cursor.getColumnName(i);
                        if (fieldColumnMap.containsKey(columnName)) {
                            Field field = fieldColumnMap.get(columnName);
                            if (String.class.equals(field.getType())) {//string
                                String s = cursor.getString(i);
                                field.set(t, cursor.getString(i));
                            } else if (Integer.class.equals(field.getType())) {//int
                                int x = cursor.getInt(i);//test
                                field.set(t, cursor.getInt(i));
                            } else if (Long.class.equals(field.getType())) {//long
                                field.set(t, cursor.getLong(i));
                            } else if (Double.class.equals(field.getType())) {//double
                                double x = cursor.getDouble(i);//test
                                field.set(t, cursor.getDouble(i));
                            } else if (Float.class.equals(field.getType())) {//float
                                field.set(t, cursor.getFloat(i));
                            }
                        }
                    }
                    //实体对象加入list
                    tList.add(t);
                } while (cursor.moveToNext());
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("cursor mapper Exception!");
            }
        }
        return tList;
    }

}
