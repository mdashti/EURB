/*
 * Copyright (C) 2006 Erik Swenson - erik@oreports.com
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

package com.sharifpro.eurb.builder.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sharifpro.eurb.builder.dao.ReportAlertDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.dao.ReportLogDao;
import com.sharifpro.eurb.builder.exception.ReportAlertDaoException;
import com.sharifpro.eurb.builder.model.ReportAlert;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportLog;
import com.sharifpro.eurb.builder.model.ReportUserAlert;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.util.PropertyProvider;

public class ReportAlertDaoImpl extends AbstractDAO implements ReportAlertDao
{
	protected static Logger log = Logger.getLogger(ReportAlertDaoImpl.class.getName());
	
	private ReportDesignDao dataSourceProvider;
	private ReportLogDao reportLogDao;	
	
	public ReportAlertDaoImpl() throws ReportAlertDaoException
	{
		log.info("Created");
	}			

	public ReportAlert getReportAlert(Integer id) throws ReportAlertDaoException 
	{
		return null;//(ReportAlert) hibernateProvider.load(ReportAlert.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ReportAlert> getReportAlerts() throws ReportAlertDaoException
	{
		String fromClause =
			"from org.efs.openreports.objects.ReportAlert reportAlert order by reportAlert.name ";
	
		return null;// (List<ReportAlert>) hibernateProvider.query(fromClause);
	}

	public ReportAlert insertReportAlert(ReportAlert reportAlert) throws ReportAlertDaoException
	{
		return null;//(ReportAlert) hibernateProvider.save(reportAlert);
	}

	public void updateReportAlert(ReportAlert reportAlert) throws ReportAlertDaoException
	{
		//hibernateProvider.update(reportAlert);
	}

	public void deleteReportAlert(ReportAlert reportAlert) throws ReportAlertDaoException
	{
		/*Session session = hibernateProvider.openSession();
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction();
			
			//delete alert			
			session.delete(reportAlert);		
			
			//delete report log entries for alert
			Iterator<?> iterator =  session
				.createQuery(
				"from  org.efs.openreports.objects.ReportLog reportLog where reportLog.alert.id = ? ")
				.setInteger(0, reportAlert.getId().intValue()).iterate();
					
			while(iterator.hasNext())
			{
				ReportLog reportLog = (ReportLog) iterator.next();		 	
				session.delete(reportLog);
			}								 
		
			tx.commit();
		}
		catch (HibernateException he)
		{
			hibernateProvider.rollbackTransaction(tx);
					
			if (he.getCause() != null && he.getCause().getMessage() != null && he.getCause().getMessage().toUpperCase().indexOf("CONSTRAINT") > 0)
			{
				throw new ReportAlertDaoException(LocalStrings.ERROR_ALERT_DELETION);
			}
				
			log.error("deleteReportAlert", he);			
			throw new ReportAlertDaoException(LocalStrings.ERROR_SERVERSIDE);
		}
		finally
		{
			hibernateProvider.closeSession(session);
		}*/
	}
	
	public ReportUserAlert executeAlert(ReportUserAlert userAlert, boolean includeReportInLog) throws ReportAlertDaoException
	{
		/*if (userAlert == null) return null;		 
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;		
		
		ReportLog alertLog = new ReportLog(userAlert.getUser(), userAlert.getAlert(), new Date());
		if (includeReportInLog) alertLog.setReport(userAlert.getReport());
		
		try
		{
			reportLogDao.insertReportLog(alertLog);
			
			ReportDesign dataSource = userAlert.getAlert().getDataSource();
			conn = dataSourceProvider.getConnection(dataSource.getId());
			
			pStmt = conn.prepareStatement(userAlert.getAlert().getQuery());

			rs = pStmt.executeQuery();

			if (!rs.next())
			{
				userAlert.setCount(0);
			}

			userAlert.setCount(rs.getInt(1));
			
			if (userAlert.isTriggered())
			{
				alertLog.setStatus(ReportLog.STATUS_TRIGGERED);
			}
			else
			{
				alertLog.setStatus(ReportLog.STATUS_NOT_TRIGGERED);
			}
			
			alertLog.setMessage("Count: " + userAlert.getCount() + " Condition: "
					+ userAlert.getCondition());
			
			alertLog.setEndTime(new Date());
			reportLogDao.updateReportLog(alertLog);
		}
		catch (Exception e)
		{
			alertLog.setMessage(e.getMessage());
			alertLog.setStatus(ReportLog.STATUS_FAILURE);
			alertLog.setEndTime(new Date());

			reportLogDao.updateReportLog(alertLog);
			
			throw new ReportAlertDaoException(PropertyProvider.QUERY_FAILED_MESSAGE);
		}
		finally
		{
			try
			{
				if (rs != null) rs.close();
				if (pStmt != null) pStmt.close();
				if (conn != null) conn.close();
			}
			catch (Exception c)
			{
				log.error("Error closing");
			}
		}*/
		
		return null;//userAlert;
	}	
	
	public void setDataSourceProvider(ReportDesignDao dataSourceProvider)
	{
		this.dataSourceProvider = dataSourceProvider;
	}

	public void setReportLogDao(ReportLogDao reportLogDao)
	{
		this.reportLogDao = reportLogDao;
	}
}