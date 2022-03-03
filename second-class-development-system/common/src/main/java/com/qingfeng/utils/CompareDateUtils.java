package com.qingfeng.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期的大小比较工具类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/3
 */
public class CompareDateUtils {

    /**
     * 比较两个日期之间的大小
     *
     * @param date1
     * @param date2
     * @return 前者大于后者返回true 反之false
     */
    public static boolean compareDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);

        if (c1.compareTo(c2) >= 0) {
            //date1大于date2就返回true
            return true;
        } else {
            return false;
        }
    }
}
