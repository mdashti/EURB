package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportScheduleDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = -3038438456977788732L;

	/**
	 * Method 'ReportScheduleDaoException'
	 * 
	 * @param message
	 */
	public ReportScheduleDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportScheduleDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportScheduleDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
