package com.sharifpro.eurb.exceptions;

public class ReportCategoryDaoException extends DaoException
{
	private static final long serialVersionUID = -248540213069213504L;

	/**
	 * Method 'ReportCategoryDaoException'
	 * 
	 * @param message
	 */
	public ReportCategoryDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportCategoryDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportCategoryDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
