package com.liuyihui.usesqlite.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewDebug;

import com.liuyihui.usesqlite.MyApplication;
import com.liuyihui.usesqlite.repository.annotations.AutoIncrement;
import com.liuyihui.usesqlite.repository.annotations.ColumnName;
import com.liuyihui.usesqlite.repository.annotations.TableName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 通用表访问类
 * <p>
 * 在业务类中创建此DAO的对象,并且将实体类作为泛型传入,即可操作实体类对应的表. 实体类需要添加表名,列名注解.
 *
 * @param <T> 实体类
 * @author liuyi 2017年12月25日18:38:18
 */

public class CommonDAO<T> {
    private static String TAG = "DAO";
    /** 实体类数据库 */
    private SQLiteDatabase DATABASE = MyApplication.getDatabase();
    /** 实体类class */
    private Class<T> tClass;
    /** 表名 */
    private String tableName;
    /** 实体类字段对应的列名map */
    private Map<Field, String> fieldColumnMap = new HashMap<>();
    /** 列名list */
    private List<String> columnNameList = new ArrayList<String>();
    /** 列名数组 */
    private String[] columnNameArray;

    /**
     * DAO构造方法
     * <p>
     * 由于类名上泛型参数T无法得到T的class对象,遂需要在构造方法传入tClass对象
     *
     * @param tClass 实体类的class对象
     */
    public CommonDAO(Class<T> tClass) {
        //获取实体类的class对象
        this.tClass = tClass;

        //从注解得到表名
        tableName = tClass.getAnnotation(TableName.class).value();

        //获取实体类的字段
        for (Field field : tClass.getDeclaredFields()) {
            ColumnName columnName = field.getAnnotation(ColumnName.class);
            if (columnName != null) {
                if (TextUtils.isEmpty(columnName.value())) {
                    //说明该字段没有注解列名,要不要提示?
                }
                fieldColumnMap.put(field, columnName.value());//存一个<字段,列名>map
            }
        }
        //列名列表和数组,能用的到吗,用不到可以删除?
        //存一个列名list
        Iterator<Field> it = fieldColumnMap.keySet().iterator();
        while (it.hasNext()) {
            columnNameList.add(fieldColumnMap.get(it.next()));
        }
        //存一个列名数组
        columnNameArray = columnNameList.toArray(new String[1]);
    }

    /**
     * 根据条件选择一个book记录
     *
     * @param o
     * @return
     */
    public T selectOne(T o) {
        StringBuilder sql = new StringBuilder("select * from " + tableName + " where 1=1 ");
        try {
            //遍历字段,每个字段拼一个条件
            for (Field field : fieldColumnMap.keySet()) {
                if (null != field.get(o)) {
                    sql.append(" and " + fieldColumnMap.get(field) + " = \"" + String.valueOf(field.get(o)) + "\"");
                }
            }
            Log.i(TAG, "Executing SQL:" + sql.toString());
            Cursor cursor = DATABASE.rawQuery(sql.toString(), new String[]{});
            return CursorMapper.mapObject(cursor, tClass);
        } catch (Exception e) {
            Log.e(TAG, "查询单条记录出错", e);
            return null;
        }
    }

    /**
     * 选择所有book记录
     *
     * @return
     */
    public List<T> selectAll() {
        String sql = "select * from " + tableName;
        Cursor cursor = DATABASE.rawQuery(sql, null);
        try {
            return CursorMapper.mapList(cursor, tClass);
        } catch (Exception e) {
            Log.e(TAG, "查询全部记录出错", e);
            return null;
        }
    }

    /**
     * 选择多个book记录
     *
     * @param o
     * @return
     */
    public List<T> selectSome(T o) {
        StringBuilder sql = new StringBuilder("select * from " + tableName + " where 1=1 ");
        try {
            //遍历字段,每个字段拼一个条件
            for (Field field : fieldColumnMap.keySet()) {
                if (null != field.get(o)) {
                    sql.append(" and " + fieldColumnMap.get(field) + " = \"" + String.valueOf(field.get(o)) + "\"");
                }
            }
            Log.i(TAG, "Executing SQL:" + sql.toString());
            Cursor cursor = DATABASE.rawQuery(sql.toString(), new String[]{});
            return CursorMapper.mapList(cursor, tClass);
        } catch (Exception e) {
            Log.e(TAG, "查询记录出错", e);
            return null;
        }
    }

    /**
     * 添加一条book表记录
     *
     * @param o book对象
     * @return
     */
    public long add(T o) {
        ContentValues contentValues = new ContentValues();
        try {
            //遍历字段
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                //自增列跳过
                Annotation autoIncrementAnnotation = field.getAnnotation(AutoIncrement.class);
                if (autoIncrementAnnotation != null) {
                    continue;
                }
                //获取该字段对应列名, 并将该字段值放入contentValue
                if (field.getAnnotation(ColumnName.class) != null) {
                    //字段对应的列名
                    String columnName = field.getAnnotation(ColumnName.class).value();
                    //数据(某列的值)放入contentValues
                    putContentValue(contentValues, columnName, field, o);
                }
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "添加记录出错", e);
        } catch (Exception e) {
            Log.e(TAG, "添加记录出错", e);
        }
        //插入数据库
        return DATABASE.insert(tableName, null, contentValues);
    }

    /**
     * 删除book表记录
     *
     * @param id 表记录的id段值
     * @return
     */
    public int deleteById(int id) {
        String whereClasue = "id = ?";
        return DATABASE.delete(tableName, whereClasue, new String[]{id + ""});
    }

    /**
     * 更新一条book记录
     *
     * @param id id
     * @param o  实体类对象
     * @return
     */
    public int updateById(int id, T o) {
        ContentValues contentValues = new ContentValues();
        try {
            //遍历字段
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                //自增列跳过
                Annotation autoIncrementAnnotation = field.getAnnotation(AutoIncrement.class);
                if (autoIncrementAnnotation != null) {
                    continue;
                }
                //获取该字段对应列名, 并将该字段值放入contentValue
                if (field.getAnnotation(ColumnName.class) != null) {
                    //字段对应的列名
                    String columnName = field.getAnnotation(ColumnName.class).value();
                    //数据(某列的值)放入contentValues
                    putContentValue(contentValues, columnName, field, o);
                }
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "更新出错", e);
        } catch (Exception e) {
            Log.e(TAG, "更新出错", e);
        }
        DATABASE.update(tableName, contentValues, "id = ?", new String[]{});
        return 0;
    }

    /**
     * 根据字段类型,将字段值放到contentValue
     * <p>
     * contentValue是存储表的列和其列值的集合
     *
     * @param contentValues
     * @param columnName    列名
     * @param field         字段
     * @param o             对象
     * @throws Exception
     */
    private void putContentValue(ContentValues contentValues, String columnName, Field field, Object o) throws Exception {
        //String型
        if (String.class.equals(field.getType())) {
            contentValues.put(columnName, (String) field.get(o));
            return;
        }
        //int型
        if (Integer.class.equals(field.getType())) {
            contentValues.put(columnName, (int) field.get(o));
            return;
        }
        //double型
        if (Double.class.equals(field.getType())) {
            contentValues.put(columnName, (double) field.get(o));
            return;
        }
        //long型
        if (Long.class.equals(field.getType())) {
            contentValues.put(columnName, (long) field.get(o));
            return;
        }
        //short型
        if (Short.class.equals(field.getType())) {
            contentValues.put(columnName, (short) field.get(o));
            return;
        }
        throw new Exception("Type Exception");
    }

}
