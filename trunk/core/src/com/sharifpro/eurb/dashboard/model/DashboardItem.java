package com.sharifpro.eurb.dashboard.model;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class DashboardItem extends PersistableObject
{
	private static final long serialVersionUID = -7891108025925147503L;

	/** 
	 * This attribute maps to the column dashboard_id in the dashboard_item table.
	 */
	protected long dashboardId;

	/** 
	 * This attribute maps to the column dashboard_col_id in the dashboard_item table.
	 */
	protected Long dashboardColId;

	/** 
	 * This attribute maps to the column item_order in the dashboard_item table.
	 */
	protected int itemOrder;

	/** 
	 * This attribute maps to the column item_height in the dashboard_item table.
	 */
	protected Double itemHeight;

	/** 
	 * This attribute maps to the column item_collapsed in the dashboard_item table.
	 */
	protected boolean itemCollapsed;

	/** 
	 * This attribute maps to the column item_closed in the dashboard_item table.
	 */
	protected boolean itemClosed;

	/** 
	 * This attribute maps to the column report_design_id in the dashboard_item table.
	 */
	protected Long reportDesignId;

	/** 
	 * This attribute maps to the column report_chart_id in the dashboard_item table.
	 */
	protected Long reportChartId;

	/** 
	 * This attribute maps to the column is_show_table in the dashboard_item table.
	 */
	protected short isShowTable;

	/** 
	 * This attribute maps to the column item_title in the dashboard_item table.
	 */
	protected String itemTitle;

	/** 
	 * This attribute maps to the column item_content in the dashboard_item table.
	 */
	protected String itemContent;

	/**
	 * Method 'DashboardItem'
	 * 
	 */
	public DashboardItem()
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
	 * Method 'getDashboardColId'
	 * 
	 * @return long
	 */
	public Long getDashboardColId()
	{
		return dashboardColId;
	}

	/**
	 * Method 'setDashboardColId'
	 * 
	 * @param dashboardColId
	 */
	public void setDashboardColId(Long dashboardColId)
	{
		this.dashboardColId = dashboardColId;
	}

	/**
	 * Method 'getItemOrder'
	 * 
	 * @return int
	 */
	public int getItemOrder()
	{
		return itemOrder;
	}

	/**
	 * Method 'setItemOrder'
	 * 
	 * @param itemOrder
	 */
	public void setItemOrder(int itemOrder)
	{
		this.itemOrder = itemOrder;
	}

	/**
	 * Method 'getItemHeight'
	 * 
	 * @return double
	 */
	public Double getItemHeight()
	{
		return itemHeight;
	}

	/**
	 * Method 'setItemHeight'
	 * 
	 * @param itemHeight
	 */
	public void setItemHeight(Double itemHeight)
	{
		this.itemHeight = itemHeight;
	}

	/**
	 * Method 'getItemCollapsed'
	 * 
	 * @return short
	 */
	public boolean getItemCollapsed()
	{
		return itemCollapsed;
	}

	/**
	 * Method 'setItemCollapsed'
	 * 
	 * @param itemCollapsed
	 */
	public void setItemCollapsed(boolean itemCollapsed)
	{
		this.itemCollapsed = itemCollapsed;
	}

	/**
	 * Method 'getItemClosed'
	 * 
	 * @return String
	 */
	public boolean getItemClosed()
	{
		return itemClosed;
	}

	/**
	 * Method 'setItemClosed'
	 * 
	 * @param itemClosed
	 */
	public void setItemClosed(boolean itemClosed)
	{
		this.itemClosed = itemClosed;
	}

	/**
	 * Method 'getReportDesignId'
	 * 
	 * @return long
	 */
	public Long getReportDesignId()
	{
		return reportDesignId;
	}

	/**
	 * Method 'setReportDesignId'
	 * 
	 * @param reportDesignId
	 */
	public void setReportDesignId(Long reportDesignId)
	{
		this.reportDesignId = reportDesignId;
	}

	/**
	 * Method 'getReportChartId'
	 * 
	 * @return long
	 */
	public Long getReportChartId()
	{
		return reportChartId;
	}

	/**
	 * Method 'setReportChartId'
	 * 
	 * @param reportChartId
	 */
	public void setReportChartId(Long reportChartId)
	{
		this.reportChartId = reportChartId;
	}

	/**
	 * Method 'getIsShowTable'
	 * 
	 * @return short
	 */
	public short getIsShowTable()
	{
		return isShowTable;
	}

	/**
	 * Method 'setIsShowTable'
	 * 
	 * @param isShowTable
	 */
	public void setIsShowTable(short isShowTable)
	{
		this.isShowTable = isShowTable;
	}

	/**
	 * Method 'getItemTitle'
	 * 
	 * @return String
	 */
	public String getItemTitle()
	{
		return itemTitle;
	}

	/**
	 * Method 'setItemTitle'
	 * 
	 * @param itemTitle
	 */
	public void setItemTitle(String itemTitle)
	{
		this.itemTitle = itemTitle;
	}

	/**
	 * Method 'getItemContent'
	 * 
	 * @return String
	 */
	public String getItemContent()
	{
		return itemContent;
	}

	/**
	 * Method 'setItemContent'
	 * 
	 * @param itemContent
	 */
	public void setItemContent(String itemContent)
	{
		this.itemContent = itemContent;
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
		
		if (!(_other instanceof DashboardItem)) {
			return false;
		}
		
		final DashboardItem _cast = (DashboardItem) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (dashboardId != _cast.dashboardId) {
			return false;
		}
		
		if (dashboardColId != _cast.dashboardColId) {
			return false;
		}
		
		if (itemOrder != _cast.itemOrder) {
			return false;
		}
		
		if (itemHeight != _cast.itemHeight) {
			return false;
		}
		
		if (itemCollapsed != _cast.itemCollapsed) {
			return false;
		}
		
		if (itemClosed != _cast.itemClosed) {
			return false;
		}
		
		if (reportDesignId != _cast.reportDesignId) {
			return false;
		}
		
		if (reportChartId != _cast.reportChartId) {
			return false;
		}
		
		if (isShowTable != _cast.isShowTable) {
			return false;
		}
		
		if (itemTitle == null ? _cast.itemTitle != itemTitle : !itemTitle.equals( _cast.itemTitle )) {
			return false;
		}
		
		if (itemContent == null ? _cast.itemContent != itemContent : !itemContent.equals( _cast.itemContent )) {
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
		_hashCode = 29 * _hashCode + (int) (dashboardColId ^ (dashboardColId >>> 32));
		_hashCode = 29 * _hashCode + itemOrder;
		long temp_itemHeight = Double.doubleToLongBits(itemHeight);
		_hashCode = 29 * _hashCode + (int) (temp_itemHeight ^ (temp_itemHeight >>> 32));
		
		_hashCode = 29 * _hashCode + (int) (reportDesignId ^ (reportDesignId >>> 32));
		_hashCode = 29 * _hashCode + (int) (reportChartId ^ (reportChartId >>> 32));
		_hashCode = 29 * _hashCode + (int) isShowTable;
		if (itemTitle != null) {
			_hashCode = 29 * _hashCode + itemTitle.hashCode();
		}
		
		if (itemContent != null) {
			_hashCode = 29 * _hashCode + itemContent.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DashboardItemPk
	 */
	public DashboardItemPk createPk()
	{
		return new DashboardItemPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dashboard.dto.DashboardItem: " );
		ret.append( "id=" + id );
		ret.append( ", dashboardId=" + dashboardId );
		ret.append( ", dashboardColId=" + dashboardColId );
		ret.append( ", itemOrder=" + itemOrder );
		ret.append( ", itemHeight=" + itemHeight );
		ret.append( ", itemCollapsed=" + itemCollapsed );
		ret.append( ", itemClosed=" + itemClosed );
		ret.append( ", reportDesignId=" + reportDesignId );
		ret.append( ", reportChartId=" + reportChartId );
		ret.append( ", isShowTable=" + isShowTable );
		ret.append( ", itemTitle=" + itemTitle );
		ret.append( ", itemContent=" + itemContent );
		return ret.toString();
	}

}
