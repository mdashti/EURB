package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportFilterDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 542089896820857287L;

	/**
	 * Method 'ReportFilterDaoException'
	 * 
	 * @param message
	 */
	public ReportFilterDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportFilterDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportFilterDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
