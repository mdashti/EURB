package com.sharifpro.eurb.exceptions;

public class ReportExecutionHistoryDaoException extends DaoException
{
	private static final long serialVersionUID = 2517268473755228470L;

	/**
	 * Method 'ReportExecutionHistoryDaoException'
	 * 
	 * @param message
	 */
	public ReportExecutionHistoryDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ReportExecutionHistoryDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ReportExecutionHistoryDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
