package com.sharifpro.eurb.management.mapping.exception;

public class ColumnMappingDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 5230637432117019930L;

	/**
	 * Method 'ColumnMappingDaoException'
	 * 
	 * @param message
	 */
	public ColumnMappingDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ColumnMappingDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ColumnMappingDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
