package com.sharifpro.eurb.dto;

import com.sharifpro.eurb.dao.*;
import com.sharifpro.eurb.factory.*;
import com.sharifpro.eurb.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TableMapping implements Serializable
{
	/** 
	 * This attribute maps to the column id in the table_mapping table.
	 */
	protected Long id;

	/** 
	 * This attribute maps to the column db_config_id in the table_mapping table.
	 */
	protected Long dbConfigId;

	/** 
	 * This attribute maps to the column table_name in the table_mapping table.
	 */
	protected String tableName;

	/** 
	 * This attribute maps to the column mapped_name in the table_mapping table.
	 */
	protected String mappedName;

	/** 
	 * This attribute maps to the column type in the table_mapping table.
	 */
	protected Integer type;

	/** 
	 * This attribute maps to the column active_for_manager in the table_mapping table.
	 */
	protected boolean activeForManager;

	/** 
	 * This attribute maps to the column active_for_user in the table_mapping table.
	 */
	protected boolean activeForUser;

	/**
	 * Method 'TableMapping'
	 * 
	 */
	public TableMapping()
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
	 * Method 'getType'
	 * 
	 * @return Integer
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(Integer type)
	{
		this.type = type;
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
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof TableMapping)) {
			return false;
		}
		
		final TableMapping _cast = (TableMapping) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		if (dbConfigId == null ? _cast.dbConfigId != dbConfigId : !dbConfigId.equals( _cast.dbConfigId )) {
			return false;
		}
		
		if (tableName == null ? _cast.tableName != tableName : !tableName.equals( _cast.tableName )) {
			return false;
		}
		
		if (mappedName == null ? _cast.mappedName != mappedName : !mappedName.equals( _cast.mappedName )) {
			return false;
		}
		
		if (type == null ? _cast.type != type : !type.equals( _cast.type )) {
			return false;
		}
		
		if (activeForManager != _cast.activeForManager) {
			return false;
		}
		
		if (activeForUser != _cast.activeForUser) {
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
		
		if (dbConfigId != null) {
			_hashCode = 29 * _hashCode + dbConfigId.hashCode();
		}
		
		if (tableName != null) {
			_hashCode = 29 * _hashCode + tableName.hashCode();
		}
		
		if (mappedName != null) {
			_hashCode = 29 * _hashCode + mappedName.hashCode();
		}
		
		if (type != null) {
			_hashCode = 29 * _hashCode + type.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (activeForManager ? 1 : 0);
		_hashCode = 29 * _hashCode + (activeForUser ? 1 : 0);
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
		ret.append( "com.sharifpro.eurb.dto.TableMapping: " );
		ret.append( "id=" + id );
		ret.append( ", dbConfigId=" + dbConfigId );
		ret.append( ", tableName=" + tableName );
		ret.append( ", mappedName=" + mappedName );
		ret.append( ", type=" + type );
		ret.append( ", activeForManager=" + activeForManager );
		ret.append( ", activeForUser=" + activeForUser );
		return ret.toString();
	}

}
