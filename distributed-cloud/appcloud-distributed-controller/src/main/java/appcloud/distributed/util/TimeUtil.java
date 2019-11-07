package appcloud.distributed.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Idan on 2017/7/20.
 */
public class TimeUtil {

    public static String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm";
    public static String DATE_FORMAT_2 = "MMddHHmm";

    public static Date getCurrentTime() {
        return new Date(System.currentTimeMillis());
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 将Date转化成一定格式的String
     * @param date
     * @param format
     * @return
     */
    public static String ConverToString(Date date, String format) {
        if (format == null) {
            format = DATE_FORMAT_1;
        }
        DateFormat df = new SimpleDateFormat(format);
        return date==null?null:df.format(date);
    }

    /**
     * 将String 转化成 date
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date ConvertToDate(String dateStr, String format) throws ParseException {
        if (format == null) {
            format = DATE_FORMAT_1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateStr);
    }

    /**
     * 将timestamp转化成一定format的String
     * @param time
     * @param format
     * @return
     */
    public static String stampToString(Timestamp time, String format){
        if (format == null) {
            format = DATE_FORMAT_1;
        }
        DateFormat df = new SimpleDateFormat(format);
        return time==null?null:df.format(time);
    }

    /**
     * 比较两个date的大小
     * @param date1
     * @param date2
     * @return
     */
    public static Boolean compareTwoDate(Date date1, Date date2) {
        Long date1Millis = date1.getTime();
        Long date2Millis = date2.getTime();
        return date1Millis>date2Millis;
    }

}
