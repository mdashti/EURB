package com.sharifpro.eurb.management.security.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the users table.
 */
public class UserPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -2572395260394655158L;
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
	public UserPk()
	{
		super();
	}

	/**
	 * Method 'UsersPk'
	 * 
	 * @param username
	 */
	public UserPk(final String username, final Long id)
	{
		super(id);
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
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof UserPk)) {
			return false;
		}
		
		final UserPk _cast = (UserPk) _other;
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
		int _hashCode = super.hashCode();
		
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
		ret.append( "model.UserPk: " );
		ret.append( super.toString() );
		ret.append( ", username=" + username );
		return ret.toString();
	}

}
