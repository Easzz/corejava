package com.easzz.corejava.utils.data;

/**
 * 数据行
 * Created by 李溪林 on 16-9-8.
 */
public class DataRow {

    /**
     * 数据行的值集合数组
     */
    private Object[] itemArray;

    /**
     * 数据行所在的table对象
     */
    private DataTable table;

    /**
     * 私有构造
     */
    private DataRow(){

    }

    /**
     * 构造方法,访问级别为package
     * @param table 所属table对象
     */
    DataRow(DataTable table){
        this.table = table;
        this.itemArray = new Object[this.table.getColumns().getCount()];
    }

    /**
     * 获取数据行的值数组
     * @return 数据行的值数组
     */
    public Object[] getItemArray() {
        return itemArray;
    }

    /**
     * 往行对象中的指定列添加指定的数据
     * @param columnName 指定列名
     * @param value 数据
     */
    public void put(String columnName, Object value){
        //需要先判定列集合中是否包含该列名
        int index = this.table.getColumns().indexOf(columnName);
        if(index > -1){
            this.itemArray[index] = value;
        }
        //是否抛出异常?
    }

    /**
     * 往行对象中的指定索引的列添加指定的数据
     * @param columnIndex 指定列索引
     * @param value 数据
     */
    public void put(int columnIndex, Object value){
        int len = this.table.getColumns().getCount();
        if(columnIndex < len){
            this.itemArray[columnIndex] = value;
        }
        //是否抛出异常?
    }

    /**
     * 根据列名获取当前行中该列名对应的数据
     * @param columnName 列名
     * @return 当前行中该列的数据
     */
    public Object getItem(String columnName){
        int index = this.table.getColumns().indexOf(columnName);
        return this.getItem(index);
    }

    public void setItem(String columnName, Object value){
        int index = this.table.getColumns().indexOf(columnName);
        this.setItem(index, value);
    }

    /**
     * 根据列索引获取当前行中该列索引对应的数据
     * @param index 列索引
     * @return 当前行中该列的数据
     */
    public Object getItem(int index){
        int len = this.itemArray.length;
        if(index < len){
            return this.itemArray[index];
        }
        return null;
    }

    /**
     * 根据列索引设置新的值
     * @param index 列索引
     * @param value 列值
     */
    public void setItem(int index, Object value){
        int len = this.itemArray.length;
        if(index < len){
            this.itemArray[index] = value;
        }
    }

    /**
     * 根据列获取当前行中该列对应的数据
     * @param column 列对象
     * @return 当前行中该列的数据
     */
    public Object getItem(DataColumn column){
        int index = this.table.getColumns().indexOf(column);
        return this.getItem(index);
    }

    /**
     * 获取数据行所属的数据表对象
     * @return 数据表对象
     */
    public DataTable getTable() {
        return table;
    }

    /**
     * 删除当前行数据
     */
    public void delete(){
        this.table.getRows().remove(this);
    }

    /**
     * 设置所属数据表对象,本方法访问级别为package
     * @param table 数据表对象
     */
    void setTable(DataTable table){
        this.table = table;
    }
}
