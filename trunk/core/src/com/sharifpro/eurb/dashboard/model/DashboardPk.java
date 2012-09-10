package com.sharifpro.eurb.dashboard.model;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the dashboard table.
 */
public class DashboardPk extends PersistableObjectPk
{
	private static final long serialVersionUID = 5729034263686134731L;

	/**
	 * Method 'DashboardPk'
	 * 
	 */
	public DashboardPk()
	{
	}

	/**
	 * Method 'DashboardPk'
	 * 
	 * @param id
	 */
	public DashboardPk(final long id)
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
		
		if (!(_other instanceof DashboardPk)) {
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
		ret.append( "model.DashboardPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
