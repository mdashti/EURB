package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the report_category table.
 */
public class ReportCategoryPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 2159454595828835065L;

	/**
	 * Method 'ReportCategoryPk'
	 * 
	 */
	public ReportCategoryPk()
	{
		super();
	}

	/**
	 * Method 'ReportCategoryPk'
	 * 
	 * @param id
	 */
	public ReportCategoryPk(final Long id)
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
		
		if (!(_other instanceof ReportCategoryPk)) {
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
		ret.append( "model.ReportCategoryPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
