package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportFormatDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 6913223300401389505L;

	/**
	 * Method 'ReportFormatDaoException'
	 * 
	 * @param message
	 */
	public ReportFormatDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportFormatDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportFormatDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
