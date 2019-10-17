package com.liuyihui.client.myexample.example16_recyclerview;

public class ItemData {
    private int id;
    private String name;

    private ItemGroupInfo itemGroupInfo;

    public ItemGroupInfo getItemGroupInfo() {
        return itemGroupInfo;
    }

    public void setItemGroupInfo(ItemGroupInfo itemGroupInfo) {
        this.itemGroupInfo = itemGroupInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
