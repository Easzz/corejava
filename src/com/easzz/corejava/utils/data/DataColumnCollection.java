package com.easzz.corejava.utils.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据列集合
 * Created by 李溪林 on 16-9-8.
 */
public class DataColumnCollection {

    /**
     * 列集合
     */
    private List<DataColumn> columns;

    /**
     * 所属table对象
     */
    private DataTable table;

    /**
     * 私有构造方法
     */
    private DataColumnCollection(){
        this.columns = new ArrayList<>();
    }

    /**
     * 构造方法
     * @param table 列所属的table对象
     */
    public DataColumnCollection(DataTable table){
        this();
        this.table = table;
    }

    /**
     * 返回列集合的数量
     * @return 列集合的数量
     */
    public int getCount() {
        return this.columns.size();
    }

    /**
     * 往列集合中增加列
     * @return 新增的列对象
     */
    public DataColumn add(){
        DataColumn newColumn = new DataColumn();
        newColumn.setTable(this.table);
        this.columns.add(newColumn);

        return newColumn;
    }

    /**
     * 往列集合中增加列
     * @param column 列对象
     */
    public void add(DataColumn column){
        column.setTable(this.table);
        this.columns.add(column);
    }

    /**
     * 往列集合中增加列
     * @param columnName 列名
     * @return 新增的列对象
     */
    public DataColumn add(String columnName){
        DataColumn newColumn = new DataColumn(columnName);
        newColumn.setTable(this.table);
        this.columns.add(newColumn);

        return newColumn;
    }

    /**
     * 往列集合中增加列
     * @param columnName 列名
     *                   @param columnTitle 列标题
     * @return 新增的列对象
     */
    public DataColumn add(String columnName, String columnTitle){
        DataColumn newColumn = new DataColumn(columnName);
        newColumn.setCaption(columnTitle);
        newColumn.setTable(this.table);
        this.columns.add(newColumn);

        return newColumn;
    }

    /**
     * 往列集合中增加列
     * @param columnName 列名
     * @param columnType 列数据类型
     * @return 新增的列对象
     */
    public DataColumn add(String columnName, Class<?> columnType){
        return this.add(columnName, columnType, null);
    }

    /**
     * 往列集合中增加列
     * @param columnName 列名
     * @param columnType 列数据类型
     *                   @param columnTitle 列标题
     * @return 新增的列对象
     */
    public DataColumn add(String columnName, Class<?> columnType, String columnTitle){
        DataColumn newColumn = new DataColumn(columnName, columnType);
        newColumn.setCaption(columnTitle);
        newColumn.setTable(this.table);
        this.columns.add(newColumn);

        return newColumn;
    }

    /**
     * 清空列集合
     */
    public void clear(){
        this.columns.clear();
    }

    /**
     * 判定指定列名在列集合中是否存在
     * @param columnName 列名
     * @return 存在返回true,否则返回false
     */
    public boolean contains(String columnName){
        boolean exist = false;
        for(DataColumn column : this.columns){
            if(column.getColumnName().equals(columnName)){
                exist = true;break;
            }
        }

        return exist;
    }

    /**
     * 根据列名在列集合中获取对应的列的索引
     * @param columnName 列名
     * @return 列索引
     */
    public int indexOf(String columnName){
        int len = this.getCount();
        for(int index = 0;index < len; index++){
            DataColumn column = this.columns.get(index);
            if(column.getColumnName().equals(columnName)){
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取指定列在列集合中的索引
     * @param column 列对象
     * @return 列的索引
     */
    public int indexOf(DataColumn column){
        int len = this.getCount();
        for(int index = 0;index < len; index++){
            DataColumn col = this.columns.get(index);
            if(col == column){
                return index;
            }
        }
        return -1;
    }

    /**
     * 从列集合中移除列
     * @param column 列对象
     */
    public void remove(DataColumn column){
        int len = this.getCount();
        for(int index = 0;index < len; index++){
            DataColumn col = this.columns.get(index);
            if(col == column){
                this.columns.remove(index);
                break;
            }
        }
    }

    /**
     * 根据列名从列集合中移除列
     * @param columnName 要移除的列的名称
     */
    public void remove(String columnName){
        int len = this.getCount();
        for(int index = 0;index < len; index++){
            DataColumn column = this.columns.get(index);
            if(column.getColumnName().equals(columnName)){
                this.columns.remove(index);
                break;
            }
        }
    }

    /**
     * 根据索引从列集合中移除列
     * @param index 要移除的列的索引
     */
    public void remove(int index){
        int len = this.getCount();
        if(index < len){
            this.columns.remove(index);
        }
    }

    /**
     * 获取指定索引的列对象
     * @param index 列索引
     * @return 列对象
     */
    public DataColumn get(int index){
        int len = this.getCount();
        if(index < len){
            return this.columns.get(index);
        }
        return null;
    }

    /**
     * 获取指定列名的列对象
     * @param columnName 列名
     * @return 列对象
     */
    public DataColumn get(String columnName){
        int len = this.getCount();
        for(int index = 0;index < len;index++){
            DataColumn column = this.columns.get(index);
            if(column.getColumnName().equals(columnName)){
                return column;
            }
        }
        return null;
    }
}
