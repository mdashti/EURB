package com.sharifpro.eurb.builder.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.sharifpro.eurb.builder.dao.ReportAlertDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.dao.ReportLogDao;
import com.sharifpro.eurb.builder.dao.impl.ReportScheduleDaoImpl;
import com.sharifpro.eurb.builder.delivery.DeliveryException;
import com.sharifpro.eurb.builder.delivery.DeliveryMethod;
import com.sharifpro.eurb.builder.engine.ReportEngine;
import com.sharifpro.eurb.builder.engine.ReportEngineHelper;
import com.sharifpro.eurb.builder.engine.input.ReportEngineInput;
import com.sharifpro.eurb.builder.engine.output.ReportEngineOutput;
import com.sharifpro.eurb.builder.model.ReportDeliveryLog;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportLog;
import com.sharifpro.eurb.builder.model.ReportSchedule;
import com.sharifpro.eurb.builder.model.ReportUserAlert;
import com.sharifpro.eurb.builder.provider.DirectoryProvider;
import com.sharifpro.eurb.builder.scheduler.ScheduledReportCallback;
import com.sharifpro.eurb.builder.util.ReportConstants.ExportType;
import com.sharifpro.eurb.management.security.model.User;

public class ScheduledReportJob	implements Job
 {
	// standard report parameter names
	public static final String USER_ID = "OPENREPORTS_USER_ID";
	public static final String USER_NAME = "OPENREPORTS_USER_NAME";
	public static final String EXTERNAL_ID = "OPENREPORTS_USER_EXTERNALID";
	public static final String IMAGE_DIR = "OPENREPORTS_IMAGE_DIR";
	public static final String REPORT_DIR = "OPENREPORTS_REPORT_DIR";
	public static final String EXPORT_TYPE_PARAM = "OPENREPORTS_EXPORT_TYPE";

	protected static Logger log = Logger.getLogger(ScheduledReportJob.class.getName());
	
	private ReportLogDao reportLogDao;	
	private DirectoryProvider directoryProvider;	
	private ReportAlertDao reportAlertDao;
	private ReportDesignDao dataSourceProvider;
	
	private List<ScheduledReportCallback> callbacks;

	public ScheduledReportJob()
	{
				
	}	

	public void execute(JobExecutionContext context)
		throws JobExecutionException
	{
		log.debug("Scheduled Report Executing....");
        
        ApplicationContext appContext = init(context);
		        
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		ReportSchedule reportSchedule =
			(ReportSchedule) jobDataMap.get(ReportScheduleDaoImpl.REPORT_SCHEDULE);
		reportSchedule.setScheduleDescription(context.getJobDetail().getDescription());

		ReportDesign report = reportSchedule.getReport();
		User user = reportSchedule.getUser();
		Map<String,Object> reportParameters = reportSchedule.getReportParameters();

		log.debug("Report: " + report.getName());
		log.debug("User: " + user.getUsername());		

       		
		ReportLog reportLog = new ReportLog(user, report, new Date());
        reportLog.setExportType(reportSchedule.getExportType());
        reportLog.setRequestId(reportSchedule.getRequestId());
        
		try
		{
			//
			ReportUserAlert alert = reportSchedule.getAlert();			
			
			if (alert != null)
			{
				log.debug("Executing Alert Condition");
				
				alert.setReport(report);
				alert = reportAlertDao.executeAlert(alert, true);
				
				if (!alert.isTriggered())
				{
					log.debug("Alert Not Triggered. Report not run.");
					return;
				}
				
				log.debug("Alert Triggered. Running report.");
			}

			// add standard report parameters
			reportParameters.put(USER_ID, user.getId());
			reportParameters.put(USER_NAME, user.getUsername());
			reportParameters.put(IMAGE_DIR, new File(directoryProvider.getReportImageDirectory()));
			reportParameters.put(REPORT_DIR, new File(directoryProvider.getReportDirectory()));
			//
			
			reportLogDao.insertReportLog(reportLog);			
			
			ReportEngineInput reportInput = new ReportEngineInput(report, reportParameters);
			reportInput.setExportType(ExportType.findByCode(reportSchedule.getExportType()));
            reportInput.setXmlInput(reportSchedule.getXmlInput());
            reportInput.setLocale(reportSchedule.getLocale());
			
			
			ReportEngine reportEngine = ReportEngineHelper.getReportEngine(report,
					dataSourceProvider, directoryProvider);	
			reportEngine.setApplicationContext(appContext);
			
			ReportEngineOutput reportOutput = reportEngine.generateReport(reportInput);            
           
            String[] deliveryMethods = reportSchedule.getDeliveryMethods();      
            
            if (deliveryMethods == null || deliveryMethods.length == 0)
            {
                deliveryMethods = new String[]{com.sharifpro.eurb.builder.util.ReportConstants.DeliveryMethod.EMAIL.getName()};
                log.warn("DeliveryMethod not set, defaulting to email delivery");
            }
            
            // set status to success. if a delivery method fails, this is updated to delivery failure
            reportLog.setStatus(ReportLog.STATUS_SUCCESS);
            
            ArrayList<ReportDeliveryLog> deliveryLogs = new ArrayList<ReportDeliveryLog>();
            
            for (int i=0; i < deliveryMethods.length; i++)
            {                                  
                ReportDeliveryLog deliveryLog = new ReportDeliveryLog(deliveryMethods[i], new Date());
            
                try
                {      
                	String deliveryMethodBeanId = deliveryMethods[i] + "DeliveryMethod";
                	
                    DeliveryMethod deliveryMethod = (DeliveryMethod) appContext.getBean(deliveryMethodBeanId, DeliveryMethod.class);            
                    deliveryMethod.deliverReport(reportSchedule, reportOutput);
                    
                    deliveryLog.setEndTime(new Date());
                    deliveryLog.setStatus(ReportLog.STATUS_SUCCESS);
                }                
                catch(DeliveryException de)
                {
                	log.error("Delivery Error: " + reportSchedule.getRequestId(), de);
                	
                    deliveryLog.setMessage(de.toString());
                    deliveryLog.setStatus(ReportLog.STATUS_DELIVERY_FAILURE);
                    
                    reportLog.setMessage(de.toString());
                    reportLog.setStatus(ReportLog.STATUS_DELIVERY_FAILURE);                    
                }
                
                deliveryLogs.add(deliveryLog);                
            }		

            reportLog.setDeliveryLogs(deliveryLogs);
			reportLog.setEndTime(new Date());			
            
			reportLogDao.updateReportLog(reportLog);

			log.debug("Scheduled Report Finished...");
		}
		catch (Exception e)
		{
			if (e.getMessage() != null && e.getMessage().indexOf("Empty") > 0)
			{
				reportLog.setStatus(ReportLog.STATUS_EMPTY);
			}
			else
			{				
				log.error("ScheduledReport Error: " + reportSchedule.getRequestId(), e);

				reportLog.setMessage(e.toString());
				reportLog.setStatus(ReportLog.STATUS_FAILURE);
			}

			reportLog.setEndTime(new Date());

			try
			{
				reportLogDao.updateReportLog(reportLog);
			}
			catch (Exception ex)
			{
				log.error("Unable to create ReportLog: " + ex.getMessage());
			}			
		}	
		
		// execute all callbacks after the job is finished processing
		executeCallbacks(reportLog);
	}
	
	@SuppressWarnings("unchecked")
	private ApplicationContext init(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationContext appContext = null;
        
        try
        {
            appContext =
                (ApplicationContext)context.getScheduler().getContext().get("applicationContext");
        }
        catch(SchedulerException se)
        {
            throw new JobExecutionException(se);
        }

        reportLogDao = (ReportLogDao) appContext.getBean("reportLogDao", ReportLogDao.class);
        directoryProvider = (DirectoryProvider) appContext.getBean("directoryProvider", DirectoryProvider.class);
        reportAlertDao = (ReportAlertDao) appContext.getBean("reportAlertDao", ReportAlertDao.class);
        dataSourceProvider = (ReportDesignDao) appContext.getBean("reportDesignDao", ReportDesignDao.class);
        
        if (appContext.containsBean("scheduledReportCallbacks"))
        {
        	callbacks = (List<ScheduledReportCallback>) appContext.getBean("scheduledReportCallbacks", List.class);
        }
        
        return appContext;
	}

	/*
	 * Execute all ScheduledReportCallbacks registered for this job. Callbacks are configured in the 
	 * Spring bean scheduledReportCallbacks
	 */
	private void executeCallbacks(ReportLog reportLog)
	{
		if (callbacks == null) return;
		
		for (ScheduledReportCallback callback : callbacks)
		{
			callback.callback(reportLog);
		}
	}	
}