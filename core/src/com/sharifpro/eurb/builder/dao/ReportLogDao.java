/*
 * Copyright (C) 2003 Erik Swenson - erik@oreports.com
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *  
 */

package com.sharifpro.eurb.builder.dao;

import java.util.List;

import com.sharifpro.eurb.builder.exception.ReportLogDaoException;
import com.sharifpro.eurb.builder.model.ReportLog;

public interface ReportLogDao
{
	public ReportLog getReportLog(Integer id) throws ReportLogDaoException;
	public List<ReportLog> getReportLogs(String status, Integer userId, Integer reportId, Integer alertId, int maxRows) throws ReportLogDaoException;
	public ReportLog insertReportLog(ReportLog reportLog)	throws ReportLogDaoException;
	public void updateReportLog(ReportLog reportLog) throws ReportLogDaoException;
	public void deleteReportLog(ReportLog reportLog) throws ReportLogDaoException;	
	
	public List<Object[]> getTopReportsByUser() throws ReportLogDaoException;
	public List<Object[]> getTopReports() throws ReportLogDaoException;
	public List<Object[]> getTopFailures() throws ReportLogDaoException;
	public List<Object[]> getTopEmptyReports() throws ReportLogDaoException;
	public List<Object[]> getTopReportsForPeriod(int daysBack) throws ReportLogDaoException;	
	
	public List<Object[]> getTopAlertsByUser() throws ReportLogDaoException;	
	public List<Object[]> getTopAlerts() throws ReportLogDaoException;	
	public List<Object[]> getTopTriggeredAlerts() throws ReportLogDaoException;	
	public List<Object[]> getTopNotTriggeredAlerts() throws ReportLogDaoException;
}