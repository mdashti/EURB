package com.sharifpro.eurb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the users table.
 */
public class UsersPk implements Serializable
{
	protected String username;

	/** 
	 * Sets the value of username
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/** 
	 * Gets the value of username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Method 'UsersPk'
	 * 
	 */
	public UsersPk()
	{
	}

	/**
	 * Method 'UsersPk'
	 * 
	 * @param username
	 */
	public UsersPk(final String username)
	{
		this.username = username;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof UsersPk)) {
			return false;
		}
		
		final UsersPk _cast = (UsersPk) _other;
		if (username == null ? _cast.username != username : !username.equals( _cast.username )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		if (username != null) {
			_hashCode = 29 * _hashCode + username.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.UsersPk: " );
		ret.append( "username=" + username );
		return ret.toString();
	}

}
