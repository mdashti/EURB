package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportColumnDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 1861437211161636078L;

	/**
	 * Method 'ReportColumnDaoException'
	 * 
	 * @param message
	 */
	public ReportColumnDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportColumnDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportColumnDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
