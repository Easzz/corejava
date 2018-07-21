package com.easzz.corejava.utils.helper;

import com.easzz.corejava.utils.annotation.Precision;
import com.easzz.corejava.utils.data.DataColumn;
import com.easzz.corejava.utils.data.DataRow;
import com.easzz.corejava.utils.data.DataTable;
import com.easzz.corejava.utils.data.DataTableUtil;
import com.easzz.corejava.utils.enums.ExcelVersion;
import com.easzz.corejava.utils.extensions.*;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出帮助类
 * Created by 李溪林 on 16-8-24.
 */
public class ExportHelper {

    private static final String currentClassName = "ExportHelper";


    private static final ExcelVersion DEFAULT_EXCEL_VERSION = ExcelVersion.XLS;
    private static final long DATA_MAX_LENGTH_WHEN_XLS = 65535;
    private static final String FONT_FAMILY = "宋体";
    private static final int FONT_SIZE = 12;
    private static final HSSFColor.HSSFColorPredefined FONT_COLOR = HSSFColor.HSSFColorPredefined.BLACK;
    private static final HSSFColor.HSSFColorPredefined BACKGROUND_COLOR = HSSFColor.HSSFColorPredefined.WHITE;
    private static final HorizontalAlignment HORIZONTAL_ALIGNMENT = HorizontalAlignment.CENTER;
    private static final VerticalAlignment VERTICAL_ALIGNMENT = VerticalAlignment.CENTER;
    private static final boolean BORDER = true;


    /**
     * 数据集合导出excel
     *
     * @param version            要导出的excel版本
     * @param tClass             T 的class
     * @param data               要导出的数据集合
     * @param exportPropertities 要导出的属性集合,若为空则表示导出所有属性的数据
     * @param rules              单元格合并规则集合
     * @param sheetSize          要导出在工作薄数量,小于等于0都表示在1张工作薄中导出数据
     * @param useDisplayName     是否使用 T 属性中 Description 注解里的描述信息作为标题列的值
     * @param <T>                数据类型
     * @return 字节组
     */
    public static <T> byte[] exportToExcel(ExcelVersion version, Class<T> tClass, List<T> data, String[] exportPropertities, List<CellMergerRule> rules, int sheetSize, boolean useDisplayName) {
        try (ByteArrayOutputStream os = buildExcelStruct(version, tClass, data, exportPropertities, rules, sheetSize, useDisplayName)) {
            return os.toByteArray();
        } catch (Exception e) {
            throw new UtilsException(currentClassName + "中的方法[exportToExcel.List]发生异常.", e);
        }
    }

    /**
     * 数据集合导出excel
     *
     * @param version          要导出的excel版本
     * @param data             要导出的数据集合
     * @param rules            单元格合并规则集合
     * @param sheetSize        每个工作薄的数据量,小于等于0都表示在1张工作薄中导出数据
     * @param allowExportEmpty 允许导出空
     * @return 字节组
     */
    public static byte[] exportToExcel(ExcelVersion version, DataTable data, List<CellMergerRule> rules, int sheetSize, boolean allowExportEmpty) {
        try (ByteArrayOutputStream os = buildExcelStruct(version, data, rules, sheetSize, allowExportEmpty)) {
            return os.toByteArray();
        } catch (Exception e) {
            throw new UtilsException(currentClassName + "中的方法[exportToExcel.DataTable]发生异常.", e);
        }
    }

    /**
     * 创建excel结构
     *
     * @param version            要导出的excel版本
     * @param tClass             T 的class
     * @param data               要导出的数据集合
     * @param exportPropertities 要导出的属性集合,若为空则表示导出所有属性的是数据
     * @param rules              单元格合并规则集合
     * @param sheetSize          要导出的工作薄的数量,小于等于0都表示在1张工作薄中导出数据
     * @param useDisplayName     是否使用 T 属性中 Description 注解里的描述信息作为标题列的值
     * @param <T>                数据类型
     * @return 字节流
     * @throws Exception 异常
     */
    private static <T> ByteArrayOutputStream buildExcelStruct(ExcelVersion version, Class<T> tClass, List<T> data, String[] exportPropertities, List<CellMergerRule> rules, int sheetSize, boolean useDisplayName) throws Exception {
        if (tClass == null) {
            throw new IllegalArgumentException("要导出的数据类型对象不能为空.");
        }
        if (version == null) {
            throw new IllegalArgumentException("要导出的excel版本不能为空.");
        }
        int dataLen = data == null ? 0 : data.size();
        if(dataLen + 1 > DATA_MAX_LENGTH_WHEN_XLS){
            //强制excel类型为 xlsx
            version = ExcelVersion.XLSX;
        }

        Workbook excel = version == ExcelVersion.XLS ? new HSSFWorkbook() : new XSSFWorkbook();

        //标题行样式
        Font headFont = getFont(excel);
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBold(true);
        CellStyle headStyle = getCellStyle(excel);
        headStyle.setFont(headFont);

        //从T的所有可公共get的属性中过滤出在exportPropertities的属性
        PropertyDescriptor[] last = ArrayUtils.isNullOrEmpty(exportPropertities) ? TypeUtils.getAllowGetPropertyDescriptors(tClass, true) : TypeUtils.getPropertyDescriptors(tClass, exportPropertities, true);
        Map<String, String> descriptions = TypeUtils.getDescriptions(tClass, last);
        //得到BigDecimal类型的属性上的小数精度注解
        Map<String, Integer> precisions = new HashMap<>();
        for (PropertyDescriptor item : last) {
            if (!TypeUtils.isAssignable(BigDecimal.class, item.getPropertyType())) {
                continue;
            }
            Precision precision = TypeUtils.getAnnotation(tClass, item.getName(), Precision.class);
            if (precision == null) {
                precisions.put(item.getName(), 0);
            } else {
                precisions.put(item.getName(), precision.value());
            }
        }

        long[] headCellByteLength = new long[last.length];
        int sheetCount = 1;
        if (sheetSize > 0) {
            sheetCount = dataLen / sheetSize;
            if (dataLen % sheetSize > 0) {
                sheetCount++;
            }
        }

        Map<String, CellStyle> cellStyleMaps = new HashMap<>();

        //解析合并规则
        List<CellRangeAddress> regions = ParseMergeRules(data.size(), descriptions.size(), rules);

        List<Sheet> sheets = new ArrayList<>();
        for (int k = 0; k < sheetCount; k++) {
            Sheet sheet = excel.createSheet("sheet" + (k + 1));
            sheets.add(sheet);
            if (k == 0) {
                buildExcelHead(sheet, headStyle, last, descriptions, useDisplayName, headCellByteLength);
            } else {
                buildExcelHead0(sheet, headStyle, last, descriptions, useDisplayName, headCellByteLength);
            }
            if (sheetSize > 0) {
                appendExcelItemData(sheet, last, precisions, headCellByteLength, cellStyleMaps, ListUtils.take(ListUtils.skip(data, k * sheetSize), sheetSize));
            } else {
                appendExcelItemData(sheet, last, precisions, headCellByteLength, cellStyleMaps, data);
            }
        }

        CellStyle regionCellStyle = getCellStyle(excel, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true);
        merge(sheets, regions, regionCellStyle);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        excel.write(os);

        return os;
    }

    /**
     * 创建excel结构
     *
     * @param version          要导出的excel版本
     * @param data             要导出的数据集合
     * @param rules            单元格合并规则集合
     * @param sheetSize        每个工作薄的数据量,小于等于0都表示在1张工作薄中导出数据
     * @param allowExportEmpty 允许导出空
     * @return 字节流
     * @throws Exception 异常
     */
    private static ByteArrayOutputStream buildExcelStruct(ExcelVersion version, DataTable data, List<CellMergerRule> rules, int sheetSize, boolean allowExportEmpty) throws Exception {
        int dataLen = data.getRows().getCount();
        if (dataLen == 0 && !allowExportEmpty) {
            throw new IllegalArgumentException("要导出的数据对象不能为空!");
        }
        if (version == null) {
            throw new IllegalArgumentException("要导出的excel版本不能为空.");
        }
        if(dataLen > DATA_MAX_LENGTH_WHEN_XLS){
            //强制excel类型为 xlsx
            version = ExcelVersion.XLSX;
        }

        Workbook excel = version == ExcelVersion.XLS ? new HSSFWorkbook() : new XSSFWorkbook();

        //标题行样式
        Font headFont = excel.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBold(true);
        CellStyle headStyle = excel.createCellStyle();
        headStyle.setFont(headFont);

        //从data中取标题列
        List<String> titles = new ArrayList<>();
        int columnLen = data.getColumns().getCount();
        for (int colIndex = 0; colIndex < columnLen; colIndex++) {
            DataColumn col = data.getColumns().get(colIndex);
            titles.add(StringUtils.isNullOrEmpty(col.getCaption()) ? col.getColumnName() : col.getCaption());
        }

        long[] headCellByteLength = new long[columnLen];
        int sheetCount = 1;
        if (sheetSize > 0) {
            sheetCount = dataLen / sheetSize;
            if (dataLen % sheetSize > 0) {
                sheetCount++;
            }
        }

        Map<String, CellStyle> cellStyleMaps = new HashMap<>();

        //解析合并规则
        List<CellRangeAddress> regions = ParseMergeRules(data.getRows().getCount() + 1, data.getColumns().getCount(), rules);

        List<Sheet> sheets = new ArrayList<>();
        for (int k = 0; k < sheetCount; k++) {
            Sheet sheet = excel.createSheet("sheet" + (k + 1));
            sheets.add(sheet);
            if (k == 0) {
                buildExcelHead(sheet, headStyle, titles, headCellByteLength);
            } else {
                buildExcelHead0(sheet, headStyle, titles, headCellByteLength);
            }
            if (sheetSize > 0) {
                appendExcelItemData(sheet, cellStyleMaps, headCellByteLength, DataTableUtil.take(DataTableUtil.skip(data, k * sheetSize), sheetSize));
            } else {
                appendExcelItemData(sheet, cellStyleMaps, headCellByteLength, data);
            }
        }

        CellStyle cellStyle1 = getCellStyle(excel, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true);
        merge(sheets, regions, cellStyle1);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        excel.write(os);

        return os;
    }

    /**
     * 创建excel头(标题)
     *
     * @param sheet              工作薄对象
     * @param headStyle          头样式
     * @param props              要导出的属性集合
     * @param descriptions       要导出的属性的 Description 注解信息集合
     * @param useDisplayName     是否使用 T 属性中 Description 注解里的描述信息作为标题列的值
     * @param headCellByteLength 头部单元格内容字节长度数组,用来计算列宽
     */
    private static void buildExcelHead(Sheet sheet, CellStyle headStyle, PropertyDescriptor[] props, Map<String, String> descriptions, boolean useDisplayName, long[] headCellByteLength) {
        int len = props.length;

        if (len == 0) {
            return;
        }

        Row headRow = sheet.createRow(0);

        String title;
        for (int k = 0; k < len; k++) {
            if (useDisplayName) {
                title = descriptions.get(props[k].getName());
            } else {
                title = props[k].getName();
            }
            headCellByteLength[k] = StringUtils.getByteLength(title);
            Cell headCellItem = headRow.createCell(k);
            headCellItem.setCellValue(title);
            headCellItem.setCellStyle(headStyle);

            sheet.setColumnWidth(k, 256 * ((int) headCellByteLength[k] + 1));
        }
    }

    /**
     * 创建excel头(标题)
     *
     * @param sheet              工作薄对象
     * @param headStyle          头样式
     * @param titles             标题集合
     * @param headCellByteLength 头部单元格内容字节长度数组,用来计算列宽
     */
    private static void buildExcelHead(Sheet sheet, CellStyle headStyle, List<String> titles, long[] headCellByteLength) {
        int len = titles.size();

        if (len == 0) {
            return;
        }

        Row headRow = sheet.createRow(0);

        String title;
        for (int k = 0; k < len; k++) {
            title = titles.get(k);
            headCellByteLength[k] = StringUtils.getByteLength(title);
            Cell headCellItem = headRow.createCell(k);
            headCellItem.setCellValue(title);
            headCellItem.setCellStyle(headStyle);
            sheet.setColumnWidth(k, 256 * ((int) headCellByteLength[k] + 1));
        }
    }

    /**
     * 创建excel头(标题)
     *
     * @param sheet              工作薄对象
     * @param headStyle          头样式
     * @param props              要导出的属性集合
     * @param descriptions       要导出的属性的 Description 注解信息集合
     * @param useDisplayName     是否使用 T 属性中 Description 注解里的描述信息作为标题列的值
     * @param headCellByteLength 头部单元格内容字节长度数组,用来计算列宽
     */
    private static void buildExcelHead0(Sheet sheet, CellStyle headStyle, PropertyDescriptor[] props, Map<String, String> descriptions, boolean useDisplayName, long[] headCellByteLength) {
        int len = props.length;
        if (len == 0) {
            return;
        }

        Row headRow = sheet.createRow(0);

        String title;
        for (int k = 0; k < len; k++) {
            if (useDisplayName) {
                title = descriptions.get(props[k].getName());
            } else {
                title = props[k].getName();
            }
            Cell headCellItem = headRow.createCell(k);
            headCellItem.setCellValue(title);
            headCellItem.setCellStyle(headStyle);
            sheet.setColumnWidth(k, 256 * ((int) headCellByteLength[k] + 1));
        }
    }

    /**
     * 创建excel头(标题)
     *
     * @param sheet              工作薄对象
     * @param headStyle          头样式
     * @param titles             标题集合
     * @param headCellByteLength 头部单元格内容字节长度数组,用来计算列宽
     */
    private static void buildExcelHead0(Sheet sheet, CellStyle headStyle, List<String> titles, long[] headCellByteLength) {
        int len = titles.size();
        if (len == 0) {
            return;
        }

        Row headRow = sheet.createRow(0);

        String title;
        for (int k = 0; k < len; k++) {
            title = titles.get(k);
            Cell headCellItem = headRow.createCell(k);
            headCellItem.setCellValue(title);
            headCellItem.setCellStyle(headStyle);
            sheet.setColumnWidth(k, 256 * ((int) headCellByteLength[k] + 1));
        }
    }

    /**
     * 创建excel数据体
     *
     * @param sheet              工作薄对象
     * @param props              要导出的属性集合
     * @param precisions         BigDecimal格式的属性的小数精度
     * @param headCellByteLength 头部单元格内容字节长度数组,用来计算列宽
     * @param cellStyles         单元格样式缓存
     * @param data               要导出的数据
     * @param <T>                数据类型
     * @throws Exception 异常
     */
    private static <T> void appendExcelItemData(Sheet sheet, PropertyDescriptor[] props, Map<String, Integer> precisions, long[] headCellByteLength, Map<String, CellStyle> cellStyles, List<T> data) throws Exception {
        int propLen = props.length;
        int dataLen = data == null ? 0 : data.size();
        Class typeOfDate = Date.class;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < dataLen; i++) {
            Row rowtemp = sheet.createRow(i + 1);
            for (int k = 0; k < propLen; k++) {
                Method getter = props[k].getReadMethod();
                Class t = getter.getReturnType();
                Object obj = getter.invoke(data.get(i));
                String val = obj == null ? "" : (t == typeOfDate ? format.format((Date) obj) : obj.toString());
                Cell cell = rowtemp.createCell(k);
                long temp = StringUtils.getByteLength(val);

                String propName = props[k].getName();
                CellStyle cellStyle = cellStyles.get(propName);

                if (TypeUtils.isAssignable(BigDecimal.class, t)) {
                    //金额,设置小数精度并自动千分位格式
                    if (cellStyle == null) {
                        cellStyle = getCellStyle(sheet.getWorkbook());
                        Integer precision = precisions.get(propName);
                        if (precision == null) {
                            precision = 0;
                        } else if (precision != 0) {
                            precision = 2;
                        }
                        String formatTemp = "";
                        if (precision > 0) {
                            formatTemp += ".";
                            for (int h = 0; h < precision; h++) {
                                formatTemp += "0";
                            }
                        }
                        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0" + formatTemp));

                        cellStyles.put(propName, cellStyle);
                    }

                    cell.setCellStyle(cellStyle);
                    if (obj != null) {
                        cell.setCellValue(Double.parseDouble(val));
                    }

                    //金额的宽度不能自适应,极影响性能
                    //sheet.autoSizeColumn(k);
                    temp = temp * (2);
                } else {
                    if (cellStyle == null) {
                        cellStyle = getCellStyle(sheet.getWorkbook());
                        DataFormat dataFormat = sheet.getWorkbook().createDataFormat();
                        cellStyle.setDataFormat(dataFormat.getFormat("@"));
                        cellStyles.put(propName, cellStyle);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(val);
                }
                if (temp > headCellByteLength[k]) {
                    headCellByteLength[k] = temp;
                    sheet.setColumnWidth(k, 256 * ((int) temp + 1));
                }
            }
        }
    }

    /**
     * 创建excel数据体
     *
     * @param sheet              工作薄对象
     * @param headCellByteLength 头部单元格内容字节长度数组,用来计算列宽
     * @param data               要导出的数据
     * @throws Exception 异常
     */
    private static void appendExcelItemData(Sheet sheet, Map<String, CellStyle> cellStyleMaps, long[] headCellByteLength, DataTable data) throws Exception {
        int colLen = data.getColumns().getCount();
        int dataLen = data.getRows().getCount();
        Class typeOfDate = Date.class;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < dataLen; i++) {
            Row rowtemp = sheet.createRow(i + 1);
            DataRow dr = data.getRows().get(i);
            for (int k = 0; k < colLen; k++) {
                DataColumn column = data.getColumns().get(k);
                if (column == null) {
                    continue;
                }
                String columnName = column.getColumnName();
                CellStyle cellStyle = cellStyleMaps.get(columnName);
                if(cellStyle == null){
                    cellStyle = getCellStyle(sheet.getWorkbook());
                    cellStyleMaps.put(columnName, cellStyle);
                }
                Object obj = dr.getItem(columnName);
                Class t = column.getDataType();
                String val = obj == null ? "" : (t == typeOfDate ? format.format((Date) obj) : obj.toString());
                Cell cell = rowtemp.createCell(k);
                cell.setCellValue(val);
                cell.setCellStyle(cellStyle);
                long temp = StringUtils.getByteLength(val);
                if (temp > headCellByteLength[k]) {
                    headCellByteLength[k] = temp;
                    sheet.setColumnWidth(k, 256 * ((int) temp + 1));
                }
            }
        }
    }

    /**
     * 获取单元格样式
     *
     * @param xls excel对象
     * @return 单元格样式
     */
    private static CellStyle getCellStyle(Workbook xls) {
        Font font = getFont(xls);
        return getCellStyle(xls, font, BACKGROUND_COLOR, HORIZONTAL_ALIGNMENT, VERTICAL_ALIGNMENT, BORDER);
    }

    /**
     * 获取单元格样式
     *
     * @param xls    excel对象
     * @param ha     水平对齐方式
     * @param va     垂直对齐方式
     * @param border 是否显示边框
     * @return 单元格样式
     */
    private static CellStyle getCellStyle(Workbook xls, HorizontalAlignment ha, VerticalAlignment va, boolean border) {
        Font font = getFont(xls);
        return getCellStyle(xls, font, BACKGROUND_COLOR, ha, va, border);
    }

    /**
     * 获取单元格样式
     *
     * @param xls             excel对象
     * @param font            字体样式
     * @param backgroundColor 背景颜色,来自 HSSFColor 中定义的 颜色 类的 index 属性
     * @param ha              水平对齐方式
     * @param va              垂直对齐方式
     * @param border          是否显示边框
     * @return 单元格样式
     */
    private static CellStyle getCellStyle(Workbook xls, Font font, HSSFColor.HSSFColorPredefined backgroundColor, HorizontalAlignment ha, VerticalAlignment va, boolean border) {
        CellStyle cellStyle = xls.createCellStyle();
        if (font != null) {
            cellStyle.setFont(font);
        }
        if (backgroundColor != null) {
            cellStyle.setFillBackgroundColor(backgroundColor.getIndex());
        }
        cellStyle.setAlignment(ha);
        cellStyle.setVerticalAlignment(va);
        if (border) {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            cellStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            cellStyle.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            cellStyle.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        }

        return cellStyle;
    }

    /**
     * 获取字体样式
     *
     * @param xls excel对象
     * @return 字体样式
     */
    private static Font getFont(Workbook xls) {
        return getFont(xls, FONT_FAMILY, FONT_SIZE, FONT_COLOR);
    }

    /**
     * 获取字体样式
     *
     * @param xls   excel对象
     * @param color 颜色,来自 HSSFColor 中定义的 颜色 类的 index 属性
     * @return 字体样式
     */
    private static Font getFont(Workbook xls, HSSFColor.HSSFColorPredefined color) {
        return getFont(xls, FONT_FAMILY, FONT_SIZE, color);
    }

    /**
     * 获取字体样式
     *
     * @param xls  excel对象
     * @param size 尺寸
     * @return 字体样式
     */
    private static Font getFont(Workbook xls, Integer size) {
        return getFont(xls, FONT_FAMILY, size, FONT_COLOR);
    }

    /**
     * 获取字体样式
     *
     * @param xls    excel对象
     * @param family 字体
     * @return 字体样式
     */
    private static Font getFont(Workbook xls, String family) {
        return getFont(xls, family, FONT_SIZE, FONT_COLOR);
    }

    /**
     * 获取字体样式
     *
     * @param xls    excel对象
     * @param family 字体
     * @param size   尺寸
     * @param color  颜色,来自 HSSFColor 中定义的 颜色 类的 index 属性
     * @return 字体样式
     */
    private static Font getFont(Workbook xls, String family, Integer size, HSSFColor.HSSFColorPredefined color) {
        Font font = xls.createFont();
        if (!StringUtils.isNullOrWhiteSpace(family)) {
            font.setFontName(family);
        }
        if (size != null) {
            font.setFontHeightInPoints(size.shortValue());
        }
        if (color != null) {
            font.setColor(color.getIndex());
        }

        return font;
    }

    /**
     * 解析单元格合并规则，返回要合并的区域集合
     *
     * @param rowsCount    数据行总数
     * @param columnsCount 数据列总数
     * @param rules        合并规则集合
     * @return 区域集合
     */
    private static List<CellRangeAddress> ParseMergeRules(int rowsCount, int columnsCount, List<CellMergerRule> rules) {
        List<CellRangeAddress> regions = new ArrayList<>();
        if (ListUtils.isNullOrEmpty(rules)) {
            return regions;
        }
        for (CellMergerRule item : rules) {
            CellRangeAddress region = new CellRangeAddress(item.getRowBeginIndex(), item.getRowEndIndex(), item.getColBeginIndex(), item.getColEndIndex());
            if (item.isRepeat() && !(item.getIntervalRowCount() < 0 && item.getIntervalColCount() < 0)) {

                // 行间隔 大于0，列间隔 小于0 A1
                // 行间隔 大于0，列间隔 等于0 C => A1
                // 行间隔 等于0，列间隔 大于0 D => B1
                // 行间隔 等于0，列间隔 小于0 A2
                // 行间隔 小于0，列间隔 大于0 B1
                // 行间隔 小于0，列间隔 等于0 B2
                if ((item.getIntervalRowCount() > 0 && item.getIntervalColCount() > 0) || (item.getIntervalRowCount() == 0 && item.getIntervalColCount() == 0)) {
                    // 都大于0 或 都等于0
                    int intervalRow = item.getRowEndIndex() - item.getRowBeginIndex() + 1;//合并区域的跨行数
                    int intervalCol = item.getColEndIndex() - item.getColBeginIndex() + 1;//合并区域的跨列数
                    for (int kr = item.getRowEndIndex() + 1 + item.getIntervalRowCount(), kc = item.getColEndIndex() + 1 + item.getIntervalColCount(); kr < rowsCount + 1 && kc < columnsCount + 1; kr += (intervalRow + item.getIntervalRowCount()), kc += (intervalCol + item.getIntervalColCount())) {
                        regions.add(new CellRangeAddress(kr, kr + intervalRow - 1, kc, kc + intervalCol - 1));
                    }
                } else {
                    if (item.getIntervalRowCount() > 0 && item.getIntervalColCount() == 0) {
                        // 行间隔 大于0，列间隔 等于0 C => A1
                        item.setIntervalColCount(-1);
                    } else if (item.getIntervalRowCount() == 0 && item.getIntervalColCount() > 0) {
                        // 行间隔 等于0，列间隔 大于0 D => B1
                        item.setIntervalRowCount(-1);
                    }

                    if (item.getIntervalColCount() < 0) {
                        // 行间隔 大于0，列间隔 小于0 A1
                        // 行间隔 等于0，列间隔 小于0 A2
                        //间隔列小于0，表示在“主体规则的列范围”中重复合并单元格
                        int intervalRow = item.getRowEndIndex() - item.getRowBeginIndex() + 1;//合并区域的跨行数
                        //item.IntervalRowCount;//重复合并区域之间的跨行数
                        for (int k = item.getRowEndIndex() + 1 + item.getIntervalRowCount(); k < rowsCount + 1; k += (intervalRow + item.getIntervalRowCount())) {
                            regions.add(new CellRangeAddress(k, k + intervalRow - 1, item.getColBeginIndex(), item.getColEndIndex()));
                        }
                    } else if (item.getIntervalRowCount() < 0) {
                        // 行间隔 小于0，列间隔 大于0 B1
                        // 行间隔 小于0，列间隔 等于0 B2
                        //间隔行小于0，表示在“主体规则的行范围”中重复合并单元格
                        int intervalCol = item.getColEndIndex() - item.getColBeginIndex() + 1;//合并区域的跨列数
                        //item.IntervalColCount;//重复合并区域之间的跨列数
                        for (int k = item.getColEndIndex() + 1 + item.getIntervalColCount(); k < columnsCount + 1; k += (intervalCol + item.getIntervalColCount())) {
                            regions.add(new CellRangeAddress(item.getRowBeginIndex(), item.getRowEndIndex(), k, k + intervalCol - 1));
                        }
                    }
                }
            }
            regions.add(region);
        }

        return regions;
    }

    /**
     * 合并单元格
     *
     * @param sheets     要合并的工作簿集合
     * @param regions    要合并的区域集合
     * @param cellStyle1 合并后使用的单元格样式
     */
    private static void merge(List<Sheet> sheets, List<CellRangeAddress> regions, CellStyle cellStyle1) {
        if (!ListUtils.isNullOrEmpty(regions)) {
            for (CellRangeAddress item : regions) {
                for (Sheet sheet : sheets) {
                    sheet.addMergedRegion(item);
                    for (int i = item.getFirstRow(); i <= item.getLastRow(); i++) {
                        Row row = CellUtil.getRow(i, sheet);
                        for (int j = item.getFirstColumn(); j <= item.getLastColumn(); j++) {
                            Cell singleCell = CellUtil.getCell(row, (short) j);
                            singleCell.setCellStyle(cellStyle1);
                        }
                    }
                }
            }
        }
    }

    /**
     * 单元格合并规则
     */
    public static class CellMergerRule {

        /**
         * 开始行的索引
         */
        private int rowBeginIndex;

        /**
         * 开始列的索引
         */
        private int colBeginIndex;

        /**
         * 结束行的索引
         */
        private int rowEndIndex;

        /**
         * 结束列的索引
         */
        private int colEndIndex;

        /**
         * 是否重复
         */
        private boolean repeat;

        /**
         * 间隔行数，小于0则表示在主体规则的行范围内重复合并
         */
        private int intervalRowCount;

        /**
         * 间隔列数，小于0则表示在主体规则的列范围内重复合并
         */
        private int intervalColCount;

        public int getRowBeginIndex() {
            return rowBeginIndex;
        }

        public void setRowBeginIndex(int rowBeginIndex) {
            this.rowBeginIndex = rowBeginIndex;
        }

        public int getColBeginIndex() {
            return colBeginIndex;
        }

        public void setColBeginIndex(int colBeginIndex) {
            this.colBeginIndex = colBeginIndex;
        }

        public int getRowEndIndex() {
            return rowEndIndex;
        }

        public void setRowEndIndex(int rowEndIndex) {
            this.rowEndIndex = rowEndIndex;
        }

        public int getColEndIndex() {
            return colEndIndex;
        }

        public void setColEndIndex(int colEndIndex) {
            this.colEndIndex = colEndIndex;
        }

        public boolean isRepeat() {
            return repeat;
        }

        public void setRepeat(boolean repeat) {
            this.repeat = repeat;
        }

        public int getIntervalRowCount() {
            return intervalRowCount;
        }

        public void setIntervalRowCount(int intervalRowCount) {
            this.intervalRowCount = intervalRowCount;
        }

        public int getIntervalColCount() {
            return intervalColCount;
        }

        public void setIntervalColCount(int intervalColCount) {
            this.intervalColCount = intervalColCount;
        }
    }

    /**
     * 获取新的单元格合并规则对象
     *
     * @return 单元格合并规则对象
     */
    public static CellMergerRule newRule() {
        CellMergerRule rule = new CellMergerRule();
        return rule;
    }
}
