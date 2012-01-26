package com.sharifpro.eurb.dto;

import java.io.Serializable;

public class Users extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -4846712694892480891L;

	/** 
	 * This attribute maps to the column username in the users table.
	 */
	protected String username;

	/** 
	 * This attribute maps to the column password in the users table.
	 */
	protected String password;

	/** 
	 * This attribute maps to the column enabled in the users table.
	 */
	protected boolean enabled;

	/**
	 * Method 'Users'
	 * 
	 */
	public Users()
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
	 * Method 'getPassword'
	 * 
	 * @return String
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Method 'setPassword'
	 * 
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Method 'isEnabled'
	 * 
	 * @return boolean
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * Method 'setEnabled'
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
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
		
		if (!(_other instanceof Users)) {
			return false;
		}
		
		final Users _cast = (Users) _other;
		
		if (username == null ? _cast.username != username : !username.equals( _cast.username )) {
			return false;
		}
		
		/*if (password == null ? _cast.password != password : !password.equals( _cast.password )) {
			return false;
		}
		
		if (enabled != _cast.enabled) {
			return false;
		}*/
		
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
		
		/*if (password != null) {
			_hashCode = 29 * _hashCode + password.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (enabled ? 1 : 0);*/
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return UsersPk
	 */
	public UsersPk createPk()
	{
		return new UsersPk(username, id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.Users: " );
		ret.append( super.toString() );
		ret.append( ", username=" + username );
		ret.append( ", password=" + password );
		ret.append( ", enabled=" + enabled );
		return ret.toString();
	}

}
