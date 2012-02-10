package com.sharifpro.eurb.management.mapping.model;

import java.io.Serializable;

public class ColumnMapping extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -2903512798099004237L;

	/** 
	 * This attribute maps to the column table_mapping_id in the column_mapping table.
	 * 
	 * Should include:
	 * + Column Size
	 * + Decimal Digits
	 * + (Default Value)
	 * + Radix
	 * + Remarks
	 * + isNullable
	 */
	protected Long tableMappingId;

	/** 
	 * This attribute maps to the column column_name in the column_mapping table.
	 */
	protected String columnName;

	/** 
	 * This attribute maps to the column mapped_name in the column_mapping table.
	 */
	protected String mappedName;

	/** 
	 * This attribute maps to the column col_type in the column_mapping table.
	 * 
	 * Type Name
	 */
	protected String colTypeName;

	/** 
	 * This attribute maps to the column col_data_type in the column_mapping table.
	 * 
	 * Data Type
	 */
	protected int colDataType;

	/** 
	 * This attribute maps to the column col_order in the column_mapping table.
	 * 
	 * Ordinal Position
	 */
	protected String colOrder;

	/** 
	 * This attribute maps to the column format_pattern in the column_mapping table.
	 */
	protected String formatPattern;

	/** 
	 * This attribute maps to the column static_mapping in the column_mapping table.
	 */
	protected String staticMapping;

	/** 
	 * This attribute maps to the column referenced_table in the column_mapping table.
	 */
	protected String referencedTable;

	/** 
	 * This attribute maps to the column referenced_id_col in the column_mapping table.
	 */
	protected String referencedIdCol;

	/** 
	 * This attribute maps to the column referenced_value_col in the column_mapping table.
	 */
	protected String referencedValueCol;

	/**
	 * Method 'ColumnMapping'
	 * 
	 */
	public ColumnMapping()
	{
		super();
	}


	/**
	 * Method 'getTableMappingId'
	 * 
	 * @return Long
	 */
	public Long getTableMappingId()
	{
		return tableMappingId;
	}

	/**
	 * Method 'setTableMappingId'
	 * 
	 * @param tableMappingId
	 */
	public void setTableMappingId(Long tableMappingId)
	{
		this.tableMappingId = tableMappingId;
	}

	/**
	 * Method 'getColumnName'
	 * 
	 * @return String
	 */
	public String getColumnName()
	{
		return columnName;
	}

	/**
	 * Method 'setColumnName'
	 * 
	 * @param columnName
	 */
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	/**
	 * Method 'getMappedName'
	 * 
	 * @return String
	 */
	public String getMappedName()
	{
		return mappedName;
	}

	/**
	 * Method 'setMappedName'
	 * 
	 * @param mappedName
	 */
	public void setMappedName(String mappedName)
	{
		this.mappedName = mappedName;
	}

	/**
	 * Method 'getColTypeName'
	 * 
	 * @return String
	 */
	public String getColTypeName()
	{
		return colTypeName;
	}

	/**
	 * Method 'setColTypeName'
	 * 
	 * @param colTypeName
	 */
	public void setColTypeName(String colType)
	{
		this.colTypeName = colType;
	}

	/**
	 * Method 'getColDataType'
	 * 
	 * @return int
	 */
	public int getColDataType()
	{
		return colDataType;
	}

	/**
	 * Method 'setColDataType'
	 * 
	 * @param colTypeName
	 */
	public void setColDataType(int colDataType)
	{
		this.colDataType = colDataType;
	}

	/**
	 * Method 'getColOrder'
	 * 
	 * @return String
	 */
	public String getColOrder()
	{
		return colOrder;
	}

	/**
	 * Method 'setColOrder'
	 * 
	 * @param colOrder
	 */
	public void setColOrder(String colOrder)
	{
		this.colOrder = colOrder;
	}

	/**
	 * Method 'getFormatPattern'
	 * 
	 * @return String
	 */
	public String getFormatPattern()
	{
		return formatPattern;
	}

	/**
	 * Method 'setFormatPattern'
	 * 
	 * @param formatPattern
	 */
	public void setFormatPattern(String formatPattern)
	{
		this.formatPattern = formatPattern;
	}

	/**
	 * Method 'getStaticMapping'
	 * 
	 * @return String
	 */
	public String getStaticMapping()
	{
		return staticMapping;
	}

	/**
	 * Method 'setStaticMapping'
	 * 
	 * @param staticMapping
	 */
	public void setStaticMapping(String staticMapping)
	{
		this.staticMapping = staticMapping;
	}

	/**
	 * Method 'getReferencedTable'
	 * 
	 * @return String
	 */
	public String getReferencedTable()
	{
		return referencedTable;
	}

	/**
	 * Method 'setReferencedTable'
	 * 
	 * @param referencedTable
	 */
	public void setReferencedTable(String referencedTable)
	{
		this.referencedTable = referencedTable;
	}

	/**
	 * Method 'getReferencedIdCol'
	 * 
	 * @return String
	 */
	public String getReferencedIdCol()
	{
		return referencedIdCol;
	}

	/**
	 * Method 'setReferencedIdCol'
	 * 
	 * @param referencedIdCol
	 */
	public void setReferencedIdCol(String referencedIdCol)
	{
		this.referencedIdCol = referencedIdCol;
	}

	/**
	 * Method 'getReferencedValueCol'
	 * 
	 * @return String
	 */
	public String getReferencedValueCol()
	{
		return referencedValueCol;
	}

	/**
	 * Method 'setReferencedValueCol'
	 * 
	 * @param referencedValueCol
	 */
	public void setReferencedValueCol(String referencedValueCol)
	{
		this.referencedValueCol = referencedValueCol;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if(!super.equals(_other)){
			return false;
		}
		
		if (!(_other instanceof ColumnMapping)) {
			return false;
		}
		
		/*final ColumnMapping _cast = (ColumnMapping) _other;
		
		if (tableMappingId == null ? _cast.tableMappingId != tableMappingId : !tableMappingId.equals( _cast.tableMappingId )) {
			return false;
		}
		
		if (columnName == null ? _cast.columnName != columnName : !columnName.equals( _cast.columnName )) {
			return false;
		}
		
		if (mappedName == null ? _cast.mappedName != mappedName : !mappedName.equals( _cast.mappedName )) {
			return false;
		}
		
		if (colTypeName == null ? _cast.colType != colTypeName : !colTypeName.equals( _cast.colType )) {
			return false;
		}
		
		if (colOrder == null ? _cast.colOrder != colOrder : !colOrder.equals( _cast.colOrder )) {
			return false;
		}
		
		if (formatPattern == null ? _cast.formatPattern != formatPattern : !formatPattern.equals( _cast.formatPattern )) {
			return false;
		}
		
		if (staticMapping == null ? _cast.staticMapping != staticMapping : !staticMapping.equals( _cast.staticMapping )) {
			return false;
		}
		
		if (referencedTable == null ? _cast.referencedTable != referencedTable : !referencedTable.equals( _cast.referencedTable )) {
			return false;
		}
		
		if (referencedIdCol == null ? _cast.referencedIdCol != referencedIdCol : !referencedIdCol.equals( _cast.referencedIdCol )) {
			return false;
		}
		
		if (referencedValueCol == null ? _cast.referencedValueCol != referencedValueCol : !referencedValueCol.equals( _cast.referencedValueCol )) {
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
		
		/*if (tableMappingId != null) {
			_hashCode = 29 * _hashCode + tableMappingId.hashCode();
		}
		
		if (columnName != null) {
			_hashCode = 29 * _hashCode + columnName.hashCode();
		}
		
		if (mappedName != null) {
			_hashCode = 29 * _hashCode + mappedName.hashCode();
		}
		
		if (colTypeName != null) {
			_hashCode = 29 * _hashCode + colTypeName.hashCode();
		}
		
		if (colOrder != null) {
			_hashCode = 29 * _hashCode + colOrder.hashCode();
		}
		
		if (formatPattern != null) {
			_hashCode = 29 * _hashCode + formatPattern.hashCode();
		}
		
		if (staticMapping != null) {
			_hashCode = 29 * _hashCode + staticMapping.hashCode();
		}
		
		if (referencedTable != null) {
			_hashCode = 29 * _hashCode + referencedTable.hashCode();
		}
		
		if (referencedIdCol != null) {
			_hashCode = 29 * _hashCode + referencedIdCol.hashCode();
		}
		
		if (referencedValueCol != null) {
			_hashCode = 29 * _hashCode + referencedValueCol.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ColumnMappingPk
	 */
	public ColumnMappingPk createPk()
	{
		return new ColumnMappingPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ColumnMapping: " );
		ret.append( super.toString() );
		ret.append( ", tableMappingId=" + tableMappingId );
		ret.append( ", columnName=" + columnName );
		ret.append( ", mappedName=" + mappedName );
		ret.append( ", colTypeName=" + colTypeName );
		ret.append( ", colDataType=" + colDataType );
		ret.append( ", colOrder=" + colOrder );
		ret.append( ", formatPattern=" + formatPattern );
		ret.append( ", staticMapping=" + staticMapping );
		ret.append( ", referencedTable=" + referencedTable );
		ret.append( ", referencedIdCol=" + referencedIdCol );
		ret.append( ", referencedValueCol=" + referencedValueCol );
		return ret.toString();
	}

}
