package com.easzz.corejava.utils.enums;

/**
 * 排序方式枚举
 * Created by 李溪林 on 17-2-16.
 */
public enum SortDirection {

    ASCENDING(1, "按升序排序"),
    DESCENDING(2, "按降序排序");

    private int key;

    private String description;

    SortDirection(int key, String description){
        this.key = key;
        this.description = description;
    }

    public int getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
