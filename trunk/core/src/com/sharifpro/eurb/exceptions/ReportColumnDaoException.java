package com.sharifpro.eurb.exceptions;

public class ReportColumnDaoException extends DaoException
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
