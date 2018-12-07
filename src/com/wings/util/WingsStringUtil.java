package com.wings.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class WingsStringUtil {

	/**
	 * <p>
	 * 에러나 이벤트와 관련된 각종 메시지를 로깅하기 위한 Log 오브젝트
	 * </p>
	 */

	private static Log log = LogFactory.getLog(WingsStringUtil.class);

	private final static char WHITE_SPACE = ' ';

	/**
	 * String 배열 중에서 null인 경우를 제외하고 반환(String 배열로 반환)
	 * @param strs
	 * @return
	 */
	public static String[] getNotNullStringArray(String[] strs) {
		int i = 0;
		for(String s : strs) {
			if(StringUtils.hasLength(s)) {
				i++;
			}
		}
		
		String[] returnStrs = new String[i];
		i = 0;
		for(String s : strs) {
			if(StringUtils.hasLength(s)) {
				returnStrs[i] = s;
				i++;
			}
		}
		return returnStrs;
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static boolean isAlpha(String str) {

		if (str == null) {
			return false;
		}

		int size = str.length();

		if (size == 0)
			return false;

		for (int i = 0; i < size; i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isAlphaNumeric(String str) {

		if (str == null) {
			return false;
		}

		int size = str.length();

		if (size == 0)
			return false;

		for (int i = 0; i < size; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param integer
	 * @return
	 */
	public static String integer2string(int integer) {
		return ("" + integer);
	}

	/**
	 * @param data
	 * @return
	 */
	public static String long2string(long longdata) {
		return String.valueOf(longdata);
	}

	public static String float2string(float floatdata) {
		return String.valueOf(floatdata);
	}

	public static String double2string(double doubledata) {
		return String.valueOf(doubledata);
	}

	/**
	 * @param source
	 * @param target
	 * @return
	 */
	public static int search(String source, String target) {
		int result = 0;
		String strCheck = new String(source);
		for (int i = 0; i < source.length();) {
			int loc = strCheck.indexOf(target);
			if (loc == -1) {
				break;
			} else {
				result++;
				i = loc + target.length();
				strCheck = strCheck.substring(i);
			}
		}
		return result;
	}

	/**
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str.trim();
	}

	/**
	 * @param str
	 * @return
	 */
	public static String ltrim(String str) {

		int index = 0;

		while (' ' == str.charAt(index++))
			;

		if (index > 0)
			str = str.substring(index - 1);

		return str;
	}

	public static String rtrim(String str) {

		int index = str.length();

		while (' ' == str.charAt(--index))
			;

		if (index < str.length())
			str = str.substring(0, index + 1);

		return str;
	}

	public static String concat(String str1, String str2) {

		StringBuffer sb = new StringBuffer(str1);
		sb.append(str2);

		return sb.toString();
	}

	public static String alignLeft(String str, int length) {
		return alignLeft(str, length, false);
	}

	/**
	 * @param str
	 * @param length
	 * @param isEllipsis
	 * @return
	 */
	public static String alignLeft(String str, int length, boolean isEllipsis) {

		if (str.length() <= length) {

			StringBuffer temp = new StringBuffer(str);
			for (int i = 0; i < (length - str.length()); i++) {
				temp.append(WHITE_SPACE);
			}
			return temp.toString();
		} else {
			if (isEllipsis) {

				StringBuffer temp = new StringBuffer(length);
				temp.append(str.substring(0, length - 3));
				temp.append("...");

				return temp.toString();
			} else {
				return str.substring(0, length);
			}
		}
	}

	public static String alignRight(String str, int length) {

		return alignRight(str, length, false);
	}

	public static String alignRight(String str, int length, boolean isEllipsis) {

		if (str.length() <= length) {

			StringBuffer temp = new StringBuffer(length);
			for (int i = 0; i < (length - str.length()); i++) {
				temp.append(WHITE_SPACE);
			}
			temp.append(str);
			return temp.toString();
		} else {
			if (isEllipsis) {

				StringBuffer temp = new StringBuffer(length);
				temp.append(str.substring(0, length - 3));
				temp.append("...");
				return temp.toString();
			} else {
				return str.substring(0, length);
			}
		}
	}

	public static String alignCenter(String str, int length) {
		return alignCenter(str, length, false);
	}

	public static String alignCenter(String str, int length, boolean isEllipsis) {
		if (str.length() <= length) {

			StringBuffer temp = new StringBuffer(length);
			int leftMargin = (int) (length - str.length()) / 2;

			int rightMargin;
			if ((leftMargin * 2) == (length - str.length())) {
				rightMargin = leftMargin;
			} else {
				rightMargin = leftMargin + 1;
			}

			for (int i = 0; i < leftMargin; i++) {
				temp.append(WHITE_SPACE);
			}

			temp.append(str);

			for (int i = 0; i < rightMargin; i++) {
				temp.append(WHITE_SPACE);
			}

			return temp.toString();
		} else {
			if (isEllipsis) {

				StringBuffer temp = new StringBuffer(length);
				temp.append(str.substring(0, length - 3));
				temp.append("...");
				return temp.toString();
			} else {
				return str.substring(0, length);
			}
		}

	}

	public static int countOf(String str, String charToFind) {
		int findLength = charToFind.length();
		int count = 0;

		for (int idx = str.indexOf(charToFind); idx >= 0; idx = str.indexOf(charToFind, idx + findLength)) {
			count++;
		}

		return count;
	}

	/*
	 * StringUtil in Anyframe
	 */

	/**
	 * Encode a string using algorithm specified in web.xml and return
	 * the resulting encrypted password. If exception, the plain
	 * credentials string is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating
	 *            this username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			log.error("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg.
		// stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if (((int) encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * Encode a string using Base64 encoding. Used when storing
	 * passwords as cookies. This is weak encoding in that anyone can
	 * use the decodeString routine to reverse the encoding.
	 * 
	 * @param str
	 *            String to be encoded
	 * @return String encoding result
	 */
	public static String encodeString(String str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return new String(encoder.encodeBuffer(str.getBytes())).trim();
	}

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 *            String to be decoded
	 * @return String decoding String
	 */
	public static String decodeString(String str) {
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		try {
			return new String(dec.decodeBuffer(str));
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	/**
	 * convert first letter to a big letter or a small letter.<br>
	 * 
	 * <pre>
	 * StringUtil.trim('Password') = 'password'
	 * StringUtil.trim('password') = 'Password'
	 * </pre>
	 * 
	 * @param str
	 *            String to be swapped
	 * @return String converting result
	 */
	public static String swapFirstLetterCase(String str) {
		StringBuffer sbuf = new StringBuffer(str);
		sbuf.deleteCharAt(0);
		if (Character.isLowerCase(str.substring(0, 1).toCharArray()[0])) {
			sbuf.insert(0, str.substring(0, 1).toUpperCase());
		} else {
			sbuf.insert(0, str.substring(0, 1).toLowerCase());
		}
		return sbuf.toString();
	}

	/**
	 * If original String has a specific String, remove specific Strings
	 * from original String.
	 * 
	 * <pre>
	 * StringUtil.trim('pass*word', '*') = 'password'
	 * </pre>
	 * 
	 * @param origString
	 *            original String
	 * @param trimString
	 *            String to be trimmed
	 * @return converting result
	 */
	public static String trim(String origString, String trimString) {
		int startPosit = origString.indexOf(trimString);
		if (startPosit != -1) {
			int endPosit = trimString.length() + startPosit;
			return origString.substring(0, startPosit) + origString.substring(endPosit);
		}
		return origString;
	}

	/**
	 * Break a string into specific tokens and return a String of last
	 * location.
	 * 
	 * <pre>
	 * StringUtil.getLastString('password*password*a*b*c', '*') = 'c'
	 * </pre>
	 * 
	 * @param origStr
	 *            original String
	 * @param strToken
	 *            specific tokens
	 * @return String of last location
	 */
	public static String getLastString(String origStr, String strToken) {
		StringTokenizer str = new StringTokenizer(origStr, strToken);
		String lastStr = "";
		while (str.hasMoreTokens()) {
			lastStr = str.nextToken();
		}
		return lastStr;
	}

	/**
	 * If original String has token, Break a string into specific tokens
	 * and change String Array. If not, return a String Array which has
	 * original String as it is.
	 * 
	 * <pre>
	 * StringUtil.getStringArray('passwordabcpassword', 'abc') 		= String[]{'password','password'}
	 * StringUtil.getStringArray('pasword*password', 'abc') 		= String[]{'pasword*password'}
	 * </pre>
	 * 
	 * @param str
	 *            original String
	 * @param strToken
	 *            specific String token
	 * @return String[]
	 */
	public static String[] getStringArray(String str, String strToken) {
		if (str.indexOf(strToken) != -1) {
			StringTokenizer st = new StringTokenizer(str, strToken);
			String[] stringArray = new String[st.countTokens()];
			for (int i = 0; st.hasMoreTokens(); i++) {
				stringArray[i] = st.nextToken();
			}
			return stringArray;
		}
		return new String[] { str };
	}

	/**
	 * replace replaced string to specific string from original string. <br>
	 * 
	 * <pre>
	 * StringUtil.replace('work$id', '$', '.') 	= 'work.id'
	 * </pre>
	 * 
	 * @param str
	 *            original String
	 * @param replacedStr
	 *            to be replaced String
	 * @param replaceStr
	 *            replace String
	 * @return converting result
	 */
	public static String replace(String str, String replacedStr, String replaceStr) {
		String newStr = "";
		if (str.indexOf(replacedStr) != -1) {
			String s1 = str.substring(0, str.indexOf(replacedStr));
			String s2 = str.substring(str.indexOf(replacedStr) + 1);
			newStr = s1 + replaceStr + s2;
		}
		return newStr;
	}

	/**
	 * It converts the string representation of a number to integer type
	 * (eg. '27' -> 27)
	 * 
	 * <pre>
	 * StringUtil.string2integer('14') 	= 14
	 * </pre>
	 * 
	 * @param str
	 *            string representation of a number
	 * @return integer integer type of string
	 * 
	 *         public static int string2integer(String str) { int ret =
	 *         Integer.parseInt(str.trim());
	 * 
	 *         return ret; }
	 * 
	 *         /** It converts integer type to String ( 27 -> '27')
	 * 
	 *         <pre>
	 * StringUtil.integer2string(14) 	= '14'
	 * </pre>
	 * 
	 * @param integer
	 *            integer type
	 * @return String string representation of a number
	 * 
	 *         public static String integer2string(int integer) { return
	 *         ("" + integer); }
	 * 
	 *         /** It returns true if str matches the pattern string. It
	 *         performs regular expression pattern matching.
	 * 
	 *         <pre>
	 * StringUtil.isPatternMatching('abc-def', '*-*') 	= true
	 * StringUtil.isPatternMatching('abc', '*-*') 	= false
	 * </pre>
	 * 
	 * @param str
	 *            original String
	 * @param pattern
	 *            pattern String
	 * @return boolean which matches the pattern string or not.
	 * @throws Exception
	 *             fail to check pattern matched
	 */
	public static boolean isPatternMatching(String str, String pattern) throws Exception {
		// if url has wild key, i.e. "*", convert it to ".*" so that we
		// can
		// perform regex matching
		if (pattern.indexOf('*') >= 0) {
			pattern = pattern.replaceAll("\\*", ".*");
		}

		pattern = "^" + pattern + "$";

		return Pattern.matches(pattern, str);
	}

	/**
	 * <p>
	 * Checks that the String contains certain characters.
	 * </p>
	 * <p>
	 * A <code>null</code> String will return <code>false</code>. A
	 * <code>null</code> invalid character array will return
	 * <code>false</code>. An empty String ("") always returns false.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.containsInvalidChars(null, *)       			= false
	 * StringUtil.containsInvalidChars(*, null)      			= false
	 * StringUtil.containsInvalidChars(&quot;&quot;, *)         = false
	 * StringUtil.containsInvalidChars(&quot;ab&quot;, '')      = false
	 * StringUtil.containsInvalidChars(&quot;abab&quot;, 'xyz') = false
	 * StringUtil.containsInvalidChars(&quot;ab1&quot;, 'xyz')  = false
	 * StringUtil.containsInvalidChars(&quot;xbz&quot;, 'xyz')  = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param invalidChars
	 *            an array of invalid chars, may be null
	 * @return false if it contains none of the invalid chars, or is
	 *         null
	 */

	public static boolean containsInvalidChars(String str, char[] invalidChars) {
		if (str == null || invalidChars == null) {
			return false;
		}
		int strSize = str.length();
		int validSize = invalidChars.length;
		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (invalidChars[j] == ch) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Checks that the String contains certain characters.
	 * </p>
	 * <p>
	 * A <code>null</code> String will return <code>false</code>. A
	 * <code>null</code> invalid character array will return
	 * <code>false</code>. An empty String ("") always returns false.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.containsInvalidChars(null, *)       			= false
	 * StringUtil.containsInvalidChars(*, null)      			= false
	 * StringUtil.containsInvalidChars(&quot;&quot;, *)         = false
	 * StringUtil.containsInvalidChars(&quot;ab&quot;, '')      = false
	 * StringUtil.containsInvalidChars(&quot;abab&quot;, 'xyz') = false
	 * StringUtil.containsInvalidChars(&quot;ab1&quot;, 'xyz')  = false
	 * StringUtil.containsInvalidChars(&quot;xbz&quot;, 'xyz')  = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param invalidChars
	 *            a String of invalid chars, may be null
	 * @return false if it contains none of the invalid chars, or is
	 *         null
	 */
	public static boolean containsInvalidChars(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		return containsInvalidChars(str, invalidChars.toCharArray());
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters or digits.
	 * </p>
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * ("") will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isAlphaNumeric(null)   			 = false
	 * StringUtil.isAlphaNumeric(&quot;&quot;)     = false
	 * StringUtil.isAlphaNumeric(&quot;  &quot;)   = false
	 * StringUtil.isAlphaNumeric(&quot;abc&quot;)  = true
	 * StringUtil.isAlphaNumeric(&quot;ab c&quot;) = false
	 * StringUtil.isAlphaNumeric(&quot;ab2c&quot;) = true
	 * StringUtil.isAlphaNumeric(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters or digits, and
	 *         is non-null
	 * 
	 *         public static boolean isAlphaNumeric(String str) { if
	 *         (str == null) { return false; } int sz = str.length(); if
	 *         (sz == 0) return false; for (int i = 0; i < sz; i++) { if
	 *         (!Character.isLetterOrDigit(str.charAt(i))) { return
	 *         false; } } return true; }
	 * 
	 *         /**
	 *         <p>
	 *         Checks if the String contains only unicode letters.
	 *         </p>
	 *         <p>
	 *         <code>null</code> will return <code>false</code>. An
	 *         empty String ("") will return <code>false</code>.
	 *         </p>
	 * 
	 *         <pre>
	 * StringUtil.isAlpha(null)   			= false
	 * StringUtil.isAlpha(&quot;&quot;)     = false
	 * StringUtil.isAlpha(&quot;  &quot;)   = false
	 * StringUtil.isAlpha(&quot;abc&quot;)  = true
	 * StringUtil.isAlpha(&quot;ab2c&quot;) = false
	 * StringUtil.isAlpha(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters, and is
	 *         non-null
	 * 
	 *         public static boolean isAlpha(String str) { if (str ==
	 *         null) { return false; } int sz = str.length(); if (sz ==
	 *         0) return false; for (int i = 0; i < sz; i++) { if
	 *         (!Character.isLetter(str.charAt(i))) { return false; } }
	 *         return true; }
	 * 
	 *         /**
	 *         <p>
	 *         Checks if the String contains only unicode digits. A
	 *         decimal point is not a unicode digit and returns false.
	 *         </p>
	 *         <p>
	 *         <code>null</code> will return <code>false</code>. An
	 *         empty String ("") will return <code>false</code>.
	 *         </p>
	 * 
	 *         <pre>
	 * StringUtil.isNumeric(null)   		   = false
	 * StringUtil.isNumeric(&quot;&quot;)     = false
	 * StringUtil.isNumeric(&quot;  &quot;)   = false
	 * StringUtil.isNumeric(&quot;123&quot;)  = true
	 * StringUtil.isNumeric(&quot;12 3&quot;) = false
	 * StringUtil.isNumeric(&quot;ab2c&quot;) = false
	 * StringUtil.isNumeric(&quot;12-3&quot;) = false
	 * StringUtil.isNumeric(&quot;12.3&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains digits, and is
	 *         non-null
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		if (sz == 0)
			return false;
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Reverses a String as per {@link StringBuffer#reverse()}.
	 * </p>
	 * <p>
	 * <A code>null</code> String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.reverse(null)  		   = null
	 * StringUtil.reverse(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.reverse(&quot;bat&quot;) = &quot;tab&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to reverse, may be null
	 * @return the reversed String, <code>null</code> if null String
	 *         input
	 */

	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	/**
	 * Make a new String that filled original to a special char as
	 * cipers
	 * 
	 * @param originalStr
	 *            original String
	 * @param ch
	 *            a special char
	 * @param cipers
	 *            cipers
	 * @return filled String
	 */
	public static String fillString(String originalStr, char ch, int cipers) {
		int originalStrLength = originalStr.length();

		if (cipers < originalStrLength)
			return null;

		int difference = cipers - originalStrLength;

		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < difference; i++)
			strBuf.append(ch);

		strBuf.append(originalStr);
		return strBuf.toString();
	}

	/**
	 * Determine whether a (trimmed) string is empty
	 * 
	 * @param foo
	 *            The text to check.
	 * @return Whether empty.
	 */
	public static final boolean isEmptyTrimmed(String foo) {
		return (foo == null || foo.trim().length() == 0);
	}

	/**
	 * This method convert "string_util" to "stringUtil"
	 * 
	 * @param String
	 *            targetString
	 * 
	 * @param char posChar
	 * 
	 * @return String result
	 */
	public static String convertToCamelCase(String targetString, char posChar) {
		StringBuffer result = new StringBuffer();
		boolean nextUpper = false;
		String allLower = targetString.toLowerCase();

		for (int i = 0; i < allLower.length(); i++) {
			char currentChar = allLower.charAt(i);
			if (currentChar == posChar) {
				nextUpper = true;
			} else {
				if (nextUpper) {
					currentChar = Character.toUpperCase(currentChar);
					nextUpper = false;
				}
				result.append(currentChar);
			}
		}
		return result.toString();
	}

	/**
	 * Convert a string that may contain underscores to camel case.
	 * 
	 * @param underScore
	 *            Underscore name.
	 * @return Camel case representation of the underscore string.
	 */
	public static String convertToCamelCase(String underScore) {
		return convertToCamelCase(underScore, '_');
	}

	/**
	 * Convert a camel case string to underscore representation.
	 * 
	 * @param camelCase
	 *            Camel case name.
	 * @return Underscore representation of the camel case string.
	 */
	public static String convertToUnderScore(String camelCase) {
		String result = "";
		for (int i = 0; i < camelCase.length(); i++) {
			char currentChar = camelCase.charAt(i);
			// This is starting at 1 so the result does not end up with
			// an
			// underscore at the begin of the value
			if (i > 0 && Character.isUpperCase(currentChar)) {
				result = result.concat("_");
			}
			result = result.concat(Character.toString(currentChar).toLowerCase());
		}
		return result;
	}

	/**
	 * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
	 * 
	 * @param
	 * @return Timestamp 값
	 * @exception MyException
	 * @see
	 */
	public static String getTimeStamp() {

		String rtnStr = null;

		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";

		try {
			SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
			Timestamp ts = new Timestamp(System.currentTimeMillis());

			rtnStr = sdfCurrent.format(ts.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtnStr;
	}


    /**
     * 왼쪽 자리수 채우기
     * 
     * @param str
     * @param len
     * @param addStr
     * @return
     */
    public static String lpad(String str, int len, String addStr) {
        String result = str;
        int templen   = len - result.length();

        for (int i = 0; i < templen; i++){
              result = addStr + result;
        }
        
        return result;
    }
}
