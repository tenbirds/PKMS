package com.wings.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final SimpleDateFormat STRING_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat HOUR24_FORMAT = new SimpleDateFormat("yyyyMMddHH");

	private static final SimpleDateFormat MINUITE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

	private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMdd");

	protected DateUtil() {
	}

	/**
	 * date의 시간을 'yyyy-MM-dd' 형태로 리턴한다.<br>
	 * 
	 * @return
	 */
	public static String dateFormat() {
		return dateFormat(new Date());
	}

	/**
	 * date의 시간을 'yyyy-MM-dd' 형태로 리턴한다.<br>
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		return DATE_FORMAT.format(date);
	}

	public static String format() {
		return format(new Date());
	}

	public static String stringFormat() {
		return STRING_FORMAT.format(new Date());
	}

	public static String format(Date date) {
		return FORMAT.format(date);
	}

	/**
	 * date의 시간을 'yyyyMMddHH24MI' 형태로 리턴한다.<br>
	 * 
	 * @param date
	 * @return
	 */

	public static String hourlyformat(Date date) {
		return HOUR24_FORMAT.format(date);
	}

	public static String minuteformat(Date date) {
		return MINUITE_FORMAT.format(date);
	}

	public static String dailyformat(Date date) {
		return DAY_FORMAT.format(date);
	}

	/**
	 * 
	 * 시작과 끝 날짜의 기간을 리턴 한다.<br>
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDateTerm(Date startDate, Date endDate) {
		long start = startDate.getTime();
		long end = endDate.getTime();
		if ((end - start) < 0) {
			return -1;
		}
		return (end - start) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 'yyyy-MM-dd HH:mm:ss'을 Date로 변환한다.<br>
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parse(String datetime) {
		try {
			return FORMAT.parse(datetime);
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * date의 시간을 'HH:mm:ss' 형태로 리턴한다.<br>
	 * 
	 * @return
	 */
	public static String timeFormat() {
		return timeFormat(new Date());
	}

	/**
	 * date의 시간을 'HH:mm:ss' 형태로 리턴한다.<br>
	 * 
	 * @param date
	 * @return
	 */
	public static String timeFormat(Date date) {
		return TIME_FORMAT.format(date);
	}

	/**
	 * datetime에서 숫자만큼 년도를 더한 datetime을 리턴한다.<br>
	 * 
	 * @param datetime
	 * @param year
	 * @return
	 */
	public static String formatByYear(String datetime, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		cal.add(Calendar.YEAR, year);
		return format(cal.getTime());
	}

	/**
	 * datetime에서 숫자만큼 월을 더한 datetime을 리턴한다.<br>
	 * 
	 * @param datetime
	 * @param year
	 * @return
	 */
	public static String formatByMonth(String datetime, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		cal.add(Calendar.MONTH, month);
		return format(cal.getTime());
	}

	public static String formatDateByMonth(String datetime, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		cal.add(Calendar.MONTH, month);
		return dateFormat(cal.getTime());
	}

	/**
	 * datetime에서 숫자만큼 날짜를 더한 datetime을 리턴한다.<br>
	 * 
	 * @param datetime
	 * @param year
	 * @return
	 */
	public static String formatByDate(String datetime, int date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		cal.add(Calendar.DAY_OF_YEAR, date);
		return format(cal.getTime());
	}

	public static String formatDateByDay(String datetime, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		cal.add(Calendar.DAY_OF_YEAR, day);
		return dateFormat(cal.getTime());
	}

	/**
	 * datetime에서 숫자만큼 시간을 더한 datetime을 리턴한다.<br>
	 * 
	 * @param datetime
	 * @param year
	 * @return
	 */
	public static String formatByHour(String datetime, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return format(cal.getTime());
	}

	/**
	 * datetime에서 숫자만큼 분을 더한 datetime을 리턴한다.<br>
	 * 
	 * @param datetime
	 * @param year
	 * @return
	 */
	public static String formatByMinute(String datetime, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		cal.add(Calendar.MINUTE, minute);
		return format(cal.getTime());
	}

	/**
	 * datetime에서 숫자만큼 초를 더한 datetime을 리턴한다.<br>
	 * 
	 * @param datetime
	 * @param year
	 * @return
	 */
	public static String formatBySecond(String datetime, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		cal.add(Calendar.SECOND, second);
		return format(cal.getTime());
	}

	/**
	 * 해당년 해당월의 마지막 날짜 구한다.<br>
	 * 
	 * @param datetime
	 * @return
	 * @exception
	 */
	public static int getLastDayOfMonth(String datetime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		switch (month) {
		case 2:
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
				return (29); // 2월 윤년계산을 위해서
			else
				return (28);
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return (31);
		case 4:
		case 6:
		case 9:
		case 11:
			return (30);
		default:
			return (0);
		}
	}

	/**
	 * 그달의 주차를 구한다.<br>
	 * 
	 * @param datetime
	 * @return
	 * @exception
	 */
	public static int getWeekOfMonth(String datetime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));

		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 입력받은 날짜의 요일을 가져온다. 일요일(1), 월요일(2), 화요일(3) ~~ 토요일(7)<br>
	 * 
	 * @param datetime
	 * @return
	 * @exception
	 */
	public static int getDayOfWeek(String datetime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(datetime));

		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * <p>
	 * 현재 연도값을 리턴
	 * </p>
	 * 
	 * @return 년(年)
	 */
	public static int getCurrentYearAsInt() {

		Calendar cd = new GregorianCalendar(Locale.KOREA);

		return cd.get(Calendar.YEAR);
	}

	/**
	 * <p>
	 * 현재 월을 리턴
	 * </p>
	 * 
	 * @return 월(月)
	 */
	public static int getCurrentMonthAsInt() {

		Calendar cd = new GregorianCalendar(Locale.KOREA);

		return cd.get(Calendar.MONTH) + 1;
	}

	/**
	 * <p>
	 * 현재 일을 리턴
	 * </p>
	 * 
	 * @return 일(日)
	 */
	public static int getCurrentDayAsInt() {

		Calendar cd = new GregorianCalendar(Locale.KOREA);

		return cd.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <p>
	 * 현재 시간을 리턴
	 * </p>
	 * 
	 * @return 시(時)
	 */
	public static int getCurrentHourAsInt() {

		Calendar cd = new GregorianCalendar(Locale.KOREA);

		return cd.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * <p>
	 * 현재 분을 리턴
	 * </p>
	 * 
	 * @return 분(分)
	 */
	public static int getCurrentMinuteAsInt() {

		Calendar cd = new GregorianCalendar(Locale.KOREA);

		return cd.get(Calendar.MINUTE);
	}

	/**
	 * <p>
	 * 현재 초를 리턴
	 * </p>
	 * 
	 * @return 밀리초
	 */
	public static int getCurrentMilliSecondAsInt() {

		Calendar cd = new GregorianCalendar(Locale.KOREA);

		return cd.get(Calendar.MILLISECOND);
	}

	/**
	 * <p>
	 * 해당 년,월,일을 받아 요일을 리턴하는 메소드
	 * </p>
	 * 
	 * @param sYear
	 *            년도
	 * @param sMonth
	 *            월
	 * @param sDay
	 *            일
	 * @return 요일(한글)
	 */
	public static String getDayOfWeekAsString(String sYear, String sMonth, String sDay) {

		Calendar cd = new GregorianCalendar(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay));

		SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.KOREA); // "EEE"
																			// -
																			// Day
																			// in
																			// Week

		Date d1 = cd.getTime();

		return sdf.format(d1);
	}

	/**
	 * <p>
	 * 해당 년의 특정월의 일자를 구한다.
	 * </p>
	 * 
	 * @param year
	 *            년도4자리
	 * @param month
	 *            월 1자리 또는 2자리
	 * @return 특정월의 일자
	 */
	public static int getDayCountForMonth(int year, int month) {

		int[] DOMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; // 평년
		int[] lDOMonth = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; // 윤년

		if ((year % 4) == 0) {

			if ((year % 100) == 0 && (year % 400) != 0) {
				return DOMonth[month - 1];
			}

			return lDOMonth[month - 1];

		} else {
			return DOMonth[month - 1];
		}
	}

	// ****** 시작일자와 종료일자 사이의 일자를 구하는 메소드군 *******//

	/**
	 * <p>
	 * 8자리로된(yyyyMMdd) 시작일자와 종료일자 사이의 일수를 구함.
	 * </p>
	 * 
	 * @param from
	 *            8자리로된(yyyyMMdd)시작일자
	 * @param to
	 *            8자리로된(yyyyMMdd)종료일자
	 * @return 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일수 리턴
	 * @throws ParseException
	 *             형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
	 */
	public static int getDayCount(String from, String to) throws ParseException {

		return getDayCountWithFormatter(from, to, "yyyyMMdd");
	}

	/**
	 * <p>
	 * 해당 문자열이 "yyyyMMdd" 형식에 합당한지 여부를 판단하여 합당하면 Date 객체를 리턴한다.
	 * </p>
	 * 
	 * @param source
	 *            대상 문자열
	 * @return "yyyyMMdd" 형식에 맞는 Date 객체를 리턴한다.
	 * @throws ParseException
	 *             형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
	 */
	public static Date dateFormatCheck(String source) throws ParseException {

		return dateFormatCheck(source, "yyyyMMdd");
	}

	/**
	 * <p>
	 * 해당 문자열이 주어진 일자 형식을 준수하는지 여부를 검사한다.
	 * </p>
	 * 
	 * @param source
	 *            검사할 대상 문자열
	 * @param format
	 *            Date 형식의 표현. 예) "yyyy-MM-dd".
	 * @return 형식에 합당하는 경우 Date 객체를 리턴한다.
	 * @throws ParseException
	 *             형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
	 */
	public static Date dateFormatCheck(String source, String format) throws ParseException {

		if (source == null) {
			throw new ParseException("date string to check is null", 0);
		}

		if (format == null) {
			throw new ParseException("format string to check date is null", 0);
		}

		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);

		Date date = null;

		try {
			date = formatter.parse(source);
		} catch (ParseException e) {
			throw new ParseException(" wrong date:\"" + source + "\" with format \"" + format + "\"", 0);
		}

		if (!formatter.format(date).equals(source)) {
			throw new ParseException("Out of bound date:\"" + source + "\" with format \"" + format + "\"", 0);
		}

		return date;
	}

	/**
	 * <p>
	 * 정해진 일자 형식을 기반으로 시작일자와 종료일자 사이의 일자를 구한다.
	 * <p/>
	 * 
	 * @param from
	 *            시작 일자
	 * @param to
	 *            종료 일자
	 * @return 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일수를 리턴
	 * @throws ParseException
	 *             형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
	 * @see #getTimeCount(String, String, String)
	 */
	public static int getDayCountWithFormatter(String from, String to, String format) throws ParseException {

		long duration = getTimeCount(from, to, format);

		return (int) (duration / (1000 * 60 * 60 * 24));
	}

	/**
	 * <p>
	 * DATE 문자열을 이용한 format string을 생성
	 * </p>
	 * 
	 * @param date
	 *            Date 문자열
	 * @return Java.text.DateFormat 부분의 정규 표현 문자열
	 * @throws ParseException
	 *             형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
	 */
	protected static String getFormatStringWithDate(String date) throws ParseException {
		String format = null;

		if (date.length() == 4) {
			format = "HHmm";
		} else if (date.length() == 8) {
			format = "yyyyMMdd";
		} else if (date.length() == 12) {
			format = "yyyyMMddHHmm";
		} else if (date.length() == 14) {
			format = "yyyyMMddHHmmss";
		} else if (date.length() == 17) {
			format = "yyyyMMddHHmmssSSS";
		} else {
			throw new ParseException(" wrong date format!:\"" + format + "\"", 0);
		}

		return format;
	}

	/**
	 * <p>
	 * <code>yyyyMMddHHmmssSSS</code> 와 같은 Format 문자열 없이 시작 일자 시간, 끝 일자
	 * 시간을
	 * </p>
	 * 
	 * @param from
	 *            시작일자
	 * @param to
	 *            끝일자
	 * @return 두 일자 간의 차의 밀리초(long)값
	 * @throws ParseException
	 *             형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
	 * @see #getFormatStringWithDate(String)
	 */
	public static long getTimeCount(String from, String to) throws ParseException {

		String format = getFormatStringWithDate(from);

		return getTimeCount(from, to, format);
	}

	/**
	 * <p>
	 * 정해진 일자 형식을 기반으로 시작일자와 종료일자 사이의 일자를 구한다.
	 * <p/>
	 * 
	 * <pre>
	 * Symbol   Meaning                 Presentation        Example
	 * ------   -------                 ------------        -------
	 * G        era designator          (Text)              AD
	 * y        year                    (Number)            1996
	 * M        month in year           (Text & Number)     July & 07
	 * d        day in month            (Number)            10
	 * h        hour in am/pm (1~12)    w(Number)            12
	 * H        hour in day (0~23)      (Number)            0
	 * m        minute in hour          (Number)            30
	 * s        second in minute        (Number)            55
	 * S        millisecond             (Number)            978
	 * E        day in week             (Text)              Tuesday
	 * D        day in year             (Number)            189
	 * F        day of week in month    (Number)            2 (2nd Wed in July)
	 * w        week in year            (Number)            27
	 * W        week in month           (Number)            2
	 * a        am/pm marker            (Text)              PM
	 * k        hour in day (1~24)      (Number)            24
	 * K        hour in am/pm (0~11)    (Number)            0
	 * z        time zone               (Text)              Pacific Standard Time
	 * '        escape for text         (Delimiter)
	 * ''       single quote            (Literal)           '
	 * </pre>
	 * 
	 * @param from
	 *            시작 일자
	 * @param to
	 *            종료 일자
	 * @param format
	 * @return 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일수를 리턴
	 * @throws ParseException
	 *             형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
	 */
	public static long getTimeCount(String from, String to, String format) throws ParseException {

		Date d1 = dateFormatCheck(from, format);
		Date d2 = dateFormatCheck(to, format);

		long duration = d2.getTime() - d1.getTime();

		return duration;
	}

	/**
	 * <p>
	 * 시작일자와 종료일자 사이의 해당 요일이 몇번 있는지 계산한다.
	 * </p>
	 * 
	 * @param from
	 *            시작 일자
	 * @param to
	 *            종료 일자
	 * @param yoil
	 *            요일
	 * @return 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일자 리턴
	 * @throws ParseException
	 *             발생. 형식이 잘못 되었거나 존재하지 않는 날짜
	 */
	public static int getDayOfWeekCount(String from, String to, String yoil) throws ParseException {

		int first = 0; // from 날짜로 부터 며칠 후에 해당 요일인지에 대한 변수
		int count = 0; // 해당 요일이 반복된 횟수
		String[] sYoil = { "일", "월", "화", "수", "목", "금", "토" };

		// 두 일자 사이의 날 수
		int betweenDays = getDayCount(from, to);

		// 첫번째 일자에 대한 요일
		Calendar cd = new GregorianCalendar(Integer.parseInt(from.substring(0, 4)), Integer.parseInt(from.substring(4, 6)) - 1, Integer.parseInt(from.substring(6, 8)));
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);

		// 요일이 3자리이면 첫자리만 취한다.
		if (yoil.length() == 3) {
			yoil = yoil.substring(0, 1);

			// 첫번째 해당 요일을 찾는다.
		}

		// bug fixed 2009.03.23
		// while (!sYoil[dayOfWeek - 1].equals(yoil)) {
		while (!sYoil[(dayOfWeek - 1) % 7].equals(yoil)) {
			dayOfWeek += 1;
			first++;
		}

		if ((betweenDays - first) < 0) {

			return 0;

		} else {

			count++;

		}
		count += (betweenDays - first) / 7;

		return count;
	}

	
	//private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static String getBaseDate(String datetime) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (datetime == null) {
			cal.setTime(new Date());
		} else {
			cal.setTime(DATE_FORMAT.parse(datetime));
		}
		cal.add(Calendar.DAY_OF_YEAR, +(7 - (getDayOfWeek(datetime))));
		return DATE_FORMAT.format(cal.getTime());
	}
 
	public static int getDayOfWeekWeekly(String datetime) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (datetime == null) {
			cal.setTime(new Date());
		} else {
			cal.setTime(DATE_FORMAT.parse(datetime));
		}
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static  String formatDateByDayWeekly(String datetime, int day) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DATE_FORMAT.parse(datetime));
		cal.add(Calendar.DAY_OF_YEAR, day);
		return DATE_FORMAT.format(cal.getTime());
	}
	 
	
}//end class

