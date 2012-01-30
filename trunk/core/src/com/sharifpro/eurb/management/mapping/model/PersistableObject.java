package com.sharifpro.eurb.management.mapping.model;

import java.io.Serializable;
import java.util.Date;

import com.sharifpro.eurb.PersistableObjectType;

public class PersistableObject implements Serializable
{
	private static final long serialVersionUID = -8510014065738364759L;

	/** 
	 * This attribute maps to the column id in the persistable_object table.
	 */
	protected Long id;

	/** 
	 * This attribute maps to the column type in the persistable_object table.
	 */
	protected Integer objectType;

	/** 
	 * This attribute maps to the column creator in the persistable_object table.
	 */
	protected String creator;

	/** 
	 * This attribute maps to the column create_date in the persistable_object table.
	 */
	protected Date createDate;

	/** 
	 * This attribute maps to the column modifier in the persistable_object table.
	 */
	protected String modifier;

	/** 
	 * This attribute maps to the column modify_date in the persistable_object table.
	 */
	protected Date modifyDate;

	/**
	 * Method 'PersistableObject'
	 * 
	 */
	public PersistableObject()
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
	 * Method 'getObjectType'
	 * 
	 * @return Integer
	 */
	public Integer getObjectType()
	{
		return PersistableObjectType.getObjectTypeFor(getClass());
	}

	/**
	 * Method 'setObjectType'
	 * 
	 * @param objectType
	 */
	public void setObjectType(Integer objectType)
	{
		this.objectType = objectType;
	}

	/**
	 * Method 'getCreator'
	 * 
	 * @return String
	 */
	public String getCreator()
	{
		return creator;
	}

	/**
	 * Method 'setCreator'
	 * 
	 * @param creator
	 */
	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	/**
	 * Method 'getCreateDate'
	 * 
	 * @return Date
	 */
	public Date getCreateDate()
	{
		return createDate;
	}

	/**
	 * Method 'setCreateDate'
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	/**
	 * Method 'getModifier'
	 * 
	 * @return String
	 */
	public String getModifier()
	{
		return modifier;
	}

	/**
	 * Method 'setModifier'
	 * 
	 * @param modifier
	 */
	public void setModifier(String modifier)
	{
		this.modifier = modifier;
	}

	/**
	 * Method 'getModifyDate'
	 * 
	 * @return Date
	 */
	public Date getModifyDate()
	{
		return modifyDate;
	}

	/**
	 * Method 'setModifyDate'
	 * 
	 * @param modifyDate
	 */
	public void setModifyDate(Date modifyDate)
	{
		this.modifyDate = modifyDate;
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
		
		if (!(_other instanceof PersistableObject)) {
			return false;
		}
		
		final PersistableObject _cast = (PersistableObject) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		/*if (objectType == null ? _cast.objectType != objectType : !objectType.equals( _cast.objectType )) {
			return false;
		}
		
		if (creator == null ? _cast.creator != creator : !creator.equals( _cast.creator )) {
			return false;
		}
		
		if (createDate == null ? _cast.createDate != createDate : !createDate.equals( _cast.createDate )) {
			return false;
		}
		
		if (modifier == null ? _cast.modifier != modifier : !modifier.equals( _cast.modifier )) {
			return false;
		}
		
		if (modifyDate == null ? _cast.modifyDate != modifyDate : !modifyDate.equals( _cast.modifyDate )) {
			return false;
		}*/
		
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
		
		/*if (objectType != null) {
			_hashCode = 29 * _hashCode + objectType.hashCode();
		}
		
		if (creator != null) {
			_hashCode = 29 * _hashCode + creator.hashCode();
		}
		
		if (createDate != null) {
			_hashCode = 29 * _hashCode + createDate.hashCode();
		}
		
		if (modifier != null) {
			_hashCode = 29 * _hashCode + modifier.hashCode();
		}
		
		if (modifyDate != null) {
			_hashCode = 29 * _hashCode + modifyDate.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return PersistableObjectPk
	 */
	public PersistableObjectPk createPk()
	{
		return new PersistableObjectPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		//ret.append( "model.PersistableObject: " );
		ret.append( "id=" + id );
		ret.append( ", type=" + objectType );
		ret.append( ", creator=" + creator );
		ret.append( ", createDate=" + createDate );
		ret.append( ", modifier=" + modifier );
		ret.append( ", modifyDate=" + modifyDate );
		return ret.toString();
	}
	
	private boolean newRecord;

	public boolean isNewRecord() {
		return newRecord;
	}

	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
	}
}
