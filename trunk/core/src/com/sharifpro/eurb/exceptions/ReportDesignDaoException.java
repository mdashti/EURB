package com.sharifpro.eurb.exceptions;

public class ReportDesignDaoException extends DaoException
{
	private static final long serialVersionUID = -1751829691576020273L;

	/**
	 * Method 'ReportDesignDaoException'
	 * 
	 * @param message
	 */
	public ReportDesignDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportDesignDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportDesignDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
