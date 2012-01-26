package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the report_format table.
 */
public class ReportFormatPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 1457152642676471683L;

	protected Long versionId;

	/** 
	 * Sets the value of versionId
	 */
	public void setVersionId(Long versionId)
	{
		this.versionId = versionId;
	}

	/** 
	 * Gets the value of versionId
	 */
	public Long getVersionId()
	{
		return versionId;
	}

	/**
	 * Method 'ReportFormatPk'
	 * 
	 */
	public ReportFormatPk()
	{
		super();
	}

	/**
	 * Method 'ReportFormatPk'
	 * 
	 * @param versionId
	 * @param id
	 */
	public ReportFormatPk(final Long versionId, final Long id)
	{
		super(id);
		this.versionId = versionId;
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
		
		if (!(_other instanceof ReportFormatPk)) {
			return false;
		}
		
		final ReportFormatPk _cast = (ReportFormatPk) _other;
		if (versionId == null ? _cast.versionId != versionId : !versionId.equals( _cast.versionId )) {
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
		if (versionId != null) {
			_hashCode = 29 * _hashCode + versionId.hashCode();
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
		ret.append( "model.ReportFormatPk: " );
		ret.append( super.toString() );
		ret.append( ", versionId=" + versionId );
		return ret.toString();
	}

}
