package io.renren.common.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.util.TextUtils;

/**
 * 
 * 此类描述的是： 日期工具类
 * 
 * @author: wangxiongd@163.com
 * @version: 1.0.0
 */
public class DateUtil {

	/** 长格式24小时制：yyyy-MM-dd HH:mm:ss */
	public static final String DATE_LONGTIME24_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 年月日：yyyy-MM-dd */
	public static final String DATE_SHORTDATE_PATTERN = "yyyy-MM-dd";

	/** 年月日：yyyy/MM/dd */
	public static final String DATE_SHORTDATE_PATTERN1 = "yyyy/MM/dd";

	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_LONGTIME24_PATTERN);

	private static SimpleDateFormat sortSdf = new SimpleDateFormat(DATE_SHORTDATE_PATTERN);

	public static Date currentTime() {
		return new Date();
	}

	public static String currentTimeText() {
		return sdf.format(currentTime());
	}

	public static String formatDate(Date date) {
		return sdf.format(date);
	}

	public static String formatShortDate(Date date) {
		return sortSdf.format(date);

	}

	public static String formatDate(Date date, String formatter) {
		return new SimpleDateFormat(formatter).format(date);
	}

	public static Date parseDate(String dateText) {
		try {
			return sdf.parse(dateText);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Date parseShortDate(String dateText) {
		try {
			return sortSdf.parse(dateText);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取当前时间的长字符串形式
	 */
	public static String getDateString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 获取当前时间的长字符串形式
	 */
	public static String getCurrentDateString(String pattern) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getCurrentDateString(String date, String pattern) {

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String dateString = formatter.format(date);
		return dateString;
	}

	public static Date getCurrentDate(String pattern) {
		String dateString = getCurrentDateString(pattern);
		Date date = getDate(dateString, pattern);
		return date;
	}

	public static Date getDate(String dateString, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			//
		}
		return null;
	}

	/**
	 * 增加天数
	 * 
	 * @param date
	 * @param cnt
	 * @return
	 */
	public static Date addDate(Date date, int cnt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, cnt);
		return calendar.getTime();
	}

	/**
	 * 增加分钟
	 * 
	 * @param date
	 * @param cnt
	 * @return
	 */
	public static Date addMinute(Date date, int cnt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, cnt);
		return calendar.getTime();
	}

	/**
	 * 增加月数
	 * 
	 * @param date
	 * @param cnt
	 * @return
	 */
	public static Date addMonth(Date date, int cnt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, cnt);
		return calendar.getTime();
	}

	/**
	 * 取得当前日期的最大时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastTimeOnDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 取得当前日期的最大时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastMonthLastDay(Date date) {

		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return (cale.getTime());
	}

	public static Date getLastMonthDay(Date date, Integer param) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (lastDay > param) {// PS：如果选择31日，2月份的缴租日为最后一天，小月份也是最后一天
			lastDay = param;
		}
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		return calendar.getTime();
	}

	/**
	 * 获取当月的 天数
	 */
	public static Date getCurrentMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取当月的 天数
	 */
	public static Date getCurrentMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		// calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 取得当前日期的最大时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastTimeOnDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(date));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 取得当前日期的最小时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstTimeOnDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期相差月份
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int countMonths(Date startTime, Date endTime) {
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(startTime);
		aft.setTime(endTime);
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		return Math.abs(month + result);
	}

	public static int countDays(Date startTime, Date endTime) {

		String startDayString = DateUtil.getDateString(startTime, DATE_SHORTDATE_PATTERN);
		String endDayString = DateUtil.getDateString(endTime, DATE_SHORTDATE_PATTERN);

		startTime = DateUtil.getDate(startDayString, DATE_SHORTDATE_PATTERN);
		endTime = DateUtil.getDate(endDayString, DATE_SHORTDATE_PATTERN);
		long diff = endTime.getTime() - startTime.getTime();

		// 开始日期若小月结束日期
		// return (int)((diff)/(1000*3600*24));

		return (int) Math.ceil((diff) / (1000 * 3600 * 24));
	}

	/**
	 * 毫秒转Date
	 * 
	 * @param millSec
	 * @return
	 */
	public static Date getLongToDate(Long millSec) {
		if (millSec == null) {
			return null;
		}
		Date date = new Date(millSec);
		return date;
	}

	/**
	 * 毫秒转Date 固定GMT+8 时区
	 * 
	 * @param millSec
	 * @return
	 */
	public static Date getLongToDateFormZone(Long millSec) {
		if (millSec == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		calendar.setTimeInMillis(millSec);
		return calendar.getTime();
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		int maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDate;
	}

	/**
	 *
	 */
	public static String dayForWeek(String pTime) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(pTime));
			int dayForWeek = 0;
			if (c.get(Calendar.DAY_OF_WEEK) == 1) {
				dayForWeek = 7;
			} else {
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}
			if (1 == dayForWeek) {
				return "星期一";
			} else if (2 == dayForWeek) {
				return "星期二";
			} else if (3 == dayForWeek) {
				return "星期三";
			} else if (4 == dayForWeek) {
				return "星期四";
			} else if (5 == dayForWeek) {
				return "星期五";
			} else if (6 == dayForWeek) {
				return "星期六";
			} else {
				return "星期日";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// 获取星期几
	public static String dayForWeek(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayForWeek = 0;
			if (c.get(Calendar.DAY_OF_WEEK) == 1) {
				dayForWeek = 7;
			} else {
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}
			if (1 == dayForWeek) {
				return "星期一";
			} else if (2 == dayForWeek) {
				return "星期二";
			} else if (3 == dayForWeek) {
				return "星期三";
			} else if (4 == dayForWeek) {
				return "星期四";
			} else if (5 == dayForWeek) {
				return "星期五";
			} else if (6 == dayForWeek) {
				return "星期六";
			} else {
				return "星期日";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static int getNowMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 日期格式字符串转换成时间戳(unix_timestamp)
	 *
	 * @return
	 */
	public static String Date2TimeStamp(Date date) {
		try {
			return String.valueOf(date.getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static List<String> getLastWeek(int weeks) {
		List<String> result = new ArrayList<String>();
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.WEEK_OF_YEAR, weeks);

			int dayForWeek = 0;
			if (c.get(Calendar.DAY_OF_WEEK) == 1) {
				dayForWeek = 7;
			} else {
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}

			if (1 != dayForWeek) {// 不是星期一,日期设置为周一
				c.add(Calendar.DATE, 0 - dayForWeek + 1);
			}

			for (int i = 0; i < 7; i++) {

				String dateStr = format.format(c.getTime());

				result.add(dateStr);
				c.add(Calendar.DATE, 1);
				System.out.println(dateStr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static int getDaysYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static BigDecimal[] getIncreaseArray(Date startDate, Date endDate, String rentIncrease) {
		int startYear = DateUtil.getDaysYear(startDate);
		int endYear = DateUtil.getDaysYear(endDate);

		BigDecimal[] result = new BigDecimal[endYear - startYear + 1];
		result[0] = new BigDecimal(1);

		if (rentIncrease == null) {
			rentIncrease = "";
		}

		String[] increaseArray = rentIncrease.split(",");

		for (int i = 1; i <= endYear - startYear; i++) {
			try {
				String increase = increaseArray[i - 1];
				BigDecimal aftetrIncrease = new BigDecimal(1).add(new BigDecimal(increase).divide(new BigDecimal(100)));
				result[i] = result[i - 1].multiply(aftetrIncrease);
			} catch (Exception ex) {
				result[i] = result[i - 1];
			}
		}
		return result;
	}

	public static int getYearIndex(Date startDate, Date endDate) {
		int startYear = DateUtil.getDaysYear(startDate);
		int endYear = DateUtil.getDaysYear(endDate);

		int result = endYear - startYear;

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.YEAR, endYear);
		cal.add(Calendar.DATE, -1);

		if (cal.getTime().getTime() >= endDate.getTime()) {
			result--;
		}
		return result;
	}

	/**
	 * 
	 * 此方法描述的是： 获取指定时间之内的每满年的日期集合
	 * 
	 * @author: wangxiongd@163.com
	 * @version: 1.0.0
	 */
	public static List<Date> getYearList(Date signStartTime, Date signEndTime) {
		List<Date> result = new ArrayList<Date>();
		result.add(signStartTime);
		signStartTime = addDate(signStartTime, 365); // 满365天算一年
		while (signStartTime.getTime() <= signEndTime.getTime()) {
			result.add(signStartTime);
			signStartTime = addDate(signStartTime, 365);
		}

		return result;
	}

	/**
	 * 
	 * 此方法描述的是： 获取上个月最后一天日期
	 * 
	 * @author: wangxiongd@163.com
	 * @version: 1.0.0
	 * @param mountCount
	 */
	public static Date getPreMonthLastDay(Date date, int mountCount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -mountCount);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 
	 * 此方法描述的是： 获取上个月最后一天日期
	 * 
	 * @author: wangxiongd@163.com
	 * @version: 1.0.0
	 * @param mountCount
	 */
	public static Date getMonthLastDay(Date date, int mountCount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, mountCount);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// 获取时间段的月份201701、201702
	public static List<String> getMonthList(String startTime, String endTime) {
		List<String> result = new ArrayList<String>();

		Date startDate = null;
		Date endDate = null;

		try {
			startDate = sortSdf.parse(startTime);
			endDate = sortSdf.parse(endTime);
		} catch (Exception ex) {
			return result;
		}
		while (startDate.getTime() <= endDate.getTime()) {
			String monthStr = new SimpleDateFormat("yyyyMM").format(startDate);
			result.add(monthStr);

			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DATE, 1);
			startDate = cal.getTime();
		}
		return result;
	}

	public static void main(String[] args) {
		// System.out.print(dayForWeek("2016-08-07"));
		// System.out.println(DateUtil.getDate("2017-02-02",DATE_SHORTDATE_PATTERN));
		// System.out.println(getDaysByYearMonth(DateUtil.getDate("2017-02-02",DATE_SHORTDATE_PATTERN)));
		Date startDay = DateUtil.getDate("2017-3-1", DATE_SHORTDATE_PATTERN);
		Date endDay = DateUtil.getDate("2017-4-2", DATE_SHORTDATE_PATTERN);
		int count = DateUtil.countMonths(startDay, endDay);
		System.out.println("count:" + count);
//
//		System.out.println(getNowMonth());
//		
//		getLastWeek(1);
		System.out.println(Arrays.toString(getIncreaseArray(startDay, endDay, null)));

		System.out.println(getMonthList("2017-1-31", "2017-3-2"));
		// System.out.println(DateUtil.getDateString(getLastMonthLastDay(DateUtil.getDate("2017-06-02",DATE_SHORTDATE_PATTERN)),DATE_SHORTDATE_PATTERN));
	}

	public static String formatDateyyMMdd(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(date);
	}

	/**
	 * 获取指定年月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getMonthFirstByYearMonth(int year, int month) {
		String firstday = "";
		Calendar cal = Calendar.getInstance();
		// 时
		cal.set(Calendar.HOUR_OF_DAY, 0);
		// 分
		cal.set(Calendar.MINUTE, 0);
		// 秒
		cal.set(Calendar.SECOND, 0);
		// 毫秒

		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最小天数
		int firstDay = cal.getMinimum(Calendar.DATE);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		firstday = DateUtil.formatShortDate(cal.getTime());
		return firstday;
	}

	/**
	 * 获取指定年月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getMonthEndByYearMonth(int year, int month) {
		String endtday = "";
		Calendar cal = Calendar.getInstance();
		// 时
		cal.set(Calendar.HOUR_OF_DAY, 0);
		// 分
		cal.set(Calendar.MINUTE, 0);
		// 秒
		cal.set(Calendar.SECOND, 0);
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DATE);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		endtday = DateUtil.formatShortDate(cal.getTime());
		return endtday;
	}

	/**
	 * 时间戳转成提示性日期格式（昨天、今天……)
	 */
	public static String getDateToString(Date milSecond) {
		Date date = milSecond;
		SimpleDateFormat format;
		String hintDate = "";
		// 先获取年份
		int year = Integer.valueOf(new SimpleDateFormat("yyyy").format(date));
		// 获取一年中的第几天
		int day = Integer.valueOf(new SimpleDateFormat("d").format(date));
		// 获取当前年份 和 一年中的第几天
		Date currentDate = new Date(System.currentTimeMillis());
		int currentYear = Integer.valueOf(new SimpleDateFormat("yyyy").format(currentDate));
		int currentDay = Integer.valueOf(new SimpleDateFormat("d").format(currentDate));
		// 计算 如果是去年的
		if (currentYear - year == 1) {
			// 如果当前正好是 1月1日 计算去年有多少天，指定时间是否是一年中的最后一天
			if (currentDay == 1) {
				int yearDay;
				if (year % 400 == 0) {
					yearDay = 366;// 世纪闰年
				} else if (year % 4 == 0 && year % 100 != 0) {
					yearDay = 366;// 普通闰年
				} else {
					yearDay = 365;// 平年
				}
				if (day == yearDay) {
					hintDate = "昨天";
				}
			}
		} else {
			if (currentDay - day == 1) {
				hintDate = "昨天";
			} else if (currentDay - day == 0) {
				hintDate = "今天";
			} else if (currentDay - day == -1) {
				hintDate = "明天";
			} else if (currentDay - day == -2) {
				hintDate = "后天";
			} else {
				hintDate = dayForWeek(date);
			}
		}
		if (TextUtils.isEmpty(hintDate)) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return format.format(date);
		} else {
			format = new SimpleDateFormat("M月d日");
			return hintDate + format.format(date);
//            format = new SimpleDateFormat("HH:mm");
//            return hintDate + " " + format.format(date);
		}

	}

	public static long getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day * 24 * 60 + hour * 60 + min;
	}

	public static int timeBetween(Date early, Date late) {
		long time = Math.abs(late.getTime() - early.getTime());
		// long m = time / 1000 / 60 / 60 / 24; //天
		long m = time / 1000 / 60; // 分钟
		return (int) Math.abs(m);
	}

	public static int dayBetween(Date early, Date late) {
		long time = Math.abs(late.getTime() - early.getTime());
		long m = time / 1000 / 60 / 60 / 24; // 天
//		long m = time / 1000 / 60; // 分钟
		return (int) Math.abs(m);
	}

	// 获取周几
	public static String dayForZouWeek(String pTime) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(pTime));
			int dayForWeek = 0;
			if (c.get(Calendar.DAY_OF_WEEK) == 1) {
				dayForWeek = 7;
			} else {
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}
			if (1 == dayForWeek) {
				return "周一";
			} else if (2 == dayForWeek) {
				return "周二";
			} else if (3 == dayForWeek) {
				return "周三";
			} else if (4 == dayForWeek) {
				return "周四";
			} else if (5 == dayForWeek) {
				return "周五";
			} else if (6 == dayForWeek) {
				return "周六";
			} else {
				return "周日";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * //yyyy-MM-dd格式转MM月dd日
	 */
	public static String dayForYueDay(String pTime) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(pTime);
			String now = new SimpleDateFormat("MM月dd日").format(date);
			return now;
		} catch (Exception e) {
			return "";
		}

	}

	public static String dayForHourMinute(String pTime) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pTime);
			String now = new SimpleDateFormat("HH:mm").format(date);
			return now;
		} catch (Exception e) {
			return "";
		}

	}

	/*
	 * 时间格式转字符串
	 */
	public static String dateToString(Date pTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String string = sdf.format(pTime);

			return string;
		} catch (Exception e) {
			return "";
		}

	}

	/*
	 *
	 * 字符串转时间
	 **/
	public static Date stringToDate(String pTime) {

		// 注意：SimpleDateFormat构造函数的样式与pTime的样式必须相符
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 加上时间
		// 必须捕获异常
		try {
			Date date = simpleDateFormat.parse(pTime);
			return date;
		} catch (ParseException px) {
			return null;
		}
	}
	/*
	 * 时间格式转字符串(只需要年月日)
	 */	
	public static String dateToStringNYD(Date pTime) {
		try {
	  SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
		String string = sdf.format(pTime);
		
		return string;
		} catch (Exception e) {
			return "";
		}
			
	}

}
