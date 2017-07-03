package com.wologic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Common {

	public static String userID = "02";

	public static String deviceid = "123";
	
	public static String clientId="1";

	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
//			format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
			date = format.parse(str);
			//�������ʱ�������⣬����ֱ�Ӱ�ʱ��+8Сʱ��ʾ
			date.setHours(date.getHours()+8);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * �ж��ַ����Ƿ�������
	 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

	/**
	 * �ж��ַ����Ƿ�������
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * �ж��ַ����Ƿ��Ǹ�����
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
