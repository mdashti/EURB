package com.sharifpro.eurb.factory;

import com.sharifpro.eurb.dao.*;
import com.sharifpro.util.MyApplicationContext;

public class DaoFactory
{
	/**
	 * Method 'createAuthoritiesDao'
	 * 
	 * @return AuthoritiesDao
	 */
	public static AuthoritiesDao createAuthoritiesDao()
	{
		return (AuthoritiesDao) MyApplicationContext.getApplicationContext().getBean( "AuthoritiesDao" );
	}

	/**
	 * Method 'createColumnMappingDao'
	 * 
	 * @return ColumnMappingDao
	 */
	public static ColumnMappingDao createColumnMappingDao()
	{
		return (ColumnMappingDao) MyApplicationContext.getApplicationContext().getBean( "ColumnMappingDao" );
	}

	/**
	 * Method 'createDbConfigDao'
	 * 
	 * @return DbConfigDao
	 */
	public static DbConfigDao createDbConfigDao()
	{
		return (DbConfigDao) MyApplicationContext.getApplicationContext().getBean( "DbConfigDao" );
	}

	/**
	 * Method 'createGroupAggregationDao'
	 * 
	 * @return GroupAggregationDao
	 */
	public static GroupAggregationDao createGroupAggregationDao()
	{
		return (GroupAggregationDao) MyApplicationContext.getApplicationContext().getBean( "GroupAggregationDao" );
	}

	/**
	 * Method 'createGroupAuthoritiesDao'
	 * 
	 * @return GroupAuthoritiesDao
	 */
	public static GroupAuthoritiesDao createGroupAuthoritiesDao()
	{
		return (GroupAuthoritiesDao) MyApplicationContext.getApplicationContext().getBean( "GroupAuthoritiesDao" );
	}

	/**
	 * Method 'createGroupMembersDao'
	 * 
	 * @return GroupMembersDao
	 */
	public static GroupMembersDao createGroupMembersDao()
	{
		return (GroupMembersDao) MyApplicationContext.getApplicationContext().getBean( "GroupMembersDao" );
	}

	/**
	 * Method 'createGroupsDao'
	 * 
	 * @return GroupsDao
	 */
	public static GroupsDao createGroupsDao()
	{
		return (GroupsDao) MyApplicationContext.getApplicationContext().getBean( "GroupsDao" );
	}

	/**
	 * Method 'createPersistableObjectDao'
	 * 
	 * @return PersistableObjectDao
	 */
	public static PersistableObjectDao createPersistableObjectDao()
	{
		return (PersistableObjectDao) MyApplicationContext.getApplicationContext().getBean( "PersistableObjectDao" );
	}

	/**
	 * Method 'createReportCategoryDao'
	 * 
	 * @return ReportCategoryDao
	 */
	public static ReportCategoryDao createReportCategoryDao()
	{
		return (ReportCategoryDao) MyApplicationContext.getApplicationContext().getBean( "ReportCategoryDao" );
	}

	/**
	 * Method 'createReportColumnDao'
	 * 
	 * @return ReportColumnDao
	 */
	public static ReportColumnDao createReportColumnDao()
	{
		return (ReportColumnDao) MyApplicationContext.getApplicationContext().getBean( "ReportColumnDao" );
	}

	/**
	 * Method 'createReportDatasetDao'
	 * 
	 * @return ReportDatasetDao
	 */
	public static ReportDatasetDao createReportDatasetDao()
	{
		return (ReportDatasetDao) MyApplicationContext.getApplicationContext().getBean( "ReportDatasetDao" );
	}

	/**
	 * Method 'createReportDesignDao'
	 * 
	 * @return ReportDesignDao
	 */
	public static ReportDesignDao createReportDesignDao()
	{
		return (ReportDesignDao) MyApplicationContext.getApplicationContext().getBean( "ReportDesignDao" );
	}

	/**
	 * Method 'createReportExecutionHistoryDao'
	 * 
	 * @return ReportExecutionHistoryDao
	 */
	public static ReportExecutionHistoryDao createReportExecutionHistoryDao()
	{
		return (ReportExecutionHistoryDao) MyApplicationContext.getApplicationContext().getBean( "ReportExecutionHistoryDao" );
	}

	/**
	 * Method 'createReportFilterDao'
	 * 
	 * @return ReportFilterDao
	 */
	public static ReportFilterDao createReportFilterDao()
	{
		return (ReportFilterDao) MyApplicationContext.getApplicationContext().getBean( "ReportFilterDao" );
	}

	/**
	 * Method 'createReportFormatDao'
	 * 
	 * @return ReportFormatDao
	 */
	public static ReportFormatDao createReportFormatDao()
	{
		return (ReportFormatDao) MyApplicationContext.getApplicationContext().getBean( "ReportFormatDao" );
	}

	/**
	 * Method 'createTableMappingDao'
	 * 
	 * @return TableMappingDao
	 */
	public static TableMappingDao createTableMappingDao()
	{
		return (TableMappingDao) MyApplicationContext.getApplicationContext().getBean( "TableMappingDao" );
	}

	/**
	 * Method 'createUsersDao'
	 * 
	 * @return UsersDao
	 */
	public static UsersDao createUsersDao()
	{
		return (UsersDao) MyApplicationContext.getApplicationContext().getBean( "UsersDao" );
	}

}
