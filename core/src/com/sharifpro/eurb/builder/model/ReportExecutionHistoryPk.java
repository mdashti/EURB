package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the report_execution_history table.
 */
public class ReportExecutionHistoryPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 1519520376177972733L;

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
	 * Method 'ReportExecutionHistoryPk'
	 * 
	 */
	public ReportExecutionHistoryPk()
	{
		super();
	}

	/**
	 * Method 'ReportExecutionHistoryPk'
	 * 
	 * @param id
	 * @param versionId
	 */
	public ReportExecutionHistoryPk(final Long id, final Long versionId)
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
		
		if (!(_other instanceof ReportExecutionHistoryPk)) {
			return false;
		}
		
		final ReportExecutionHistoryPk _cast = (ReportExecutionHistoryPk) _other;
		
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
		ret.append( "model.ReportExecutionHistoryPk: " );
		ret.append( super.toString() );
		ret.append( ", versionId=" + versionId );
		return ret.toString();
	}

}
