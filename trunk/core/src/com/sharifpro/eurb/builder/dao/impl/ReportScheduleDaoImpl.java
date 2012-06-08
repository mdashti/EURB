package com.sharifpro.eurb.builder.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;

import com.sharifpro.eurb.builder.dao.ReportScheduleDao;
import com.sharifpro.eurb.builder.exception.ReportScheduleDaoException;
import com.sharifpro.eurb.builder.model.ReportSchedule;
import com.sharifpro.eurb.builder.util.ScheduledReportJob;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.security.model.User;

public class ReportScheduleDaoImpl extends AbstractDAO implements ReportScheduleDao
{
	protected static Logger log = Logger.getLogger(ReportScheduleDaoImpl.class.getName());
	public static final String REPORT_SCHEDULE = "reportSchedule";
    private Scheduler scheduler;
    
	public ReportScheduleDaoImpl() 
	{		
		log.info("ReportScheduleDaoImpl created.");		
	}

	public void scheduleReport(ReportSchedule reportSchedule) throws ReportScheduleDaoException
	{
		JobDetail jobDetail = JobBuilder.newJob(ScheduledReportJob.class).withIdentity(reportSchedule.getScheduleName(), reportSchedule
				.getScheduleGroup()).build();

		jobDetail.getJobDataMap().put(REPORT_SCHEDULE, reportSchedule);

		if (reportSchedule.getScheduleType() == ReportSchedule.DAILY
				|| reportSchedule.getScheduleType() == ReportSchedule.WEEKLY
				|| reportSchedule.getScheduleType() == ReportSchedule.MONTHLY 
				|| reportSchedule.getScheduleType() == ReportSchedule.WEEKDAYS
				|| reportSchedule.getScheduleType() == ReportSchedule.HOURLY
				|| reportSchedule.getScheduleType() == ReportSchedule.CRON)
		{			
			StringBuffer cronExpression = new StringBuffer();
			
			if (reportSchedule.getScheduleType() == ReportSchedule.CRON)
			{
				cronExpression.append(reportSchedule.getCronExpression());
			}
			else
			{
				cronExpression.append("0 ");
				cronExpression.append(reportSchedule.getStartMinute());
				cronExpression.append(" ");
				cronExpression.append(reportSchedule.getAbsoluteStartHour());

				if (reportSchedule.getScheduleType() == ReportSchedule.HOURLY)
				{
					cronExpression.append("-" + reportSchedule.getAbsoluteEndHour());
				}
				
				if (reportSchedule.getScheduleType() == ReportSchedule.WEEKLY)
				{
					cronExpression.append(" ? * ");
					cronExpression.append(reportSchedule.getDayOfWeek());
				}
				else if (reportSchedule.getScheduleType() == ReportSchedule.MONTHLY)
				{
					cronExpression.append(" " + reportSchedule.getDayOfMonth());
					cronExpression.append(" * ? ");
				}
				else if (reportSchedule.getScheduleType() == ReportSchedule.WEEKDAYS)
				{
					cronExpression.append(" ? * MON-FRI");
				}
				else
				{
					cronExpression.append(" * * ?");
				}
			}			

			ScheduleBuilder<CronTrigger> cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression.toString());
			Trigger cronTrigger = TriggerBuilder.newTrigger()
					.withIdentity(reportSchedule.getScheduleName(),reportSchedule.getScheduleGroup())
					.withDescription(reportSchedule.getScheduleDescription())
					.withSchedule(cronScheduleBuilder)
					.startAt(reportSchedule.getStartDate())
					.withPriority(reportSchedule.getSchedulePriority())
					.usingJobData(reportSchedule.getScheduleName(), reportSchedule.getRequestId())
					.build();

            try
            {
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
            catch(SchedulerException e)
            {
                throw new ReportScheduleDaoException("Error in ReportScheduleDaoImpl.scheduleReport",e);
            }
		}		
		else
		{
			// default to run once...
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(reportSchedule.getScheduleName(),reportSchedule.getScheduleGroup())
					.withPriority(reportSchedule.getSchedulePriority())
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
					.usingJobData(reportSchedule.getScheduleName(), reportSchedule.getRequestId())
					.build();

            try
            {
                scheduler.scheduleJob(jobDetail, trigger);
            }
            catch(SchedulerException e)
            {
                throw new ReportScheduleDaoException("Error in ReportScheduleDaoImpl.scheduleReport", e);
            }
		}		
	}

	public List<ReportSchedule> getScheduledReports(User user) throws ReportScheduleDaoException
	{
        
		List<ReportSchedule> scheduledReports = new ArrayList<ReportSchedule>();
        
        try
        {
    		String group = user.getUsername();
    
    		Set<JobKey> jobKeySet = scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group));
    
    		for (JobKey jobKey : jobKeySet)
    		{
    			try
    			{
    				JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobKey.getName(), group));
    
    				ReportSchedule reportSchedule = (ReportSchedule) jobDetail.getJobDataMap().get(
    						REPORT_SCHEDULE);
    				reportSchedule.setScheduleState(getTriggerStateName(jobKey.getName(), group));
    				
    				Trigger trigger = scheduler.getTrigger(new TriggerKey(jobKey.getName(), group));
    				if (trigger != null)
    				{
    					reportSchedule.setNextFireDate(trigger.getNextFireTime());					
    				}
    
    				scheduledReports.add(reportSchedule);
    			}
    			catch(ReportScheduleDaoException pe)
    			{
    				log.error(group + " | " + jobKey.getName() + " | " + pe.toString());
    			}
    		}		
        }
        catch(SchedulerException e)
        {
            throw new ReportScheduleDaoException("Error in ReportScheduleDaoImpl.", e);
        }

		return scheduledReports;
	}

	public void deleteScheduledReport(User user, String name)
			throws ReportScheduleDaoException
	{
        try
        {
            String group = user.getUsername();
            scheduler.deleteJob(new JobKey(name, group));	
        }
        catch(SchedulerException e)
        {
            throw new ReportScheduleDaoException("Error in ReportScheduleDaoImpl.", e);
        }
	}

	public ReportSchedule getScheduledReport(User user, String name)
			throws ReportScheduleDaoException
	{
        try
        {
    		String group = user.getUsername();
    
    		JobDetail jobDetail = scheduler.getJobDetail(new JobKey(name, group));
    
    		ReportSchedule reportSchedule = (ReportSchedule) jobDetail.getJobDataMap().get(REPORT_SCHEDULE);
    		reportSchedule.setScheduleDescription(jobDetail.getDescription());
    		reportSchedule.setScheduleState(getTriggerStateName(name, group));
    				
    		return reportSchedule;
        }
        catch(SchedulerException e)
        {
            throw new ReportScheduleDaoException("Error in ReportScheduleDaoImpl.", e);
        }
	}
	
	public void pauseScheduledReport(User user, String name)
			throws ReportScheduleDaoException
	{
        try
        {
            scheduler.pauseJob(new JobKey(name, user.getUsername()));
        }
        catch(SchedulerException e)
        {
            throw new ReportScheduleDaoException("Error in ReportScheduleDaoImpl.", e);
        }
	}
	
	public void resumeScheduledReport(User user, String name)
			throws ReportScheduleDaoException
	{
        try
        {
            scheduler.resumeJob(new JobKey(name, user.getUsername()));
        }
        catch(SchedulerException e)
        {
            throw new ReportScheduleDaoException("Error in ReportScheduleDaoImpl.", e);
        }
	}	
    
    public void setScheduler(Scheduler scheduler)
    {
        this.scheduler = scheduler;
    }
    
    private String getTriggerStateName(String name, String group) throws ReportScheduleDaoException
    {
        TriggerState state = TriggerState.NORMAL;
        
        try
        {
            state = scheduler.getTriggerState(new TriggerKey(name, group));
        }
        catch(SchedulerException e)
        {
            throw new ReportScheduleDaoException("Error in ReportScheduleDaoImpl.getTriggerStateName",e);
        }
        
        switch (state)
        {
            case BLOCKED:
                return "Blocked";
            
            case COMPLETE:
                return "Complete";
            
            case ERROR:
                return "ERROR";

            case NORMAL:
                return "Normal";

            case PAUSED:
                return "Paused";

            default:
                return "";
        }
    }
}