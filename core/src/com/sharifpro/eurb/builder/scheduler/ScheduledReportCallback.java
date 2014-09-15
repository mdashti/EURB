package com.sharifpro.eurb.builder.scheduler;

import com.sharifpro.eurb.builder.model.ReportLog;

/**
 * Interface for a callback executed at the end of a ScheduledReportJob. The ReportLog
 * contains status details on the finished job.
 */
public interface ScheduledReportCallback {
	
	public void callback(ReportLog reportLog);
}
