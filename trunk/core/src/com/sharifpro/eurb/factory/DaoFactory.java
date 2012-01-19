package com.sharifpro.eurb.factory;

import com.sharifpro.eurb.dao.*;
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
		return (AuthoritiesDao) SharifProApplicationContext.getApplicationContext().getBean( "AuthoritiesDao" );
	}

	/**
	 * Method 'createColumnMappingDao'
	 * 
	 * @return ColumnMappingDao
	 */
	public static ColumnMappingDao createColumnMappingDao()
	{
		return (ColumnMappingDao) SharifProApplicationContext.getApplicationContext().getBean( "ColumnMappingDao" );
	}

	/**
	 * Method 'createDbConfigDao'
	 * 
	 * @return DbConfigDao
	 */
	public static DbConfigDao createDbConfigDao()
	{
		return (DbConfigDao) SharifProApplicationContext.getApplicationContext().getBean( "DbConfigDao" );
	}

	/**
	 * Method 'createGroupAggregationDao'
	 * 
	 * @return GroupAggregationDao
	 */
	public static GroupAggregationDao createGroupAggregationDao()
	{
		return (GroupAggregationDao) SharifProApplicationContext.getApplicationContext().getBean( "GroupAggregationDao" );
	}

	/**
	 * Method 'createGroupAuthoritiesDao'
	 * 
	 * @return GroupAuthoritiesDao
	 */
	public static GroupAuthoritiesDao createGroupAuthoritiesDao()
	{
		return (GroupAuthoritiesDao) SharifProApplicationContext.getApplicationContext().getBean( "GroupAuthoritiesDao" );
	}

	/**
	 * Method 'createGroupMembersDao'
	 * 
	 * @return GroupMembersDao
	 */
	public static GroupMembersDao createGroupMembersDao()
	{
		return (GroupMembersDao) SharifProApplicationContext.getApplicationContext().getBean( "GroupMembersDao" );
	}

	/**
	 * Method 'createGroupsDao'
	 * 
	 * @return GroupsDao
	 */
	public static GroupsDao createGroupsDao()
	{
		return (GroupsDao) SharifProApplicationContext.getApplicationContext().getBean( "GroupsDao" );
	}

	/**
	 * Method 'createPersistableObjectDao'
	 * 
	 * @return PersistableObjectDao
	 */
	public static PersistableObjectDao createPersistableObjectDao()
	{
		return (PersistableObjectDao) SharifProApplicationContext.getApplicationContext().getBean( "PersistableObjectDao" );
	}

	/**
	 * Method 'createReportCategoryDao'
	 * 
	 * @return ReportCategoryDao
	 */
	public static ReportCategoryDao createReportCategoryDao()
	{
		return (ReportCategoryDao) SharifProApplicationContext.getApplicationContext().getBean( "ReportCategoryDao" );
	}

	/**
	 * Method 'createReportColumnDao'
	 * 
	 * @return ReportColumnDao
	 */
	public static ReportColumnDao createReportColumnDao()
	{
		return (ReportColumnDao) SharifProApplicationContext.getApplicationContext().getBean( "ReportColumnDao" );
	}

	/**
	 * Method 'createReportDatasetDao'
	 * 
	 * @return ReportDatasetDao
	 */
	public static ReportDatasetDao createReportDatasetDao()
	{
		return (ReportDatasetDao) SharifProApplicationContext.getApplicationContext().getBean( "ReportDatasetDao" );
	}

	/**
	 * Method 'createReportDesignDao'
	 * 
	 * @return ReportDesignDao
	 */
	public static ReportDesignDao createReportDesignDao()
	{
		return (ReportDesignDao) SharifProApplicationContext.getApplicationContext().getBean( "ReportDesignDao" );
	}

	/**
	 * Method 'createReportExecutionHistoryDao'
	 * 
	 * @return ReportExecutionHistoryDao
	 */
	public static ReportExecutionHistoryDao createReportExecutionHistoryDao()
	{
		return (ReportExecutionHistoryDao) SharifProApplicationContext.getApplicationContext().getBean( "ReportExecutionHistoryDao" );
	}

	/**
	 * Method 'createReportFilterDao'
	 * 
	 * @return ReportFilterDao
	 */
	public static ReportFilterDao createReportFilterDao()
	{
		return (ReportFilterDao) SharifProApplicationContext.getApplicationContext().getBean( "ReportFilterDao" );
	}

	/**
	 * Method 'createReportFormatDao'
	 * 
	 * @return ReportFormatDao
	 */
	public static ReportFormatDao createReportFormatDao()
	{
		return (ReportFormatDao) SharifProApplicationContext.getApplicationContext().getBean( "ReportFormatDao" );
	}

	/**
	 * Method 'createTableMappingDao'
	 * 
	 * @return TableMappingDao
	 */
	public static TableMappingDao createTableMappingDao()
	{
		return (TableMappingDao) SharifProApplicationContext.getApplicationContext().getBean( "TableMappingDao" );
	}

	/**
	 * Method 'createUsersDao'
	 * 
	 * @return UsersDao
	 */
	public static UsersDao createUsersDao()
	{
		return (UsersDao) SharifProApplicationContext.getApplicationContext().getBean( "UsersDao" );
	}

}
