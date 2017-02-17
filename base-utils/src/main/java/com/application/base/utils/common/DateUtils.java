
package com.application.base.utils.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @version 1.0
 * @since 1.0
 */
public class DateUtils {

    /**
     * 年(yyyy)
     */
    public static final String YEAR = "yyyy";

    /**
     * 年-月(yyyy-MM)
     */
    public static final String YEAR_MONTH = "yyyy-MM";

    /**
     * 年-月-日(yyyy-MM-dd)
     */
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";

    /**
     * 年月日(yyyyMMdd)
     */
    public static final String YEAR_MONTH_DAY_SIMPLE = "yyyyMMdd";

    /**
     * 年-月-日 小时(yyyy-MM-dd HH)
     */
    public static final String YEAR_MONTH_DAY_HOUR = "yyyy-MM-dd HH";

    /**
     * 年-月-日 小时(yyyy-MM-dd HH)中文输出
     */
    public static final String YEAR_MONTH_DAY_HOUR_CN = "yyyy年MM月dd日HH时";

    /**
     * 年-月-日 小时:分钟(yyyy-MM-dd HH:mm)
     */
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";

    /**
     * 年-月-日 小时:分钟:秒钟(yyyy-MM-dd HH:mm:ss)
     */
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日小时分钟秒钟(yyyyMMddHHmmss)
     */
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SIMPLE = "yyyyMMddHHmmss";

    /**
     * 小时:分钟:秒钟(HH:mm:ss)
     */
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";

    /**
     * 小时:分钟(HH:mm)
     */
    public static final String HOUR_MINUTE = "HH:mm";

    /**
     * 月.日(M.d)
     */
    public static final String MONTH_DAY = "M.d";

    /**
     * 一天的秒数
     */
    private static final int DAY_SECOND = 24 * 60 * 60;

    /**
     * 一小时的秒数
     */
    private static final int HOUR_SECOND = 60 * 60;

    /**
     * 一分钟的秒数
     */
    private static final int MINUTE_SECOND = 60;
    
	/**
	 * 日期格式 MM月dd日
	 */
	public static final String SIMPLE_DATE_FORMAT = "MM月dd日";
	/**
	 * 日期格式 yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * 日期时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATETIME_FORMAT1 = "yyyy-MM-dd HH:mm:ss";

	private static final String BLANK = " ";

	/**
	 * 日期时间格式 yyyy-MM-dd HH:mm
	 */
	public static final String DEFAULT_DATETIME_FORMAT2 = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_DATETIME_FORMAT3 = "yyyy/MM/dd HH:mm:ss";
	public static final String DEFAULT_DATETIME_FORMAT7 = "yyyy/MM/dd";
	/**
	 * 日期时间格式 yyyy-MM-dd HH24:mm:ss
	 */
	public static final String DEFAULT_DATETIME_FORMAT4 = "yyyy-MM-dd HH24:mm:ss";
	/**
	 * 日期时间格式 yyyy-MM-dd HH24:mm
	 */
	public static final String DEFAULT_DATETIME_FORMAT5 = "yyyy-MM-dd HH24:mm";
	/**
	 * oracle 字符串转日期格式
	 */
	public static final String DEFAULT_DATETIME_FORMAT6 = "YYYY-MM-DD:HH24:MI:SS";
	/**
	 * 时间格式 HH:mm:ss
	 */
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	public static final String OTHER_TIME_FORMAT = "HH:mm";

	/**
	 * 每天小时数
	 */
	private static final long HOURS_PER_DAY = 24;
	/**
	 * 每小时分钟数
	 */
	private static final long MINUTES_PER_HOUR = 60;
	/**
	 * 每分钟秒数
	 */
	private static final long SECONDS_PER_MINUTE = 60;
	/**
	 * 每秒的毫秒数
	 */
	private static final long MILLIONSECONDS_PER_SECOND = 1000;
	/**
	 * 每分钟毫秒数
	 */
	private static final long MILLIONSECONDS_PER_MINUTE = MILLIONSECONDS_PER_SECOND * SECONDS_PER_MINUTE;
	/**
	 * 每天毫秒数
	 */
	private static final long MILLIONSECONDS_SECOND_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE
			* MILLIONSECONDS_PER_SECOND;

	public static TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static enum SearchDateBuff {
		SEARCH_BEGIN_TIME("00:00:00"), SEARCH_END_TIME("23:59:59");
		private String buff;

		SearchDateBuff(String buff) {
			this.buff = buff;
		}

		@Override
		public String toString() {
			return buff;
		}
	}

	private DateUtils() {
	}

	/**
	 * 将yyyy-MM-dd格式的字符串转换为日期对象
	 *
	 * @param date
	 *            待转换字符串
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 */
	public static Date getDate(String date) {
		return getDate(date, DEFAULT_DATE_FORMAT, null);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的字符串转换为日期对象
	 *
	 * @param date
	 *            待转换字符串
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 */
	public static Date getDateTime(String date) {
		if (!StringDefaultValue.isEmpty(date)) {
			date = date.replaceAll("/", "-");
			return getDate(date, DEFAULT_DATETIME_FORMAT1, null);
		}
		else {
			return null;
		}

	}

	/**
	 * 将指定格式的字符串转换为日期对象
	 *
	 * @param date
	 *            待转换字符串
	 * @param format
	 *            日期格式
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 */
	public static Date getDate(String date, String format) {
		return getDate(date, format, null);
	}

	/**
	 * 将指定格式的字符串转换为日期对象
	 *
	 * @param date
	 *            日期对象
	 * @param format
	 *            日期格式
	 * @param defVal
	 *            转换失败时的默认返回值
	 * @return 转换后的日期对象
	 */
	public static Date getDate(String date, String format, Date defVal) {
		Date d;
		try {
			d = new SimpleDateFormat(format).parse(date);
		}
		catch (ParseException e) {
			d = defVal;
		}
		return d;
	}

	/**
	 * 将日期对象格式化成yyyy-MM-dd格式的字符串
	 *
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATE_FORMAT, null);
	}

	/**
	 * 将日期对象格式化成yyyy-MM-dd HH:mm:ss格式的字符串
	 *
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 */
	public static String forDatetime(Date date) {
		if (date != null) {
			return formatDate(date, DEFAULT_DATETIME_FORMAT1, null);
		}
		else {
			return null;
		}

	}

	/**
	 * 将日期对象格式化成HH:mm:ss格式的字符串
	 *
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 */
	public static String formatTime(Date date) {
		return formatDate(date, DEFAULT_TIME_FORMAT, null);
	}

	/**
	 * 将日期对象格式化成HH:mm:ss格式的字符串
	 *
	 * @param date
	 *            待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 */
	public static String formatTime(Date date, String format) {
		return formatDate(date, format, null);
	}

	/**
	 * 将日期对象格式化成指定类型的字符串
	 *
	 * @param date
	 *            待格式化日期对象
	 * @param format
	 *            格式化格式
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 */
	public static String formatDate(Date date, String format) {
		return formatDate(date, format, null);
	}

	/**
	 * 带时区的格式化时间
	 *
	 * @param date
	 * @param format
	 * @param timeZone
	 * @return
	 */
	public static String formatDateTimeZone(Date date, String format, TimeZone timeZone) {
		String ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setTimeZone(timeZone);
			ret = sdf.format(date);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 将日期对象格式化成指定类型的字符串
	 *
	 * @param date
	 *            待格式化日期对象
	 * @param format
	 *            格式化格式
	 * @param defVal
	 *            格式化失败时的默认返回空
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date, String format, String defVal) {
		String ret;
		try {
			ret = new SimpleDateFormat(format).format(date);
		}
		catch (Exception e) {
			ret = defVal;
		}
		return ret;
	}

	/**
	 * 日期加减天数 返回指定日期加上days天后的日期
	 *
	 * @param date
	 *            如果为空则默认为当前时间
	 * @param days
	 *            为正数：加天，负数：减天
	 * @return
	 */
	public static Date plusDays(Date date, int days) {
		if (date == null) date = getToday();
		return changeDays(date, days);
	}

	/**
	 * 日期加减小时 返回指定日期加减上小时后的日期
	 *
	 * @param date
	 *            如果为空则默认为当前时间
	 * @param hours
	 *            为正数：加小时，负数：减小时
	 * @return
	 */
	public static Date plusHours(Date date, int hours) {
		if (date == null) date = getToday();
		return changeHours(date, hours);
	}

	/**
	 * 分钟加减 返回指定日期加减上分钟后的日期
	 *
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date plusMinute(Date date, int minutes) {
		if (date == null) date = getToday();
		return changeMinute(date, minutes);
	}

	/**
	 * 月份加减 返回指定日期加减上月数后的日期
	 *
	 * @param date
	 * @return
	 */
	public static Date plusMonth(Date date, int months) {
		if (date == null) date = getToday();
		return changeMonth(date, months);
	}

	/**
	 * 日期加减年数 返回指定日期加减上年数后的日期
	 *
	 * @param date
	 *            如果为空则默认为当前时间
	 * @return
	 */
	public static Date plusYear(Date date, int years) {
		if (date == null) date = getToday();
		return changeYear(date, years);
	}

	/**
	 * 获取当前日期加时间
	 *
	 * @return
	 */
	public static Date getToday() {
		return new Date();
	}

	/**
	 * 当前时间的毫秒数
	 *
	 * @return
	 */
	public static long currentTimeMillis() {
		return getToday().getTime();
	}

	/**
	 * 获得当前时间sql.date
	 */
	public static java.sql.Date getTodaySqlDate() {
		return new java.sql.Date(getToday().getTime());
	}

	/**
	 * @param date
	 *            为空：默认当前时间
	 * @param format
	 *            ：为空：默认yyyy-MM-dd
	 * @return
	 */
	public static String getTodayStr(Date date, String format) {
		if (date == null) date = getToday();
		if (StringDefaultValue.isEmpty(format)) format = DEFAULT_DATE_FORMAT;
		return formatDate(date, format);
	}

	/**
	 * 比较两个日期相差的天数 D1-D2
	 *
	 * @param d1
	 *            为空：默认为当前时间
	 * @param d2
	 * @return
	 */
	public static int intervalDay(Date d1, Date d2) {
		if (d1 == null) d1 = getToday();
		long intervalMillSecond = setToDayStartTime(d1).getTime() - setToDayStartTime(d2).getTime();
		// 相差的天数 = 相差的毫秒数 / 每天的毫秒数 (小数位采用去尾制)
		return (int) (intervalMillSecond / MILLIONSECONDS_SECOND_PER_DAY);
	}

	/**
	 * 获得两个日期之间相差的分钟数。（date1 - date2）
	 *
	 * @param date1
	 * @param date2
	 * @return 返回两个日期之间相差的分钟数值
	 */
	public static int intervalMinutes(Date date1, Date date2) {
		long intervalMillSecond = date1.getTime() - date2.getTime();

		// 相差的分钟数 = 相差的毫秒数 / 每分钟的毫秒数 (小数位采用进位制处理，即大于0则加1)
		return (int) (intervalMillSecond / MILLIONSECONDS_PER_MINUTE
				+ (intervalMillSecond % MILLIONSECONDS_PER_MINUTE > 0 ? 1 : 0));
	}

	/**
	 * 获得两个日期之间相差的秒数差（date1 - date2）
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int intervalSeconds(Date date1, Date date2) {
		long intervalMillSecond = date1.getTime() - date2.getTime();

		return (int) (intervalMillSecond / MILLIONSECONDS_PER_SECOND
				+ (intervalMillSecond % MILLIONSECONDS_PER_SECOND > 0 ? 1 : 0));
	}

	/**
	 * 将时间调整到当天0:0:0
	 *
	 * @param date
	 * @return
	 */
	public static Date setToDayStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 判断当前时间
	 *
	 * @return
	 */
	public static String getDateStatus() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour >= 6 && hour < 12) {
			return "morning";
		}
		else if (hour >= 12 && hour < 18) {
			return "noon";
		}
		else if (hour >= 18 && hour < 24) {
			return "evning";
		}
		else {
			return "midnight";
		}
	}

	public static int getAge(Date birthday) {
		Calendar now = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthday);
		// 取得生日年份
		int year = birth.get(Calendar.YEAR);
		// 年龄
		int age = now.get(Calendar.YEAR) - year;
		// 修正
		now.set(Calendar.YEAR, year);
		age = (now.before(birth)) ? age - 1 : age;
		return age;
	}

	/**
	 * d1 和 d2 是否同一天
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDate(Date d1, Date d2) {
		if (d1 == null || d2 == null) return false;
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(d1.getTime());
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(d2.getTime());

		return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 判断是否d2是d1的后一天
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isContinueDay(Date d1, Date d2) {
		if (d1 == null || d2 == null) return false;
		if (intervalDay(d1, d2) == 1) return true;
		return false;
	}

	/**
	 * 得到没有时间的日期
	 *
	 * @param date
	 * @return
	 */
	public static Date truncDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 得到没有分和秒的时间
	 *
	 * @param date
	 * @return
	 */
	public static Date truncDateHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 得到旬.
	 *
	 * @param input
	 * @return
	 * @author <a href="mailto:wangxin@knet.cn">北京王欣</a>
	 */
	public static String getCnDecade(Date input) {
		String day = formatDate(input);
		String decade = day.replaceAll("01日", "上旬").replaceAll("11日", "中旬").replaceAll("21日", "下旬");
		return decade;
	}

	public static Date getTodayZero() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static Date getTheDayBefore(Date date) {
		return new Date(date.getTime() - (long) 24 * (long) 60 * 60 * 1000);
	}

	public static Date[] getTenDayBefore() {// 计算之前一旬的起止时间
		Date[] ret = new Date[2];
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);// 0点0分0秒
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {// 今天处在某月的上旬，起始时间是前一个月的21号，终止时间是本月的1号
			c.set(Calendar.DAY_OF_MONTH, 1);// 本月的1号
			ret[1] = new Date(c.getTime().getTime());
			c.setTime(getTheDayBefore(c.getTime()));// 往前翻一天，到上一个月
			c.set(Calendar.DAY_OF_MONTH, 21);
			ret[0] = new Date(c.getTime().getTime());
		}
		else {//

			if (10 < day && day <= 20) {// 今天处在某月的中旬，起始时间是本月的1号，终止时间是本月的11号
				c.set(Calendar.DAY_OF_MONTH, 1);
				ret[0] = new Date(c.getTime().getTime());
				c.set(Calendar.DAY_OF_MONTH, 11);
				ret[1] = new Date(c.getTime().getTime());
			}
			else {// 今天处在某月的下旬，起始时间是本月的11号，终止时间是本月的21号
				c.set(Calendar.DAY_OF_MONTH, 11);
				ret[0] = new Date(c.getTime().getTime());
				c.set(Calendar.DAY_OF_MONTH, 21);
				ret[1] = new Date(c.getTime().getTime());
			}
		}
		return ret;
	}

	/**
	 * 计算某个输入时间的当前旬起止时间
	 *
	 * @param input
	 * @return
	 */
	public static Date[] getCurrentTenDay(Date input) {// 计算某个输入时间的当前旬起止时间
		Date[] ret = new Date[2];
		Calendar c = Calendar.getInstance();
		c.setTime(input);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);// 0点0分0秒
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {// 今天处在某月的上旬，起始时间是本月的1号，终止时间是本月的11号
			c.set(Calendar.DAY_OF_MONTH, 1);// 本月的1号
			ret[0] = new Date(c.getTime().getTime());
			c.set(Calendar.DAY_OF_MONTH, 11);
			ret[1] = new Date(c.getTime().getTime());
		}
		else {//

			if (10 < day && day <= 20) {// 今天处在某月的中旬，起始时间是本月的11号，终止时间是本月的21号
				c.set(Calendar.DAY_OF_MONTH, 11);
				ret[0] = new Date(c.getTime().getTime());
				c.set(Calendar.DAY_OF_MONTH, 21);
				ret[1] = new Date(c.getTime().getTime());
			}
			else {// 今天处在某月的下旬，起始时间是本月的21号，终止时间是下个月的1号
				c.set(Calendar.DAY_OF_MONTH, 21);
				ret[0] = new Date(c.getTime().getTime());
				ret[1] = getNextMonthFirst(c.getTime());
			}
		}
		return ret;
	}

	public static Date getNextMonthFirst(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);// 0点0分0秒
		c.add(Calendar.MONTH, 1);// 加一个月
		c.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		return c.getTime();
	}

	public static Date[] getTheMonthBefore(Date date) {// 计算之前一旬的起止时间
		Date[] ret = new Date[2];
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);// 0点0分0秒
		c.set(Calendar.DAY_OF_MONTH, 1);// 本月的1号
		ret[1] = new Date(c.getTime().getTime());
		c.setTime(getTheDayBefore(c.getTime()));// 往前翻一天，到上一个月
		c.set(Calendar.DAY_OF_MONTH, 1);// 上月的1号
		ret[0] = new Date(c.getTime().getTime());
		return ret;
	}

	/**
	 * 获取当前季度 getCurrentQuarter
	 *
	 * @return Integer
	 * @throws @since
	 *             1.0.0
	 */
	public static Integer getCurrentQuarter() {
		int month = Integer.parseInt(DateUtils.formatDate(new Date(), "MM"));
		int quarter = 0;
		if (month >= 1 && month <= 3) {
			quarter = 1;
		}
		else if (month >= 4 && month <= 6) {
			quarter = 2;
		}
		else if (month >= 7 && month <= 9) {
			quarter = 3;
		}
		else if (month >= 10 && month <= 12) {
			quarter = 4;
		}
		return quarter;
	}

	/**
	 * QuarterToYearMonthDay
	 *
	 * @param year
	 * @param quarter
	 * @return Map<String,String>
	 * @throws @since
	 *             1.0.0
	 */
	public static Map<String, String> getQuarterToYearMonthDay(Integer year, Integer quarter) {
		if (year != null && year > 0 && quarter != null && quarter > 0) {
			Map<String, String> map = new HashMap<String, String>();
			if (quarter == 1) {
				map.put("startTime", year + "-01-" + getMonthDays(year, 1) + " 00:00:00");
				map.put("endTime", year + "-03-" + getMonthDays(year, 3) + " 23:59:59");
			}
			else if (quarter == 2) {
				map.put("startTime", year + "-04-" + getMonthDays(year, 4) + " 00:00:00");
				map.put("endTime", year + "-06-" + getMonthDays(year, 6) + " 23:59:59");
			}
			else if (quarter == 3) {
				map.put("startTime", year + "-07-" + getMonthDays(year, 7) + " 00:00:00");
				map.put("endTime", year + "-09-" + getMonthDays(year, 9) + " 23:59:59");
			}
			else if (quarter == 4) {
				map.put("startTime", year + "-10-" + getMonthDays(year, 10) + " 00:00:00");
				map.put("endTime", year + "-12-" + getMonthDays(year, 12) + " 23:59:59");
			}
			return map;
		}
		return null;
	}

	/**
	 * 根据指定年月，获取月的天数 getMonthDays
	 *
	 * @param year
	 * @return Integer
	 * @throws @since
	 *             1.0.0
	 */
	public static Integer getMonthDays(Integer year, Integer month) {
		if (year != null && year > 0 && month != null && month > 0) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month);
			c.set(Calendar.DATE, 1);
			c.add(Calendar.DATE, -1);
			return c.get(Calendar.DATE);
		}
		return 0;
	}

	/**
	 * 计算两个时间点的差，以文本形式返回（如：1分钟前，2小时前，3天前），两个参数可以自动识别出较早的和较晚的
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String getTimeDiffText(Date date1, Date date2) {
		long diff = Math.abs(date1.getTime() - date2.getTime()) / 1000;
		long minuteSeconds = 60;
		long hourSeconds = minuteSeconds * 60;
		long daySeconds = hourSeconds * 24;
		long weekSeconds = daySeconds * 7;
		Date min = date1.compareTo(date2) < 0 ? date1 : date2;
		// 时间差超过一周时，返回具体日期即可
// long monthSeconds = daySeconds * 30;
// long yearSeconds = monthSeconds * 12;
// if (diff >= yearSeconds) {
// return diff / yearSeconds + "年前";
// } else if (diff >= monthSeconds) {
// return diff / monthSeconds + "月前";
		if (diff >= weekSeconds) {
			return formatDate(min);
		}
		else if (diff >= daySeconds) {
			return diff / daySeconds + "天前";
		}
		else if (diff >= hourSeconds) {
			return diff / hourSeconds + "小时前";
		}
		else if (diff >= minuteSeconds) {
			return diff / minuteSeconds + "分钟前";
		}
		else {
			return diff + "秒前";
		}
	}

	/**
	 * 获取当前时间是星期几
	 *
	 * @param dt
	 * @return
	 */
	public static int getWeek(Date dt) {

		int[] week = { 7, 1, 2, 3, 4, 5, 6 };
		// String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
		// "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) w = 0;
		return week[w];
	}

	/**
	 * 获取当前日期
	 *
	 * @return
	 */
	public static Date getCurrentDate(String datePattern) {

		try {
			return new SimpleDateFormat(datePattern).parse(getCurrentDateByString(datePattern));
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 以字符串形式获取当前日期
	 *
	 * @return
	 */
	public static String getCurrentDateByString(String datePattern) {

		return new SimpleDateFormat(datePattern).format(System.currentTimeMillis());
	}

	public static String getCurrentDateByString(Date date, String datePattern) {

		return new SimpleDateFormat(datePattern).format(date);
	}

	public static boolean beforeDate(Date date1, Date date2) {

		return date1.before(date2);
	}

	public static boolean beforeDate(String date1, String date2) {

		Date dt1 = null, dt2 = null;
		dt1 = getDateTime(date1);
		dt2 = getDateTime(date2);
		return beforeDate(dt1, dt2);
	}

	/**
	 * 比较时间是否在一个指定时间范围之内
	 *
	 * @param date
	 * @param from
	 * @param end
	 * @return
	 */
	public static boolean betweenDateScope(String date, String from, String end) {

		if (date == null || from == null || end == null) return false;
		return (!beforeDate(date, from) && beforeDate(date, end));
	}

	/**
	 * 判断时间范围
	 *
	 * @param time
	 * @param startRange
	 * @param endRange
	 * @return
	 */
	public static boolean checkTimeRange(String time, String startRange, String endRange) {

		String[] s = startRange.split(":");
		int totalStart = (Integer.parseInt(s[0]) * 3600) + (Integer.parseInt(s[1]) * 60) + Integer.parseInt(s[2]);
		String[] e = endRange.split(":");
		int totalEnd = (Integer.parseInt(e[0]) * 3600) + (Integer.parseInt(e[1]) * 60) + Integer.parseInt(e[2]);

		String[] t = time.split(":");
		int timeTotal = (Integer.parseInt(t[0]) * 3600) + (Integer.parseInt(t[1]) * 60) + Integer.parseInt(t[2]);
		return (timeTotal >= totalStart && timeTotal <= totalEnd);
	}

	/**
	 * 指定日期时间分钟上加上分钟数
	 *
	 * @param date
	 * @param minutes
	 * @return
	 */
	private static Date changeMinute(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	/**
	 * 指定日期时间上加上时间数
	 *
	 * @param date
	 * @param hours
	 * @return
	 */
	private static Date changeHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}

	/**
	 * 指定的日期加减天数
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	private static Date changeDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	/**
	 * 指定的日期加减年数
	 *
	 * @param date
	 * @param years
	 * @return
	 */
	private static Date changeYear(Date date, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	/**
	 * 指定的日期加减月数
	 *
	 * @param date
	 * @return
	 */
	private static Date changeMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 获取当前日期的开始时间 2015-1-25 00:00:00
	 * 
	 * @return
	 */
	public static Date getCurrentDayBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当天结束时间 2015-1-25 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentDayEnd() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获取昨天日期的开始时间 2015-1-24 00:00:00
	 * 
	 * @return
	 */
	public static Date getLastDayBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, -1);// 前一天
		return calendar.getTime();
	}

	/**
	 * 获取昨天日期的结束时间 2015-1-24 23:59:59
	 * 
	 * @return
	 */
	public static Date getLastDayEnd() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.add(Calendar.DATE, -1);// 前一天
		return calendar.getTime();
	}

	/**
	 * 获取前天的开始时间 2015-1-24 00:00:00
	 * 
	 * @return
	 */
	public static Date getBeforeYesterdayBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, -2);// 前一天
		return calendar.getTime();
	}

	/**
	 * 获取前天的结束时间 2015-1-24 23:59:59
	 * 
	 * @return
	 */
	public static Date BeforeYesterdayEnd() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.add(Calendar.DATE, -2);// 前一天
		return calendar.getTime();
	}

	/**
	 * 获取当前月的第一天的开始时间 2014-12-01 00:00:00
	 * 
	 * @return
	 */
	public static Date getCurrentMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取前30天的开始时间 2014-12-24 00:00:00
	 * 
	 * @return
	 */
	public static Date getLastMonthDayBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, -30);// 前30天
		return calendar.getTime();
	}

	public static Date getBeginTime() {// 5年内
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.YEAR, -5);// 默认是当前系统5年
		return calendar.getTime();
	}

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * @Title: compareDate @Description: TODO(日期比较，如果s>=e 返回true
	 * 否则返回false) @param s @param e @return boolean @throws
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		}
		catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		}
		catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	public static String getSearchBeginDate(String date, SearchDateBuff buff) {
		StringBuilder builder = new StringBuilder(date);
		builder.append(BLANK).append(buff.toString());
		return builder.toString();
	}

	/**
	 * 获取以毫秒为单位的当前时间。保留10位
	 * 
	 * @return
	 */
	public static int getCurrentTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 毫秒数得到相应的天数
	 * @param millis
	 * @return
	 */
	public static String fromMillisToTime(long millis) {
		StringBuilder builder = new StringBuilder();
		long days = millis / (1000 * 60 * 60 * 24);
		long hours = (millis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (millis % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (millis % (1000 * 60)) / 1000;
		long mi = (millis % 1000);
		return builder.append(days).append("天/").append(hours).append("小时/").append(minutes).append("分钟/")
				.append(seconds).append("秒/").append(mi).append("毫秒").toString();
	}
	

    /**
     * 根据秒数获得x天x小时x分钟x秒字符串
     *
     * @param second 秒数
     * @return x天x小时x分钟x秒字符串
     */
    public static String getDayHourMinuteSecond(int second) {
        if (second == 0) {
            return "0秒";
        }
        StringBuilder sb = new StringBuilder();
        int days = second / DAY_SECOND;
        if (days > 0) {
            sb.append(days);
            sb.append("天");
            second -= days * DAY_SECOND;
        }

        int hours = second / HOUR_SECOND;
        if (hours > 0) {
            sb.append(hours);
            sb.append("小时");
            second -= hours * HOUR_SECOND;
        }

        int minutes = second / MINUTE_SECOND;
        if (minutes > 0) {
            sb.append(minutes);
            sb.append("分钟");
            second -= minutes * MINUTE_SECOND;
        }
        if (second > 0) {
            sb.append(second);
            sb.append("秒");
        }
        return sb.toString();
    }


    /**
     * 根据秒数获得x天x小时x分钟字符串
     *
     * @param second 秒数
     * @return x天x小时x分钟字符串
     */
    public static String getDayHourMinute(int second) {
        if (second == 0) {
            return "0分钟";
        }
        StringBuilder sb = new StringBuilder();
        int days = second / DAY_SECOND;
        if (days > 0) {
            sb.append(days);
            sb.append("天");
            second -= days * DAY_SECOND;
        }

        int hours = second / HOUR_SECOND;
        if (hours > 0) {
            sb.append(hours);
            sb.append("小时");
            second -= hours * HOUR_SECOND;
        }
        int minutes = second / MINUTE_SECOND;
        if (minutes > 0) {
            sb.append(minutes);
            sb.append("分钟");
        }
        return sb.toString();
    }


    /**
     * 比较两个时间是否相等（省略毫秒）
     *
     * @param date        Date对象
     * @param compareDate 比较Date对象
     * @return 是否相等
     */
    public static boolean compareDateIgnoreMillisecond(Date date, Date compareDate) {
        if (date == null && compareDate == null) {
            return true;
        } else if (date == null && compareDate != null) {
            return false;
        } else if (date != null && compareDate == null) {
            return false;
        }

        return (date.getTime() / 1000 == compareDate.getTime() / 1000);
    }

    /**
     * 根据秒数获取天数
     *
     * @param second 秒数
     * @return 天数
     */
    public static int getDay(int second) {
        return second / DAY_SECOND;
    }

    
    /**
     * 比较两个时间相差分钟数
     *
     * @param now         当前时间
     * @param compareDate 比较时间
     * @return 相差分钟数
     */
    public static int compareMinutes(Date now, Date compareDate) {
        return (int) (now.getTime() - compareDate.getTime()) / 60000;
    }

    
    /**
     * 判断2个时间的时间差 返回字符串形式
     *
     * @param date  要对比的字符串
     * @param date2 要对比的字符串
     * @return 字符串形式 如1小时 ，2天2小时
     */
    public static String compareDaysWithDate(Date date, Date date2) {
        StringBuilder msg = new StringBuilder();
        int minutes = (int) Math.abs((date.getTime() - date2.getTime()) / 60000);
        if (minutes / 60 > 0 && minutes / 60 / 24 <= 0) {
            msg.append(minutes / 60 + "小时");
        }
        if (minutes / 60 / 24 > 0) {
            msg.append(minutes / 60 / 24 + "天");
            msg.append(minutes / 60 % 24 + "小时");
        }
        return msg.toString();
    }

    /**
     * 时间字符串.
     */
    public static final String REG_EXP_DATE = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    
}
