package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the report_design table.
 */
public class ReportDesignPk implements Serializable
{
	private static final long serialVersionUID = 3685496952009516376L;

	protected Long id;

	protected Long versionId;

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
	 * Method 'ReportDesignPk'
	 * 
	 */
	public ReportDesignPk()
	{
	}

	/**
	 * Method 'ReportDesignPk'
	 * 
	 * @param id
	 * @param versionId
	 */
	public ReportDesignPk(final Long id, final Long versionId)
	{
		this.id = id;
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
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof ReportDesignPk)) {
			return false;
		}
		
		final ReportDesignPk _cast = (ReportDesignPk) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
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
		int _hashCode = 0;
		if (id != null) {
			_hashCode = 29 * _hashCode + id.hashCode();
		}
		
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
		ret.append( "com.sharifpro.eurb.dto.ReportDesignPk: " );
		ret.append( "id=" + id );
		ret.append( ", versionId=" + versionId );
		return ret.toString();
	}

}
