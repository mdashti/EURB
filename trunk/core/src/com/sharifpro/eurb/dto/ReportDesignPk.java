package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the report_design table.
 */
public class ReportDesignPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 3685496952009516376L;

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
	 * Method 'ReportDesignPk'
	 * 
	 */
	public ReportDesignPk()
	{
		super();
	}

	/**
	 * Method 'ReportDesignPk'
	 * 
	 * @param id
	 * @param versionId
	 */
	public ReportDesignPk(final Long id, final Long versionId)
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
		
		if (!(_other instanceof ReportDesignPk)) {
			return false;
		}
		
		final ReportDesignPk _cast = (ReportDesignPk) _other;
		
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
		ret.append( "model.ReportDesignPk: " );
		ret.append( super.toString() );
		ret.append( ", versionId=" + versionId );
		return ret.toString();
	}

}
