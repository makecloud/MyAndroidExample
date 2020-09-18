package com.liuyihui.client.myexample.demo16_recyclerview;

public class ItemGroupInfo {
    private int groupId;
    private String GroupName;
    private boolean isGroupFirst;
    private boolean isGroupLast;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public boolean isGroupFirst() {
        return isGroupFirst;
    }

    public void setGroupFirst(boolean groupFirst) {
        isGroupFirst = groupFirst;
    }

    public boolean isGroupLast() {
        return isGroupLast;
    }

    public void setGroupLast(boolean groupLast) {
        isGroupLast = groupLast;
    }
}
