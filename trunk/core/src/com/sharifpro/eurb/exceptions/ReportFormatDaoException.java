package com.sharifpro.eurb.exceptions;

public class ReportFormatDaoException extends DaoException
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
