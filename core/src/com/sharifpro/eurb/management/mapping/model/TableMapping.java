package com.sharifpro.eurb.management.mapping.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.sharifpro.db.meta.ITableInfo;
import com.sharifpro.util.PropertyProvider;

public class TableMapping extends PersistableObject implements Serializable
{
	public final static int MAPPED_TYPE_TABLE = 0;
	public final static int MAPPED_TYPE_VIEW = 1;
	
	private static final long serialVersionUID = 8016638670642078075L;
	
	public static final long ACL_CLASS_IDENTIFIER = 4;

	/** 
	 * This attribute maps to the column db_config_id in the table_mapping table.
	 */
	protected Long dbConfigId;

	/** 
	 * This attribute maps to the column catalog in the table_mapping table.
	 */
	protected String catalog;

	/** 
	 * This attribute maps to the column schema in the table_mapping table.
	 */
	protected String schema;

	/** 
	 * This attribute maps to the column table_name in the table_mapping table.
	 */
	protected String tableName;

	/** 
	 * This attribute maps to the column mapped_name in the table_mapping table.
	 */
	protected String mappedName;

	/** 
	 * This attribute maps to the column mapped_type in the table_mapping table.
	 */
	protected int mappedType;

	/** 
	 * This attribute maps to the column active_for_manager in the table_mapping table.
	 */
	protected boolean activeForManager;

	/** 
	 * This attribute maps to the column active_for_user in the table_mapping table.
	 */
	protected boolean activeForUser;

	private boolean accessPreventDel = true;
	private boolean accessPreventEdit = true;
	private boolean accessPreventExecute = true;
	private boolean accessPreventSharing = true;

	/**
	 * Method 'TableMapping'
	 * 
	 */
	public TableMapping()
	{
		super();
	}
	
	/**
	 * Method 'TableMapping'
	 * 
	 */
	public TableMapping(Long dbconfigId, String catalog, String schema, String tableName, int mappedType)
	{
		this();
		this.dbConfigId = dbconfigId;
		this.catalog = catalog;
		this.schema = schema;
		this.tableName = tableName;
		this.mappedType = mappedType;
		this.mappedName = null;
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
	 * Method 'getCatalog'
	 * 
	 * @return String
	 */
	public String getCatalog()
	{
		return catalog;
	}

	/**
	 * Method 'setCatalog'
	 * 
	 * @param catalog
	 */
	public void setCatalog(String catalog)
	{
		this.catalog = catalog;
	}

	/**
	 * Method 'getSchema'
	 * 
	 * @return String
	 */
	public String getSchema()
	{
		return schema;
	}

	/**
	 * Method 'setSchema'
	 * 
	 * @param schema
	 */
	public void setSchema(String schema)
	{
		if(StringUtils.isEmpty(schema)) {
			this.schema = null;
		} else {
			this.schema = schema;
		}
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return tableName;
	}

	/**
	 * Method 'setTableName'
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
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
	 * Method 'getMappedType'
	 * 
	 * @return int
	 */
	public int getMappedType()
	{
		return mappedType;
	}

	/**
	 * Method 'setMappedType'
	 * 
	 * @param mappedType
	 */
	public void setMappedType(int mappedType)
	{
		this.mappedType = mappedType;
	}
	
	public String getMappedTypeName()
	{
		return mappedType == MAPPED_TYPE_TABLE ? PropertyProvider.get("eurb.table") : PropertyProvider.get("eurb.view");
	}
	
	public String getMappedTypeEnglishName()
	{
		return mappedType == MAPPED_TYPE_TABLE ? "TABLE" : "VIEW";
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
		if(_other instanceof ITableInfo) {
			ITableInfo _cast = (ITableInfo)_other;
			if (catalog == null ? _cast.getCatalogName() != catalog : !catalog.equals( _cast.getCatalogName() )) {
				return false;
			}
			
			if (schema == null ? _cast.getSchemaName() != schema : !schema.equals( _cast.getSchemaName() )) {
				return false;
			}
			
			if (!tableName.equals( _cast.getSimpleName() )) {
				return false;
			}
			
			if (MAPPED_TYPE_TABLE == mappedType ? !"TABLE".equals(_cast.getType()) : !"VIEW".equals(_cast.getType())) {
				return false;
			}
		} else {
			if (!super.equals(_other)) {
				return false;
			}
			
			if (!(_other instanceof TableMapping)) {
				return false;
			}
		}
		
		/*final TableMapping _cast = (TableMapping) _other;
		
		if (dbConfigId == null ? _cast.dbConfigId != dbConfigId : !dbConfigId.equals( _cast.dbConfigId )) {
			return false;
		}
		
		if (tableName == null ? _cast.tableName != tableName : !tableName.equals( _cast.tableName )) {
			return false;
		}
		
		if (mappedName == null ? _cast.mappedName != mappedName : !mappedName.equals( _cast.mappedName )) {
			return false;
		}
		
		if (mappedType == null ? _cast.mappedType != mappedType : !mappedType.equals( _cast.mappedType )) {
			return false;
		}
		
		if (activeForManager != _cast.activeForManager) {
			return false;
		}
		
		if (activeForUser != _cast.activeForUser) {
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
		
		/*if (dbConfigId != null) {
			_hashCode = 29 * _hashCode + dbConfigId.hashCode();
		}
		
		if (tableName != null) {
			_hashCode = 29 * _hashCode + tableName.hashCode();
		}
		
		if (mappedName != null) {
			_hashCode = 29 * _hashCode + mappedName.hashCode();
		}
		
		if (mappedType != null) {
			_hashCode = 29 * _hashCode + mappedType.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (activeForManager ? 1 : 0);
		_hashCode = 29 * _hashCode + (activeForUser ? 1 : 0);*/
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TableMappingPk
	 */
	public TableMappingPk createPk()
	{
		return new TableMappingPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.TableMapping: " );
		ret.append( super.toString() );
		ret.append( ", dbConfigId=" + dbConfigId );
		ret.append( ", catalog=" + catalog );
		ret.append( ", schema=" + schema );
		ret.append( ", tableName=" + tableName );
		ret.append( ", mappedName=" + mappedName );
		ret.append( ", mappedType=" + mappedType );
		ret.append( ", activeForManager=" + activeForManager );
		ret.append( ", activeForUser=" + activeForUser );
		return ret.toString();
	}

	public Object getTableFullName() {
		StringBuilder fullName = new StringBuilder();
		if(!StringUtils.isEmpty(this.getCatalog())) {
			fullName.append(this.getCatalog()).append(".");
		}
		if(!StringUtils.isEmpty(this.getSchema())) {
			fullName.append(this.getSchema()).append(".");
		}
		fullName.append(this.getTableName());
		return fullName.toString();
	}

	public boolean isAccessPreventDel() {
		return accessPreventDel;
	}

	public void setAccessPreventDel(Boolean accessPreventDel) {
		this.accessPreventDel = accessPreventDel == null ? false : true;;
	}

	public void setAccessPreventDel(boolean accessPreventDel) {
		this.accessPreventDel = accessPreventDel;
	}

	public boolean isAccessPreventEdit() {
		return accessPreventEdit;
	}

	public void setAccessPreventEdit(Boolean accessPreventEdit) {
		this.accessPreventEdit = accessPreventEdit == null ? false : true;;
	}

	public void setAccessPreventEdit(boolean accessPreventEdit) {
		this.accessPreventEdit = accessPreventEdit;
	}

	public boolean isAccessPreventExecute() {
		return accessPreventExecute;
	}

	public void setAccessPreventExecute(Boolean accessPreventExecute) {
		this.accessPreventExecute = accessPreventExecute == null ? false : true;;
	}

	public void setAccessPreventExecute(boolean accessPreventExecute) {
		this.accessPreventExecute = accessPreventExecute;
	}

	public boolean isAccessPreventSharing() {
		return accessPreventSharing;
	}

	public void setAccessPreventSharing(Boolean accessPreventSharing) {
		this.accessPreventSharing = accessPreventSharing == null ? false : true;;
	}

	public void setAccessPreventSharing(boolean accessPreventSharing) {
		this.accessPreventSharing = accessPreventSharing;
	}

}
