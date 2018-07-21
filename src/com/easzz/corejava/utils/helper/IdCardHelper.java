package com.easzz.corejava.utils.helper;


import com.easzz.corejava.utils.extensions.DateUtils;
import com.easzz.corejava.utils.extensions.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 李溪林
 * @Descriptions:
 * @CreateDate: 17/3/6
 */
public class IdCardHelper {

    /**
     * 1级行政机构code
     */
    final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();

    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
    }

    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2};

    /**
     * 校验身份证号码是否合法
     * @param certNo
     * @return 合法返回true,否则返回false
     */
    public static boolean isIdCard(String certNo) {
        if (certNo == null || (certNo.length() != 15 && certNo.length() != 18))
            return false;
        final char[] cs = certNo.toUpperCase().toCharArray();
        //校验位数
        int power = 0;
        for (int i = 0; i < cs.length; i++) {
            if (i == cs.length - 1 && cs[i] == 'X')
                break;//最后一位可以 是X或x
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length - 1) {
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }

        //校验区位码
        if (!zoneNum.containsKey(Integer.valueOf(certNo.substring(0, 2)))) {
            return false;
        }

        //校验年份
        String year = certNo.length() == 15 ? ("19" + certNo.substring(6, 8)) : certNo.substring(6, 10);
        final int iyear = Integer.parseInt(year);
        if (iyear < 1900 || iyear > DateUtils.getYear(new Date()))
            return false;

        //校验月份
        String month = certNo.length() == 15 ? certNo.substring(8, 10) : certNo.substring(10, 12);
        final int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12) {
            return false;
        }

        //校验天数
        String day = certNo.length() == 15 ? certNo.substring(10, 12) : certNo.substring(12, 14);
        final int iday = Integer.parseInt(day);
        if (iday < 1 || iday > 31)
            return false;

        //校验"校验码"
        if (certNo.length() == 15)
            return true;
        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }

    /**
     * 检查身份证区位号(6位)是否合法
     * @param zoneCode 身份证区位号(6位)
     * @return 合法返回true,否则返回false
     */
    public static boolean checkZoneCode(String zoneCode){
        if(StringUtils.isNullOrWhiteSpace(zoneCode) || zoneCode.length() != 6){
            return false;
        }

        return zoneNum.containsKey(Integer.valueOf(zoneCode.substring(0, 2)));
    }

    /**
     * 检查身份证出生日期(8位)是否合法
     * @param birthdayCode 身份证出生日期(8位)
     * @return 合法返回true,否则返回false
     */
    public static boolean checkBirthday(String birthdayCode){
        if(StringUtils.isNullOrWhiteSpace(birthdayCode) || birthdayCode.length() != 8){
            return false;
        }
        try{
            //用转date的方式不行,会自动进位
            Date dt = DateUtils.newDate(birthdayCode, "yyyyMMdd");
            int year = DateUtils.getYear(dt), month = DateUtils.getMonth(dt), day = DateUtils.getDay(dt);
            if(!String.valueOf(year).equals(birthdayCode.substring(0,4))){
                return false;
            }
            if(!String.valueOf(month).equals(birthdayCode.substring(4,6))){
                return false;
            }
            if(!String.valueOf(day).equals(birthdayCode.substring(6,8))){
                return false;
            }
            Date min = DateUtils.newDate(1900,1,1), max = new Date();
            if(dt.getTime() >= min.getTime() && dt.getTime() <= max.getTime()){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 检查身份证号码长度是否合法
     * @param idCard 身份证号码
     * @return 合法返回true,否则返回false
     */
    public static boolean checkLength(String idCard){
        if (StringUtils.isNullOrWhiteSpace(idCard) || (idCard.length() != 15 && idCard.length() != 18)){
            return false;
        }
        final char[] cs = idCard.toCharArray();
        if(idCard.length() == 15){
            //15位,全都必须是数字
            for (int i = 0; i < cs.length; i++) {
                if (cs[i] < '0' || cs[i] > '9'){
                    return false;
                }
            }
        }else{
            //18位,最后1位可以是x,其余必须都是数字
            for (int i = 0; i < cs.length; i++) {
                if (i == cs.length - 1 && Character.toUpperCase(cs[i]) == 'X'){
                    break;
                }
                if (cs[i] < '0' || cs[i] > '9'){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 转换为18位的身份证号码
     * @param idCard 原始身份证号码(只能是15位或18位)
     * @return 18位的身份证号码
     */
    public static String transferTo18(String idCard){
        if(idCard.length() == 18){
            return idCard;
        }
        String temp1 = idCard.substring(0,6) + "19" + idCard.substring(6);
        final char[] cs = temp1.toCharArray();
        int power = 0;
        for (int i = 0; i < cs.length; i++) {
            power += (cs[i] - '0') * POWER_LIST[i];
        }

        return temp1 + (char)PARITYBIT[power % 11];
    }

    /**
     * 检查身份证号码的最后校验码是否合法
     * @param idCard 18位身份证号码
     * @return 合法返回true,否则返回false
     */
    public static boolean checkTheValidCode(String idCard){
        final char[] cs = idCard.toCharArray();
        int power = 0;
        for (int i = 0; i < cs.length - 1; i++) {
            power += (cs[i] - '0') * POWER_LIST[i];
        }

        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }
}
