package com.distributed.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Idan on 2018/3/13.
 */
public class TimeUtil {

    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String interval(Timestamp driverStartTime) {
        double millis = System.currentTimeMillis()-driverStartTime.getTime();
        return (int)Math.ceil(millis/(24*3600*1000))+" 天";
    }

    /**
     * 获得当前时间形式为 2017-06-24 00:00:00
     * @return
     */
    public static String getFormatCurrentTime(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND,0);
        Date date =  calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

        String result =  simpleDateFormat.format(date);
        return result;
    }

    public static String getFormatTime(Date date){
        if(date==null){
            return "无";
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND,0);
        Date temp =  calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

        String result =  simpleDateFormat.format(temp);
        return result;
    }

}
