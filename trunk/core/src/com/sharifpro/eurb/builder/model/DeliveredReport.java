package com.sharifpro.eurb.builder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class DeliveredReport implements Serializable
{	
	private static final long serialVersionUID = 9134829141030371147L;
	
    private String requestId;
	private String userName;
	private String reportName;
	private String reportDescription;
	private String reportFileName;
    private String deliveryMethod;
	private Date runDate;
	private Map parameters;		
    
    public String getRequestId() 
    {
        return requestId;
    }
    
    public void setRequestId(String requestId) 
    {
        this.requestId = requestId;
    }

    public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public Map getParameters()
	{
		return parameters;
	}
	
	public void setParameters(Map parameters)
	{
		this.parameters = parameters;
	}
	
	public String getParameterList()
	{
		if (parameters == null || parameters.size() < 1) return "";
		
		StringBuffer buffer = new StringBuffer();
		
		Iterator iterator = parameters.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = (String) iterator.next();
			if (key.indexOf("OPENREPORTS_") == -1)
			{			
				buffer.append(key + "=" + parameters.get(key) + " ");
			}
		}
		
		return buffer.toString();
	}
	
	public String getReportDescription()
	{
		return reportDescription;
	}
	
	public void setReportDescription(String reportDescription)
	{
		this.reportDescription = reportDescription;
	}
	
	public String getReportName()
	{
		return reportName;
	}
	
	public void setReportName(String reportName)
	{
		this.reportName = reportName;
	}
	
	public Date getRunDate()
	{
		return runDate;
	}
	
	public void setRunDate(Date runDate)
	{
		this.runDate = runDate;
	}

	public String getReportFileName()
	{
		return reportFileName;
	}

	public void setReportFileName(String reportFileName)
	{
		this.reportFileName = reportFileName;
	}
    
    public String getDeliveryMethod() 
    {
        return deliveryMethod;
    }
    
    public void setDeliveryMethod(String deliveryMethod) 
    {
        this.deliveryMethod = deliveryMethod;
    }
}