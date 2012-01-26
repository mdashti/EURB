package com.sharifpro.eurb.management.mapping.model;

import java.io.Serializable;

/** 
 * This class represents the primary key of the table_mapping table.
 */
public class TableMappingPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 4898300769518763179L;

	/**
	 * Method 'TableMappingPk'
	 * 
	 */
	public TableMappingPk()
	{
		super();
	}

	/**
	 * Method 'TableMappingPk'
	 * 
	 * @param id
	 */
	public TableMappingPk(final Long id)
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
		
		if (!(_other instanceof TableMappingPk)) {
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
		ret.append( "model.TableMappingPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
