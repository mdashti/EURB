package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

/** 
 * This class represents the primary key of the report_filter table.
 */
public class ObjectConfigPk implements Serializable
{
	private static final long serialVersionUID = -3518340072436103311L;

	/**
	 * Method 'ObjectConfigPk'
	 * 
	 */
	public ObjectConfigPk()
	{
		super();
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
		
		if (!(_other instanceof ObjectConfigPk)) {
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
		ret.append( "model.ObjectConfigPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
