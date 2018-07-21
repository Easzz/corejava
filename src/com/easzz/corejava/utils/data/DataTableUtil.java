package com.easzz.corejava.utils.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据表工具类
 * Created by 李溪林 on 16-9-9.
 */
public class DataTableUtil {

    /**
     * 略过数据表中的指定数量的元素,返回新的数据表
     *
     * @param data 原集合
     * @param skip 要忽略的数据量
     * @return 新数据表
     */
    public static DataTable skip(DataTable data, int skip) {
        DataTable result = new DataTable();
        List<DataColumn> newCols = copyDataColumns(data);
        for (DataColumn column : newCols) {
            result.getColumns().add(column);
        }

        int len = data.getRows().getCount();
        if (skip > len) {
            return result;
        }
        if (skip < 0) {
            skip = 0;
        }
        int colLen = newCols.size();
        for (int index = skip; index < len; index++) {
            DataRow row = data.getRows().get(index);
            result.getRows().add(copyDataRow(data, colLen, row));
        }

        return result;
    }

    /**
     * 获取数据表中指定数量的元素,返回新的数据表
     * @param data 原集合
     * @param take 要获取的数据量
     * @return 新数据表
     */
    public static DataTable take(DataTable data, int take) {
        DataTable result = new DataTable();
        List<DataColumn> newCols = copyDataColumns(data);
        for (DataColumn column : newCols) {
            result.getColumns().add(column);
        }
        if (take < 0) {
            take = 0;
        }
        if (take == 0) {
            return result;
        }

        int len = data.getRows().getCount();
        if (take > len) {
            take = len;
        }

        int colLen = newCols.size();
        for (int index = 0; index < take; index++) {
            DataRow row = data.getRows().get(index);
            result.getRows().add(copyDataRow(data, colLen, row));
        }

        return result;
    }

    /**
     * 拷贝table的列集合
     *
     * @param data table对象
     * @return 新的列集合
     */
    private static List<DataColumn> copyDataColumns(DataTable data) {
        List<DataColumn> result = new ArrayList<>();
        int len = data.getColumns().getCount();
        for (int index = 0; index < len; index++) {
            DataColumn column = data.getColumns().get(index);
            DataColumn colNew = new DataColumn(column.getColumnName(), column.getDataType());
            colNew.setCaption(column.getCaption());
            colNew.setDefaultValue(column.getDefaultValue());

            result.add(colNew);
        }

        return result;
    }

    /**
     * 拷贝行对象
     * @param table table对象
     * @param columnLength 列长度
     * @param oldrow 被拷贝的行对象
     * @return 新的行对象
     */
    private static DataRow copyDataRow(DataTable table, int columnLength, DataRow oldrow) {
        DataRow newRow = table.newRow();
        Object[] itemArray = oldrow.getItemArray();
        for (int colIndex = 0; colIndex < columnLength; colIndex++) {
            newRow.put(colIndex, itemArray[colIndex]);
        }

        return newRow;
    }
}
