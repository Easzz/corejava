package com.easzz.corejava.utils.data;

/**
 * 数据列
 * Created by 李溪林 on 16-9-8.
 */
public class DataColumn {

    private static final String defaultColumnName = "ColumnName";

    /**
     * 列名
     */
    private String columnName;

    /**
     * 数据类型
     */
    private Class<?> dataType;

    /**
     * 默认值
     */
    private Object defaultValue;

    /**
     * 列标题
     */
    private String caption;

    /**
     * 所属数据表对象
     */
    private DataTable table;

    /**
     * 构造方法
     */
    public DataColumn() {
        this("");
    }

    /**
     * 构造方法
     *
     * @param columnName 列名
     */
    public DataColumn(String columnName) {
        this((columnName == null || columnName.equals("") ? defaultColumnName : columnName), Object.class);
    }

    /**
     * 构造方法
     *
     * @param columnName 列名
     * @param type       列数据类型
     */
    public DataColumn(String columnName, Class<?> type) {
        this.columnName = columnName;
        this.dataType = type;
    }

    /**
     * 获取列名
     *
     * @return 列名
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 设置列名
     *
     * @param columnName 列名
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * 获取列数据类型
     *
     * @return 列数据类型
     */
    public Class<?> getDataType() {
        return dataType;
    }

    /**
     * 设置列数据类型
     *
     * @param dataType 数据类型
     */
    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取列默认值
     *
     * @return 列默认值
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置列默认值
     *
     * @param defaultValue 列默认值
     */
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 获取列标题
     *
     * @return 列标题
     */
    public String getCaption() {
        return caption;
    }

    /**
     * 设置列标题
     *
     * @param caption 列标题
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * 获取列对象所在得数据表对象
     *
     * @return 数据表对象
     */
    public DataTable getTable() {
        return table;
    }

    /**
     * 设置所属数据表对象,本方法访问级别为package
     *
     * @param table 数据表对象
     */
    void setTable(DataTable table) {
        this.table = table;
    }
}
