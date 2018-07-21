package com.easzz.corejava.utils.enums;

/**
 * 排序方式枚举
 * Created by 李溪林 on 17-2-16.
 */
public enum ExcelVersion {

    XLS(2003, "2003版exlcel,文件扩展名[xls]"),
    XLSX(2007, "2007版exlcel,文件扩展名[xlsx]");

    private int key;

    private String description;

    ExcelVersion(int key, String description){
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
