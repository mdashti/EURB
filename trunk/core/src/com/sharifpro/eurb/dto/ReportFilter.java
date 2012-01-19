package com.sharifpro.eurb.dto;

import com.sharifpro.eurb.dao.*;
import com.sharifpro.eurb.factory.*;
import com.sharifpro.eurb.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class ReportFilter implements Serializable
{
	/** 
	 * This attribute maps to the column id in the report_filter table.
	 */
	protected Long id;

	/** 
	 * This attribute maps to the column report_column_id in the report_filter table.
	 */
	protected Long reportColumnId;

	/** 
	 * This attribute maps to the column report_column_dataset_id in the report_filter table.
	 */
	protected Long reportColumnDatasetId;

	/** 
	 * This attribute maps to the column report_column_design_id in the report_filter table.
	 */
	protected Long reportColumnDesignId;

	/** 
	 * This attribute maps to the column report_column_design_version_id in the report_filter table.
	 */
	protected Long reportColumnDesignVersionId;

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
	 * This attribute maps to the column type in the report_filter table.
	 */
	protected Integer type;

	/** 
	 * This attribute maps to the column operand1_column_id in the report_filter table.
	 */
	protected Long operand1ColumnId;

	/** 
	 * This attribute maps to the column operand1_column_dataset_id in the report_filter table.
	 */
	protected Long operand1ColumnDatasetId;

	/** 
	 * This attribute maps to the column operand1_column_design_id in the report_filter table.
	 */
	protected Long operand1ColumnDesignId;

	/**
	 * Method 'ReportFilter'
	 * 
	 */
	public ReportFilter()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return Long
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Method 'getReportColumnId'
	 * 
	 * @return Long
	 */
	public Long getReportColumnId()
	{
		return reportColumnId;
	}

	/**
	 * Method 'setReportColumnId'
	 * 
	 * @param reportColumnId
	 */
	public void setReportColumnId(Long reportColumnId)
	{
		this.reportColumnId = reportColumnId;
	}

	/**
	 * Method 'getReportColumnDatasetId'
	 * 
	 * @return Long
	 */
	public Long getReportColumnDatasetId()
	{
		return reportColumnDatasetId;
	}

	/**
	 * Method 'setReportColumnDatasetId'
	 * 
	 * @param reportColumnDatasetId
	 */
	public void setReportColumnDatasetId(Long reportColumnDatasetId)
	{
		this.reportColumnDatasetId = reportColumnDatasetId;
	}

	/**
	 * Method 'getReportColumnDesignId'
	 * 
	 * @return Long
	 */
	public Long getReportColumnDesignId()
	{
		return reportColumnDesignId;
	}

	/**
	 * Method 'setReportColumnDesignId'
	 * 
	 * @param reportColumnDesignId
	 */
	public void setReportColumnDesignId(Long reportColumnDesignId)
	{
		this.reportColumnDesignId = reportColumnDesignId;
	}

	/**
	 * Method 'getReportColumnDesignVersionId'
	 * 
	 * @return Long
	 */
	public Long getReportColumnDesignVersionId()
	{
		return reportColumnDesignVersionId;
	}

	/**
	 * Method 'setReportColumnDesignVersionId'
	 * 
	 * @param reportColumnDesignVersionId
	 */
	public void setReportColumnDesignVersionId(Long reportColumnDesignVersionId)
	{
		this.reportColumnDesignVersionId = reportColumnDesignVersionId;
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
	 * Method 'getType'
	 * 
	 * @return Integer
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(Integer type)
	{
		this.type = type;
	}

	/**
	 * Method 'getOperand1ColumnId'
	 * 
	 * @return Long
	 */
	public Long getOperand1ColumnId()
	{
		return operand1ColumnId;
	}

	/**
	 * Method 'setOperand1ColumnId'
	 * 
	 * @param operand1ColumnId
	 */
	public void setOperand1ColumnId(Long operand1ColumnId)
	{
		this.operand1ColumnId = operand1ColumnId;
	}

	/**
	 * Method 'getOperand1ColumnDatasetId'
	 * 
	 * @return Long
	 */
	public Long getOperand1ColumnDatasetId()
	{
		return operand1ColumnDatasetId;
	}

	/**
	 * Method 'setOperand1ColumnDatasetId'
	 * 
	 * @param operand1ColumnDatasetId
	 */
	public void setOperand1ColumnDatasetId(Long operand1ColumnDatasetId)
	{
		this.operand1ColumnDatasetId = operand1ColumnDatasetId;
	}

	/**
	 * Method 'getOperand1ColumnDesignId'
	 * 
	 * @return Long
	 */
	public Long getOperand1ColumnDesignId()
	{
		return operand1ColumnDesignId;
	}

	/**
	 * Method 'setOperand1ColumnDesignId'
	 * 
	 * @param operand1ColumnDesignId
	 */
	public void setOperand1ColumnDesignId(Long operand1ColumnDesignId)
	{
		this.operand1ColumnDesignId = operand1ColumnDesignId;
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
		
		if (!(_other instanceof ReportFilter)) {
			return false;
		}
		
		final ReportFilter _cast = (ReportFilter) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
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
		
		if (type == null ? _cast.type != type : !type.equals( _cast.type )) {
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
		if (id != null) {
			_hashCode = 29 * _hashCode + id.hashCode();
		}
		
		if (reportColumnId != null) {
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
		
		if (type != null) {
			_hashCode = 29 * _hashCode + type.hashCode();
		}
		
		if (operand1ColumnId != null) {
			_hashCode = 29 * _hashCode + operand1ColumnId.hashCode();
		}
		
		if (operand1ColumnDatasetId != null) {
			_hashCode = 29 * _hashCode + operand1ColumnDatasetId.hashCode();
		}
		
		if (operand1ColumnDesignId != null) {
			_hashCode = 29 * _hashCode + operand1ColumnDesignId.hashCode();
		}
		
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

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.ReportFilter: " );
		ret.append( "id=" + id );
		ret.append( ", reportColumnId=" + reportColumnId );
		ret.append( ", reportColumnDatasetId=" + reportColumnDatasetId );
		ret.append( ", reportColumnDesignId=" + reportColumnDesignId );
		ret.append( ", reportColumnDesignVersionId=" + reportColumnDesignVersionId );
		ret.append( ", prefix=" + prefix );
		ret.append( ", operator=" + operator );
		ret.append( ", suffix=" + suffix );
		ret.append( ", operand1=" + operand1 );
		ret.append( ", operand2=" + operand2 );
		ret.append( ", type=" + type );
		ret.append( ", operand1ColumnId=" + operand1ColumnId );
		ret.append( ", operand1ColumnDatasetId=" + operand1ColumnDatasetId );
		ret.append( ", operand1ColumnDesignId=" + operand1ColumnDesignId );
		return ret.toString();
	}

}
