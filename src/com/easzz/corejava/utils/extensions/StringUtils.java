package com.easzz.corejava.utils.extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by 李溪林 on 16-8-10.
 */
public class StringUtils {

    private static final String currentClassName = "StringUtils";

    /**
     * 是否为null或空字符串
     *
     * @param str 要判定的字符串
     * @return 若为空或空字符串, 则返回 true,否则返回 false.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");
    }

    /**
     * 是否为null或空字符串或空格
     *
     * @param str 要判定的字符串
     * @return 若为空或空字符串或空格, 则返回 true,否则返回 false.
     */
    public static boolean isNullOrWhiteSpace(String str) {
        return isNullOrEmpty(str) || Pattern.compile("\\s+").matcher(str).matches();
    }

    /**
     * 将字符串以指定符号分割
     *
     * @param str 要分割的字符串
     * @param op  符号
     * @return 字符串数组
     */
    public static String[] split(String str, String op) {
        return split(str, op, false);
    }

    /**
     * 将字符串以指定符号分割
     *
     * @param str         要分割的字符串
     * @param op          符号
     * @param removeEmpty 是否移除空字符串
     * @return 字符串数组
     */
    public static String[] split(String str, String op, boolean removeEmpty) {
        if (str == null) {
            return null;
        }
        checkSplitOpIsNotNull(op, "split");
        if(op.equals(".")){
            op = "\\.";
        }
        String[] r = str.split(op, -1);
        if (!removeEmpty) {
            return r;
        }

        List<String> list = new ArrayList();
        for (String item : r) {
            if (!isNullOrEmpty(item)) {
                list.add(item);
            }
        }

        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    /**
     * 将字符串以指定符号分割并返回整型List集合
     *
     * @param str                    要分割的字符串
     * @param op                     符号
     * @param ignoreConvertException 是否忽略转化异常
     * @return 整型List集合
     */
    public static List<Integer> toIntList(String str, String op, boolean ignoreConvertException) {
        if (str == null) {
            return null;
        }
        checkSplitOpIsNotNull(op, "toIntList");
        String[] arr = split(str, op);

        return ArrayUtils.toList(ArrayUtils.toIntArray(arr, ignoreConvertException));
    }

    /**
     * 将字符串以指定符号分割并返回整型Set集合
     *
     * @param str                    要分割的字符串
     * @param op                     符号
     * @param ignoreConvertException 是否忽略转化异常
     * @return 整型Set集合
     */
    public static Set<Integer> toIntSet(String str, String op, boolean ignoreConvertException) {
        if (str == null) {
            return null;
        }
        checkSplitOpIsNotNull(op, "toIntSet");
        String[] arr = split(str, op);

        return ArrayUtils.toSet(ArrayUtils.toIntArray(arr, ignoreConvertException));
    }

    /**
     * 将字符串以指定符号分割并返回长整型List集合
     *
     * @param str                    要分割的字符串
     * @param op                     符号
     * @param ignoreConvertException 是否忽略转化异常
     * @return 长整型List集合
     */
    public static List<Long> toLongList(String str, String op, boolean ignoreConvertException) {
        if (str == null) {
            return null;
        }
        checkSplitOpIsNotNull(op, "toLongList");
        String[] arr = split(str, op);

        return ArrayUtils.toList(ArrayUtils.toLongArray(arr, ignoreConvertException));
    }

    /**
     * 将字符串以指定符号分割并返回长整型Set集合
     *
     * @param str                    要分割的字符串
     * @param op                     符号
     * @param ignoreConvertException 是否忽略转化异常
     * @return 长整型Set集合
     */
    public static Set<Long> toLongSet(String str, String op, boolean ignoreConvertException) {
        if (str == null) {
            return null;
        }
        checkSplitOpIsNotNull(op, "toLongSet");
        String[] arr = split(str, op);

        return ArrayUtils.toSet(ArrayUtils.toLongArray(arr, ignoreConvertException));
    }

    /**
     * 获取字符串的字节长度
     *
     * @param value 字符串
     * @return 字节长度, 汉字按2个字节算
     */
    public static long getByteLength(String value) {
        if (isNullOrEmpty(value)) {
            return 0;
        }

        int strLen = 0;
        try {
            char[] cs = value.toCharArray();
            for (int i = 0; i < cs.length; i++) {
                strLen += String.valueOf(cs[i]).getBytes("gbk").length;
            }
        } catch (Exception e) {
            throw new UtilsException(currentClassName + "中的方法[getByteLength]发生异常.", e);
        }

        return strLen;
    }

    /**
     * 检查分割符号是否为null,为null则抛出异常.
     * @param op 分割符号
     * @param method 方法名
     */
    private static void checkSplitOpIsNotNull(String op, String method) {
        if (op == null) {
            throw new UtilsException(currentClassName + "中的方法[" + method + "]发生异常:分割符号不能为null.");
        }
    }
}
