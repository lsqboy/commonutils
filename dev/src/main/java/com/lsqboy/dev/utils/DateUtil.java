package com.lsqboy.dev.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;


@SuppressLint("SimpleDateFormat")
public final class DateUtil {

    private static final SimpleDateFormat MM_DD_FORMAT = new SimpleDateFormat("MM-dd");

    private DateUtil() {
        /* no-op */
    }
    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTimeyyMMdd() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        return date;
    }
    /**
     *      * 获取任意时间的月份
     *      * 描述:<描述函数实现的功能>.
     *      * @param repeatDate
     *      * @return
     *     
     */
    public static String getMonth(String yyyyMMdd) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(yyyyMMdd);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date time = calendar.getTime();
        String s = DateUtil.formatWithMM(time.getTime());
        return s;
    }

    /**
     *      * 获取任意时间的下一个月
     *      * 描述:<描述函数实现的功能>.
     *      * @param repeatDate
     *      * @return
     *     
     */
    public static String getPreMonth(String yyyyMM) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(yyyyMM);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date time = calendar.getTime();
        String s = DateUtil.formatWithYM(time.getTime());
        return s;
    }


    /**
     *      * 获取任意时间的下一个年份
     *      * 描述:<描述函数实现的功能>.
     *      * @param repeatDate
     *      * @return
     *     
     */
    public static String getPreYearMonth(String yyyyMMdd, int year) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(yyyyMMdd);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date time = calendar.getTime();
        String s = DateUtil.formatWithYM(time.getTime());
        return s;
    }

    /**
     * 不考虑国际化的月日格式化
     * <p>MM-dd
     *
     * @param date 时间
     * @return 格式化结果
     */
    public static String formatDateTimeMmDd(@NonNull Date date) {
        return MM_DD_FORMAT.format(date);
    }

    /**
     * 不考虑国际化的月日格式化
     * <p>MM-dd
     *
     * @param millis 时间，毫秒
     * @return 格式化结果
     */
    public static String formatDateTimeMmDd(long millis) {
        return formatDateTimeMmDd(new Date(millis));
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTimeYYMMdd() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static String getCurrentMonth() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 获取当前Day
     *
     * @return
     */
    public static int getCurrentDayInt() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("dd");
        String date = sDateFormat.format(new Date());
        try {
            return Integer.valueOf(date);
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonthInt() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM");
        String date = sDateFormat.format(new Date());
        try {
            return Integer.valueOf(date);
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrentYear() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        String date = sDateFormat.format(new Date());
        try {
            return Integer.valueOf(date);
        } catch (Exception e) {
            return 2019;
        }
    }

    /**
     * 比较时间差，如：相差5分钟
     *
     * @param time1 当前时间
     * @param time2 要比较的时间
     * @return
     */
    public static long compareTime(String time1, String time2) {
        long minutes = 0L;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            minutes = diff / (60 * 1000);
        } catch (Exception e) {
        }
        return minutes;
    }

    /**
     * 用法：Date d = getDate("2013-01-18 16:16:43.0", "yyyy-MM-dd HH:mm:ss");
     *
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date getDate(String dateStr, String pattern) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.parse(dateStr);
    }

    /**
     * 用法: formatDate(d, "yyyy-MM-dd HH:mm:ss");
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Long date, String pattern) {
        if (date == null || pattern == null) {
            throw new NullPointerException("The arguments are null !");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(date));
    }

    /**
     * 转为yyyy-MM
     *
     * @param date
     * @return
     */
    public static String formatWithYM(long date) {
        if (date == 0) {
            return "";
        }

        return formatDate(date, "yyyy-MM");
    }

    /**
     * 转为yyyy-MM
     *
     * @param date
     * @return
     */
    public static String formatWithMM(long date) {
        if (date == 0) {
            return "";
        }

        return formatDate(date, "MM");
    }
    /**
     * 转为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatWithYMD(long date) {
        if (date == 0) {
            return "";
        }

        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 转为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatWithYMDCN(long date) {
        if (date == 0) {
            return "";
        }

        return formatDate(date, "yyyy年MM月dd日");
    }

    /**
     * 转为HH:mm
     *
     * @param date
     * @return
     */
    public static String formatWithHm(long date) {
        if (date == 0) {
            return "";
        }

        return formatDate(date, "HH:mm");
    }

    /**
     * 转为HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatWithHms(long date) {
        if (date == 0) {
            return "";
        }

        return formatDate(date, "HH:mm:ss");
    }

    /**
     * 转为 yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String formatWithYMDHm(long date) {
        if (date == 0) {
            return "";
        }

        return formatDate(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 转为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatWithYMDHms(long date) {
        if (date == 0) {
            return "";
        }

        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 由字符串转为时间戳
     *
     * @param datetime
     * @param pattern
     * @return
     */
    public static long parse(String datetime, String pattern) {
        try {

            if (TextUtils.isEmpty(datetime)) {
                return System.currentTimeMillis();
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
        }

        return System.currentTimeMillis();
    }

    /**
     * 由字符串转为Date对象
     *
     * @param datetime
     * @param pattern
     * @return
     */
    public static Date parseToDate(String datetime, String pattern) {
        try {

            if (TextUtils.isEmpty(datetime)) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(datetime);
        } catch (ParseException e) {
        }

        return null;
    }

    //两个日期相差几天
    public static long dateDiff(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
            if (day >= 1) {
                return day;
            } else {
                if (day == 0) {
                    return 1;
                } else {
                    return 0;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    //两个日期比较大小
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

}
