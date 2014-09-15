package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the report_chart table.
 */
public class ReportChartPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -3518340072436103311L;

	/**
	 * Method 'ReportChartPk'
	 * 
	 */
	public ReportChartPk()
	{
		super();
	}

	/**
	 * Method 'ReportChartPk'
	 * 
	 * @param id
	 */
	public ReportChartPk(final Long id)
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
		
		if (!(_other instanceof ReportChartPk)) {
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
		ret.append( "model.ReportChartPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
