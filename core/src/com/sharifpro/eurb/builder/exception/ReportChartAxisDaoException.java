package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportChartAxisDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 5405082594019799278L;

	/**
	 * Method 'ReportChartAxisDaoException'
	 * 
	 * @param message
	 */
	public ReportChartAxisDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportChartAxisDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportChartAxisDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
