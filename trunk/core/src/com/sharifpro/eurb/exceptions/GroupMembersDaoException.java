package com.sharifpro.eurb.exceptions;

public class GroupMembersDaoException extends DaoException
{
	private static final long serialVersionUID = 4404418729641675663L;

	/**
	 * Method 'GroupMembersDaoException'
	 * 
	 * @param message
	 */
	public GroupMembersDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'GroupMembersDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public GroupMembersDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
