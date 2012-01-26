package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class ReportExecutionHistory extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = 1452367950789156477L;

	/** 
	 * This attribute maps to the column version_id in the report_execution_history table.
	 */
	protected Long versionId;

	/** 
	 * This attribute maps to the column execution_result in the report_execution_history table.
	 */
	protected String executionResult;

	/** 
	 * This attribute maps to the column is_current in the report_execution_history table.
	 */
	protected boolean isCurrent;

	/** 
	 * This attribute maps to the column record_status in the report_execution_history table.
	 */
	protected String recordStatus;

	/** 
	 * This attribute maps to the column report_design_id in the report_execution_history table.
	 */
	protected Long reportDesignId;

	/** 
	 * This attribute maps to the column report_design_version_id in the report_execution_history table.
	 */
	protected Long reportDesignVersionId;

	/**
	 * Method 'ReportExecutionHistory'
	 * 
	 */
	public ReportExecutionHistory()
	{
		super();
	}

	/**
	 * Method 'getVersionId'
	 * 
	 * @return Long
	 */
	public Long getVersionId()
	{
		return versionId;
	}

	/**
	 * Method 'setVersionId'
	 * 
	 * @param versionId
	 */
	public void setVersionId(Long versionId)
	{
		this.versionId = versionId;
	}

	/**
	 * Method 'getExecutionResult'
	 * 
	 * @return String
	 */
	public String getExecutionResult()
	{
		return executionResult;
	}

	/**
	 * Method 'setExecutionResult'
	 * 
	 * @param executionResult
	 */
	public void setExecutionResult(String executionResult)
	{
		this.executionResult = executionResult;
	}

	/**
	 * Method 'isIsCurrent'
	 * 
	 * @return boolean
	 */
	public boolean isCurrent()
	{
		return isCurrent;
	}

	/**
	 * Method 'setIsCurrent'
	 * 
	 * @param isCurrent
	 */
	public void setCurrent(boolean isCurrent)
	{
		this.isCurrent = isCurrent;
	}

	/**
	 * Method 'getRecordStatus'
	 * 
	 * @return String
	 */
	public String getRecordStatus()
	{
		return recordStatus;
	}

	/**
	 * Method 'setRecordStatus'
	 * 
	 * @param recordStatus
	 */
	public void setRecordStatus(String recordStatus)
	{
		this.recordStatus = recordStatus;
	}

	/**
	 * Method 'getReportDesignId'
	 * 
	 * @return Long
	 */
	public Long getReportDesignId()
	{
		return reportDesignId;
	}

	/**
	 * Method 'setReportDesignId'
	 * 
	 * @param reportDesignId
	 */
	public void setReportDesignId(Long reportDesignId)
	{
		this.reportDesignId = reportDesignId;
	}

	/**
	 * Method 'getReportDesignVersionId'
	 * 
	 * @return Long
	 */
	public Long getReportDesignVersionId()
	{
		return reportDesignVersionId;
	}

	/**
	 * Method 'setReportDesignVersionId'
	 * 
	 * @param reportDesignVersionId
	 */
	public void setReportDesignVersionId(Long reportDesignVersionId)
	{
		this.reportDesignVersionId = reportDesignVersionId;
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
		
		if (!(_other instanceof ReportExecutionHistory)) {
			return false;
		}
		
		final ReportExecutionHistory _cast = (ReportExecutionHistory) _other;
		
		if (versionId == null ? _cast.versionId != versionId : !versionId.equals( _cast.versionId )) {
			return false;
		}
		
		/*if (executionResult == null ? _cast.executionResult != executionResult : !executionResult.equals( _cast.executionResult )) {
			return false;
		}
		
		if (isCurrent != _cast.isCurrent) {
			return false;
		}
		
		if (recordStatus == null ? _cast.recordStatus != recordStatus : !recordStatus.equals( _cast.recordStatus )) {
			return false;
		}
		
		if (reportDesignId == null ? _cast.reportDesignId != reportDesignId : !reportDesignId.equals( _cast.reportDesignId )) {
			return false;
		}
		
		if (reportDesignVersionId == null ? _cast.reportDesignVersionId != reportDesignVersionId : !reportDesignVersionId.equals( _cast.reportDesignVersionId )) {
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
		
		if (versionId != null) {
			_hashCode = 29 * _hashCode + versionId.hashCode();
		}
		
		/*if (executionResult != null) {
			_hashCode = 29 * _hashCode + executionResult.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (isCurrent ? 1 : 0);
		if (recordStatus != null) {
			_hashCode = 29 * _hashCode + recordStatus.hashCode();
		}
		
		if (reportDesignId != null) {
			_hashCode = 29 * _hashCode + reportDesignId.hashCode();
		}
		
		if (reportDesignVersionId != null) {
			_hashCode = 29 * _hashCode + reportDesignVersionId.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportExecutionHistoryPk
	 */
	public ReportExecutionHistoryPk createPk()
	{
		return new ReportExecutionHistoryPk(id, versionId);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportExecutionHistory: " );
		ret.append( super.toString() );
		ret.append( ", versionId=" + versionId );
		ret.append( ", executionResult=" + executionResult );
		ret.append( ", isCurrent=" + isCurrent );
		ret.append( ", recordStatus=" + recordStatus );
		ret.append( ", reportDesignId=" + reportDesignId );
		ret.append( ", reportDesignVersionId=" + reportDesignVersionId );
		return ret.toString();
	}

}
