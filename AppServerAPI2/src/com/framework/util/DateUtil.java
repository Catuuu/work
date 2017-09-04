package com.framework.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期操作功能类.
 */
public class DateUtil {
    private static final Log logger = LogFactory.getLog(DateUtil.class);

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);
        return formatter.format(date);
    }

    /**
     * 判断字符串是否是有效的日期，
     * 格式"yyyy-MM-dd,yyyy-MM-d,yyyy-M-dd,yyyy-M-d
     * "yyyy/MM/dd,yyyy/MM/d,yyyy/M/dd,yyyy/M/d"
     * "yyyyMMdd"
     *
     * @param date 日期字符串
     * @return 是则返回true，否则返回false
     */
    public static boolean isValidDate(String date) {
        if ((date == null) || (date.length() < 8)) {
            return false;
        }
        try {
            boolean result = false;
            SimpleDateFormat formatter;
            char dateSpace = date.charAt(4);
            String format[];
            if ((dateSpace == '-') || (dateSpace == '/')) {
                format = new String[4];
                String strDateSpace = Character.toString(dateSpace);
                format[0] = "yyyy" + strDateSpace + "MM" + strDateSpace + "dd";
                format[1] = "yyyy" + strDateSpace + "MM" + strDateSpace + "d";
                format[2] = "yyyy" + strDateSpace + "M" + strDateSpace + "dd";
                format[3] = "yyyy" + strDateSpace + "M" + strDateSpace + "d";
            } else {
                format = new String[1];
                format[0] = "yyyyMMdd";
            }

            for (int i = 0; i < format.length; i++) {
                String aFormat = format[i];
                formatter = new SimpleDateFormat(aFormat);
                formatter.setLenient(false);
                String tmp = formatter.format(formatter.parse(date));
                if (date.equals(tmp)) {
                    result = true;
                    break;
                }
            }
            return result;
        }
        catch (ParseException e) {
            return false;
        }
    }


    /**
     * 判断字符串是否是有效的日期，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param date 日期字符串
     * @return 是则返回true，否则返回false
     */
    public static boolean isValidTime(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setLenient(false);
            formatter.parse(date);
            return true;
        }
        catch (ParseException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是有交换的日期格式
     *
     * @param str    日期字符串
     * @param format 日期的的格式
     * @return 是则返回true，否则返回false
     */
    public static boolean isValid(String str, String format) {
        java.text.DateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 转换字符串为日期，
     *
     * @param date   日期字符串
     * @param format 日期的的格式 yyyy-MM-dd
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date strToDate(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        return formatter.parse(date);
    }

    /**
     * 转换字符串为日期，格式"yyyy-MM-dd"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date strToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        return formatter.parse(date);
    }


    /**
     * 转换字符串为日期，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date strToTime(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);
        return formatter.parse(date);
    }

    /**
     * 转换日期为字符串，格式"yyyy-MM-dd"
     *
     * @param date 日期
     * @return 返回格式化的日期字符串
     */
    public static String dateToStr(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 转换日期为字符串
     *
     * @param date   日期
     * @param format 格式
     * @return 返回格式化的日期字符串
     */
    public static String dateToStr(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 返回 yyyy年MM月dd日
     *
     * @param date 日期对象
     * @return 返回格式化的日期字符串
     */
    public static String dateToStrCN(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月d日");
        return formatter.format(date);
    }


    /**
     * 转换日期为字符串，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param date 日期
     * @return 返回格式化的日期字符串
     */
    public static String timeToStr(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 转换日期为字符串，格式"yyyy-MM-dd HH:mm:ss.SSS"
     *
     * @param date 日期
     * @return 返回格式化的日期字符串
     */
    public static String timemsToStr(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return formatter.format(date);
    }

    /**
     * 取得现在的日期，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @return 返回格式化的日期字符串
     */
    public static String getNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Now = new Date();
        return formatter.format(Now);
    }


    /**
     * 取得现在的日期，格式"yyyy-MM"
     *
     * @return 返回格式化的日期字符串
     */
    public static String getNow_y_m() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date Now = new Date();
        return formatter.format(Now);
    }


    /**
     * 取得现在的日期，格式"MM-dd"
     *
     * @return 返回格式化的日期字符串
     */
    public static String getMMdd() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        Date Now = new Date();
        return formatter.format(Now);
    }

    /**
     * 取得现在的日期，格式"yyyy-MM-dd"
     *
     * @return 返回格式化的日期字符串
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date Now = new Date();
        return formatter.format(Now);
    }

    /**
     * 取得现在的时间，格式"HH:mm:ss"
     *
     * @return 返回格式化的时间字符串
     */
    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date Now = new Date();
        return formatter.format(Now);
    }

    /**
     * 取得年份，格式"yyyy"
     *
     * @return 返回当前年份
     */
    public static int getYear() {
        Date Now = new Date();
        return getYear(Now);
    }

    /**
     * 取得日期的年份，格式"yyyy"
     *
     * @param date 日期
     * @return 日期的年份
     */
    public static int getYear(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return Integer.parseInt(formatter.format(date));
    }

    /**
     * 取得当前时间的月份
     *
     * @return 返回当前月份
     */
    public static int getMonth() {
        Date Now = new Date();
        return getMonth(Now);
    }

    /**
     * 取得日期的月份
     *
     * @param date 日期
     * @return 日期的月份
     */
    public static int getMonth(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return Integer.parseInt(formatter.format(date));
    }


    /**
     * 取得今天的日期数
     *
     * @return 返回今天的日期数
     */
    public static int getDay() {
        Date Now = new Date();
        return getDay(Now);
    }

    /**
     * 取得日期的天数
     *
     * @param date 日期
     * @return 日期的天数
     */
    public static int getDay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return Integer.parseInt(formatter.format(date));
    }

    public static int getHour(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        return Integer.parseInt(formatter.format(date));
    }

    public static int getMinute(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm");
        return Integer.parseInt(formatter.format(date));
    }


    public static int getSecond(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("ss");
        return Integer.parseInt(formatter.format(date));
    }

    /**
     * 获得与某日期相隔几分钟的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的月数
     * @return 返回的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date addMinute(Date date, int addCount) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, addCount);
        return calendar.getTime();
    }

    /**
     * 获得与某日期相隔几小时的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的小时
     * @return 返回的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date addHour(Date date, int addCount) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, addCount);
        return calendar.getTime();
    }

    /**
     * 获得与某日期相隔几天的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的天数
     * @return 返回的日期
     */
    public static Date addDay(Date date, int addCount) {
        //Calendar cal = new GregorianCalendar();
        //最好用Calendar.getInstance();
        //因为有的地方，不是使用GregorianCalendar的。
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, addCount);
        return calendar.getTime();
    }

    /**
     * 获得与某日期相隔几月的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的月数
     * @return 返回的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date addMonth(Date date, int addCount) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addCount);
        return calendar.getTime();
    }

    /**
     * 获得与某日期相隔几年的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的月数
     * @return 返回的日期
     * @throws ParseException
     */
    public static Date addYear(Date date, int addCount) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, addCount);
        return calendar.getTime();
    }

    /**
     * 获得指定日期所在月份的第一天
     *
     * @param date 日期对象
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date getFirstDate(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-01");
        String firstdate = formatter.format(date);
        return strToDate(firstdate);
    }

    /**
     * 获得今天所在月份的第一天
     *
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date getFirstDate() throws ParseException {
        Date now = new Date();
        return getFirstDate(now);
    }

    /**
     * 获得指定日期所在月份的最后一天
     *
     * @param date 日期对象
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date getLastDate(Date date) throws ParseException {
        Date nextMonthfirstDate = addMonth(getFirstDate(date), 1);
        return addDay(nextMonthfirstDate, -1);
    }


    /**
     * 获得今天所在月份的最后一天
     *
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static Date getLastDate() throws ParseException {
        Date now = new Date();
        return getLastDate(now);
    }

    /**
     * 获得当年12-31
     *
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static String getLastYear() throws ParseException {
        return String.valueOf(getYear()) + "-12-31";
    }

    /**
     * 获得今天的日期对象
     *
     * @return Date
     */
    public static Date getToday() {
        return new Date();
    }


    /**
     * 时间比较
     *
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static boolean compareDateTime(String date1) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(formatter.parse(date1));
        cal2.setTime(formatter.parse(getDate()));
        return cal1.before(cal2);
    }
    /**
     * 时间比较
     *
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static boolean compareDateTime(String date1,String date2) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(formatter.parse(date1));
        cal2.setTime(formatter.parse(date2));
        return cal1.before(cal2);
    }
    /**
     * 时间比较
     *
     * @return Date
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static boolean compareDateTime(Date date1,Date date2) throws ParseException {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.before(cal2);
    }

    /**
     * 得出时间差
     *
     * @return date1开始时间 ，date2 结束时间
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static String getBetween(Date date1, Date date2) {
        String daVal = "";
        long da = date2.getTime() - date1.getTime();
        long day = da / (24 * 60 * 60 * 1000);
        long hour = (da / (60 * 60 * 1000) - day * 24);
        long min = ((da / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (da / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        daVal = hour + ":" + min + ":" + s;
        return daVal;
    }

    /**
     * 得出时间差
     *
     * @return date1开始时间 ，date2 结束时间
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static long getBetweenDay(Date date1, Date date2) {
        String daVal = "";
        long da = date2.getTime() - date1.getTime();
        int day = (int) (da / (24 * 60 * 60 * 1000));
        return day;
    }

    /**
     * 根据生日获取年龄
     *
     * @param year
     * @return
     */
    public static String getAge(Date year) {
        return String.valueOf(DateUtil.getYear() - DateUtil.getYear(year));
    }

    public static Integer getWeekDay(Date date) {
        Integer weekDay = null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            weekDay = 7;
        } else {
            weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return weekDay;
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static void main(String[] args) {

    }
}
