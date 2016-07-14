package com.nhry.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gongjk on 2016/7/12.
 */
public class YearLastMonthUtil {

    public static void main(String[] args){
        System.out.println(getYearLastMonth());
        System.out.println(getLastMonthFirstDay());
        System.out.println(getLastMonthLastDay());
    }


    public static String getYearLastMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        return sdf.format(getLastDate(date));
    }

    public static Date getLastMonthFirstDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH,1);
        return  c.getTime();
    }

    public static Date getLastMonthLastDay(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DATE, -1);
        return  c.getTime();
    }

    private static Date getLastDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }
}
