package com.sharifpro.eurb.dashboard.model;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class DashboardCol extends PersistableObject
{
	private static final long serialVersionUID = 6423443425919017771L;

	/** 
	 * This attribute maps to the column dashboard_id in the dashboard_col table.
	 */
	protected long dashboardId;

	/** 
	 * This attribute maps to the column col_order in the dashboard_col table.
	 */
	protected int colOrder;

	/** 
	 * This attribute maps to the column col_width in the dashboard_col table.
	 */
	protected Double colWidth;

	/**
	 * Method 'DashboardCol'
	 * 
	 */
	public DashboardCol()
	{
	}

	/**
	 * Method 'getDashboardId'
	 * 
	 * @return long
	 */
	public long getDashboardId()
	{
		return dashboardId;
	}

	/**
	 * Method 'setDashboardId'
	 * 
	 * @param dashboardId
	 */
	public void setDashboardId(long dashboardId)
	{
		this.dashboardId = dashboardId;
	}

	/**
	 * Method 'getColOrder'
	 * 
	 * @return int
	 */
	public int getColOrder()
	{
		return colOrder;
	}

	/**
	 * Method 'setColOrder'
	 * 
	 * @param colOrder
	 */
	public void setColOrder(int colOrder)
	{
		this.colOrder = colOrder;
	}

	/**
	 * Method 'getColWidth'
	 * 
	 * @return double
	 */
	public Double getColWidth()
	{
		return colWidth;
	}

	/**
	 * Method 'setColWidth'
	 * 
	 * @param colWidth
	 */
	public void setColWidth(Double colWidth)
	{
		this.colWidth = colWidth;
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
		
		if (!(_other instanceof DashboardCol)) {
			return false;
		}
		
		final DashboardCol _cast = (DashboardCol) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (dashboardId != _cast.dashboardId) {
			return false;
		}
		
		if (colOrder != _cast.colOrder) {
			return false;
		}
		
		if (colWidth != _cast.colWidth) {
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
		_hashCode = 29 * _hashCode + (int) (dashboardId ^ (dashboardId >>> 32));
		_hashCode = 29 * _hashCode + colOrder;
		long temp_colWidth = Double.doubleToLongBits(colWidth);
		_hashCode = 29 * _hashCode + (int) (temp_colWidth ^ (temp_colWidth >>> 32));
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DashboardColPk
	 */
	public DashboardColPk createPk()
	{
		return new DashboardColPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dashboard.model.DashboardCol: " );
		ret.append( "id=" + id );
		ret.append( ", dashboardId=" + dashboardId );
		ret.append( ", colOrder=" + colOrder );
		ret.append( ", colWidth=" + colWidth );
		return ret.toString();
	}

}
