package com.easzz.corejava.utils.helper;


import com.easzz.corejava.utils.extensions.StringUtils;
import com.easzz.corejava.utils.extensions.UtilsException;

import java.math.BigDecimal;

/**
 * @Author: 李溪林
 * @Descriptions:
 * @CreateDate: 17/12/6
 */
public class CurrencyHelper {

    private static final String currentClassName = "CurrencyHelper";

    private static final String DEFAULT_SEPARATOR = ",";
    private static final int DEFAULT_SEPARATE_LENGTH = 3;

    /**
     * 将指定的数值进行分割，以实现诸如千分位的效果
     * 使用默认的分割间隔长度[3]
     * 使用默认的分割符号[,]
     *
     * @param amount 要分割的数值
     * @return
     */
    public static String separate(BigDecimal amount) {
        return separate(amount, DEFAULT_SEPARATE_LENGTH);
    }

    /**
     * 将指定的数值进行分割，以实现诸如千分位的效果
     * 使用默认的分割符号[,]
     *
     * @param amount 要分割的数值
     * @param len    分割的间隔长度
     * @return
     */
    public static String separate(BigDecimal amount, int len) {
        return separate(amount, len, DEFAULT_SEPARATOR);
    }

    /**
     * 将指定的数值进行分割，以实现诸如千分位的效果
     *
     * @param amount    要分割的数值
     * @param len       分割的间隔长度
     * @param separator 分割符号
     * @return
     */
    public static String separate(BigDecimal amount, int len, String separator) {
        return internalSeparate(amount, len, separator);
    }

    /**
     * 将指定的数值进行分割，以实现诸如千分位的效果
     *
     * @param amount    要分割的数值
     * @param len       分割的间隔长度
     * @param separator 分割符号
     * @return
     */
    private static String internalSeparate(BigDecimal amount, int len, String separator) {
        if (amount == null) {
            throw new UtilsException("参数 amount 不能为空.");
        }
        if (len < 1) {
            throw new UtilsException("参数 len 不能小于1.");
        }
        if (StringUtils.isNullOrWhiteSpace(separator)) {
            throw new UtilsException("参数 separator 不能为空.");
        }
        String numStrTemp = amount.toString(), numStr1 = "", numStr2 = "";
        boolean isFloat = numStrTemp.indexOf('.') > -1;
        if (isFloat) {
            String[] temp = StringUtils.split(numStrTemp, ".");
            numStr1 = temp[0];
            numStr2 = temp[1];
        } else {
            numStr1 = numStrTemp;
        }
        String result = "";
        while (numStr1.length() > len) {
            result = separator + numStr1.substring(numStr1.length() - len) + result;
            numStr1 = numStr1.substring(0, numStr1.length() - len);
        }
        if (!StringUtils.isNullOrWhiteSpace(numStr1)) {
            result = numStr1 + result;
        }
        if (!StringUtils.isNullOrWhiteSpace(numStr2)) {
            result = result + "." + numStr2;
        }

        return result;
    }
}
