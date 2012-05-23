package com.sharifpro.eurb.builder.model;

import java.io.Serializable;
import java.util.Comparator;

import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class ReportColumn extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = 608566522317972974L;

	public final static Integer SORT_TYPE_NO_SORT = 0;
	public final static Integer SORT_TYPE_ASC = 1;
	public final static Integer SORT_TYPE_DESC = 2;
	
	public final static String COLUMN_ALIGN_LEFT = "left";
	public final static String COLUMN_ALIGN_RIGHT = "right";
	public final static String COLUMN_ALIGN_CENTER = "center";
	
	/** 
	 * This attribute maps to the column dataset_id in the report_column table.
	 */
	protected Long datasetId;

	/** 
	 * This attribute maps to the column design_id in the report_column table.
	 */
	protected Long designId;

	/** 
	 * This attribute maps to the column design_version_id in the report_column table.
	 */
	protected Long designVersionId;

	/** 
	 * This attribute maps to the column col_type in the report_column table.
	 */
	protected Integer colType;

	/** 
	 * This attribute maps to the column column_mapping_id in the report_column table.
	 */
	protected Long columnMappingId;
	
	protected ColumnMapping columnMapping;

	/** 
	 * This attribute maps to the column report_column_id in the report_column table.
	 */
	protected Long reportColumnId;

	/** 
	 * This attribute maps to the column col_order in the report_column table.
	 */
	protected Integer colOrder;

	/** 
	 * This attribute maps to the column sort_order in the report_column table.
	 */
	protected Integer sortOrder;

	/** 
	 * This attribute maps to the column sort_type in the report_column table.
	 */
	protected Integer sortType;

	/** 
	 * This attribute maps to the column group_level in the report_column table.
	 */
	protected Integer groupLevel;

	/** 
	 * This attribute maps to the column column_width in the report_column table.
	 */
	protected Integer columnWidth;

	/** 
	 * This attribute maps to the column column_align in the report_column table.
	 */
	protected String columnAlign;

	/** 
	 * This attribute maps to the column column_dir in the report_column table.
	 */
	protected String columnDir;

	/** 
	 * This attribute maps to the column column_header in the report_column table.
	 */
	protected String columnHeader;

	/** 
	 * This attribute maps to the column is_custom in the report_column table.
	 */
	protected boolean isCustom;

	/** 
	 * This attribute maps to the column formula in the report_column table.
	 */
	protected String formula;

	/**
	 * Method 'ReportColumn'
	 * 
	 */
	public ReportColumn()
	{
		super();
	}

	/**
	 * Method 'getDatasetId'
	 * 
	 * @return Long
	 */
	public Long getDatasetId()
	{
		if(datasetId == 0)
			return null;
		return datasetId;
	}

	/**
	 * Method 'setDatasetId'
	 * 
	 * @param datasetId
	 */
	public void setDatasetId(Long datasetId)
	{
		this.datasetId = datasetId;
	}

	/**
	 * Method 'getDesignId'
	 * 
	 * @return Long
	 */
	public Long getDesignId()
	{
		return designId;
	}

	/**
	 * Method 'setDesignId'
	 * 
	 * @param designId
	 */
	public void setDesignId(Long designId)
	{
		this.designId = designId;
	}

	/**
	 * Method 'getDesignVersionId'
	 * 
	 * @return Long
	 */
	public Long getDesignVersionId()
	{
		return designVersionId;
	}

	/**
	 * Method 'setDesignVersionId'
	 * 
	 * @param designVersionId
	 */
	public void setDesignVersionId(Long designVersionId)
	{
		this.designVersionId = designVersionId;
	}

	/**
	 * Method 'getColType'
	 * 
	 * @return Integer
	 */
	public Integer getColType()
	{
		return colType;
	}

	/**
	 * Method 'setColType'
	 * 
	 * @param colType
	 */
	public void setColType(Integer colType)
	{
		this.colType = colType;
	}

	/**
	 * Method 'getColumnMappingId'
	 * 
	 * @return Long
	 */
	public Long getColumnMappingId()
	{
		return columnMappingId;
	}

	/**
	 * Method 'setColumnMappingId'
	 * 
	 * @param columnMappingId
	 */
	public void setColumnMappingId(Long columnMappingId)
	{
		this.columnMappingId = columnMappingId;
	}

	/**
	 * Method 'getColumnMappingId'
	 * 
	 * @return Long
	 */
	public ColumnMapping getColumnMapping()
	{
		return columnMapping;
	}

	/**
	 * Method 'setColumnMappingId'
	 * 
	 * @param columnMappingId
	 */
	public void setColumnMapping(ColumnMapping columnMapping)
	{
		this.columnMapping = columnMapping;
	}

	/**
	 * Method 'getReportColumnId'
	 * 
	 * @return Long
	 */
	public Long getReportColumnId()
	{
		return reportColumnId;
	}

	/**
	 * Method 'setReportColumnId'
	 * 
	 * @param reportColumnId
	 */
	public void setReportColumnId(Long reportColumnId)
	{
		this.reportColumnId = reportColumnId;
	}

	/**
	 * Method 'getColOrder'
	 * 
	 * @return Integer
	 */
	public Integer getColOrder()
	{
		return colOrder;
	}

	/**
	 * Method 'setColOrder'
	 * 
	 * @param colOrder
	 */
	public void setColOrder(Integer colOrder)
	{
		this.colOrder = colOrder;
	}

	/**
	 * Method 'getSortOrder'
	 * 
	 * @return Integer
	 */
	public Integer getSortOrder()
	{
		return sortOrder;
	}

	/**
	 * Method 'setSortOrder'
	 * 
	 * @param sortOrder
	 */
	public void setSortOrder(Integer sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	/**
	 * Method 'getSortType'
	 * 
	 * @return boolean
	 */
	public Integer getSortType()
	{
		return sortType;
	}

	/**
	 * Method 'setSortType'
	 * 
	 * @param sortType
	 */
	public void setSortType(Integer sortType){
			this.sortType = sortType;
	}

	

	/**
	 * Method 'getGroupLevel'
	 * 
	 * @return Integer
	 */
	public Integer getGroupLevel()
	{
		return groupLevel;
	}

	/**
	 * Method 'setGroupLevel'
	 * 
	 * @param groupLevel
	 */
	public void setGroupLevel(Integer groupLevel)
	{
		this.groupLevel = groupLevel;
	}

	/**
	 * Method 'getColumnWidth'
	 * 
	 * @return Integer
	 */
	public Integer getColumnWidth()
	{
		return columnWidth;
	}

	/**
	 * Method 'setColumnWidth'
	 * 
	 * @param columnWidth
	 */
	public void setColumnWidth(Integer columnWidth)
	{
		this.columnWidth = columnWidth;
	}

	/**
	 * Method 'getColumnAlign'
	 * 
	 * @return String
	 */
	public String getColumnAlign()
	{
		return columnAlign;
	}

	/**
	 * Method 'setColumnAlign'
	 * 
	 * @param columnAlign
	 */
	public void setColumnAlign(String columnAlign)
	{
		this.columnAlign = columnAlign;
	}

	/**
	 * Method 'getColumnDir'
	 * 
	 * @return String
	 */
	public String getColumnDir()
	{
		return columnDir;
	}

	/**
	 * Method 'setColumnDir'
	 * 
	 * @param columnDir
	 */
	public void setColumnDir(String columnDir)
	{
		this.columnDir = columnDir;
	}

	/**
	 * Method 'getColumnHeader'
	 * 
	 * @return String
	 */
	public String getColumnHeader()
	{
		return columnHeader;
	}

	/**
	 * Method 'setColumnHeader'
	 * 
	 * @param columnHeader
	 */
	public void setColumnHeader(String columnHeader)
	{
		this.columnHeader = columnHeader;
	}

	/**
	 * Method 'isIsCustom'
	 * 
	 * @return boolean
	 */
	public boolean isCustom()
	{
		return isCustom;
	}

	/**
	 * Method 'setIsCustom'
	 * 
	 * @param isCustom
	 */
	public void setCustom(boolean isCustom)
	{
		this.isCustom = isCustom;
	}

	/**
	 * Method 'getFormula'
	 * 
	 * @return String
	 */
	public String getFormula()
	{
		return formula;
	}

	/**
	 * Method 'setFormula'
	 * 
	 * @param formula
	 */
	public void setFormula(String formula)
	{
		this.formula = formula;
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
		
		if (!(_other instanceof ReportColumn)) {
			return false;
		}
		
		final ReportColumn _cast = (ReportColumn) _other;
		
		if (datasetId == null ? _cast.datasetId != datasetId : !datasetId.equals( _cast.datasetId )) {
			return false;
		}
		
		if (designId == null ? _cast.designId != designId : !designId.equals( _cast.designId )) {
			return false;
		}
		
		if (designVersionId == null ? _cast.designVersionId != designVersionId : !designVersionId.equals( _cast.designVersionId )) {
			return false;
		}
		
		/*if (colType == null ? _cast.colType != colType : !colType.equals( _cast.colType )) {
			return false;
		}
		
		if (columnMappingId == null ? _cast.columnMappingId != columnMappingId : !columnMappingId.equals( _cast.columnMappingId )) {
			return false;
		}
		
		if (reportColumnId == null ? _cast.reportColumnId != reportColumnId : !reportColumnId.equals( _cast.reportColumnId )) {
			return false;
		}
		
		if (colOrder == null ? _cast.colOrder != colOrder : !colOrder.equals( _cast.colOrder )) {
			return false;
		}
		
		if (sortOrder == null ? _cast.sortOrder != sortOrder : !sortOrder.equals( _cast.sortOrder )) {
			return false;
		}
		
		if (sortType != _cast.sortType) {
			return false;
		}
		
		if (groupLevel == null ? _cast.groupLevel != groupLevel : !groupLevel.equals( _cast.groupLevel )) {
			return false;
		}
		
		if (columnWidth == null ? _cast.columnWidth != columnWidth : !columnWidth.equals( _cast.columnWidth )) {
			return false;
		}
		
		if (columnAlign == null ? _cast.columnAlign != columnAlign : !columnAlign.equals( _cast.columnAlign )) {
			return false;
		}
		
		if (columnDir == null ? _cast.columnDir != columnDir : !columnDir.equals( _cast.columnDir )) {
			return false;
		}
		
		if (columnHeader == null ? _cast.columnHeader != columnHeader : !columnHeader.equals( _cast.columnHeader )) {
			return false;
		}
		
		if (isCustom != _cast.isCustom) {
			return false;
		}
		
		if (formula == null ? _cast.formula != formula : !formula.equals( _cast.formula )) {
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
		
		if (datasetId != null) {
			_hashCode = 29 * _hashCode + datasetId.hashCode();
		}
		
		if (designId != null) {
			_hashCode = 29 * _hashCode + designId.hashCode();
		}
		
		if (designVersionId != null) {
			_hashCode = 29 * _hashCode + designVersionId.hashCode();
		}
		
		/*if (colType != null) {
			_hashCode = 29 * _hashCode + colType.hashCode();
		}
		
		if (columnMappingId != null) {
			_hashCode = 29 * _hashCode + columnMappingId.hashCode();
		}
		
		if (reportColumnId != null) {
			_hashCode = 29 * _hashCode + reportColumnId.hashCode();
		}
		
		if (colOrder != null) {
			_hashCode = 29 * _hashCode + colOrder.hashCode();
		}
		
		if (sortOrder != null) {
			_hashCode = 29 * _hashCode + sortOrder.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (sortType ? 1 : 0);
		if (groupLevel != null) {
			_hashCode = 29 * _hashCode + groupLevel.hashCode();
		}
		
		if (columnWidth != null) {
			_hashCode = 29 * _hashCode + columnWidth.hashCode();
		}
		
		if (columnAlign != null) {
			_hashCode = 29 * _hashCode + columnAlign.hashCode();
		}
		
		if (columnDir != null) {
			_hashCode = 29 * _hashCode + columnDir.hashCode();
		}
		
		if (columnHeader != null) {
			_hashCode = 29 * _hashCode + columnHeader.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (isCustom ? 1 : 0);
		if (formula != null) {
			_hashCode = 29 * _hashCode + formula.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportColumnPk
	 */
	public ReportColumnPk createPk()
	{
		return new ReportColumnPk(id, datasetId, designId, designVersionId);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportColumn: " );
		ret.append( super.toString() );
		ret.append( ", datasetId=" + datasetId );
		ret.append( ", designId=" + designId );
		ret.append( ", designVersionId=" + designVersionId );
		ret.append( ", colType=" + colType );
		ret.append( ", columnMappingId=" + columnMappingId );
		ret.append( ", reportColumnId=" + reportColumnId );
		ret.append( ", colOrder=" + colOrder );
		ret.append( ", sortOrder=" + sortOrder );
		ret.append( ", sortType=" + sortType );
		ret.append( ", groupLevel=" + groupLevel );
		ret.append( ", columnWidth=" + columnWidth );
		ret.append( ", columnAlign=" + columnAlign );
		ret.append( ", columnDir=" + columnDir );
		ret.append( ", columnHeader=" + columnHeader );
		ret.append( ", isCustom=" + isCustom );
		ret.append( ", formula=" + formula );
		return ret.toString();
	}

	/**
	 * The return value is used as key referring to this column
	 * usually used in UI elements
	 * 
	 * @return column key
	 */
	public String getColumnKey() {
		return this.getColumnMapping().getColumnKey(this.getDatasetId());
	}

	/**
	 * The return value is used as key referring to this column
	 * usually used in queries
	 * 
	 * @return column key
	 */
	public String getDatabaseKey() {
		return this.getColumnMapping().getDatabaseKey(this.getDatasetId());
	}
	
	public static class ReportColumnSortOrderComparator implements Comparator<ReportColumn> {

		@Override
		public int compare(ReportColumn thiz, ReportColumn that) {
			Integer thizSortOrder = thiz.getSortOrder();
			Integer thatSortOrder = that.getSortOrder();
			//NULL sortOrder columns must be added to end of the sort columns
			if(thizSortOrder == null && thatSortOrder == null) {
				return 0;
			} else if(thizSortOrder == null) {
				return 1;
			} else if(thatSortOrder == null) {
				return -1;
			} else {
				return thizSortOrder - thatSortOrder;
			}
		}
		
	}
}
