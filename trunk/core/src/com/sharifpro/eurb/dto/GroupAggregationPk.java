package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the group_aggregation table.
 */
public class GroupAggregationPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -1191737386686997465L;

	/**
	 * Method 'GroupAggregationPk'
	 * 
	 */
	public GroupAggregationPk()
	{
		super();
	}

	/**
	 * Method 'GroupAggregationPk'
	 * 
	 * @param id
	 */
	public GroupAggregationPk(final Long id)
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
		
		if (!(_other instanceof GroupAggregationPk)) {
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
		ret.append( "model.GroupAggregationPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
