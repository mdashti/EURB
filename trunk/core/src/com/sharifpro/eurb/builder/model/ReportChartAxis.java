package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class ReportChartAxis extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -5852320298976109788L;

	/** 
	 * This attribute maps to the column chart_id in the report_chart_axis table.
	 */
	protected Long chartId;

	/** 
	 * This attribute maps to the column column_mapping_id in the report_chart_axis table.
	 */
	protected Long columnMappingId;

	/** 
	 * This attribute maps to the column axis_type in the report_chart_axis table.
	 */
	protected String type;

	/** 
	 * This attribute maps to the column title in the report_chart_axis table.
	 */
	protected String title;

	/** 
	 * This attribute maps to the column aggregation in the report_chart_axis table.
	 */
	protected String aggregation;

	
	/**
	 * Method 'ReportChart'
	 * 
	 */
	public ReportChartAxis()
	{
		super();
	}


	/**
	 * Method 'getChartId'
	 * 
	 * @return Long
	 */
	public Long getChartId()
	{
		return chartId;
	}

	/**
	 * Method 'setChartId'
	 * 
	 * @param chartId
	 */
	public void setChartId(Long chartId)
	{
		this.chartId = chartId;
	}

	/**
	 * Method 'getColumnMappingId'
	 * 
	 * @return Long
	 */
	public Long getColumnMappingId()
	{
		return columnMappingId;
	}

	/**
	 * Method 'setColumnMappingId'
	 * 
	 * @param columnMappingId
	 */
	public void setColumnMappingId(Long columnMappingId)
	{
		this.columnMappingId = columnMappingId;
	}

	/**
	 * Method 'getType'
	 * 
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
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
		
		if (!(_other instanceof ReportChart)) {
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
		
				return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportChartAxisPk
	 */
	public ReportChartAxisPk createPk()
	{
		return new ReportChartAxisPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportChartAxis: " );
		ret.append( super.toString() );
		ret.append( ", chartId=" + chartId );
		ret.append( ", columnMappingId=" + columnMappingId );
		ret.append( ", type=" + type );
		ret.append( ", title=" + title );
		return ret.toString();
	}

}
