package com.sharifpro.eurb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the report_format table.
 */
public class ReportFormatPk implements Serializable
{
	protected Long versionId;

	protected Long id;

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
	 * Method 'ReportFormatPk'
	 * 
	 */
	public ReportFormatPk()
	{
	}

	/**
	 * Method 'ReportFormatPk'
	 * 
	 * @param versionId
	 * @param id
	 */
	public ReportFormatPk(final Long versionId, final Long id)
	{
		this.versionId = versionId;
		this.id = id;
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
		
		if (!(_other instanceof ReportFormatPk)) {
			return false;
		}
		
		final ReportFormatPk _cast = (ReportFormatPk) _other;
		if (versionId == null ? _cast.versionId != versionId : !versionId.equals( _cast.versionId )) {
			return false;
		}
		
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
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
		if (versionId != null) {
			_hashCode = 29 * _hashCode + versionId.hashCode();
		}
		
		if (id != null) {
			_hashCode = 29 * _hashCode + id.hashCode();
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
		ret.append( "com.sharifpro.eurb.dto.ReportFormatPk: " );
		ret.append( "versionId=" + versionId );
		ret.append( ", id=" + id );
		return ret.toString();
	}

}
