package com.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DateUtils {
	private static final Logger logger = Logger.getLogger(DateUtils.class);
	public static String[] PATTERN = new String[] { "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyyMMdd", "yyyy-MM-dd",
			"yyyy/MM/dd", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMM/dd",
			"yyyy-MM-dd HH:mm", "HH:mm:ss" };
	
	/** 正则表达式，合并平年和闰年，YYYY-MM-DD */
	public static final String DATE_PATTERN_FULL = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
	
	/**
	 * 转化为Date类型
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static Date convertToDate(String source, String pattern) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = dateFormat.parse(source);
		return date;
	}

	/**
	 * 将格式为yyyy-MM-dd HH:mm:ss的String类型的时间转化为 格式为yyyyMMddHHmmss的String
	 * 
	 * @param time
	 * @return
	 */
	public static String getStringTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
		java.util.Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			logger.error("日期格式转化异常：" + e);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf2.format(date);
	}

	/**
	 * 获取系统当前时间，具体格式：yyyyMMddHHmmss
	 * 
	 * @return 默认时间按“yyyyMMddHHmmss“格式显示
	 */
	public static String getCurrentDateTimeStr() {
		Date date = new Date();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeString = sdf2.format(date);
		return timeString;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, String date_sdf) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date_sdf);
		if (null == date) {
			return null;
		}
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取系统时间
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getSysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间的毫秒数
	 * 
	 * @return
	 */
	public static long getCurrentTimeInMillis() {
		long millionSeconds = Calendar.getInstance().getTimeInMillis();
		return millionSeconds;
	}

	/**
	 * 获取一个时间的毫秒数
	 * 
	 * @return
	 */
	public static long getTimeInMillis(Date time_sdf) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeString = sdf.format(time_sdf);
			long millionSeconds = sdf.parse(timeString).getTime();// 毫秒
			return millionSeconds;
		} catch (ParseException e) {
			logger.error("日期格式转化异常：" + e);
			return 0;
		}
	}

	/**
	 * 系统时间取得
	 */
	public static String getSysDate() {
		Date nowDate = new Date();
		DateFormat formatSysDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return formatSysDate.format(nowDate);
	}

	/**
	 * 获取多少小时前的时间
	 * 
	 * @param hours
	 * @return
	 */
	public static Date getDateTimeBeforeHours(int hours) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.HOUR, -hours);// 减hours小时
		return rightNow.getTime();
	}

	/**
	 * 获取多少分钟前的时间
	 * 
	 * @param minutes
	 * @return
	 */
	public static Date getDateTimeBeforeMinute(int minutes) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MINUTE, -minutes);// 减hours分钟
		return rightNow.getTime();
	}

	/**
	 * 获取指定时间多少分钟前的时间
	 */
	public static Date getDateBeforeMinute(int minutes, Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MINUTE, -minutes);// 减minutes分钟
		return rightNow.getTime();
	}

	/**
	 * 获取指定时间多少天前的时间
	 * 
	 * @throws ParseException
	 */
	public static Date getDateBeforeDate(int day, Date date) throws Exception {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DATE, -day);// 减minutes分钟
		return s.parse(s.format(rightNow.getTime()));
	}

	/**
	 * 把毫秒转换成格式xxxx-xx-xx
	 */
	public static String getDateByMil(Date date) {
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		return sp.format(date);
	}

	/**
	 * 日期运算
	 * 
	 * @param date
	 *            日期
	 * @param field
	 *            日历，年：1，月：2，日：5，周：3
	 * @param value
	 *            要加减的值，加为正数，减为负数
	 * @return
	 */
	public static Date getDate(Date date, int field, int value) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, value);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期相差的月数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int calculateMonthIn(Date date1, Date date2) {
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(date1);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(date2);
		int c = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH)
				- cal2.get(Calendar.MONTH);
		return c;
	}

	public static String formatDate(Date date, String pattern) {
		String format = StringUtils.EMPTY;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			format = formatter.format(date);
		} catch (Exception e) {
			logger.error("日期格式转化异常：" + e);
		}
		return format;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		long time1 = smdate.getTime();
		long time2 = bdate.getTime();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 日期 转 字符
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (null == date) {
			return StringUtils.EMPTY;
		}
		String format = StringUtils.EMPTY;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			format = sdf.format(date);
		} catch (Exception e) {
			logger.error("日期格式转化异常：" + e);
		}
		return format;
	}

	/**
	 * 字符 转 日期
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			logger.error("日期格式转化异常：" + e);
		}
		return date;
	}
	/**
	 * 将格式为yyyy-MM-dd HH:mm:ss的String类型的时间转化为 格式为指定格式的String
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String getStringTime(String time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
		java.util.Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			logger.error("日期格式转化异常：" + e);
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
		return sdf2.format(date);
	}
	
	/**
	 * 验证日期字符格式，YYYY-MM-DD
	 * 
	 * @param date
	 *            日期
	 * @param reg
	 *            正则式
	 * @return boolean true:是|false:不是
	 */
	public static boolean isDateStr(String date, String reg) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(date);
		return matcher.matches();
	}
}
