package com.sharifpro.eurb.management.security.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the authorities table.
 */
public class AuthoritiesPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -3818159220097967583L;

	protected String username;

	protected String authority;

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
	 * Sets the value of authority
	 */
	public void setAuthority(String authority)
	{
		this.authority = authority;
	}

	/** 
	 * Gets the value of authority
	 */
	public String getAuthority()
	{
		return authority;
	}

	/**
	 * Method 'AuthoritiesPk'
	 * 
	 */
	public AuthoritiesPk()
	{
	}

	/**
	 * Method 'AuthoritiesPk'
	 * 
	 * @param username
	 * @param authority
	 */
	public AuthoritiesPk(final String username, final String authority)
	{
		this.username = username;
		this.authority = authority;
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
		
		if (!(_other instanceof AuthoritiesPk)) {
			return false;
		}
		
		final AuthoritiesPk _cast = (AuthoritiesPk) _other;
		if (username == null ? _cast.username != username : !username.equals( _cast.username )) {
			return false;
		}
		
		if (authority == null ? _cast.authority != authority : !authority.equals( _cast.authority )) {
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
		
		if (authority != null) {
			_hashCode = 29 * _hashCode + authority.hashCode();
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
		ret.append( "model.AuthoritiesPk: " );
		ret.append( "username=" + username );
		ret.append( ", authority=" + authority );
		return ret.toString();
	}

}
