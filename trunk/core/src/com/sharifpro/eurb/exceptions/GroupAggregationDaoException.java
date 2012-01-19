package com.sharifpro.eurb.exceptions;

public class GroupAggregationDaoException extends DaoException
{
	private static final long serialVersionUID = 6120547439863822456L;

	/**
	 * Method 'GroupAggregationDaoException'
	 * 
	 * @param message
	 */
	public GroupAggregationDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'GroupAggregationDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public GroupAggregationDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
