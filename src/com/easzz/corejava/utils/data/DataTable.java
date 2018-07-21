package com.easzz.corejava.utils.data;


/**
 * 数据表
 * Created by 李溪林 on 16-9-8.
 */
public class DataTable {

    private String tableName;

    private DataRowCollection rows;

    private DataColumnCollection columns;

    private DataSet set;


    public DataTable(){
        this.rows = new DataRowCollection(this);
        this.columns = new DataColumnCollection(this);
    }

    public DataTable(String tableName){
        this();
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataRowCollection getRows() {
        return rows;
    }

    public DataColumnCollection getColumns() {
        return columns;
    }

    public DataSet getSet() {
        return set;
    }

    public void clear(){
        this.rows.clear();
    }

    public DataRow newRow(){
        DataRow newRow = new DataRow(this);

        return newRow;
    }

    public DataRow getRow(int index){
        int len = this.getRows().getCount();
        if(index < len){
            return this.getRows().get(index);
        }
        return null;
    }
}
