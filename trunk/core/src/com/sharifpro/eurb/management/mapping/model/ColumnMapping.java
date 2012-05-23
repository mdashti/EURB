package com.sharifpro.eurb.management.mapping.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sharifpro.db.meta.TableColumnInfo;
import com.sharifpro.util.json.Json2DArraySerializer;

public class ColumnMapping extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -2903512798099004237L;

	private static final int MAPPING_TYPE_NONE = 0;
	private static final int MAPPING_TYPE_DYNAMIC = 1;
	private static final int MAPPING_TYPE_STATIC = 2;
	
	/** 
	 * This attribute maps to the column db_config_id in the column_mapping table.
	 */
	protected Long dbConfigId;

	/** 
	 * This attribute maps to the column table_mapping_id in the column_mapping table.
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
	protected int colOrder;

	/** 
	 * This attribute maps to the column format_pattern in the column_mapping table.
	 * 
	 * Should include:
	 * + Column Size
	 * + Decimal Digits
	 * + (Default Value)
	 * + Radix
	 * + Remarks
	 * + isNullable
	 */
	protected String formatPattern;

	/** 
	 * This attribute maps to the column static_mapping in the column_mapping table.
	 */
	protected String staticMapping;

	/** 
	 * This attribute maps to the column referenced_table in the column_mapping table referred to table_mapping.id .
	 */
	protected Long referencedTable;

	/** 
	 * This attribute maps to the column referenced_id_col in the column_mapping table referred to column_mapping.id .
	 */
	protected Long referencedIdCol;

	/** 
	 * This attribute maps to the column referenced_value_col in the column_mapping table referred to column_mapping.id .
	 */
	protected Long referencedValueCol;

	/** 
	 * This attribute maps to the column active_for_manager in the column_mapping table.
	 */
	protected boolean activeForManager;

	/** 
	 * This attribute maps to the column active_for_user in the column_mapping table.
	 */
	protected boolean activeForUser;

	/**
	 * Method 'ColumnMapping'
	 * 
	 */
	public ColumnMapping()
	{
		super();
	}

	

	public ColumnMapping(Long dbConfigId, Long tableMappingId,
			String columnName, String colTypeName, int colDataType,
			int colOrder, String formatPattern) {
		super();
		this.dbConfigId = dbConfigId;
		this.tableMappingId = tableMappingId;
		this.columnName = columnName;
		this.colTypeName = colTypeName;
		this.colDataType = colDataType;
		this.colOrder = colOrder;
		this.formatPattern = formatPattern;
		this.activeForManager = true;
		this.activeForUser = true;
	}



	/**
	 * Method 'getDbConfigId'
	 * 
	 * @return Long
	 */
	public Long getDbConfigId()
	{
		return dbConfigId;
	}

	/**
	 * Method 'setDbConfigId'
	 * 
	 * @param dbConfigId
	 */
	public void setDbConfigId(Long dbConfigId)
	{
		this.dbConfigId = dbConfigId;
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
	 * @return int
	 */
	public int getColOrder()
	{
		return colOrder;
	}

	/**
	 * Method 'setColOrder'
	 * 
	 * @param colOrder
	 */
	public void setColOrder(int colOrder)
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
	@JsonSerialize(using=Json2DArraySerializer.class)
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
	public Long getReferencedTable()
	{
		return referencedTable;
	}

	/**
	 * Method 'setReferencedTable'
	 * 
	 * @param referencedTable
	 */
	public void setReferencedTable(Long referencedTable)
	{
		this.referencedTable = referencedTable;
	}

	/**
	 * Method 'getReferencedIdCol'
	 * 
	 * @return String
	 */
	public Long getReferencedIdCol()
	{
		return referencedIdCol;
	}

	/**
	 * Method 'setReferencedIdCol'
	 * 
	 * @param referencedIdCol
	 */
	public void setReferencedIdCol(Long referencedIdCol)
	{
		this.referencedIdCol = referencedIdCol;
	}

	/**
	 * Method 'getReferencedValueCol'
	 * 
	 * @return String
	 */
	public Long getReferencedValueCol()
	{
		return referencedValueCol;
	}

	/**
	 * Method 'setReferencedValueCol'
	 * 
	 * @param referencedValueCol
	 */
	public void setReferencedValueCol(Long referencedValueCol)
	{
		this.referencedValueCol = referencedValueCol;
	}
	
	/**
	 * Method 'isActiveForManager'
	 * 
	 * @return boolean
	 */
	public boolean isActiveForManager()
	{
		return activeForManager;
	}

	/**
	 * Method 'setActiveForManager'
	 * 
	 * @param activeForManager
	 */
	public void setActiveForManager(boolean activeForManager)
	{
		this.activeForManager = activeForManager;
	}

	/**
	 * Method 'isActiveForUser'
	 * 
	 * @return boolean
	 */
	public boolean isActiveForUser()
	{
		return activeForUser;
	}

	/**
	 * Method 'setActiveForUser'
	 * 
	 * @param activeForUser
	 */
	public void setActiveForUser(boolean activeForUser)
	{
		this.activeForUser = activeForUser;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if(_other instanceof TableColumnInfo) {
			TableColumnInfo _cast = (TableColumnInfo)_other;
			if (columnName == null ? _cast.getColumnName() != columnName : !columnName.equals( _cast.getColumnName() )) {
				return false;
			}
		} else {
			if(!super.equals(_other)){
				return false;
			}
			
			if (!(_other instanceof ColumnMapping)) {
				return false;
			}
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
		ret.append( ", dbConfigId=" + dbConfigId );
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
		ret.append( ", activeForManager=" + activeForManager );
		ret.append( ", activeForUser=" + activeForUser );
		return ret.toString();
	}

	public int getMappingType() {
		return (referencedTable == null && referencedIdCol == null && referencedValueCol == null) ? (StringUtils.isEmpty(staticMapping) || "[]".equals(staticMapping) ? MAPPING_TYPE_NONE : MAPPING_TYPE_STATIC) : MAPPING_TYPE_DYNAMIC;
	}
}
