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

import com.sharifpro.eurb.builder.exception.ReportScheduleDaoException;
import com.sharifpro.eurb.builder.model.ReportSchedule;
import com.sharifpro.eurb.management.security.model.User;

public interface ReportScheduleDao
{
	public void scheduleReport(ReportSchedule reportSchedule) throws ReportScheduleDaoException;

	public ReportSchedule getScheduledReport(User reportUser, String name)
			throws ReportScheduleDaoException;

	public List<ReportSchedule> getScheduledReports(User reportUser) throws ReportScheduleDaoException;

	public void deleteScheduledReport(User reportUser, String name)
			throws ReportScheduleDaoException;

	public void pauseScheduledReport(User reportUser, String name)
			throws ReportScheduleDaoException;

	public void resumeScheduledReport(User reportUser, String name)
			throws ReportScheduleDaoException;
}