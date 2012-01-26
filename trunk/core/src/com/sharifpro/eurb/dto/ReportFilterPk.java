package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the report_filter table.
 */
public class ReportFilterPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = -3518340072436103311L;

	/**
	 * Method 'ReportFilterPk'
	 * 
	 */
	public ReportFilterPk()
	{
		super();
	}

	/**
	 * Method 'ReportFilterPk'
	 * 
	 * @param id
	 */
	public ReportFilterPk(final Long id)
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
		
		if (!(_other instanceof ReportFilterPk)) {
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
		ret.append( "model.ReportFilterPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
