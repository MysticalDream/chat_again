package client.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 日期工具
 * 
 * @author MysticalDream
 */
public final class DateUtil {
	/**
	 * 用于转换的日期模式,可以更改
	 */
	private static String DATE_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";
	/**
	 * 日期格式
	 */
	private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

	/**
	 * 不允许实例化
	 */
	private DateUtil() {

	}

	/**
	 * 根据需要更改匹配模式
	 * 
	 * @param datePattern
	 */
	public static void setDatePattern(String datePattern) {
		DATE_PATTERN = datePattern;
		DATE_FORMATTER = DateTimeFormatter.ofPattern(datePattern);
	}

	/**
	 * 根据模式返回字符串
	 * 
	 * @param LocalDate 日期
	 * @return 格式化的日期
	 */
	public static String format(LocalDate date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}

	/**
	 * 根据模式返回字符串
	 * 
	 * @param date
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月12日 上午10:13:15
	 */
	public static String format(LocalDateTime date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}

	/**
	 * 将模板日期字符串转化为LocalDate对象
	 * 
	 * 如果传入不满足模式的字符串将会返回null
	 * 
	 * @param 日期字符串
	 * @return 日期对象,如果无法转换,则返回null
	 */
	public static LocalDateTime parse(String dateString) {
		try {
			return DATE_FORMATTER.parse(dateString, LocalDateTime::from);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**
	 * 检查字符串是否是有效日期。
	 * 
	 * @param 日期字符串
	 * @return 检查字符串是否是有效日期,是则返回{@code true},否则返回{@code false};
	 */
	public static boolean validDate(String dateString) {
		return DateUtil.parse(dateString) != null;
	}

	/**
	 * 根据提供的网址获取网络时间
	 * 
	 * @param url 要获取时间的网址
	 * @return 返回模式{@link DATE_PATTERN}的字符串 异常则返回null <b>Author:</b>
	 *         <a href="https://github.com/MysticalDream" target=
	 *         "_blank">MysticalDream</a> <b>Date:</b> 2021年5月26日 上午9:30:35
	 */
	public static String getWebsiteTime(String website) {
		try {
			URL url = new URL(website);
			URLConnection con = url.openConnection();
			con.connect();
			long timeStamp = 0;
			while (timeStamp == 0) {
				timeStamp = con.getDate();
			}
			Instant instant = Instant.ofEpochMilli(timeStamp);
			LocalDateTime localTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			return localTime.format(DATE_FORMATTER);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param website
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月12日 上午10:15:02
	 */
	public static LocalDateTime getLocalDateTimeNow(String website) {

		URLConnection con;
		try {
			URL url = new URL(website);
			con = url.openConnection();
			con.connect();
			long timeStamp = 0;
			while (timeStamp == 0) {
				timeStamp = con.getDate();
			}
			Instant instant = Instant.ofEpochMilli(timeStamp);
			return instant.atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取LocalDate
	 * 
	 * @param url
	 * @return <b>Author:</b> <a href="https://github.com/MysticalDream" target=
	 *         "_blank">MysticalDream</a> <b>Date:</b> 2021年5月29日 下午7:44:01
	 */
	public static LocalDate getLocalDateNow(String website) {

		URLConnection con;
		try {
			URL url = new URL(website);
			con = url.openConnection();
			con.connect();
			long timeStamp = 0;
			while (timeStamp == 0) {
				timeStamp = con.getDate();
			}
			Instant instant = Instant.ofEpochMilli(timeStamp);
			return instant.atZone(ZoneOffset.ofHours(8)).toLocalDate();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
