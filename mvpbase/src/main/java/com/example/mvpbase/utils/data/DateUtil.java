package com.example.mvpbase.utils.data;


import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: 雄厚
 * Date: 2020/8/20
 * Time: 10:00
 */
public class DateUtil {

    private DateUtil() {

    }

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";

    /**
     * 时间戳转日期/时间
     *
     * @param seconds 时间戳（毫秒）
     * @param pattern 时间格式
     * @return 格式化的日期/时间
     */
    public static String timeStamp2Date(long seconds, String pattern) {
        String time = "暂无数据";
        if (TextUtils.isEmpty(pattern)) {
            pattern = DF_YYYY_MM_DD_HH_MM_SS;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(seconds / 1000L, 0, ZoneOffset.ofHours(8));
            if (seconds != 0) {
                time = dateTime.format(DateTimeFormatter.ofPattern(pattern));
            }
        }else {
            Date date = new Date(seconds);
            if (seconds != 0){
                time = format(pattern, date);
            }
        }
        return time;
    }

    /**
     * 时间戳转日期/时间
     *
     * @param seconds 时间戳（毫秒）
     * @param pattern 时间格式
     * @return 格式化的日期/时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeStampDate(long seconds, String pattern){
        String time = "暂无数据";
        if (TextUtils.isEmpty(pattern)) {
            pattern = DF_YYYY_MM_DD_HH_MM_SS;
        }
        Date date = new Date(seconds);
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if (seconds != 0) {
            time = simpleDateFormat.format(date);
        }
        return time;
    }

    /**
     * 日期/时间转时间戳
     *
     * @param date    日期/时间 2020-08-20 0.:25:02
     * @param pattern 时间格式 yyyy-MM-dd HH:mm:ss
     * @return 时间戳(毫秒)
     */
    public static long date2TimeStamp(String date, String pattern) {
        long timeStamp = 0;
        if (TextUtils.isEmpty(pattern)) {
            pattern = DF_YYYY_MM_DD_HH_MM_SS;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
            timeStamp = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }else {
            Date myDate;
            try {
                myDate = parse(pattern, date);
                timeStamp = myDate.getTime();
            }catch(ParseException e) {
                e.printStackTrace();
            }

        }
        return timeStamp;
    }
    /**
     * 日期/时间转时间戳
     *
     * @param date    日期/时间 2020-08-20 0.:25:02
     * @param pattern 时间格式 yyyy-MM-dd HH:mm:ss
     * @return 时间戳(毫秒)
     */
    @SuppressLint("SimpleDateFormat")
    public static long dateTimeStamp(String date, String pattern){
        long timeStamp = 0;
        if (TextUtils.isEmpty(pattern)) {
            pattern = DF_YYYY_MM_DD_HH_MM_SS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date myDate;
        try{
            myDate = sdf.parse(date);
            timeStamp = myDate.getTime();
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 创建一个ThreadLocal类变量，这里创建时用了一个匿名类，覆盖了initialValue方法，主要作用是创建时初始化实例。
     */
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal() {
        @Override
        protected synchronized Object initialValue() {
            return new SimpleDateFormat();
        }
    };

    /**
     * 获取线程的变量副本，如果不覆盖initialValue，第一次get返回null，故需要初始化一个SimpleDateFormat，并set到threadLocal中
     */
    private static DateFormat getDateFormat(String pattern) {
        DateFormat dateFormat = (DateFormat) THREAD_LOCAL.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(pattern);
            THREAD_LOCAL.set(dateFormat);
        }
        return dateFormat;
    }

    public static Date parse(String pattern, String textDate) throws ParseException {
        return getDateFormat(pattern).parse(textDate);
    }

    private static String format(String pattern, Date date) {
        return getDateFormat(pattern).format(date);
    }

}
