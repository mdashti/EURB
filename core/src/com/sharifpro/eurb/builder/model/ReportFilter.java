package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class ReportFilter extends PersistableObject implements Serializable
{
	public static final Integer FILTER_TYPE_JOIN = new Integer(1);
	public static final Integer FILTER_TYPE_NORMAL = new Integer(0);

	private static final long serialVersionUID = -5852320298976109788L;

	/** 
	 * This attribute maps to the column report_column_id in the report_filter table.
	 */
	protected Long columnMappingId;
	
	protected String selectedColumn;

	/** 
	 * This attribute maps to the column report_column_dataset_id in the report_filter table.
	 */
	protected Long reportDatasetId;

	/** 
	 * This attribute maps to the column report_column_design_id in the report_filter table.
	 */
	protected Long reportDesignId;

	/** 
	 * This attribute maps to the column report_column_design_version_id in the report_filter table.
	 */
	protected Long reportDesignVersionId;

	/** 
	 * This attribute maps to the column prefix in the report_filter table.
	 */
	protected String prefix;

	/** 
	 * This attribute maps to the column operator in the report_filter table.
	 */
	protected String operator;

	/** 
	 * This attribute maps to the column suffix in the report_filter table.
	 */
	protected String suffix;

	/** 
	 * This attribute maps to the column operand1 in the report_filter table.
	 */
	protected String operand1;

	/** 
	 * This attribute maps to the column operand2 in the report_filter table.
	 */
	protected String operand2;

	/** 
	 * This attribute maps to the column filter_type in the report_filter table.
	 */
	protected Integer filterType;

	/** 
	 * This attribute maps to the column operand1_column_mapping_id in the report_filter table.
	 */
	protected Long operand1ColumnMappingId;
	
	protected String operand1SelectedColumn;

	/** 
	 * This attribute maps to the column operand1_dataset_id in the report_filter table.
	 */
	protected Long operand1DatasetId;

	/** 
	 * This attribute maps to the column operand1_column_id in the report_filter table.
	 */
	protected Long operand1ReportColumnId;

	/**
	 * Method 'ReportFilter'
	 * 
	 */
	public ReportFilter()
	{
		super();
	}

	/**
	 * Method 'getReportColumnId'
	 * 
	 * @return Long
	 */
	public Long getColumnMappingId()
	{
		return columnMappingId;
	}

	/**
	 * Method 'setReportColumnId'
	 * 
	 * @param columnMappingId
	 */
	public void setColumnMappingId(Long columnMappingId)
	{
		this.columnMappingId = columnMappingId;
	}
	
	public String getSelectedColumn()
	{
		return this.reportDatasetId + "-" + this.columnMappingId;
	}

	/**
	 * Method 'getReportColumnDatasetId'
	 * 
	 * @return Long
	 */
	public Long getReportDatasetId()
	{
		return reportDatasetId;
	}

	/**
	 * Method 'setReportColumnDatasetId'
	 * 
	 * @param reportDatasetId
	 */
	public void setReportDatasetId(Long reportDatasetId)
	{
		this.reportDatasetId = reportDatasetId;
	}

	/**
	 * Method 'getReportColumnDesignId'
	 * 
	 * @return Long
	 */
	public Long getReportDesignId()
	{
		return reportDesignId;
	}

	/**
	 * Method 'setReportColumnDesignId'
	 * 
	 * @param reportDesignId
	 */
	public void setReportDesignId(Long reportDesignId)
	{
		this.reportDesignId = reportDesignId;
	}

	/**
	 * Method 'getReportColumnDesignVersionId'
	 * 
	 * @return Long
	 */
	public Long getReportDesignVersionId()
	{
		return reportDesignVersionId;
	}

	/**
	 * Method 'setReportColumnDesignVersionId'
	 * 
	 * @param reportDesignVersionId
	 */
	public void setReportDesignVersionId(Long reportDesignVersionId)
	{
		this.reportDesignVersionId = reportDesignVersionId;
	}

	/**
	 * Method 'getPrefix'
	 * 
	 * @return String
	 */
	public String getPrefix()
	{
		return prefix;
	}

	/**
	 * Method 'setPrefix'
	 * 
	 * @param prefix
	 */
	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	/**
	 * Method 'getOperator'
	 * 
	 * @return String
	 */
	public String getOperator()
	{
		return operator;
	}

	/**
	 * Method 'setOperator'
	 * 
	 * @param operator
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * Method 'getOperatorObj'
	 * 
	 * @return ReportFilterOperator
	 */
	public ReportFilterOperator getOperatorObj()
	{
		return ReportFilterOperator.getInstance(operator);
	}

	/**
	 * Method 'getSuffix'
	 * 
	 * @return String
	 */
	public String getSuffix()
	{
		return suffix;
	}

	/**
	 * Method 'setSuffix'
	 * 
	 * @param suffix
	 */
	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}

	/**
	 * Method 'getOperand1'
	 * 
	 * @return String
	 */
	public String getOperand1()
	{
		return operand1;
	}

	/**
	 * Method 'setOperand1'
	 * 
	 * @param operand1
	 */
	public void setOperand1(String operand1)
	{
		this.operand1 = operand1;
	}

	/**
	 * Method 'getOperand2'
	 * 
	 * @return String
	 */
	public String getOperand2()
	{
		return operand2;
	}

	/**
	 * Method 'setOperand2'
	 * 
	 * @param operand2
	 */
	public void setOperand2(String operand2)
	{
		this.operand2 = operand2;
	}

	/**
	 * Method 'getFilterType'
	 * 
	 * @return Integer
	 */
	public Integer getFilterType()
	{
		return filterType;
	}

	/**
	 * Method 'setFilterType'
	 * 
	 * @param filterType
	 */
	public void setFilterType(Integer filterType)
	{
		this.filterType = filterType;
	}

	/**
	 * Method 'getOperand1ColumnMappingId'
	 * 
	 * @return Long
	 */
	public Long getOperand1ColumnMappingId()
	{
		return (operand1ColumnMappingId == null || operand1ColumnMappingId == 0) ? null : operand1ColumnMappingId;
	}

	/**
	 * Method 'setOperand1ColumnMappingId'
	 * 
	 * @param operand1ColumnMappingId
	 */
	public void setOperand1ColumnMappingId(Long operand1ColumnMappingId)
	{
		this.operand1ColumnMappingId = operand1ColumnMappingId;
	}
	
	public String getOperand1SelectedColumn()
	{
		return this.operand1DatasetId + "-" + this.operand1ColumnMappingId;
	}

	/**
	 * Method 'getOperand1DatasetId'
	 * 
	 * @return Long
	 */
	public Long getOperand1DatasetId()
	{
		return (operand1DatasetId == null || operand1DatasetId == 0) ? null : operand1DatasetId;
	}

	/**
	 * Method 'setOperand1DatasetId'
	 * 
	 * @param operand1DatasetId
	 */
	public void setOperand1DatasetId(Long operand1DatasetId)
	{
		this.operand1DatasetId = operand1DatasetId;
	}

	/**
	 * Method 'getOperand1ReportColumnId'
	 * 
	 * @return Long
	 */
	public Long getOperand1ReportColumnId()
	{
		return operand1ReportColumnId;
	}

	/**
	 * Method 'setOperand1ReportColumnId'
	 * 
	 * @param operand1ReportColumnId
	 */
	public void setOperand1ReportColumnId(Long operand1ReportColumnId)
	{
		this.operand1ReportColumnId = operand1ReportColumnId;
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
		
		if (!(_other instanceof ReportFilter)) {
			return false;
		}
		
		/*final ReportFilter _cast = (ReportFilter) _other;
		
		if (reportColumnId == null ? _cast.reportColumnId != reportColumnId : !reportColumnId.equals( _cast.reportColumnId )) {
			return false;
		}
		
		if (reportColumnDatasetId == null ? _cast.reportColumnDatasetId != reportColumnDatasetId : !reportColumnDatasetId.equals( _cast.reportColumnDatasetId )) {
			return false;
		}
		
		if (reportColumnDesignId == null ? _cast.reportColumnDesignId != reportColumnDesignId : !reportColumnDesignId.equals( _cast.reportColumnDesignId )) {
			return false;
		}
		
		if (reportColumnDesignVersionId == null ? _cast.reportColumnDesignVersionId != reportColumnDesignVersionId : !reportColumnDesignVersionId.equals( _cast.reportColumnDesignVersionId )) {
			return false;
		}
		
		if (prefix == null ? _cast.prefix != prefix : !prefix.equals( _cast.prefix )) {
			return false;
		}
		
		if (operator == null ? _cast.operator != operator : !operator.equals( _cast.operator )) {
			return false;
		}
		
		if (suffix == null ? _cast.suffix != suffix : !suffix.equals( _cast.suffix )) {
			return false;
		}
		
		if (operand1 == null ? _cast.operand1 != operand1 : !operand1.equals( _cast.operand1 )) {
			return false;
		}
		
		if (operand2 == null ? _cast.operand2 != operand2 : !operand2.equals( _cast.operand2 )) {
			return false;
		}
		
		if (filterType == null ? _cast.filterType != filterType : !filterType.equals( _cast.filterType )) {
			return false;
		}
		
		if (operand1ColumnId == null ? _cast.operand1ColumnId != operand1ColumnId : !operand1ColumnId.equals( _cast.operand1ColumnId )) {
			return false;
		}
		
		if (operand1ColumnDatasetId == null ? _cast.operand1ColumnDatasetId != operand1ColumnDatasetId : !operand1ColumnDatasetId.equals( _cast.operand1ColumnDatasetId )) {
			return false;
		}
		
		if (operand1ColumnDesignId == null ? _cast.operand1ColumnDesignId != operand1ColumnDesignId : !operand1ColumnDesignId.equals( _cast.operand1ColumnDesignId )) {
			return false;
		}*/
		
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
		
		/*if (reportColumnId != null) {
			_hashCode = 29 * _hashCode + reportColumnId.hashCode();
		}
		
		if (reportColumnDatasetId != null) {
			_hashCode = 29 * _hashCode + reportColumnDatasetId.hashCode();
		}
		
		if (reportColumnDesignId != null) {
			_hashCode = 29 * _hashCode + reportColumnDesignId.hashCode();
		}
		
		if (reportColumnDesignVersionId != null) {
			_hashCode = 29 * _hashCode + reportColumnDesignVersionId.hashCode();
		}
		
		if (prefix != null) {
			_hashCode = 29 * _hashCode + prefix.hashCode();
		}
		
		if (operator != null) {
			_hashCode = 29 * _hashCode + operator.hashCode();
		}
		
		if (suffix != null) {
			_hashCode = 29 * _hashCode + suffix.hashCode();
		}
		
		if (operand1 != null) {
			_hashCode = 29 * _hashCode + operand1.hashCode();
		}
		
		if (operand2 != null) {
			_hashCode = 29 * _hashCode + operand2.hashCode();
		}
		
		if (filterType != null) {
			_hashCode = 29 * _hashCode + filterType.hashCode();
		}
		
		if (operand1ColumnId != null) {
			_hashCode = 29 * _hashCode + operand1ColumnId.hashCode();
		}
		
		if (operand1ColumnDatasetId != null) {
			_hashCode = 29 * _hashCode + operand1ColumnDatasetId.hashCode();
		}
		
		if (operand1ColumnDesignId != null) {
			_hashCode = 29 * _hashCode + operand1ColumnDesignId.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportFilterPk
	 */
	public ReportFilterPk createPk()
	{
		return new ReportFilterPk(id);
	}
	
	@JsonIgnore
	public boolean isJoinFilter() {
		return FILTER_TYPE_JOIN.equals(filterType);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportFilter: " );
		ret.append( super.toString() );
		ret.append( ", reportColumnId=" + columnMappingId );
		ret.append( ", reportColumnDatasetId=" + reportDatasetId );
		ret.append( ", reportColumnDesignId=" + reportDesignId );
		ret.append( ", reportColumnDesignVersionId=" + reportDesignVersionId );
		ret.append( ", prefix=" + prefix );
		ret.append( ", operator=" + operator );
		ret.append( ", suffix=" + suffix );
		ret.append( ", operand1=" + operand1 );
		ret.append( ", operand2=" + operand2 );
		ret.append( ", filterType=" + filterType );
		ret.append( ", operand1ColumnId=" + operand1ColumnMappingId );
		ret.append( ", operand1ColumnDatasetId=" + operand1DatasetId );
		ret.append( ", operand1ColumnDesignId=" + operand1ReportColumnId );
		return ret.toString();
	}

	public static class ReportFilterOperator {
		public final String operator;
		public final int numberOfOperands;

		private final static ReportFilterOperator IS_NOT_NULL = new ReportFilterOperator("IS NOT NULL", 0);
		private final static ReportFilterOperator IS_NULL = new ReportFilterOperator("IS NULL", 1);
		private final static ReportFilterOperator EQUALS = new ReportFilterOperator("=", 1);
		private final static ReportFilterOperator NOT_EQUALS = new ReportFilterOperator("<>", 1);
		private final static ReportFilterOperator SMALLER_THAN = new ReportFilterOperator("<", 1);
		private final static ReportFilterOperator GREATER_THAN = new ReportFilterOperator(">", 1);
		private final static ReportFilterOperator BETWEEN = new ReportFilterOperator("BETWEEN", 1);
		private final static ReportFilterOperator LIKE = new ReportFilterOperator("LIKE", 1);

		
		private ReportFilterOperator(String operator, int numberOfOperands) {
			this.operator = operator;
			this.numberOfOperands = numberOfOperands;
		}
		
		public static ReportFilterOperator getInstance(String operator) {
			if(IS_NOT_NULL.operator.equalsIgnoreCase(operator)) {
				return IS_NOT_NULL;
			} else if(IS_NULL.operator.equalsIgnoreCase(operator)) {
				return IS_NULL;
			} else if(EQUALS.operator.equalsIgnoreCase(operator)) {
				return EQUALS;
			} else if(NOT_EQUALS.operator.equalsIgnoreCase(operator)) {
				return NOT_EQUALS;
			} else if(SMALLER_THAN.operator.equalsIgnoreCase(operator)) {
				return SMALLER_THAN;
			} else if(GREATER_THAN.operator.equalsIgnoreCase(operator)) {
				return GREATER_THAN;
			} else if(BETWEEN.operator.equalsIgnoreCase(operator)) {
				return BETWEEN;
			} else if(LIKE.operator.equalsIgnoreCase(operator)) {
				return LIKE;
			}
			return null;
		}
	}
}
