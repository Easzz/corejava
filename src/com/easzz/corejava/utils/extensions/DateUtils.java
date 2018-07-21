package com.easzz.corejava.utils.extensions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * Created by 李溪林 on 16-8-11.
 */
public class DateUtils {

    private static final String currentClassName = "DateUtils";

    private static final String DefaultFormat = "yyyy-MM-dd";

    private static final SimpleDateFormat DefaultDatetimeFormat = new SimpleDateFormat(DefaultFormat);

    private static DateFormat[] dateFormats;

    static {
        dateFormats = new DateFormat[5];
        dateFormats[0] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        dateFormats[1] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormats[2] = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormats[3] = new SimpleDateFormat("yyyy-MM-dd");
        dateFormats[4] = new SimpleDateFormat("yyyy-MM");
    }

    /**
     * 将日期格式化为标准 yyyy-MM-dd 格式
     *
     * @param date 要格式化的日期
     * @return 一个格式为 yyyy-MM-dd 的字符串
     */
    public static String format(Date date) {
        checkDateIsNotNull(date, "format");
        return DefaultDatetimeFormat.format(date);
    }

    /**
     * 将日期格式化为指定格式
     *
     * @param date   要格式化的日期
     * @param format 格式
     * @return 字符串
     */
    public static String format(Date date, String format) {
        checkDateIsNotNull(date, "format");
        if (StringUtils.isNullOrWhiteSpace(format)) {
            throw new UtilsException(currentClassName + "中的方法[format]发生异常:日期格式化表达式不能为null、空或空格.");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            throw new UtilsException(currentClassName + "中的方法[format]发生异常:无法识别日期格式化表达式.");
        }
    }

    /**
     * 清空日期的时分秒
     *
     * @param date 要处理的日期
     * @return 清空时分秒后的日期
     */
    public static Date clearTime(Date date) {
        checkDateIsNotNull(date, "clearTime");
        Calendar c = getCalendar(date);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 装满日期的时分秒
     *
     * @param date 要处理的日期
     * @return 装满时分秒后的日期
     */
    public static Date fullTime(Date date) {
        checkDateIsNotNull(date, "fullTime");
        Calendar c = getCalendar(date);

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 为日期增加年份
     *
     * @param date  日期
     * @param count 要增加的年份数量
     * @return 日期
     */
    public static Date addYears(Date date, int count) {
        checkDateIsNotNull(date, "addYears");
        Calendar c = getCalendar(date);
        c.add(Calendar.YEAR, count);

        return c.getTime();
    }

    /**
     * 为日期增加月份
     *
     * @param date  日期
     * @param count 要增加的月份数量
     * @return 日期
     */
    public static Date addMonths(Date date, int count) {
        checkDateIsNotNull(date, "addMonths");
        Calendar c = getCalendar(date);
        c.add(Calendar.MONTH, count);

        return c.getTime();
    }


    /**
     * 为日期增加天
     *
     * @param date  日期
     * @param count 要增加的天数量
     * @return 日期
     */
    public static Date addDays(Date date, int count) {
        checkDateIsNotNull(date, "addDays");
        Calendar c = getCalendar(date);
        c.add(Calendar.DATE, count);

        return c.getTime();
    }

    /**
     * 为日期增加小时
     *
     * @param date  日期
     * @param count 要增加的小时数量
     * @return 日期
     */
    public static Date addHours(Date date, int count) {
        checkDateIsNotNull(date, "addHours");
        Calendar c = getCalendar(date);
        c.add(Calendar.HOUR, count);

        return c.getTime();
    }

    /**
     * 为日期增加分钟
     *
     * @param date  日期
     * @param count 要增加的分钟数量
     * @return 日期
     */
    public static Date addMinutes(Date date, int count) {
        checkDateIsNotNull(date, "addMinutes");
        Calendar c = getCalendar(date);
        c.add(Calendar.MINUTE, count);

        return c.getTime();
    }

    /**
     * 为日期增加秒
     *
     * @param date  日期
     * @param count 要增加的秒数量
     * @return 日期
     */
    public static Date addSeconds(Date date, int count) {
        checkDateIsNotNull(date, "addSeconds");
        Calendar c = getCalendar(date);
        c.add(Calendar.SECOND, count);

        return c.getTime();
    }

    /**
     * 为日期增加毫秒
     *
     * @param date  日期
     * @param count 要增加的毫秒数量
     * @return 日期
     */
    public static Date addMilliSeconds(Date date, int count) {
        checkDateIsNotNull(date, "addMilliSeconds");
        Calendar c = getCalendar(date);
        c.add(Calendar.MILLISECOND, count);

        return c.getTime();
    }

    /**
     * 获取年份
     *
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        checkDateIsNotNull(date, "getYear");
        Calendar c = getCalendar(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        checkDateIsNotNull(date, "getMonth");
        Calendar c = getCalendar(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日(月中的第几天)
     *
     * @param date 日期
     * @return 日
     */
    public static int getDay(Date date) {
        checkDateIsNotNull(date, "getDay");
        Calendar c = getCalendar(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取小时
     *
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        checkDateIsNotNull(date, "getHour");
        Calendar c = getCalendar(date);
        return c.get(Calendar.HOUR);
    }

    /**
     * 获取分钟
     *
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        checkDateIsNotNull(date, "getMinute");
        Calendar c = getCalendar(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @param date 日期
     * @return 秒
     */
    public static int getSecond(Date date) {
        checkDateIsNotNull(date, "getSecond");
        Calendar c = getCalendar(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取毫秒
     *
     * @param date 日期
     * @return 毫秒
     */
    public static int getMilliSecond(Date date) {
        checkDateIsNotNull(date, "getMilliSecond");
        Calendar c = getCalendar(date);
        return c.get(Calendar.MILLISECOND);
    }

    /**
     * 获取星期几
     *
     * @param date 日期
     * @return 星期几
     */
    public static int getDayOfWeek(Date date) {
        checkDateIsNotNull(date, "getDayOfWeek");
        Calendar c = getCalendar(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取日(月中的第几天)
     *
     * @param date 日期
     * @return 日
     */
    public static int getDayOfMonth(Date date) {
        checkDateIsNotNull(date, "getDayOfMonth");
        return getDay(date);
    }

    /**
     * 获取一年中的第几天
     *
     * @param date 日期
     * @return 一年中的第几天
     */
    public static int getDayOfYear(Date date) {
        checkDateIsNotNull(date, "getDayOfYear");
        Calendar c = getCalendar(date);
        return c.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期对象
     */
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 按日期生成Calendar
     *
     * @param date 日期
     * @return Calendar
     */
    private static Calendar getCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 获取指定年月日时分秒的日期对象
     *
     * @param year  年
     * @param month 月
     * @return 日期
     */
    public static Date newDate(int year, int month) {
        return newDate(year, month, 1);
    }

    /**
     * 获取指定年月日时分秒的日期对象
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 日期
     */
    public static Date newDate(int year, int month, int day) {
        return newDate(year, month, day, 0);
    }

    /**
     * 获取指定年月日时分秒的日期对象
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @param hour  时
     * @return 日期
     */
    public static Date newDate(int year, int month, int day, int hour) {
        return newDate(year, month, day, hour, 0);
    }

    /**
     * 获取指定年月日时分秒的日期对象
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @return 日期
     */
    public static Date newDate(int year, int month, int day, int hour, int minute) {
        return newDate(year, month, day, hour, minute, 0);
    }

    /**
     * 获取指定年月日时分秒的日期对象
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return 日期
     */
    public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
        return newDate(year, month, day, hour, minute, second, 0);
    }

    /**
     * 获取指定年月日时分秒的日期对象
     *
     * @param year       年
     * @param month      月
     * @param day        日
     * @param hour       时
     * @param minute     分
     * @param second     秒
     * @param millsecond 毫秒
     * @return 日期
     */
    public static Date newDate(int year, int month, int day, int hour, int minute, int second, int millsecond) {
        try {
            Calendar c = Calendar.getInstance();
            c.set(year, month - 1, day, hour, minute, second);
            c.set(Calendar.MILLISECOND, millsecond);
            return c.getTime();
        } catch (Exception e) {
            throw new UtilsException(currentClassName + "中的方法[newDate]发生异常:提供的时间值错误.");
        }

    }

    /**
     * 将字符串类型的日期数据转换成日期对象
     *
     * @param str 要转换的日期字符串
     * @return 日期
     */
    public static Date newDate(String str) {
        return newDate(str, DefaultFormat);
    }

    /**
     * 将字符串类型的日期数据转换成日期对象
     *
     * @param str    要转换的日期字符串
     * @param format 转化日期的格式
     * @return 日期
     */
    public static Date newDate(String str, String format) {
        if (StringUtils.isNullOrWhiteSpace(str)) {
            throw new UtilsException(currentClassName + "中的方法[newDate]发生异常:要解析成日期的字符串不能为空.");
        }
        if (StringUtils.isNullOrWhiteSpace(format)) {
            throw new UtilsException(currentClassName + "中的方法[newDate]发生异常:日期格式化表达式不能为null、空或空格.");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(str);
        } catch (Exception e) {
            throw new UtilsException(currentClassName + "中的方法[newDate]发生异常:无法识别日期格式化表达式.");
        }
    }

    /**
     * 将字符串解析成日期格式
     *
     * @param text            字符串
     * @param ignoreException 是否忽略异常
     * @return 日期
     */
    public static Date parse(String text, boolean ignoreException) {
        //依次解析,成功就跳出
        Date dt = null;
        int len = dateFormats.length;
        boolean success = false;
        Exception lastException = null;
        for (int index = 0; index < len; index++) {
            try {
                dt = dateFormats[index].parse(text);
                success = true;
                break;
            } catch (Exception e) {
                lastException = e;
                if (!ignoreException) {
                    throw new UtilsException(currentClassName + "中的方法[parse]发生异常:无法识别日期字符串.", e);
                }
            }
        }
        if (!success) {
            throw new UtilsException(currentClassName + "中的方法[parse]发生异常:解析日期字符串失败.", lastException);
        } else {
            return dt;
        }
    }

    /**
     * 判断2个时间段是否存在重合日期
     *
     * @param beginDt1 时间段1的开始日期
     * @param endDt1   时间段1的结束日期
     * @param beginDt2 时间段2的开始日期
     * @param endDt2   时间段2的结束日期
     * @return 若存在重合日期则返回true, 否则返回false
     */
    public static boolean hasRereat(Date beginDt1, Date endDt1, Date beginDt2, Date endDt2) {
        if (beginDt1 == null || endDt1 == null || beginDt2 == null || endDt2 == null) {
            throw new UtilsException(currentClassName + "中的方法[hasRereat]发生异常:日期参数不能为空.");
        }
        long b1 = beginDt1.getTime();
        long e1 = endDt1.getTime();
        long b2 = beginDt2.getTime();
        long e2 = endDt2.getTime();
        if (b1 > e1 || b2 > e2) {
            throw new UtilsException(currentClassName + "中的方法[hasRereat]发生异常:起始日期不能大于结束日期.");
        }
        boolean result = false;

        if (b1 <= b2 && e1 >= e2) {//（b1---【b2-----e2】--e1）
            //System.out.println("1包含2");
            result = true;
        } else if (b1 >= b2 && e1 <= e2) {//【b2---（b1-----e1）--e2】
            //System.out.println("2包含1");
            result = true;
        } else if (b1 >= b2 && b1 <= e2 && e2 <= e1) {//【b2---(b1---e2】----e1)
            //System.out.println("相交");
            result = true;
        } else if (b1 <= b2 && e1 <= e2 && e1 >= b2) {//（b1---【b2---e1）----e2】
            //System.out.println("相交");
            result = true;
        } else if (e1 <= b2 || b1 >= e2) {
            //未重合
        }

        return result;
    }

    /**
     * 获取2个日期的天数差,将忽略其time信息
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return 天数差
     */
    public static long getDiffOfDays(Date begin, Date end) {
        return getDiffOfDays(begin, end, true);
    }

    /**
     * 获取2个日期的天数差
     *
     * @param begin      开始日期
     * @param end        结束日期
     * @param ignoreTime 是否忽略时分秒
     * @return 天数差
     */
    public static long getDiffOfDays(Date begin, Date end, boolean ignoreTime) {
        if (begin == null || end == null) {
            throw new UtilsException(currentClassName + "中的方法[getDiffOfDays]发生异常:日期参数不能为空.");
        }
        begin = ignoreTime ? clearTime(begin) : begin;
        end = ignoreTime ? clearTime(end) : end;

        long seconds = (end.getTime() - begin.getTime()) / 1000;
        long days = seconds / (60 * 60 * 24);

        return days + 1;
    }

    /**
     * 检查日期对象是否为null,为null则抛出异常.
     *
     * @param date   日期对象
     * @param method 方法名
     */
    private static void checkDateIsNotNull(Date date, String method) {
        if (date == null) {
            throw new UtilsException(currentClassName + "中的方法[" + method + "]发生异常:日期对象不能为null.");
        }
    }
}
