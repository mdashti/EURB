package com.sharifpro.eurb.dto;

import com.sharifpro.eurb.dao.*;
import com.sharifpro.eurb.factory.*;
import com.sharifpro.eurb.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class ReportDesign implements Serializable
{
	/** 
	 * This attribute maps to the column id in the report_design table.
	 */
	protected Long id;

	/** 
	 * This attribute maps to the column version_id in the report_design table.
	 */
	protected Long versionId;

	/** 
	 * This attribute maps to the column name in the report_design table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column description in the report_design table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column category_id in the report_design table.
	 */
	protected Long categoryId;

	/** 
	 * This attribute maps to the column query_text in the report_design table.
	 */
	protected String queryText;

	/** 
	 * This attribute maps to the column select_part in the report_design table.
	 */
	protected String selectPart;

	/** 
	 * This attribute maps to the column result_data in the report_design table.
	 */
	protected String resultData;

	/** 
	 * This attribute maps to the column format_file in the report_design table.
	 */
	protected String formatFile;

	/** 
	 * This attribute maps to the column is_current in the report_design table.
	 */
	protected boolean isCurrent;

	/** 
	 * This attribute maps to the column record_status in the report_design table.
	 */
	protected String recordStatus;

	/**
	 * Method 'ReportDesign'
	 * 
	 */
	public ReportDesign()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return Long
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(Long id)
	{
		this.id = id;
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
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Method 'getCategoryId'
	 * 
	 * @return Long
	 */
	public Long getCategoryId()
	{
		return categoryId;
	}

	/**
	 * Method 'setCategoryId'
	 * 
	 * @param categoryId
	 */
	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	/**
	 * Method 'getQueryText'
	 * 
	 * @return String
	 */
	public String getQueryText()
	{
		return queryText;
	}

	/**
	 * Method 'setQueryText'
	 * 
	 * @param queryText
	 */
	public void setQueryText(String queryText)
	{
		this.queryText = queryText;
	}

	/**
	 * Method 'getSelectPart'
	 * 
	 * @return String
	 */
	public String getSelectPart()
	{
		return selectPart;
	}

	/**
	 * Method 'setSelectPart'
	 * 
	 * @param selectPart
	 */
	public void setSelectPart(String selectPart)
	{
		this.selectPart = selectPart;
	}

	/**
	 * Method 'getResultData'
	 * 
	 * @return String
	 */
	public String getResultData()
	{
		return resultData;
	}

	/**
	 * Method 'setResultData'
	 * 
	 * @param resultData
	 */
	public void setResultData(String resultData)
	{
		this.resultData = resultData;
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
		
		if (!(_other instanceof ReportDesign)) {
			return false;
		}
		
		final ReportDesign _cast = (ReportDesign) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		if (versionId == null ? _cast.versionId != versionId : !versionId.equals( _cast.versionId )) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (categoryId == null ? _cast.categoryId != categoryId : !categoryId.equals( _cast.categoryId )) {
			return false;
		}
		
		if (queryText == null ? _cast.queryText != queryText : !queryText.equals( _cast.queryText )) {
			return false;
		}
		
		if (selectPart == null ? _cast.selectPart != selectPart : !selectPart.equals( _cast.selectPart )) {
			return false;
		}
		
		if (resultData == null ? _cast.resultData != resultData : !resultData.equals( _cast.resultData )) {
			return false;
		}
		
		if (formatFile == null ? _cast.formatFile != formatFile : !formatFile.equals( _cast.formatFile )) {
			return false;
		}
		
		if (isCurrent != _cast.isCurrent) {
			return false;
		}
		
		if (recordStatus == null ? _cast.recordStatus != recordStatus : !recordStatus.equals( _cast.recordStatus )) {
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
		
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		if (categoryId != null) {
			_hashCode = 29 * _hashCode + categoryId.hashCode();
		}
		
		if (queryText != null) {
			_hashCode = 29 * _hashCode + queryText.hashCode();
		}
		
		if (selectPart != null) {
			_hashCode = 29 * _hashCode + selectPart.hashCode();
		}
		
		if (resultData != null) {
			_hashCode = 29 * _hashCode + resultData.hashCode();
		}
		
		if (formatFile != null) {
			_hashCode = 29 * _hashCode + formatFile.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (isCurrent ? 1 : 0);
		if (recordStatus != null) {
			_hashCode = 29 * _hashCode + recordStatus.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportDesignPk
	 */
	public ReportDesignPk createPk()
	{
		return new ReportDesignPk(id, versionId);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.ReportDesign: " );
		ret.append( "id=" + id );
		ret.append( ", versionId=" + versionId );
		ret.append( ", name=" + name );
		ret.append( ", description=" + description );
		ret.append( ", categoryId=" + categoryId );
		ret.append( ", queryText=" + queryText );
		ret.append( ", selectPart=" + selectPart );
		ret.append( ", resultData=" + resultData );
		ret.append( ", formatFile=" + formatFile );
		ret.append( ", isCurrent=" + isCurrent );
		ret.append( ", recordStatus=" + recordStatus );
		return ret.toString();
	}

}
