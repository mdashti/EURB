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
		super();
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
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof ColumnMappingPk)) {
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
		ret.append( "model.ColumnMappingPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
