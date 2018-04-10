package com.liuyihui.usesqlite.repository;

import java.util.Arrays;
import java.util.List;

/**
 * 所有sql定义在一块
 *
 * @author liuyi
 * @date 2017/12/20
 */

public class Sqls {
    /**
     * book表定义sql
     */
    private static final String CREATE_TABLE_BOOK = "create table book ( " +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "pages integer, " +
            "name text )";
    private static final String CREATE_TABLE_USER = "";
    private static final String CREATE_TABLE_ROLE = "";
    private static final String CREATE_TABLE_PRIVILEGE = "";
    private static final String CREATE_TABLE_USERGROUP = "";

    /**
     * data
     */
    public static final String bookData = "INSERT INTO [book] ([id], [author], [price], [pages], [name]) VALUES (5, '孙新', 2.0, 450, 'java从入门到放弃');\n" +
            "INSERT INTO [book] ([id], [author], [price], [pages], [name]) VALUES (6, 'CSDN', 5.4, 328, '第一行代码');\n" +
            "INSERT INTO [book] ([id], [author], [price], [pages], [name]) VALUES (7, '慧点', 3.3, 142, 'spring实战');\n" +
            "INSERT INTO [book] ([id], [author], [price], [pages], [name]) VALUES (8, 'liuyihui', 89.99, 256, 'mybatis教程');";


    /**
     * 获取所有表定义sql
     *
     * @return
     */
    public static List<String> getTables() {
        return Arrays.asList(
                CREATE_TABLE_BOOK);
    }
}
