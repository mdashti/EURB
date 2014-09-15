package com.sharifpro.eurb.builder.delivery;

import java.util.List;

import com.sharifpro.eurb.builder.engine.output.ReportEngineOutput;
import com.sharifpro.eurb.builder.model.DeliveredReport;
import com.sharifpro.eurb.builder.model.ReportSchedule;
import com.sharifpro.eurb.management.security.model.User;

public interface DeliveryMethod 
{    
    public void deliverReport(ReportSchedule reportSchedule, ReportEngineOutput reportOutput) throws DeliveryException;
    
    public List<DeliveredReport> getDeliveredReports(User user) throws DeliveryException;
    
    public byte[] getDeliveredReport(DeliveredReport deliveredReport) throws DeliveryException;        
}
