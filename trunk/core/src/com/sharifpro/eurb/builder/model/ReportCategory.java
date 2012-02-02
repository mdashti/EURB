package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class ReportCategory extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -6872414545571485755L;

	/** 
	 * This attribute maps to the column name in the report_category table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column description in the report_category table.
	 */
	protected String description;

	/**
	 * Method 'ReportCategory'
	 * 
	 */
	public ReportCategory()
	{
		super();
	}

	/**
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
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
		
		if (!(_other instanceof ReportCategory)) {
			return false;
		}
		
		/*final ReportCategory _cast = (ReportCategory) _other;
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
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
		int _hashCode = super.hashCode();
		
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		/*if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ReportCategoryPk
	 */
	public ReportCategoryPk createPk()
	{
		return new ReportCategoryPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.ReportCategory: " );
		ret.append( super.toString() );
		ret.append( ", name=" + name );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
