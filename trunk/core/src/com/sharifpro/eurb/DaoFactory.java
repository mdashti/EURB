package com.sharifpro.eurb;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.acls.model.AclService;

import com.sharifpro.eurb.builder.dao.GroupAggregationDao;
import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.dao.ReportExecutionHistoryDao;
import com.sharifpro.eurb.builder.dao.ReportFilterDao;
import com.sharifpro.eurb.builder.dao.ReportFormatDao;
import com.sharifpro.eurb.dashboard.dao.DashboardColDao;
import com.sharifpro.eurb.dashboard.dao.DashboardDao;
import com.sharifpro.eurb.dashboard.dao.DashboardItemDao;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.PersistableObjectDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.security.dao.AuthoritiesDao;
import com.sharifpro.eurb.management.security.dao.GroupAuthoritiesDao;
import com.sharifpro.eurb.management.security.dao.GroupMembersDao;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.util.SharifProApplicationContext;

public class DaoFactory
{
	/**
	 * Method 'createAuthoritiesDao'
	 * 
	 * @return AuthoritiesDao
	 */
	public static AuthoritiesDao createAuthoritiesDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( AuthoritiesDao.class );
	}

	/**
	 * Method 'createColumnMappingDao'
	 * 
	 * @return ColumnMappingDao
	 */
	public static ColumnMappingDao createColumnMappingDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( ColumnMappingDao.class );
	}

	/**
	 * Method 'createDbConfigDao'
	 * 
	 * @return DbConfigDao
	 */
	public static DbConfigDao createDbConfigDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( DbConfigDao.class );
	}

	/**
	 * Method 'createGroupAggregationDao'
	 * 
	 * @return GroupAggregationDao
	 */
	public static GroupAggregationDao createGroupAggregationDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( GroupAggregationDao.class );
	}

	/**
	 * Method 'createGroupAuthoritiesDao'
	 * 
	 * @return GroupAuthoritiesDao
	 */
	public static GroupAuthoritiesDao createGroupAuthoritiesDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( GroupAuthoritiesDao.class );
	}

	/**
	 * Method 'createGroupMembersDao'
	 * 
	 * @return GroupMembersDao
	 */
	public static GroupMembersDao createGroupMembersDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( GroupMembersDao.class );
	}

	/**
	 * Method 'createGroupsDao'
	 * 
	 * @return GroupsDao
	 */
	public static GroupsDao createGroupsDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( GroupsDao.class );
	}

	/**
	 * Method 'createPersistableObjectDao'
	 * 
	 * @return PersistableObjectDao
	 */
	public static PersistableObjectDao createPersistableObjectDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( PersistableObjectDao.class );
	}

	/**
	 * Method 'createReportCategoryDao'
	 * 
	 * @return ReportCategoryDao
	 */
	public static ReportCategoryDao createReportCategoryDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( ReportCategoryDao.class );
	}

	/**
	 * Method 'createReportColumnDao'
	 * 
	 * @return ReportColumnDao
	 */
	public static ReportColumnDao createReportColumnDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( ReportColumnDao.class );
	}

	/**
	 * Method 'createReportDatasetDao'
	 * 
	 * @return ReportDatasetDao
	 */
	public static ReportDatasetDao createReportDatasetDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( ReportDatasetDao.class );
	}

	/**
	 * Method 'createReportDesignDao'
	 * 
	 * @return ReportDesignDao
	 */
	public static ReportDesignDao createReportDesignDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( ReportDesignDao.class );
	}

	/**
	 * Method 'createReportExecutionHistoryDao'
	 * 
	 * @return ReportExecutionHistoryDao
	 */
	public static ReportExecutionHistoryDao createReportExecutionHistoryDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( ReportExecutionHistoryDao.class );
	}

	/**
	 * Method 'createReportFilterDao'
	 * 
	 * @return ReportFilterDao
	 */
	public static ReportFilterDao createReportFilterDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( ReportFilterDao.class );
	}

	/**
	 * Method 'createReportFormatDao'
	 * 
	 * @return ReportFormatDao
	 */
	public static ReportFormatDao createReportFormatDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( ReportFormatDao.class );
	}

	/**
	 * Method 'createTableMappingDao'
	 * 
	 * @return TableMappingDao
	 */
	public static TableMappingDao createTableMappingDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( TableMappingDao.class );
	}

	/**
	 * Method 'createUserDao'
	 * 
	 * @return UserDao
	 */
	public static UserDao createUserDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( UserDao.class );
	}

	/**
	 * Method 'createAclService'
	 * 
	 * @return AclService
	 */
	public static AclService createAclService()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( AclService.class );
	}
	
	/**
	 * Method 'createDashboardDao'
	 * 
	 * @return DashboardDao
	 */
	public static DashboardDao createDashboardDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( DashboardDao.class );
	}

	/**
	 * Method 'createDashboardColDao'
	 * 
	 * @return DashboardColDao
	 */
	public static DashboardColDao createDashboardColDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( DashboardColDao.class );
	}

	/**
	 * Method 'createDashboardItemDao'
	 * 
	 * @return DashboardItemDao
	 */
	public static DashboardItemDao createDashboardItemDao()
	{
		return SharifProApplicationContext.getApplicationContext().getBean( DashboardItemDao.class );
	}
}
