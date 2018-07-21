package com.easzz.corejava.utils.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据行集合
 * Created by 李溪林 on 16-9-8.
 */
public class DataRowCollection {

    private List<DataRow> rows;

    private DataTable table;

    private DataRowCollection(){
        this.rows = new ArrayList<>();
    }

    public DataRowCollection(DataTable table){
        this();
        this.table = table;
    }

    /**
     * 获取行集合的数量
     * @return 行集合的数量
     */
    public int getCount(){
        return this.rows.size();
    }

    /**
     * 往行集合中增加新行
     * @param row 数据行
     */
    public void add(DataRow row){
        row.setTable(this.table);
        this.rows.add(row);
    }

    /**
     * 清空行集合
     */
    public void clear(){
        this.rows.clear();
    }

    /**
     * 获取行对象在行集合中的索引
     * @param row 行对象
     * @return 行索引
     */
    public int indexOf(DataRow row){
        int len = this.getCount();
        for(int index = 0;index < len;index++){
            DataRow r = this.rows.get(index);
            if(row == r){
                return index;
            }
        }

        return -1;
    }

    /**
     * 从行集合中移除指定行对象
     * @param row 要移除的行对象
     */
    public void remove(DataRow row){
        int len = this.getCount();
        for(int index = 0;index < len;index++){
            DataRow r = this.rows.get(index);
            if(row == r){
                this.rows.remove(index);
                break;
            }
        }
    }

    /**
     * 从行集合中移除指定索引的行对象
     * @param index 要移除的行的索引
     */
    public void remove(int index){
        int len = this.getCount();
        if(index < len){
            this.rows.remove(index);
        }
    }

    /**
     * 获取指定索引的行对象
     * @param index 行索引
     * @return 行对象
     */
    public DataRow get(int index){
        int len = this.getCount();
        if(index < len){
            return this.rows.get(index);
        }
        return null;
    }
}
