package com.sharifpro.eurb.dashboard.model;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class Dashboard extends PersistableObject
{
	private static final long serialVersionUID = 3646506796424448786L;

	/** 
	 * This attribute maps to the column is_default in the dashboard table.
	 */
	protected short isDefault;

	/** 
	 * This attribute maps to the column title in the dashboard table.
	 */
	protected String title;

	/** 
	 * This attribute maps to the column parent_dashboard in the dashboard table.
	 */
	protected Long parentDashboard;

	/** 
	 * This attribute maps to the column username in the dashboard table.
	 */
	protected String username;

	/**
	 * Method 'Dashboard'
	 * 
	 */
	public Dashboard()
	{
	}

	/**
	 * Method 'getIsDefault'
	 * 
	 * @return short
	 */
	public short getIsDefault()
	{
		return isDefault;
	}

	/**
	 * Method 'setIsDefault'
	 * 
	 * @param isDefault
	 */
	public void setIsDefault(short isDefault)
	{
		this.isDefault = isDefault;
	}

	/**
	 * Method 'getTitle'
	 * 
	 * @return String
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Method 'setTitle'
	 * 
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Method 'getParentDashboard'
	 * 
	 * @return long
	 */
	public Long getParentDashboard()
	{
		return parentDashboard;
	}

	/**
	 * Method 'setParentDashboard'
	 * 
	 * @param parentDashboard
	 */
	public void setParentDashboard(Long parentDashboard)
	{
		this.parentDashboard = parentDashboard;
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
		
		if (!(_other instanceof Dashboard)) {
			return false;
		}
		
		final Dashboard _cast = (Dashboard) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (isDefault != _cast.isDefault) {
			return false;
		}
		
		if (title == null ? _cast.title != title : !title.equals( _cast.title )) {
			return false;
		}
		
		if (parentDashboard != _cast.parentDashboard) {
			return false;
		}
		
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
		_hashCode = 29 * _hashCode + (int) (id ^ (id >>> 32));
		_hashCode = 29 * _hashCode + (int) isDefault;
		if (title != null) {
			_hashCode = 29 * _hashCode + title.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (parentDashboard ^ (parentDashboard >>> 32));
		if (username != null) {
			_hashCode = 29 * _hashCode + username.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DashboardPk
	 */
	public DashboardPk createPk()
	{
		return new DashboardPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dashboard.model.Dashboard: " );
		ret.append( "id=" + id );
		ret.append( ", isDefault=" + isDefault );
		ret.append( ", title=" + title );
		ret.append( ", parentDashboard=" + parentDashboard );
		ret.append( ", username=" + username );
		return ret.toString();
	}

}
