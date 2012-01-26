package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the report_column table.
 */
public class ReportColumnPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 1413131159263541714L;

	protected Long datasetId;

	protected Long designId;

	protected Long designVersionId;


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
		super();
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
		super(id);
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
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof ReportColumnPk)) {
			return false;
		}
		
		final ReportColumnPk _cast = (ReportColumnPk) _other;
		
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
		int _hashCode = super.hashCode();
		
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
		ret.append( "model.ReportColumnPk: " );
		ret.append( super.toString() );
		ret.append( ", datasetId=" + datasetId );
		ret.append( ", designId=" + designId );
		ret.append( ", designVersionId=" + designVersionId );
		return ret.toString();
	}

}
