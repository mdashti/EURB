package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportLogDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 8054234817524982895L;

	/**
	 * Method 'ReportLogDaoException'
	 * 
	 * @param message
	 */
	public ReportLogDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportLogDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportLogDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
