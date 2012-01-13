package com.sharifpro.util;

import java.util.Arrays;
import java.util.Date;

import com.ghasemkiani.util.DateFields;
import com.ghasemkiani.util.SimplePersianCalendar;
import com.ibm.icu.util.Calendar;

public class DateUtil {
	public static int[] convertGregorianToPersian(int year, int month, int day) {
		month--;
		SimplePersianCalendar c = new SimplePersianCalendar();
		c.set(SimplePersianCalendar.YEAR, year);
		c.set(SimplePersianCalendar.MONTH, month);
		c.set(SimplePersianCalendar.DAY_OF_MONTH, day);
		DateFields t = c.getDateFields();
		return new int[] { t.getYear(), t.getMonth() + 1, t.getDay() };
	}

	public static String convertGregorianToPersianString(int year, int month,
			int day) {
		int[] gregorian = convertGregorianToPersian(year, month, day);
		return gregorian[0] + "/"
				+ (gregorian[1] < 10 ? "0" + gregorian[1] : gregorian[1]) + "/"
				+ (gregorian[2] < 10 ? "0" + gregorian[2] : gregorian[2]);
	}

	public static String convertGregorianToPersianString(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int[] persian = convertGregorianToPersian(c.get(Calendar.YEAR),
				c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
		return persian[0] + "/"
				+ (persian[1] < 10 ? "0" + persian[1] : persian[1]) + "/"
				+ (persian[2] < 10 ? "0" + persian[2] : persian[2]);
	}

	public static Date convertPersianStringToGregorian(String pd) {
		try {
			String[] dateParts = pd.split("/");

			int year, month, day;
			try {
				year = Integer.parseInt(dateParts[0]);
			} catch (NumberFormatException nfe) {
				year = 0;
			}
			try {
				month = Integer.parseInt(dateParts[1]) - 1;
			} catch (NumberFormatException nfe) {
				month = 0;
			}
			try {
				day = Integer.parseInt(dateParts[2]);
			} catch (NumberFormatException nfe) {
				day = 0;
			}
			int[] gregorian = convertPersianToGregorian(year, month, day);
			Calendar c = Calendar.getInstance();
			c.set(SimplePersianCalendar.YEAR, gregorian[0]);
			c.set(SimplePersianCalendar.MONTH, gregorian[1]);
			c.set(SimplePersianCalendar.DAY_OF_MONTH, gregorian[2]);
			return c.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	public static int[] convertPersianToGregorian(int year, int month, int day) {
		month--;

		SimplePersianCalendar c = new SimplePersianCalendar();
		c.setDateFields(year, month, day);
		return new int[] {
				c.get(SimplePersianCalendar.ERA) == SimplePersianCalendar.AD ? c
						.get(SimplePersianCalendar.YEAR) : -(c
						.get(SimplePersianCalendar.YEAR) - 1),
				c.get(SimplePersianCalendar.MONTH) + 1,
				c.get(SimplePersianCalendar.DAY_OF_MONTH) };
	}
	
	public static void main(String[] args) {
		int [] persian = convertGregorianToPersian(1988, 4, 29);
		System.out.println(Arrays.toString(persian));
		
		int[] gregorian = convertPersianToGregorian(1367, 2, 9);
		System.out.println(Arrays.toString(gregorian));
		
		Date greg = convertPersianStringToGregorian("1367/2/9");
		System.out.println(greg);
		
		String per = convertGregorianToPersianString(greg);
		System.out.println(per);
		
		String persianstr = convertGregorianToPersianString(1988, 4, 29);
		System.out.println(persianstr);
	}
}
