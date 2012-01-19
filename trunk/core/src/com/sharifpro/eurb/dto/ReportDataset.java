package com.sharifpro.eurb.dto;

import com.sharifpro.eurb.dao.*;
import com.sharifpro.eurb.factory.*;
import com.sharifpro.eurb.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class ReportDataset implements Serializable
{
	/** 
	 * This attribute maps to the column id in the report_dataset table.
	 */
	protected Long id;

	/** 
	 * This attribute maps to the column design_id in the report_dataset table.
	 */
	protected Long designId;

	/** 
	 * This attribute maps to the column design_version_id in the report_dataset table.
	 */
	protected Long designVersionId;

	/** 
	 * This attribute maps to the column table_mapping_id in the report_dataset table.
	 */
	protected Long tableMappingId;

	/** 
	 * This attribute maps to the column base_report_id in the report_dataset table.
	 */
	protected Long baseReportId;

	/** 
	 * This attribute maps to the column base_report_version_id in the report_dataset table.
	 */
	protected Long baseReportVersionId;

	/** 
	 * This attribute maps to the column order in the report_dataset table.
	 */
	protected Integer order;

	/** 
	 * This attribute maps to the column operator in the report_dataset table.
	 */
	protected Integer operator;

	/**
	 * Method 'ReportDataset'
	 * 
	 */
	public ReportDataset()
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
	 * Method 'getDesignId'
	 * 
	 * @return Long
	 */
	public Long getDesignId()
	{
		return designId;
	}

	/**
	 * Method 'setDesignId'
	 * 
	 * @param designId
	 */
	public void setDesignId(Long designId)
	{
		this.designId = designId;
	}

	/**
	 * Method 'getDesignVersionId'
	 * 
	 * @return Long
	 */
	public Long getDesignVersionId()
	{
		return designVersionId;
	}

	/**
	 * Method 'setDesignVersionId'
	 * 
	 * @param designVersionId
	 */
	public void setDesignVersionId(Long designVersionId)
	{
		this.designVersionId = designVersionId;
	}

	/**
	 * Method 'getTableMappingId'
	 * 
	 * @return Long
	 */
	public Long getTableMappingId()
	{
		return tableMappingId;
	}

	/**
	 * Method 'setTableMappingId'
	 * 
	 * @param tableMappingId
	 */
	public void setTableMappingId(Long tableMappingId)
	{
		this.tableMappingId = tableMappingId;
	}

	/**
	 * Method 'getBaseReportId'
	 * 
	 * @return Long
	 */
	public Long getBaseReportId()
	{
		return baseReportId;
	}

	/**
	 * Method 'setBaseReportId'
	 * 
	 * @param baseReportId
	 */
	public void setBaseReportId(Long baseReportId)
	{
		this.baseReportId = baseReportId;
	}

	/**
	 * Method 'getBaseReportVersionId'
	 * 
	 * @return Long
	 */
	public Long getBaseReportVersionId()
	{
		return baseReportVersionId;
	}

	/**
	 * Method 'setBaseReportVersionId'
	 * 
	 * @param baseReportVersionId
	 */
	public void setBaseReportVersionId(Long baseReportVersionId)
	{
		this.baseReportVersionId = baseReportVersionId;
	}

	/**
	 * Method 'getOrder'
	 * 
	 * @return Integer
	 */
	public Integer getOrder()
	{
		return order;
	}

	/**
	 * Method 'setOrder'
	 * 
	 * @param order
	 */
	public void setOrder(Integer order)
	{
		this.order = order;
	}

	/**
	 * Method 'getOperator'
	 * 
	 * @return Integer
	 */
	public Integer getOperator()
	{
		return operator;
	}

	/**
	 * Method 'setOperator'
	 * 
	 * @param operator
	 */
	public void setOperator(Integer operator)
	{
		this.operator = operator;
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
		
		if (!(_other instanceof ReportDataset)) {
			return false;
		}
		
		final ReportDataset _cast = (ReportDataset) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		if (designId == null ? _cast.designId != designId : !designId.equals( _cast.designId )) {
			return false;
		}
		
		if (designVersionId == null ? _cast.designVersionId != designVersionId : !designVersionId.equals( _cast.designVersionId )) {
			return false;
		}
		
		if (tableMappingId == null ? _cast.tableMappingId != tableMappingId : !tableMappingId.equals( _cast.tableMappingId )) {
			return false;
		}
		
		if (baseReportId == null ? _cast.baseReportId != baseReportId : !baseReportId.equals( _cast.baseReportId )) {
			return false;
		}
		
		if (baseReportVersionId == null ? _cast.baseReportVersionId != baseReportVersionId : !baseReportVersionId.equals( _cast.baseReportVersionId )) {
			return false;
		}
		
		if (order == null ? _cast.order != order : !order.equals( _cast.order )) {
			return false;
		}
		
		if (operator == null ? _cast.operator != operator : !operator.equals( _cast.operator )) {
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
		
		if (designId != null) {
			_hashCode = 29 * _hashCode + designId.hashCode();
		}
		
		if (designVersionId != null) {
			_hashCode = 29 * _hashCode + designVersionId.hashCode();
		}
		
		if (tableMappingId != null) {
			_hashCode = 29 * _hashCode + tableMappingId.hashCode();
		}
		
		if (baseReportId != null) {
			_hashCode = 29 * _hashCode + baseReportId.hashCode();
		}
		
		if (baseReportVersionId != null) {
			_hashCode = 29 * _hashCode + baseReportVersionId.hashCode();
		}
		
		if (order != null) {
			_hashCode = 29 * _hashCode + order.hashCode();
		}
		
		if (operator != null) {
			_hashCode = 29 * _hashCode + operator.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportDatasetPk
	 */
	public ReportDatasetPk createPk()
	{
		return new ReportDatasetPk(id, designId, designVersionId);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.ReportDataset: " );
		ret.append( "id=" + id );
		ret.append( ", designId=" + designId );
		ret.append( ", designVersionId=" + designVersionId );
		ret.append( ", tableMappingId=" + tableMappingId );
		ret.append( ", baseReportId=" + baseReportId );
		ret.append( ", baseReportVersionId=" + baseReportVersionId );
		ret.append( ", order=" + order );
		ret.append( ", operator=" + operator );
		return ret.toString();
	}

}
