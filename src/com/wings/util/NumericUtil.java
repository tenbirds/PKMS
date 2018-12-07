package com.wings.util;

import java.math.BigDecimal;

public class NumericUtil {

	/**
	 * <p>
	 * 올림
	 * </p>
	 */
	public static final int ROUND_UP = BigDecimal.ROUND_UP;

	/**
	 * <p>
	 * 내림(절사)
	 * </p>
	 */
	public static final int ROUND_DOWN = BigDecimal.ROUND_DOWN;

	/**
	 * <p>
	 * 반올림
	 * </p>
	 */
	public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;

	/**
	 * <p>
	 * 사용하지 않음
	 * </p>
	 */
	public static final int ROUND_UNNECESSARY = BigDecimal.ROUND_UNNECESSARY;

	/**
	 * <p>
	 * <strong>NumericHelper</strong>의 default 컨스트럭터(Constructor).
	 * </p>
	 */
	protected NumericUtil() {
	}

	/**
	 * <p>
	 * String형 값의 기본덧셈을 실행한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @return 결과 값
	 * @see #isPlus(String, String, int)
	 * @see #isPlus(String, String, int, int)
	 */
	public static String plus(String thisVal, String addVal) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.add(two).toString();
		return result;
	}

	/**
	 * <p>
	 * 덧셈한 결과값의 소숫점 자릿수만 지정한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @param scale
	 *            <code>int</code> 자리수지정
	 * @return 결과 값
	 * @see #isPlus(String, String, int, int)
	 * @see #isPlus(String, String)
	 */
	public static String plus(String thisVal, String addVal, int scale) {
		return plus(thisVal, addVal, scale, ROUND_UNNECESSARY);
	}

	/**
	 * <p>
	 * 덧셈한 결과값의 자릿수와 올림,내림,절삭여부 지정한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @param scale
	 *            <code>int</code> 자리수지정
	 * @param roundMode
	 *            <code>int</code> Round 여부 <br>
	 *            올 림 : {@link #ROUND_UP} <br>
	 *            내 림 : {@link #ROUND_DOWN} <br>
	 *            반올림 : {@link #ROUND_HALF_UP}
	 * @return 결과 값
	 * @see #isPlus(String, String, int)
	 * @see #isPlus(String, String)
	 */
	public static String plus(String thisVal, String addVal, int scale, int roundMode) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.add(two).setScale(scale, roundMode).toString();
		return result;
	}

	/**
	 * <p>
	 * 기본뺄셈을 실행한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @return 결과 값
	 * @see #isMinus(String, String, int)
	 * @see #isMinus(String, String, int, int)
	 */
	public static String minus(String thisVal, String addVal) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.subtract(two).toString();
		return result;

	}

	/**
	 * <p>
	 * 뺄셈한 결과값의 자릿수만 지정한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @param scale
	 *            <code>int</code> 자리수지정
	 * @return 결과 값
	 * @see #isMinus(String, String, int, int)
	 * @see #isMinus(String, String)
	 */
	public static String minus(String thisVal, String addVal, int scale) {
		return minus(thisVal, addVal, scale, ROUND_UNNECESSARY);
	}

	/**
	 * <p>
	 * 뺄셈한 결과값의 자릿수와 올림,내림,절삭여부 지정한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @param scale
	 *            <code>int</code> 자리수지정
	 * @param roundMode
	 *            <code>int</code> Round 여부 <br>
	 *            올 림 : {@link #ROUND_UP} <br>
	 *            내 림 : {@link #ROUND_DOWN} <br>
	 *            반올림 : {@link #ROUND_HALF_UP}
	 * @return 결과 값
	 * @see #isMinus(String, String, int)
	 * @see #isMinus(String, String)
	 */
	public static String minus(String thisVal, String addVal, int scale, int roundMode) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.subtract(two).setScale(scale, roundMode).toString();
		return result;
	}

	/**
	 * <p>
	 * 기본곱셈을 실행한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @return 결과 값
	 * @see #isMultiply(String, String, int)
	 * @see #isMultiply(String, String, int, int)
	 */
	public static String multiply(String thisVal, String addVal) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.multiply(two).toString();
		return result;
	}

	/**
	 * <p>
	 * 곱셈한 결과값의 자릿수만 지정한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @param scale
	 *            <code>int</code> 자리수지정
	 * @return 결과 값
	 * @see #isMultiply(String, String, int, int)
	 * @see #isMultiply(String, String)
	 */
	public static String multiply(String thisVal, String addVal, int scale) {
		return multiply(thisVal, addVal, scale, ROUND_UNNECESSARY);
	}

	/**
	 * <p>
	 * 곱셈한 결과값의 자릿수와 올림,내림,절삭여부 지정한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @param scale
	 *            <code>int</code> 자리수지정
	 * @param roundMode
	 *            <code>int</code> Round 여부 <br>
	 *            올 림 : {@link #ROUND_UP} <br>
	 *            내 림 : {@link #ROUND_DOWN} <br>
	 *            반올림 : {@link #ROUND_HALF_UP}
	 * @return 결과 값
	 * @see #isMultiply(String, String, int)
	 * @see #isMultiply(String, String)
	 */
	public static String multiply(String thisVal, String addVal, int scale, int roundMode) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.multiply(two).setScale(scale, roundMode).toString();
		return result;
	}

	/**
	 * <p>
	 * 기본나눗셈을 실행한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @return 결과 값
	 * @see #isDivide(String, String, int)
	 * @see #isDivide(String, String, int, int)
	 */
	public static String divide(String thisVal, String addVal) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.divide(two).toString();
		return result;
	}

	/**
	 * <p>
	 * 나눗셈의 결과값이 정수
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @param roundMode
	 *            <code>int</code> Round 여부 <br>
	 *            올 림 : {@link #ROUND_UP} <br>
	 *            내 림 : {@link #ROUND_DOWN} <br>
	 *            반올림 : {@link #ROUND_HALF_UP}
	 * @return 결과 값
	 * @see #isDivide(String, String, int, int)
	 */
	public static String divide(String thisVal, String addVal, int roundMode) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.divide(two, roundMode).toString();
		return result;
	}

	/**
	 * <p>
	 * 나눗셈 결과값의 자릿수와 올림,내림,절삭여부 지정한다
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param addVal
	 *            <code>String</code>
	 * @param scale
	 *            <code>int</code> 자리수지정
	 * @param roundMode
	 *            <code>int</code> Round 여부 <br>
	 *            올 림 : {@link #ROUND_UP} <br>
	 *            내 림 : {@link #ROUND_DOWN} <br>
	 *            반올림 : {@link #ROUND_HALF_UP}
	 * @return String
	 * @see #isDivide(String, String, int)
	 */
	public static String divide(String thisVal, String addVal, int scale, int roundMode) {
		String result = null;
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
		result = one.divide(two, scale, roundMode).toString();
		return result;
	}

	/**
	 * <p>
	 * 기본값을 scale 자릿수만큼 ROUND한다.
	 * </p>
	 * 
	 * @param thisVal
	 *            <code>String</code>
	 * @param scale
	 *            <code>int</code> 자리수지정
	 * @param roundMode
	 *            <code>int</code> Round 여부 <br>
	 *            올 림 : {@link #ROUND_UP} <br>
	 *            내 림 : {@link #ROUND_DOWN} <br>
	 *            반올림 : {@link #ROUND_HALF_UP}
	 * @return String
	 */
	public static String setScale(String thisVal, int scale, int roundMode) {
		BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
		return one.setScale(scale, roundMode).toString();
	}

}
