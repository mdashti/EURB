package com.sharifpro.eurb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the report_column table.
 */
public class ReportColumnPk implements Serializable
{
	protected Long id;

	protected Long datasetId;

	protected Long designId;

	protected Long designVersionId;

	/** 
	 * Sets the value of id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/** 
	 * Gets the value of id
	 */
	public Long getId()
	{
		return id;
	}

	/** 
	 * Sets the value of datasetId
	 */
	public void setDatasetId(Long datasetId)
	{
		this.datasetId = datasetId;
	}

	/** 
	 * Gets the value of datasetId
	 */
	public Long getDatasetId()
	{
		return datasetId;
	}

	/** 
	 * Sets the value of designId
	 */
	public void setDesignId(Long designId)
	{
		this.designId = designId;
	}

	/** 
	 * Gets the value of designId
	 */
	public Long getDesignId()
	{
		return designId;
	}

	/** 
	 * Sets the value of designVersionId
	 */
	public void setDesignVersionId(Long designVersionId)
	{
		this.designVersionId = designVersionId;
	}

	/** 
	 * Gets the value of designVersionId
	 */
	public Long getDesignVersionId()
	{
		return designVersionId;
	}

	/**
	 * Method 'ReportColumnPk'
	 * 
	 */
	public ReportColumnPk()
	{
	}

	/**
	 * Method 'ReportColumnPk'
	 * 
	 * @param id
	 * @param datasetId
	 * @param designId
	 * @param designVersionId
	 */
	public ReportColumnPk(final Long id, final Long datasetId, final Long designId, final Long designVersionId)
	{
		this.id = id;
		this.datasetId = datasetId;
		this.designId = designId;
		this.designVersionId = designVersionId;
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
		
		if (!(_other instanceof ReportColumnPk)) {
			return false;
		}
		
		final ReportColumnPk _cast = (ReportColumnPk) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		if (datasetId == null ? _cast.datasetId != datasetId : !datasetId.equals( _cast.datasetId )) {
			return false;
		}
		
		if (designId == null ? _cast.designId != designId : !designId.equals( _cast.designId )) {
			return false;
		}
		
		if (designVersionId == null ? _cast.designVersionId != designVersionId : !designVersionId.equals( _cast.designVersionId )) {
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
		
		if (datasetId != null) {
			_hashCode = 29 * _hashCode + datasetId.hashCode();
		}
		
		if (designId != null) {
			_hashCode = 29 * _hashCode + designId.hashCode();
		}
		
		if (designVersionId != null) {
			_hashCode = 29 * _hashCode + designVersionId.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.ReportColumnPk: " );
		ret.append( "id=" + id );
		ret.append( ", datasetId=" + datasetId );
		ret.append( ", designId=" + designId );
		ret.append( ", designVersionId=" + designVersionId );
		return ret.toString();
	}

}
