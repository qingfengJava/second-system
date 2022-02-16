package com.qingfeng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化工具类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/16
 */
public class DateFormatUtils {

    /**
     * 时间转字符串
     * @param date
     * @param pattern 要转换的格式 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateToStr(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }


    /**
     * 字符串转时间
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date strToDate(String dateStr, String pattern){
        try {
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
