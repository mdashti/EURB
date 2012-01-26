package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the report_dataset table.
 */
public class ReportDatasetPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -7675029193501887771L;

	protected Long designId;

	protected Long designVersionId;

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
	 * Method 'ReportDatasetPk'
	 * 
	 */
	public ReportDatasetPk()
	{
		super();
	}

	/**
	 * Method 'ReportDatasetPk'
	 * 
	 * @param id
	 * @param designId
	 * @param designVersionId
	 */
	public ReportDatasetPk(final Long id, final Long designId, final Long designVersionId)
	{
		super(id);
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
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof ReportDatasetPk)) {
			return false;
		}
		
		final ReportDatasetPk _cast = (ReportDatasetPk) _other;
		
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
		int _hashCode = super.hashCode();
		
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
		ret.append( "model.ReportDatasetPk: " );
		ret.append( super.toString() );
		ret.append( ", designId=" + designId );
		ret.append( ", designVersionId=" + designVersionId );
		return ret.toString();
	}

}
