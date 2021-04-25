package com.muyi.courage.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;


public final class StringUtil {

	/**
	 * 处理字符串
	 *
	 * @param arg0 要处理的对象
	 * @return 若obj为空(null)则返回"",否则返回obj转换成字符串且除去该字符前后空格之后的值
	 */

	public static String parseString(Object arg0) {
		return arg0 == null || "null".equalsIgnoreCase(arg0.toString()) ? "" : arg0.toString().trim();
	}

	/**
	 * 将字符串转换为int型整数。
	 *
	 * @return int 整数
	 */
	public static int ch2Int(String str) {
		try {
			return (Integer.parseInt(str));
		} catch (NumberFormatException e) {
			return (-1);
		}
	}

	/**
	 * 将字符串转换为int型整数。
	 *
	 * @return int 整数
	 */
	public static BigDecimal objBigDecimal(Object value) {
		try {
			BigDecimal ret = null;
			if (value != null) {
				if (value instanceof BigDecimal) {
					ret = (BigDecimal) value;
				} else if (value instanceof String) {
					ret = new BigDecimal((String) value);
				} else if (value instanceof BigInteger) {
					ret = new BigDecimal((BigInteger) value);
				} else if (value instanceof Number) {
					ret = new BigDecimal(((Number) value).doubleValue());
				} else {
					throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
				}
			}
			return ret;
		} catch (NumberFormatException e) {
			return (BigDecimal.valueOf(-1));
		}
	}

	/**
	 * 将字符串转换为double型。
	 *
	 * @return double 长整型
	 */
	public static double ch2Double(String str) {
		try {
			return (Double.parseDouble(str));
		} catch (NumberFormatException e) {
			return (-1);
		}
	}

	/**
	 * ch2Int(parseString(object))
	 *
	 * @param object
	 * @return
	 */
	public static int objectToInt(Object object) {
		return ch2Int(parseString(object));
	}


	public static Integer objectToInteger(Object object) {
		try {
			return Integer.parseInt(parseString(object));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * ch2Int(parseString(object))
	 *
	 * @param object
	 * @return
	 */
	public static double objectToDouble(Object object) {
		try {
			if (object == null) {
				return 0;
			}
			return Double.parseDouble(parseString(object));
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}


	/**
	 * 中文转码方法
	 *
	 * @param arg0 要转换编码的字符串
	 * @return 转换编码后的字符串
	 */

	public static String toGBK(String arg0) {
		try {
			arg0 = (arg0 == null) ? "" : new String(arg0.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
		}
		return arg0;
	}

	/**
	 * 过滤字符串方法
	 *
	 * @param arg0 需要过滤的字符串
	 * @param arg1 要过滤掉的字符串
	 * @return 返回过滤后的字符串
	 */
	public static String filterString(String arg0, String arg1) {

		int pos;
		String str1 = StringUtil.parseString(arg0);
		String str2 = StringUtil.parseString(arg1);
		String str = str1;

		while (str.indexOf(str2) >= 0) {
			pos = str.indexOf(str2);
			str = str.substring(0, pos) + str.substring(pos + str2.length());
		}
		return str;
	}


	public static boolean isNullorEmpty(String id) {
		return id == null || "".equals(id.trim());
	}

	/**
	 * 字符串是否为空
	 * 与Empty的区别是对于\t \r \n 等特殊字符都属于true
	 *
	 * @param cs 字符串
	 * @return boolean
	 */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs != null && (strLen = cs.length()) != 0) {
			for (int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(cs.charAt(i))) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

	public static boolean isNotBlank(String id) {
		return !isBlank(id);
	}

	/**
	 * 空格字符串返回false，没有trim()
	 */
	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static boolean isNotEmpty(String id) {
		return !isEmpty(id);
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j)) {
				tmp.append(j);
			} else if (j < 256) {
				tmp.append("%");
				if (j < 16) {
					tmp.append("0");
				}
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static String parseFileLength(long length) {
		long gSize = 1024 * 1024 * 1024;
		long mSize = 1024 * 1024;
		long bSize = 1024;

		if (length == 0) {
			return "0";
		}

		if (length >= gSize) {
			return NumberUtil.decimal2((double) length / gSize) + " G";
		} else if (length >= mSize) {
			return NumberUtil.decimal2((double) length / mSize) + " M";
		} else {
			return NumberUtil.decimal2((double) length / bSize) + " K";
		}
	}

	public static String changePathSeparator(String path) {
		if (path == null) {
			return null;
		}
		return path.replace("\\", "/");
	}

	/**
	 * 将前端传入的具有Base64格式的文件进行解码
	 *
	 * @param base64String  base64编码数据流
	 * @param attributeCode 判断文件是否存在特征码,如果存在,需要去掉 (data:*\/*;base64)
	 * @return 解码后的数据流
	 */
	public static byte[] parseFromBase64(Object base64String, boolean attributeCode) {
		byte[] decodeString = null;
		try {
			String fileContent = StringUtil.parseString(base64String);
			if (attributeCode) {
				fileContent = fileContent.substring(fileContent.indexOf(",") + 1);
			}
			decodeString = Base64.decodeBase64(fileContent.getBytes("UTF-8"));
		} catch (Exception e) {
			e.getStackTrace();
		}
		return decodeString;
	}

	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		} else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}

	/**
	 * 字符串截取（避免空指针）
	 * @author: 陈超
	 * @param str str:被截取的字符串 a;截取开始 b:截取结束（若截取后几位，a为后几位数，b为-1）
	 * @param str 被截取字符串
	 * @return
	 */
	public static String GetSubstring(String str,int a,int b) {
		if(str==null){
			return "";
		}
		int length = str.length();
		if(a>=length){
			return "";
		}
		if(-1 == b){
			return str.substring(length-a);
		}
		if(b>length){
			return str.substring(a,length);
		}
		return str.substring(a,b);
	}

	public static int getPenultIndex(String string, char c) {
		int lastIndex = string.lastIndexOf(c);
		return lastIndex == -1 ? -1 : string.substring(0, lastIndex).lastIndexOf(c);
	}

	public static boolean checkIfHasUserDefObjects(Object testObj) {
		if (testObj != null && !(testObj instanceof String) && !(testObj instanceof Integer) && !(testObj instanceof Boolean) && !(testObj instanceof Float) && !(testObj instanceof Double) && !(testObj instanceof BigDecimal) && !(testObj instanceof Long) && !(testObj instanceof Character) && !(testObj instanceof Byte) && !(testObj instanceof Number) && !(testObj instanceof Date)) {
			Iterator iterator;
			Object key;
			if (testObj instanceof JSONArray) {
				iterator = ((JSONArray)testObj).iterator();

				while(iterator.hasNext()) {
					key = iterator.next();
					if (checkIfHasUserDefObjects(key)) {
						return true;
					}
				}
			} else {
				if (!(testObj instanceof JSONObject)) {
					return true;
				}

				iterator = ((JSONObject)testObj).keySet().iterator();

				while(iterator.hasNext()) {
					key = iterator.next();
					if (checkIfHasUserDefObjects(((JSONObject)testObj).get(key))) {
						return true;
					}
				}
			}
		}

		return false;
	}


	public static void main(String[] args) {
		System.out.println("hello word!");
	}

}
