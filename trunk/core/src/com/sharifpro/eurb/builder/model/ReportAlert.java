package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

public class ReportAlert implements Serializable, Comparable<ReportAlert>
{		
	private static final long serialVersionUID = 6594082161499885218L;
	
	public static final String OPERATOR_EQUAL = "=";
	public static final String OPERATOR_GREATER = ">";
	public static final String OPERATOR_LESS = "<";	
	
	private Integer id;
	private String name;
	private String description;
	private String query;		
	
	private ReportDesign dataSource;

	public ReportAlert()
	{
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String toString()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public Integer getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int compareTo(ReportAlert reportAlert)
	{		
		return name.compareTo(reportAlert.getName());
	}

	public ReportDesign getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(ReportDesign dataSource)
	{
		this.dataSource = dataSource;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}	
	
	public boolean equals(Object o)
	{
		ReportAlert alert = (ReportAlert) o;		
		return id.equals(alert.getId());
	}
}