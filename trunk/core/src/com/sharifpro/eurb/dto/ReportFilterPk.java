package com.sharifpro.eurb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the report_filter table.
 */
public class ReportFilterPk implements Serializable
{
	protected Long id;

	/** 
	 * Sets the value of id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/** 
	 * Gets the value of id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Method 'ReportFilterPk'
	 * 
	 */
	public ReportFilterPk()
	{
	}

	/**
	 * Method 'ReportFilterPk'
	 * 
	 * @param id
	 */
	public ReportFilterPk(final Long id)
	{
		this.id = id;
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
		
		if (!(_other instanceof ReportFilterPk)) {
			return false;
		}
		
		final ReportFilterPk _cast = (ReportFilterPk) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
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
		int _hashCode = 0;
		if (id != null) {
			_hashCode = 29 * _hashCode + id.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.ReportFilterPk: " );
		ret.append( "id=" + id );
		return ret.toString();
	}

}
