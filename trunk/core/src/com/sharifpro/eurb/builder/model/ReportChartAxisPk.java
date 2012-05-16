package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the report_chart_axis table.
 */
public class ReportChartAxisPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -3518340072436103311L;

	/**
	 * Method 'ReportChartAxisPk'
	 * 
	 */
	public ReportChartAxisPk()
	{
		super();
	}

	/**
	 * Method 'ReportChartAxisPk'
	 * 
	 * @param id
	 */
	public ReportChartAxisPk(final Long id)
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
		
		if (!(_other instanceof ReportChartAxisPk)) {
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
		ret.append( "model.ReportChartAxisPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
