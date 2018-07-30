package com.liuyihui.usesqlite.repository;

import com.liuyihui.usesqlite.repository.annotations.AutoIncrement;
import com.liuyihui.usesqlite.repository.annotations.ColumnName;
import com.liuyihui.usesqlite.repository.annotations.TableName;

/**
 * book实体类
 * <p>
 * 类名需注解上对应表名,属性名注解上对应的列名,id需注解上自增
 *
 * @author liuyi
 * @date 2017/12/20
 */
@TableName("Book")
public class Book {

    @AutoIncrement
    @ColumnName("id")
    public Integer id;
    @ColumnName("author")
    public String author;
    @ColumnName("price")
    public Double price;
    @ColumnName("pages")
    public Integer pages;
    @ColumnName("name")
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book = id:" + id + ",name:" + name + ",price:" + price + ",pages:" + pages + ",author:" + author;
    }
}
