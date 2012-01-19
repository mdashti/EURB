package com.sharifpro.eurb.exceptions;

public class ReportFilterDaoException extends DaoException
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
