package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class ReportFormat extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -3386076504808806513L;

	/** 
	 * This attribute maps to the column version_id in the report_format table.
	 */
	protected Long versionId;

	/** 
	 * This attribute maps to the column format_file in the report_format table.
	 */
	protected String formatFile;

	/** 
	 * This attribute maps to the column is_current in the report_format table.
	 */
	protected boolean isCurrent;

	/** 
	 * This attribute maps to the column record_status in the report_format table.
	 */
	protected String recordStatus;

	/** 
	 * This attribute maps to the column report_design_id in the report_format table.
	 */
	protected Long reportDesignId;

	/** 
	 * This attribute maps to the column report_design_version_id in the report_format table.
	 */
	protected Long reportDesignVersionId;

	/**
	 * Method 'ReportFormat'
	 * 
	 */
	public ReportFormat()
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
	 * Method 'getFormatFile'
	 * 
	 * @return String
	 */
	public String getFormatFile()
	{
		return formatFile;
	}

	/**
	 * Method 'setFormatFile'
	 * 
	 * @param formatFile
	 */
	public void setFormatFile(String formatFile)
	{
		this.formatFile = formatFile;
	}

	/**
	 * Method 'isIsCurrent'
	 * 
	 * @return boolean
	 */
	public boolean isIsCurrent()
	{
		return isCurrent;
	}

	/**
	 * Method 'setIsCurrent'
	 * 
	 * @param isCurrent
	 */
	public void setIsCurrent(boolean isCurrent)
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
		
		if (!(_other instanceof ReportFormat)) {
			return false;
		}
		
		final ReportFormat _cast = (ReportFormat) _other;
		
		if (versionId == null ? _cast.versionId != versionId : !versionId.equals( _cast.versionId )) {
			return false;
		}
		
		/*if (formatFile == null ? _cast.formatFile != formatFile : !formatFile.equals( _cast.formatFile )) {
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
		
		/*if (formatFile != null) {
			_hashCode = 29 * _hashCode + formatFile.hashCode();
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
	 * @return ReportFormatPk
	 */
	public ReportFormatPk createPk()
	{
		return new ReportFormatPk(versionId, id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportFormat: " );
		ret.append( super.toString() );
		ret.append( ", versionId=" + versionId );
		ret.append( ", formatFile=" + formatFile );
		ret.append( ", isCurrent=" + isCurrent );
		ret.append( ", recordStatus=" + recordStatus );
		ret.append( ", reportDesignId=" + reportDesignId );
		ret.append( ", reportDesignVersionId=" + reportDesignVersionId );
		return ret.toString();
	}

}
