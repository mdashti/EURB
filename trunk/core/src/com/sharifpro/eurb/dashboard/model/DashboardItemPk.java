package com.sharifpro.eurb.dashboard.model;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the dashboard_item table.
 */
public class DashboardItemPk extends PersistableObjectPk
{
	private static final long serialVersionUID = -970392715844219109L;

	/**
	 * Method 'DashboardItemPk'
	 * 
	 */
	public DashboardItemPk()
	{
	}

	/**
	 * Method 'DashboardItemPk'
	 * 
	 * @param id
	 */
	public DashboardItemPk(final long id)
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
		
		if (!(_other instanceof DashboardItemPk)) {
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
		ret.append( "model.DashboardItemPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
