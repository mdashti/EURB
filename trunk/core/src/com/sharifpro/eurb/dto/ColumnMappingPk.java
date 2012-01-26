package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the column_mapping table.
 */
public class ColumnMappingPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -7588588103055754287L;

	/**
	 * Method 'ColumnMappingPk'
	 * 
	 */
	public ColumnMappingPk()
	{
	}

	/**
	 * Method 'ColumnMappingPk'
	 * 
	 * @param id
	 */
	public ColumnMappingPk(final Long id)
	{
		super(id);
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
		
		if (!(_other instanceof ColumnMappingPk)) {
			return false;
		}
		
		final ColumnMappingPk _cast = (ColumnMappingPk) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.ColumnMappingPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
