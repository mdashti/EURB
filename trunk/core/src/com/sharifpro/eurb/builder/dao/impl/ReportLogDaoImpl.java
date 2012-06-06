package com.sharifpro.eurb.builder.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.sharifpro.eurb.builder.dao.ReportLogDao;
import com.sharifpro.eurb.builder.exception.ReportLogDaoException;
import com.sharifpro.eurb.builder.model.ReportLog;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;

public class ReportLogDaoImpl extends AbstractDAO implements ReportLogDao
{
	protected static Logger log =
		Logger.getLogger(ReportLogDaoImpl.class.getName());	

	public ReportLogDaoImpl() throws ReportLogDaoException
	{
		log.info("ReportLogDaoImpl created");
	}


	public ReportLog getReportLog(Integer id) throws ReportLogDaoException
	{
		return null;//(ReportLog) hibernateProvider.load(ReportLog.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReportLog> getReportLogs(String status, Integer userId, Integer reportId, Integer alertId, int maxRows) throws ReportLogDaoException
	{
		/*Session session = hibernateProvider.openSession();		

		try
		{
			Criteria criteria = session.createCriteria(ReportLog.class);
			criteria.setMaxResults(maxRows);
			
			if (status != null && !status.equals("-1"))
			{
				criteria.add(Restrictions.eq("status", status));
			}
			
			if (userId != null && !userId.equals(new Integer(-1)))
			{
				criteria.add(Restrictions.eq("user.id", userId));
			}
			
			if (reportId != null && !reportId.equals(new Integer(-1)))
			{
				if (reportId.intValue() == -1)
				{
					criteria.add(Restrictions.isNull("report"));
				}
				else
				{
					criteria.add(Restrictions.eq("report.id", reportId));
				}
			}
			
			if (alertId != null && !alertId.equals(new Integer(-1)))
			{
				if (alertId.intValue() == -1)
				{
					criteria.add(Restrictions.isNull("alert"));
				}
				else
				{
					criteria.add(Restrictions.eq("alert.id", alertId));
				}
			}
			
			return criteria.list();
		}
		catch (HibernateException he)
		{			
			throw new ReportLogDaoException(he);
		}
		finally
		{
			hibernateProvider.closeSession(session);
		}*/
		return null;
	}

	public ReportLog insertReportLog(ReportLog reportLog)
		throws ReportLogDaoException
	{
		return null;//(ReportLog) hibernateProvider.save(reportLog);
	}

	public void updateReportLog(ReportLog reportLog) throws ReportLogDaoException
	{
		//hibernateProvider.update(reportLog);
	}

	public void deleteReportLog(ReportLog reportLog) throws ReportLogDaoException
	{
		/*try
		{
			hibernateProvider.delete(reportLog);
		}
		catch (ConstraintException e)
		{
			throw new ReportLogDaoException(e.getMessage());
		}*/
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopReportsByUser() throws ReportLogDaoException
	{
		String fromClause = "select log.user.name, log.report.name, count(*) from org.efs.openreports.objects.ReportLog log group by log.user.name, log.report.name order by log.user.name, count(*) desc ";

		return null;//(List<Object[]>) hibernateProvider.query(fromClause);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopReports() throws ReportLogDaoException
	{
		String fromClause = "select log.report.name, count(*) from org.efs.openreports.objects.ReportLog log group by log.report.name order by count(*) desc ";

		return null;//(List<Object[]>) hibernateProvider.query(fromClause);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopFailures() throws ReportLogDaoException
	{
		String fromClause = "select log.report.name, count(*) from org.efs.openreports.objects.ReportLog log where log.status = 'failure' group by log.report.name order by count(*) desc ";

		return null;//(List<Object[]>) hibernateProvider.query(fromClause);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopEmptyReports() throws ReportLogDaoException
	{
		String fromClause = "select log.report.name, count(*) from org.efs.openreports.objects.ReportLog log where log.status = 'empty' group by log.report.name order by count(*) desc ";

		return null;//(List<Object[]>) hibernateProvider.query(fromClause);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopReportsForPeriod(int daysBack) throws ReportLogDaoException
	{
		/*try
		{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -daysBack);			
			
			Session session = hibernateProvider.openSession();			
			
			try
			{			
				List<Object[]> list = session.createQuery(
						"select log.report.name, count(*) from org.efs.openreports.objects.ReportLog log " 
						+ " where log.startTime > ? group by log.report.name order by count(*) desc").setDate(0, calendar.getTime()).list();					

				return list;
			}
			catch (HibernateException he)
			{			
				throw he;
			}
			finally
			{
				session.close();
			}
		}
		catch (HibernateException he)
		{
			throw new ReportLogDaoException(he);
		}*/
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopAlertsByUser() throws ReportLogDaoException
	{
		String fromClause = "select log.user.name, log.alert.name, count(*) from org.efs.openreports.objects.ReportLog log group by log.user.name, log.alert.name order by log.user.name, count(*) desc ";

		return null;//(List<Object[]>) hibernateProvider.query(fromClause);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopAlerts() throws ReportLogDaoException
	{
		String fromClause = "select log.alert.name, count(*) from org.efs.openreports.objects.ReportLog log group by log.alert.name order by count(*) desc ";

		return null;//(List<Object[]>) hibernateProvider.query(fromClause);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopTriggeredAlerts() throws ReportLogDaoException
	{
		String fromClause = "select log.alert.name, count(*) from org.efs.openreports.objects.ReportLog log where log.status = 'triggered' group by log.alert.name order by count(*) desc ";

		return null;//(List<Object[]>) hibernateProvider.query(fromClause);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopNotTriggeredAlerts() throws ReportLogDaoException
	{
		String fromClause = "select log.alert.name, count(*) from org.efs.openreports.objects.ReportLog log where log.status = 'not triggered' group by log.alert.name order by count(*) desc ";

		return null;//(List<Object[]>) hibernateProvider.query(fromClause);
	}
}