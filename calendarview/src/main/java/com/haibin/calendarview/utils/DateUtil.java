package com.haibin.calendarview.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by lzq on 2017/2/11 0011.
 */
public class DateUtil {
    public static final String[] weekDays = {"周一","周二","周三","周四","周五","周六","周日"};
    private static final HashMap<String, Integer> WEEK_MAP = new HashMap<String, Integer>(){
        {
            put("周日", 1);
            put("周一", 2);
            put("周二", 3);
            put("周三", 4);
            put("周四", 5);
            put("周五", 6);
            put("周六", 7);
        }
    };
    public static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();
    public static final String DEFAULTFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_12 = "yyyy-MM-dd hh:mm:ss";
    public static final String FORMAT_END_DAY = "yyyy-MM-dd";
    public static final String FORMAT_END_MINUTE = "yyyy-MM-dd HH:mm";

    public static Date stringToDate(String dateString) {
        Date dateValue = null;
        try {
            ParsePosition position = new ParsePosition(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULTFORMAT);
            dateValue = simpleDateFormat.parse(dateString, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateValue;
    }

    public static Date stringToDate(String dateString, String isNoHour) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_END_DAY, Locale.UK);
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /**
     * string转date
     * @param dateString 输入的字符砖时间格式
     * @param format 输出的时间格式
     * @return
     */
    public static Date strToDate(String dateString, String format) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULTFORMAT);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrHalf(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_12);
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_END_DAY);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_END_DAY);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strHalfToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_12);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式字符串（yyyy-MM-dd HH:mm:ss）转换为格式为 yyyy-MM-dd 的字符串
     *
     * @param strDate
     * @return
     */
    public static String strToStr(String strDate) {
        if(TextUtils.isEmpty(strDate)) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_END_DAY);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        String format = formatter.format(strtodate);
        return format.replace("-",".");
    }

    /**
     * 将短时间格式字符串（yyyy-MM-dd HH:mm:ss）转换为格式为 yyyy-MM-dd 的字符串
     *
     * @param strDate 转化前的时间格式
     * @param isCircle 转化后的时间格式是否以“.”分割
     * @return
     */
    public static String strToStr(String strDate, boolean isCircle) {
        if(TextUtils.isEmpty(strDate)) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_END_DAY);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        String format = formatter.format(strtodate);
        if(isCircle) {
            return format.replace("-",".");
        }else {
            return format;
        }

    }

    /**
     * 将短时间格式字符串（yyyy-MM-dd HH:mm:ss）转换为格式为 yyyy-MM-dd 的字符串
     *
     * @param strDate 转化前的时间格式
     * @param out_format 转化后的时间格式
     * @param separator 输出后的替换分隔符，如".","/"等
     * @return
     */
    public static String strToStr(String strDate, String out_format, String separator) {
        if(TextUtils.isEmpty(strDate)) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(out_format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        String format = formatter.format(strtodate);
        if(!TextUtils.isEmpty(separator)) {
            return format.replace("-", separator);
        }else {
            return format;
        }

    }

    /**
     * 将短时间格式字符串（yyyy-MM-dd HH:mm:ss）转换为格式为 MM/dd hh:mm的字符串
     *
     * @param strDate
     * @return
     */
    public static String strToStr(String strDate, SimpleDateFormat formatter) {
        if(TextUtils.isEmpty(strDate)) {
            return "";
        }
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        String format = formatter.format(strtodate);
        return format;
    }
    /**
     * 得到现在时间
     *
     * @return
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat
     *            yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 判断当前是不是24小时制
     * @param context
     * @return
     */
    public static boolean is24Hour(Context context) {
        String strTimeFormat = null;
        try {
            ContentResolver resolver = context.getContentResolver();
            // 获取当前系统设置
            strTimeFormat = android.provider.Settings.System.getString(resolver,
                    android.provider.Settings.System.TIME_12_24);
            if (strTimeFormat != null && strTimeFormat.equals("12")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否为今天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {
        if(TextUtils.isEmpty(day)) {
            return false;
        }
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(String day) throws ParseException {
        if(TextUtils.isEmpty(day)) {
            return false;
        }
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat(FORMAT_END_DAY, Locale.CHINA));
        }
        return DateLocal.get();
    }

    /**
     * 增加日期
     * @param days
     * @return
     */
    public static Date getTime(int days){
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(Calendar.DAY_OF_MONTH, days);
        return gc.getTime();
    }

    /**
     * 增加月份
     * @param months
     * @return
     */
    public static Date addMonths(int months){
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(Calendar.MONTH, months);
        return gc.getTime();
    }

    /**
     * 增加周数
     * @param weeks
     * @return
     */
    public static Date addWeeks(int weeks){
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(Calendar.WEEK_OF_YEAR, weeks);
        return gc.getTime();
    }

    /**
     * 判断输入日期是一个星期中的第几天(星期天为一个星期第一天)
     *
     * @param date
     * @return
     */
    public static int getWeekIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 如果返回值为0，则代表在常量中不存在，需要对这种情况进行处理
     * @param weekDay
     * @return
     */
    public static int getWeekIndex(String weekDay){
        int index = 0;
        try {
            index = WEEK_MAP.get(weekDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public static String getExpertDate(String date){
        if(TextUtils.isEmpty(date)) {
            return dateToStr(getNow()) +"\u0020"+"00:00:00";
        }else {
            String newData;
            if(date.contains("上午")) {
                newData = date.replace("上午", "08:00:00");
            }else if(date.contains("下午")) {
                newData = date.replace("下午", "13:00:00");
            }else if(date.contains("夜晚")) {
                newData = date.replace("夜晚", "18:00:00");
            }else if(date.contains("前")) {
                newData = date.replace("前", " 00:00:00");
            }else if(date.contains("08:00:00")) {
                newData = date.replace("08:00:00", "上午");
            }else if(date.contains("13:00:00")) {
                newData = date.replace("13:00:00", "下午");
            }else if(date.contains("18:00:00")) {
                newData = date.replace("18:00:00", "夜晚");
            }else if(date.contains("00:00:00")) {
                newData = date.replace("00:00:00", "前");
            }else {
                String substring = date.substring(0, 10);
                newData = substring +"\u0020"+ "00:00:00";
            }
            return newData;
        }
    }

    /**
     * 用于判断环信时间戳的显示间隔
     * @param var0
     * @param var2
     * @return
     */
    public static boolean isCloseEnough(long var0, long var2) {
        long var4 = var0 - var2;
        if(var4 < 0L) {
            var4 = -var4;
        }

        return var4 < 180000L;
    }

    public static boolean isToday(String str, String formatStr){
        if(TextUtils.isEmpty(str)) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH)+1;
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(new Date());
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH)+1;
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        if(year1 == year2 && month1 == month2 && day1 == day2){
            return true;
        }
        return false;
    }

}
