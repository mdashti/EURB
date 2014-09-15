package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

/** 
 * This class represents the primary key of the report_filter table.
 */
public class ObjectConfigPk implements Serializable
{
	private static final long serialVersionUID = -3518340072436103311L;

	/** 
	 * This attribute maps to the column object_id	 in the object_config table.
	 */
	protected Long objectId;

	
	/** 
	 * This attribute maps to the column key in the object_config table.
	 */
	protected String key;

	
	
	public void setObjectId(Long objectId){
		this.objectId = objectId;
	}
	
	public Long getObjectId(){
		return this.objectId;
	}
	
	
	public void setKey(String key){
		this.key = key;
	}
	
	public String getKey(){
		return this.key;
	}
	/**
	 * Method 'ObjectConfigPk'
	 * 
	 */
	public ObjectConfigPk()
	{
		super();
	}

	
	public ObjectConfigPk(Long objectId, String key){
		super();
		this.objectId = objectId;
		this.key = key;
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
