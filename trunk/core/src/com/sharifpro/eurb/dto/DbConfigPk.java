package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the db_config table.
 */
public class DbConfigPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -3702933065555246694L;

	/**
	 * Method 'DbConfigPk'
	 * 
	 */
	public DbConfigPk()
	{
		super();
	}

	/**
	 * Method 'DbConfigPk'
	 * 
	 * @param id
	 */
	public DbConfigPk(final Long id)
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
		
		if (!(_other instanceof DbConfigPk)) {
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
		ret.append( "model.DbConfigPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
