package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportAlertDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = -5002548222725917445L;

	/**
	 * Method 'ReportAlertDaoException'
	 * 
	 * @param message
	 */
	public ReportAlertDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportAlertDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportAlertDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
