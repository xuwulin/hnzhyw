package com.swx.ibms.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * DateUtil.java 日期通用类
 *
 * @author 何向东
 * @version 1.0
 * @date<p>2017年8月8日</p>
 * @description <p></p>
 */
public class DateUtil {

    /**
     * // 格式：年－月－日 小时：分钟：秒
     */
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

    /**
     * // 格式：年－月－日 小时：分钟
     */
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

    /**
     * // 格式：年月日
     */
    public static final String FORMAT_THREE = "yyyyMMdd";

    /**
     * // 格式：年－月－日
     */
    public static final String FORMAT_FOUR = "yyyy-MM-dd";

    /**
     * // 格式：时分秒
     */
    public static final String FORMAT_FIVE = "HH:mm:ss";

    //节假日列表
    private static List<String> holidayList;
    //周末为工作日
    private static List<String> weekendList;

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream inHoliday = classLoader.getResourceAsStream("cfg-holiday.txt");
        InputStream inWeekend = classLoader.getResourceAsStream("cfg-weekend.txt");

        try {
            String txtHoliday = IOUtils.toString(inHoliday);
            String txtWeekend = IOUtils.toString(inWeekend);

            //            System.out.println(txtHoliday.replaceAll("[\r\n]", "").trim());
            //            System.out.println(txtWeekend.replaceAll("[\r\n]", "").trim());

            String compactTxtHoliday = txtHoliday.replaceAll("[\r\n]", "").trim();
            String compactTxtWeekend = txtWeekend.replaceAll("[\r\n]", "").trim();


            holidayList = Arrays.asList(compactTxtHoliday.split(","));
            weekendList = Arrays.asList(compactTxtWeekend.split(","));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param datestr
     *
     * @return
     *
     * @description yyyy-MM-dd 转换为 yyyyMMdd
     */
    public static String convert2yyyyMMdd (String datestr) {
        return datestr.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$1$2$3");
    }

    /**
     * @param datestr
     *
     * @return
     *
     * @description yyyyMMdd 转换为 yyyy-MM-dd
     */
    public static String convert2yyyy_MM_dd (String datestr) {
        return datestr.replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
    }

    /**
     * @param src    日期原串
     * @param infmt  日期输入格式
     * @param outfmt 日期输出格式
     * @param
     *
     * @return 目标日期字符串
     *
     * @throws ParseException
     * @Description: 将字符串从一种格式转化的
     */
    public static String getFmtDateStr (String src, String infmt, String outfmt) throws ParseException {
        // 输入格式
        DateFormat informater = new SimpleDateFormat(infmt);
        // 输出格式
        DateFormat outfomater = new SimpleDateFormat(outfmt);
        String result = outfomater.format(informater.parse(src));
        return result;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @throws ParseException
     */
    public static Date stringtoDate (String dateStr, String format) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        formater.setLenient(false);
        Date d = formater.parse(dateStr);
        return d;
    }

    /**
     * 把日期转换为字符串
     */
    public static String dateToString (java.util.Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String result = formater.format(date);
        return result;
    }

    /**
     * @param dateKind 指定处理年、月、日
     * @param dateStr  指定日期
     * @param amount   指定长度
     *
     * @return
     *
     * @throws ParseException
     */
    public static String dateSub (int dateKind, String dateStr, int amount) throws ParseException {
        Date date = stringtoDate(dateStr, FORMAT_ONE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateKind, amount);
        return dateToString(calendar.getTime(), FORMAT_ONE);
    }

    /**
     * @param days
     *
     * @return 返回一个相加减后的日期 yyyy-MM-dd
     */
    public static String dateSub (int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return dateToString(calendar.getTime(), FORMAT_FOUR);
    }

    /**
     * 两个日期相减
     *
     * @return 相减得到的秒数
     *
     * @throws ParseException
     */
    public static long timeSub (String firstTime, String secTime) throws ParseException {
        long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
        long second = stringtoDate(secTime, FORMAT_ONE).getTime();
        return (second - first) / 1000;
    }

    /**
     * 获取某年某月的天数
     */
    public static int getDaysOfMonth (int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回日期的年
     */
    public static int getYear (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的月份，1-12
     */
    public static int getMonth (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日期的日1-31
     */
    public static int getDay (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的时
     */
    public static int getHour (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }

    /**
     * 返回日期的分
     */
    public static int getMinute (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回日期的秒
     */
    public static int getSecond (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 返回日期在一个年中是第几天
     */
    public static int getDayOfYear (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 返回日期在一个月中是第几天
     */
    public static int getDayOfMonth (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回日期在一个周中是第几天
     */
    public static int getDayOfWeek (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 返回日期在一个月中是第几周
     */
    public static int getDayOfWeekInMonth (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 返回日期在一个年中是第几周
     */
    public static int getWeekOfYear (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 计算两个日期相差的天数，如果date1 > date2 返回正数，否则返回负数
     */
    public static long dayDiff (Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime());
        diff /= 3600 * 1000 * 24;
        return diff;
    }

    /**
     * 比较两个日期的年差
     *
     * @throws ParseException
     */
    public static int yearDiff (String before, String after) throws ParseException {
        Date beforeDay = stringtoDate(before, FORMAT_FOUR);
        Date afterDay = stringtoDate(after, FORMAT_FOUR);
        return getYear(afterDay) - getYear(beforeDay);
    }

    /**
     * 比较指定日期与当前日期的差
     *
     * @throws ParseException
     */
    public static int yearDiffCurr (String after) throws ParseException {
        return yearDiff(getSysCurrentDate(), after);
    }

    /**
     * 获取当前时间的指定格式
     */
    public static String getCurrDate (String format) {
        return dateToString(new Date(), format);
    }

    /**
     * @return 当前系统时间 yyyy-MM-dd HH24:MI:SS
     */
    public static String getSysCurrentDateTime () {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_ONE);
        String time = sdf.format(new java.util.Date());
        return time;
    }

    /**
     * @return 当前系统时间 yyyy-MM-dd
     */
    public static String getSysCurrentDate () {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FOUR);
        String time = sdf.format(new java.util.Date());
        return time;
    }

    /**
     * @return 当前系统时间 HH24:MI:SS
     */
    public static String getSysCurrentTime () {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FIVE);
        String time = sdf.format(new java.util.Date());
        return time;
    }

    /**
     * 将字符串转化为日期类型
     *
     * @param dateString 日期类型字符串
     *
     * @return
     */
    public static Date getDate4Str (String dateString) {
        if (dateString != null && dateString.trim() != "") {
            try {
                if (dateString.length() == 10) {
                    int year;
                    try {
                        year = Integer.parseInt(dateString.substring(0, 4));
                    } catch (Exception e) {
                        return null;
                    }
                    if (year % 100 != 0 && year % 4 != 0 || year % 100 == 0 && year % 400 != 0) {
                        if (dateString.trim()
                                .matches(
                                        "^([2][0]|[1][9])[0-9]{2}\\.((([0][13456789]|[1][012])\\.([0][1-9]|[12][0-9]|[3][01]))|([0][2]\\.([0][1-9]|[1][0-9]|[2][0-8])))$")) {
                            return new SimpleDateFormat("yyyy.MM.dd").parse(dateString.trim());
                        } else if (dateString.trim()
                                .matches(
                                        "^([2][0]|[1][9])[0-9]{2}\\-((([0][13456789]|[1][012])\\-([0][1-9]|[12][0-9]|[3][01]))|([0][2]\\-([0][1-9]|[1][0-9]|[2][0-8])))$")) {
                            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString.trim());
                        } else if (dateString.trim()
                                .matches(
                                        "^([2][0]|[1][9])[0-9]{2}\\/((([0][13456789]|[1][012])\\/([0][1-9]|[12][0-9]|[3][01]))|([0][2]\\/([0][1-9]|[1][0-9]|[2][0-8])))$")) {
                            return new SimpleDateFormat("yyyy/MM/dd").parse(dateString.trim());
                        } else {
                            return null;
                        }
                    } else {
                        if (dateString.trim()
                                .matches(
                                        "^([2][0]|[1][9])[0-9]{2}\\.((([0][13456789]|[1][012])\\.([0][1-9]|[12][0-9]|[3][01]))|([0][2]\\.([0][1-9]|[1][0-9]|[2][0-9])))$")) {
                            return new SimpleDateFormat("yyyy.MM.dd").parse(dateString.trim());
                        } else if (dateString.trim()
                                .matches(
                                        "^([2][0]|[1][9])[0-9]{2}\\-((([0][13456789]|[1][012])\\-([0][1-9]|[12][0-9]|[3][01]))|([0][2]\\-([0][1-9]|[1][0-9]|[2][0-9])))$")) {
                            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString.trim());
                        } else if (dateString.trim()
                                .matches(
                                        "^([2][0]|[1][9])[0-9]{2}\\/((([0][13456789]|[1][012])\\/([0][1-9]|[12][0-9]|[3][01]))|([0][2]\\/([0][1-9]|[1][0-9]|[2][0-9])))$")) {
                            return new SimpleDateFormat("yyyy/MM/dd").parse(dateString.trim());
                        } else {
                            return null;
                        }
                    }
                } else if (dateString.length() == 9) {
                    if (dateString.indexOf(".") > 0) {
                        if (dateString.split("\\.")[1].length() > 1) {
                            return new SimpleDateFormat("yyyy.MM.d").parse(dateString.trim());
                        } else {
                            return new SimpleDateFormat("yyyy.M.dd").parse(dateString.trim());
                        }
                    } else if (dateString.indexOf("/") > 0) {
                        if (dateString.split("\\/")[1].length() > 1) {
                            return new SimpleDateFormat("yyyy/MM/d").parse(dateString.trim());
                        } else {
                            return new SimpleDateFormat("yyyy/M/dd").parse(dateString.trim());
                        }
                    } else if (dateString.indexOf("-") > 0) {
                        if (dateString.split("\\-")[1].length() > 1) {
                            return new SimpleDateFormat("yyyy-MM-d").parse(dateString.trim());
                        } else {
                            return new SimpleDateFormat("yyyy-M-dd").parse(dateString.trim());
                        }
                    } else {
                        return null;
                    }
                } else if (dateString.length() == 8) {
                    if (dateString.indexOf(".") > 0) {
                        return new SimpleDateFormat("yyyy.M.d").parse(dateString.trim());
                    } else if (dateString.indexOf("/") > 0) {
                        return new SimpleDateFormat("yyyy/M/d").parse(dateString.trim());
                    } else if (dateString.indexOf("-") > 0) {
                        return new SimpleDateFormat("yyyy-M-d").parse(dateString.trim());
                    } else {
                        return null;
                    }
                } else if (dateString.length() == 7) {
                    if (dateString.trim().matches("^([2][0]|[1][9])[0-9]{2}\\.([0][1-9]|[1][012])$")) {
                        return new SimpleDateFormat("yyyy.MM").parse(dateString.trim());
                    } else if (dateString.trim().matches("^([2][0]|[1][9])[0-9]{2}\\-([0][1-9]|[1][012])$")) {
                        return new SimpleDateFormat("yyyy-MM").parse(dateString.trim());
                    } else if (dateString.trim().matches("^([2][0]|[1][9])[0-9]{2}\\/([0][1-9]|[1][012])$")) {
                        return new SimpleDateFormat("yyyy/MM").parse(dateString.trim());
                    } else {
                        return null;
                    }
                } else if (dateString.length() == 4) {
                    if (dateString.trim().matches("^([2][0]|[1][9])[0-9]{2}$")) {
                        return new SimpleDateFormat("yyyy").parse(dateString.trim());
                    } else if (dateString.trim().matches("^([2][0]|[1][9])[0-9]{2}$")) {
                        return new SimpleDateFormat("yyyy").parse(dateString.trim());
                    } else if (dateString.trim().matches("^([2][0]|[1][9])[0-9]{2}$")) {
                        return new SimpleDateFormat("yyyy").parse(dateString.trim());
                    } else {
                        return null;
                    }
                } else if (dateString.length() == 19) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString.trim());
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 根据当前年份、月份获取该月有多少天
     *
     * @param year  年
     * @param month 月
     *
     * @return 该年该月有多少天
     */
    public static int getDayByYearAndMonth (int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取日期【格式为年月日 时分秒】返回一个同步任务的时间表达式
     *
     * @param date 日期【格式为年月日 时分秒】
     *
     * @return 同步任务的时间表达式【格式为：秒+空格+分+空格+时+空格+日+空格+月+空格+? -> 表示每年某月某日某时某分某秒进行同步任务】
     */
    public static String getQuartzCronByDate (Date date) {
        String cronStr = StringUtils.EMPTY;
        SimpleDateFormat nyrformat = new SimpleDateFormat("yyyy-MM-dd");
        String nyrDate = nyrformat.format(date);  //年月日
        //		System.out.println(nyrDate.split("-")[0]+"======"+Integer.parseInt(nyrDate.split("-")[1])+"========"+Integer.parseInt(nyrDate.split("-")[2]));

        SimpleDateFormat sfmformat = new SimpleDateFormat("HH:mm:ss");
        String sfmDate = sfmformat.format(date);  //时分秒
        //		System.out.println(Integer.parseInt(sfmDate.split(":")[0])+"======"+Integer.parseInt(sfmDate.split(":")[1])+"========"+Integer.parseInt(sfmDate.split(":")[2]));
        StringBuilder cron = new StringBuilder();//秒 分 时 日 月 周/年;
        cron.append(sfmDate.split(":")[2])
                .append(" ")
                .append(Integer.parseInt(sfmDate.split(":")[1]))
                .append(" ")
                .append(Integer.parseInt(sfmDate.split(":")[0]))
                .append(" ")
                .append(Integer.parseInt(nyrDate.split("-")[2]))
                .append(" ")
                .append(Integer.parseInt(nyrDate.split("-")[1]))
                .append(" ")
                .append("?");
        cronStr = cron.toString();
        return cronStr;
    }

    /**
     * 获取日期格式的字符串【格式为年月日 时分秒】返回一个同步任务的时间表达式
     *
     * @param date 日期字符串【格式为年月日 时分秒】
     *
     * @return 同步任务的时间表达式【格式为：秒+空格+分+空格+时+空格+日+空格+月+空格+? -> 表示每年某月某日某时某分某秒进行同步任务】
     */
    public static String getQuartzCronByDateString (String date) {
        String cronStr = StringUtils.EMPTY;
        Date cronDate = DateUtil.getDate4Str(date);
        SimpleDateFormat nyrformat = new SimpleDateFormat("yyyy-MM-dd");
        String nyrDate = nyrformat.format(cronDate);  //年月日
        //		System.out.println(nyrDate.split("-")[0]+"======"+Integer.parseInt(nyrDate.split("-")[1])+"========"+Integer.parseInt(nyrDate.split("-")[2]));

        SimpleDateFormat sfmformat = new SimpleDateFormat("HH:mm:ss");
        String sfmDate = sfmformat.format(cronDate);  //时分秒
        //		System.out.println(Integer.parseInt(sfmDate.split(":")[0])+"======"+Integer.parseInt(sfmDate.split(":")[1])+"========"+Integer.parseInt(sfmDate.split(":")[2]));
        StringBuilder cron = new StringBuilder();//秒 分 时 日 月 周/年;
        cron.append(sfmDate.split(":")[2])
                .append(" ")
                .append(Integer.parseInt(sfmDate.split(":")[1]))
                .append(" ")
                .append(Integer.parseInt(sfmDate.split(":")[0]))
                .append(" ")
                .append(Integer.parseInt(nyrDate.split("-")[2]))
                .append(" ")
                .append(Integer.parseInt(nyrDate.split("-")[1]))
                .append(" ")
                .append("?");
        cronStr = cron.toString();
        return cronStr;
    }

    /**
     * 获取某一年中的周末
     *
     * @param whichYear
     *
     * @return
     */
    public static List<String> getWeekend (int whichYear) {

        List<String> results = new ArrayList<String>();


        DateTime starTime = new DateTime().withYear(whichYear).withDayOfYear(1);

        while (!(starTime.getYear() > whichYear)) {
            if (starTime.getDayOfWeek() > 5) {
                String date = starTime.toString("yyyy-MM-dd");
                results.add(date);
            }
            starTime = starTime.plusDays(1);
        }
        return results;
    }


    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     *
     * @return
     */
    public static List<String> getDays (String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    /**
     * 判断日期是否是工作日
     *
     * @param date
     *
     * @return
     */
    public static boolean isWorkday (String date) {
        boolean res = holidayList.contains(date);
        if (!res) {
            res = weekendList.contains(date);
        }
        return !res;
    }

    /**
     * 通过两个日期之间的天数（排除周末和节假日）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int countExcludeWorkday (String startDate, String endDate) {

        int days = 0;

        List<String> dateList = getDays(startDate, endDate);
        for (String day : dateList) {
            if (isWorkday(day)) {
                days++;
            }
        }

        return days;
    }

    public static void main (String[] args) {
        //        List<String> weekend = getWeekend(2021);
        //        for (String s : weekend) {
        //            System.out.println(s + ",");
        //        }
        int workday = countExcludeWorkday("2018-12-07","2019-01-03");
        System.out.println(workday);
    }
}
