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
	
	protected String selectedColumn;
	
	/** 
	 * This attribute maps to the column report_dataset_id in the report_chart_axis table.
	 */
	protected Long datasetId;
	

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
	 * This attribute maps to the column has_formula in the report_chart_axis table.
	 */
	protected boolean hasFormula;
	
	/** 
	 * This attribute maps to the column formula in the report_chart_axis table.
	 */
	protected String formula;
	

	
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
		return (columnMappingId == null || columnMappingId == 0) ? null : columnMappingId;
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
	
	public String getSelectedColumn()
	{
		if(this.datasetId == null || this.datasetId == 0 || this.columnMappingId == null || this.columnMappingId == 0)
		{
			return null;
		}
		return this.datasetId + "-" + this.columnMappingId;
	}
	
	/**
	 * Method 'getDatasetId
	 * 
	 * @return Long
	 */
	public Long getDatasetId()
	{
		return (datasetId == null || datasetId == 0 ) ? null : datasetId;
	}
	
	/**
	 * Method 'setDatasetId'
	 * 
	 * @param datasetId
	 */
	public void setDatasetId(Long datasetId){
		this.datasetId = datasetId;
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
	 * Method 'getAggregation'
	 * 
	 * @return String
	 */
	public String getAggregation()
	{
		return aggregation;
	}
	
	/**
	 * Method 'hasAggregation'
	 * 
	 * @return true if aggregation is not empty
	 */
	public boolean hasAggregation()
	{
		return (aggregation != null && !aggregation.equals(""));
	}

	/**
	 * Method 'setAggregation'
	 * 
	 * @param aggregation
	 */
	public void setAggregation(String aggregation)
	{
		this.aggregation = aggregation;
	}
	

	public boolean hasFormula()
	{
		return hasFormula;
	}
	
	public void setHasFormula(boolean hasFormula)
	{
		this.hasFormula = hasFormula;
	}
	
	
	public String getFormula()
	{
		return formula;
	}

	public void setFormula(String formula)
	{
		this.formula = formula;
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
		ret.append( ", hasFormula=" + hasFormula);
		ret.append( ", formula=" + formula);
		return ret.toString();
	}

}
