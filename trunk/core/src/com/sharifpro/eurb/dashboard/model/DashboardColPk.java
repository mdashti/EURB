package com.sharifpro.eurb.dashboard.model;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the dashboard_col table.
 */
public class DashboardColPk extends PersistableObjectPk
{
	private static final long serialVersionUID = -3347462501662990105L;

	/**
	 * Method 'DashboardColPk'
	 * 
	 */
	public DashboardColPk()
	{
	}

	/**
	 * Method 'DashboardColPk'
	 * 
	 * @param id
	 */
	public DashboardColPk(final long id)
	{
		super(id);
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
		
		if (!(_other instanceof DashboardColPk)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.DashboardColPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
