package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

public class ObjectConfig implements Serializable
{
	private static final long serialVersionUID = -5852320298976109788L;

	/** 
	 * This attribute maps to the column object_id	 in the object_config table.
	 */
	protected Long objectId;

	
	/** 
	 * This attribute maps to the column key in the object_config table.
	 */
	protected String key;

	/** 
	 * This attribute maps to the column value in the object_config table.
	 */
	protected String value;

	
	/**
	 * Method 'ReportChart'
	 * 
	 */
	public ObjectConfig()
	{
		super();
	}

	



	/**
	 * Method 'getObjectId'
	 * 
	 * @return Long
	 */
	public Long getObjectId()
	{
		return objectId;
	}

	/**
	 * Method 'setObjectId'
	 * 
	 * @param objectId
	 */
	public void setObjectId(Long objectId)
	{
		this.objectId = objectId;
	}

	
	/**
	 * Method 'getKey'
	 * 
	 * @return String
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * Method 'setKey'
	 * 
	 * @param key
	 */
	public void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * Method 'getValue'
	 * 
	 * @return String
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Method 'setValue'
	 * 
	 * @param value
	 */
	public void setValue(String value)
	{
		this.value = value;
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
		
		if (!(_other instanceof ObjectConfig)) {
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
		int _hashCode = super.hashCode();
		
				return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportFilterPk
	 */
	public ObjectConfigPk createPk()
	{
		return new ObjectConfigPk();
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportFilter: " );
		ret.append( super.toString() );
		ret.append( ", ojbectId=" + objectId );
		ret.append( ", key=" + key );
		ret.append( ", value=" + value);
		return ret.toString();
	}

}
