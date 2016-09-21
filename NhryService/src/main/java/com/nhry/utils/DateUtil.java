package com.nhry.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by gongjk on 2016/7/12.
 */
public class DateUtil {

//    public static void main(String[] args){
//        System.out.println(getYestoday(new Date()));
//        System.out.println(getTomorrow(new Date()));
//
//        System.out.println(getDayAfterTomorrow(new Date()));
//    }

    //获取date 日期的明天
    public static Date getYestoday(Date today) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(calendar.DATE,-1);//把日期往后增加1天.整数往后推 这个时间就是日期往后推一天的结果
        Date firstDay = calendar.getTime();
        return firstDay;
    }


    //获取date 日期的明天
    public static Date getTomorrow(Date today) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(calendar.DATE,1);//把日期往后增加1天.整数往后推 这个时间就是日期往后推一天的结果
        Date firstDay = calendar.getTime();
        return firstDay;
    }
    //获取date 日期的后天
    public static Date getDayAfterTomorrow(Date today){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(calendar.DATE,2);//把日期往后增加2天.整数往后推 这个时间就是日期往后推两天的结果
        Date secondDay = calendar.getTime();
        return secondDay;
    }

}
