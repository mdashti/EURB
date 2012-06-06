package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.security.model.User;

public class ReportUserAlert implements Comparable<ReportUserAlert>, Serializable
{		
	private static final long serialVersionUID = 6833718935442336961L;
	
	private User user;
	private ReportAlert alert;
	private ReportDesign report;
	private int limit;
	private String operator;	
	
	private int count;	
	
	public String getCondition()
	{
		return getOperator() + " " + limit;
	}
	
	public ReportAlert getAlert()
	{
		return alert;
	}

	public void setAlert(ReportAlert alert)
	{
		this.alert = alert;
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public String getOperator()
	{
		if (operator == null || operator.trim().length() < 1) return ReportAlert.OPERATOR_EQUAL;		
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public int compareTo(ReportUserAlert reportUserAlert)
	{			
		return alert.getName().compareTo(reportUserAlert.getAlert().getName());
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public boolean isTriggered()
	{
		if ("=".equals(operator))
		{
			if (limit == count) return true;
		}
		else if (">".equals(operator))
		{
			if (count > limit) return true;
		}
		else if ("<".equals(operator))
		{
			if (count < limit) return true;
		}
		
		return false;
	}

	public ReportDesign getReport()
	{
		return report;
	}

	public void setReport(ReportDesign report)
	{
		this.report = report;
	}	
}