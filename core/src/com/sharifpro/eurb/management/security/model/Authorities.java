package com.sharifpro.eurb.management.security.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class Authorities extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = 9080484423268142973L;

	/** 
	 * This attribute maps to the column username in the authorities table.
	 */
	protected String username;

	/** 
	 * This attribute maps to the column authority in the authorities table.
	 */
	protected String authority;

	/**
	 * Method 'Authorities'
	 * 
	 */
	public Authorities()
	{
		super();
	}

	/**
	 * Method 'getUsername'
	 * 
	 * @return String
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Method 'setUsername'
	 * 
	 * @param username
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Method 'getAuthority'
	 * 
	 * @return String
	 */
	public String getAuthority()
	{
		return authority;
	}

	/**
	 * Method 'setAuthority'
	 * 
	 * @param authority
	 */
	public void setAuthority(String authority)
	{
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
		
		if (!(_other instanceof Authorities)) {
			return false;
		}
		
		final Authorities _cast = (Authorities) _other;
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
	 * Method 'createPk'
	 * 
	 * @return AuthoritiesPk
	 */
	public AuthoritiesPk createPk()
	{
		return new AuthoritiesPk(username, authority);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.Authorities: " );
		ret.append( "username=" + username );
		ret.append( ", authority=" + authority );
		return ret.toString();
	}

}
