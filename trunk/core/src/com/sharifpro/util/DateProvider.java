package com.sharifpro.util;

import java.util.Date;
import org.apache.log4j.Logger;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;
public class DateProvider {
	protected static Logger log = Logger.getLogger(DateProvider.class.getName());

	public static Date parseDate(String date)
			throws PersistableObjectDaoException {
		try {
			return DateUtil.convertPersianStringToGregorian(date);
		} catch (Exception e) {
			throw new PersistableObjectDaoException("Error in DateProvider.parseDate", e);
		}
	}

	public static String formatDate(Date date) {
		return DateUtil.convertGregorianToPersianString(date);
	}
}