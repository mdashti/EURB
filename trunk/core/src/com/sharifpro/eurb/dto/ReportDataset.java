package com.sharifpro.eurb.dto;

import java.io.Serializable;

public class ReportDataset extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = 7156092611112201177L;

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
	 * This attribute maps to the column ds_order in the report_dataset table.
	 */
	protected Integer dsOrder;

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
		super();
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
	 * Method 'getDsOrder'
	 * 
	 * @return Integer
	 */
	public Integer getDsOrder()
	{
		return dsOrder;
	}

	/**
	 * Method 'setDsOrder'
	 * 
	 * @param dsOrder
	 */
	public void setDsOrder(Integer dsOrder)
	{
		this.dsOrder = dsOrder;
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
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof ReportDataset)) {
			return false;
		}
		
		final ReportDataset _cast = (ReportDataset) _other;
		
		if (designId == null ? _cast.designId != designId : !designId.equals( _cast.designId )) {
			return false;
		}
		
		if (designVersionId == null ? _cast.designVersionId != designVersionId : !designVersionId.equals( _cast.designVersionId )) {
			return false;
		}
		
		/*if (tableMappingId == null ? _cast.tableMappingId != tableMappingId : !tableMappingId.equals( _cast.tableMappingId )) {
			return false;
		}
		
		if (baseReportId == null ? _cast.baseReportId != baseReportId : !baseReportId.equals( _cast.baseReportId )) {
			return false;
		}
		
		if (baseReportVersionId == null ? _cast.baseReportVersionId != baseReportVersionId : !baseReportVersionId.equals( _cast.baseReportVersionId )) {
			return false;
		}
		
		if (dsOrder == null ? _cast.dsOrder != dsOrder : !dsOrder.equals( _cast.dsOrder )) {
			return false;
		}
		
		if (operator == null ? _cast.operator != operator : !operator.equals( _cast.operator )) {
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
		
		if (designId != null) {
			_hashCode = 29 * _hashCode + designId.hashCode();
		}
		
		if (designVersionId != null) {
			_hashCode = 29 * _hashCode + designVersionId.hashCode();
		}
		
		/*if (tableMappingId != null) {
			_hashCode = 29 * _hashCode + tableMappingId.hashCode();
		}
		
		if (baseReportId != null) {
			_hashCode = 29 * _hashCode + baseReportId.hashCode();
		}
		
		if (baseReportVersionId != null) {
			_hashCode = 29 * _hashCode + baseReportVersionId.hashCode();
		}
		
		if (dsOrder != null) {
			_hashCode = 29 * _hashCode + dsOrder.hashCode();
		}
		
		if (operator != null) {
			_hashCode = 29 * _hashCode + operator.hashCode();
		}*/
		
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
		ret.append( "model.ReportDataset: " );
		ret.append( super.toString() );
		ret.append( ", designId=" + designId );
		ret.append( ", designVersionId=" + designVersionId );
		ret.append( ", tableMappingId=" + tableMappingId );
		ret.append( ", baseReportId=" + baseReportId );
		ret.append( ", baseReportVersionId=" + baseReportVersionId );
		ret.append( ", dsOrder=" + dsOrder );
		ret.append( ", operator=" + operator );
		return ret.toString();
	}

}
