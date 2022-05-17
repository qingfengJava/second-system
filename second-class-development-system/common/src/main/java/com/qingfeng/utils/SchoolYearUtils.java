package com.qingfeng.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 编写一个生成学年的方法：前后总共5个学年
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/13
 */
public class SchoolYearUtils {

    /**
     * 使用默认时区和语言环境获得一个日历。
     */
    private static Calendar calendar = Calendar.getInstance();

    /**
     * 根据当前时间来分析得到学年
     * @return
     */
    public static List<String> getSchoolYear(){
        List<String> list = new ArrayList<>();
        //获取当前年份
        int year = calendar.get(Calendar.YEAR)-5;
        for (int i = 0; i < 7 ; i++) {
            String schoolYear = "";
            int month = calendar.get(Calendar.MONTH) + 1;
            if (month > 6){
                //是下半年  生成一个学年
                schoolYear = schoolYear+year+"-"+(year + 1);
            }else{
                //说明是上半年 生成一个学年字符串
                schoolYear = schoolYear+(year - 1)+"-"+year;
            }
            year++;
            list.add(schoolYear);
        }
        return list;
    }

    /**
     * 根据传入的学年返回一组学年集合
     * @param year
     * @return
     */
    public static List<String> getSchoolYear(int year) {
        List<String> list = new ArrayList<>();
        //获取当前年份
        year -= 2;
        for (int i = 0; i < 7 ; i++) {
            String schoolYear = "";
            int month = calendar.get(Calendar.MONTH) + 1;
            if (month > 6){
                //是下半年  生成一个学年
                schoolYear = schoolYear+year+"-"+(year + 1);
            }else{
                //说明是上半年 生成一个学年字符串
                schoolYear = schoolYear+(year - 1)+"-"+year;
            }
            year++;
            list.add(schoolYear);
        }
        return list;
    }

    /**
     * 按时间生成一个学年字符串
     * @return
     */
    public static String getSchoolYearByOne(){
        //获取当前年份
        int year = calendar.get(Calendar.YEAR);
        String schoolYear = "";
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month > 6){
            //是下半年  生成一个学年
            schoolYear = schoolYear+year+"-"+(year + 1)+"-1";
        }else{
            //说明是上半年 生成一个学年字符串
            schoolYear = schoolYear+(year - 1)+"-"+year+"-2";
        }
        return schoolYear;
    }

    /**
     * 根据年返回一个学年字符串
     * @param year
     * @return
     */
    public static String getSchoolYearByOne(int year){
        //获取当前年份
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month > 6){
            //是下半年  生成一个学年
            return year+"-"+(year + 1);
        }else{
            //说明是上半年 生成一个学年字符串
            return (year - 1)+"-"+year;
        }
    }

    public static void main(String[] args) {
        List<String> schoolYear = getSchoolYear();
        Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
    }

}
