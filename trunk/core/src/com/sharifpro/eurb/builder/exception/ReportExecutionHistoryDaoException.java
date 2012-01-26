package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ReportExecutionHistoryDaoException extends PersistableObjectDaoException
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
