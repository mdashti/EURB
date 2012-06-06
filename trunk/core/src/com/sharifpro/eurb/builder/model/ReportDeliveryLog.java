package com.sharifpro.eurb.builder.model;

import java.util.Date;

public class ReportDeliveryLog 
{
    private Integer id;
    private String deliveryMethod;
    private Date startTime;
    private Date endTime;
    private String status;
    private String message;
    
    public ReportDeliveryLog() {}
    
    public ReportDeliveryLog(String deliveryMethod, Date startTime)
    {
        this.deliveryMethod = deliveryMethod;
        this.startTime = startTime;
    }
    
    public String getDeliveryMethod() 
    {
        return deliveryMethod;
    }
    
    public void setDeliveryMethod(String deliveryMethod) 
    {
        this.deliveryMethod = deliveryMethod;
    }
    
    public Date getEndTime() 
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }
    
    public Integer getId() 
    {
        return id;
    }
    
    public void setId(Integer id) 
    {
        this.id = id;
    }
    
    public String getMessage() 
    {
        return message;
    }
    
    public void setMessage(String message) 
    {
        this.message = message;
    }
    
    public Date getStartTime() 
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }
    
    public String getStatus() 
    {
        return status;
    }
    
    public void setStatus(String status) 
    {
        this.status = status;
    }    
}
