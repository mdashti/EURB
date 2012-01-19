package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the table_mapping table.
 */
public class TableMappingPk implements Serializable
{
	private static final long serialVersionUID = 4898300769518763179L;
	protected Long id;

	/** 
	 * Sets the value of id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/** 
	 * Gets the value of id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Method 'TableMappingPk'
	 * 
	 */
	public TableMappingPk()
	{
	}

	/**
	 * Method 'TableMappingPk'
	 * 
	 * @param id
	 */
	public TableMappingPk(final Long id)
	{
		this.id = id;
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
		
		if (!(_other instanceof TableMappingPk)) {
			return false;
		}
		
		final TableMappingPk _cast = (TableMappingPk) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
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
		ret.append( "com.sharifpro.eurb.dto.TableMappingPk: " );
		ret.append( "id=" + id );
		return ret.toString();
	}

}
