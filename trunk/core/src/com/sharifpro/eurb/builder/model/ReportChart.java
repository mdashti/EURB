package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class ReportChart extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -5852320298976109788L;

	/** 
	 * This attribute maps to the column report_design_id in the report_chart table.
	 */
	protected Long reportDesignId;

	/** 
	 * This attribute maps to the column report_column_design_version_id in the report_chart table.
	 */
	protected Long reportDesignVersionId;

	/** 
	 * This attribute maps to the column chart_type in the report_chart table.
	 */
	protected String type;

	/** 
	 * This attribute maps to the column name in the report_chart table.
	 */
	protected String name;

	
	/**
	 * Method 'ReportChart'
	 * 
	 */
	public ReportChart()
	{
		super();
	}

	



	/**
	 * Method 'getReportDesignId'
	 * 
	 * @return Long
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
	 * Method 'getReportDesignVersionId'
	 * 
	 * @return Long
	 */
	public Long getReportDesignVersionId()
	{
		return reportDesignVersionId;
	}

	/**
	 * Method 'setReportDesignVersionId'
	 * 
	 * @param reportDesignVersionId
	 */
	public void setReportDesignVersionId(Long reportDesignVersionId)
	{
		this.reportDesignVersionId = reportDesignVersionId;
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
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
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
	 * @return ReportChartPk
	 */
	public ReportChartPk createPk()
	{
		return new ReportChartPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportChart: " );
		ret.append( super.toString() );
		ret.append( ", reportDesignId=" + reportDesignId );
		ret.append( ", reportDesignVersionId=" + reportDesignVersionId );
		ret.append( ", type=" + type );
		ret.append( ", name=" + name );
		return ret.toString();
	}

}
