package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportDatasetDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 5405082594019799278L;

	/**
	 * Method 'ReportDatasetDaoException'
	 * 
	 * @param message
	 */
	public ReportDatasetDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportDatasetDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportDatasetDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
