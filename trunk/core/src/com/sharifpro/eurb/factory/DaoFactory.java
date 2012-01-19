package com.sharifpro.eurb.factory;

import com.sharifpro.eurb.dao.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class DaoFactory
{
	/**
	 * Method 'createAuthoritiesDao'
	 * 
	 * @return AuthoritiesDao
	 */
	public static AuthoritiesDao createAuthoritiesDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (AuthoritiesDao) bf.getBean( "AuthoritiesDao" );
	}

	/**
	 * Method 'createColumnMappingDao'
	 * 
	 * @return ColumnMappingDao
	 */
	public static ColumnMappingDao createColumnMappingDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (ColumnMappingDao) bf.getBean( "ColumnMappingDao" );
	}

	/**
	 * Method 'createDbConfigDao'
	 * 
	 * @return DbConfigDao
	 */
	public static DbConfigDao createDbConfigDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (DbConfigDao) bf.getBean( "DbConfigDao" );
	}

	/**
	 * Method 'createGroupAggregationDao'
	 * 
	 * @return GroupAggregationDao
	 */
	public static GroupAggregationDao createGroupAggregationDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (GroupAggregationDao) bf.getBean( "GroupAggregationDao" );
	}

	/**
	 * Method 'createGroupAuthoritiesDao'
	 * 
	 * @return GroupAuthoritiesDao
	 */
	public static GroupAuthoritiesDao createGroupAuthoritiesDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (GroupAuthoritiesDao) bf.getBean( "GroupAuthoritiesDao" );
	}

	/**
	 * Method 'createGroupMembersDao'
	 * 
	 * @return GroupMembersDao
	 */
	public static GroupMembersDao createGroupMembersDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (GroupMembersDao) bf.getBean( "GroupMembersDao" );
	}

	/**
	 * Method 'createGroupsDao'
	 * 
	 * @return GroupsDao
	 */
	public static GroupsDao createGroupsDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (GroupsDao) bf.getBean( "GroupsDao" );
	}

	/**
	 * Method 'createPersistableObjectDao'
	 * 
	 * @return PersistableObjectDao
	 */
	public static PersistableObjectDao createPersistableObjectDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (PersistableObjectDao) bf.getBean( "PersistableObjectDao" );
	}

	/**
	 * Method 'createReportCategoryDao'
	 * 
	 * @return ReportCategoryDao
	 */
	public static ReportCategoryDao createReportCategoryDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (ReportCategoryDao) bf.getBean( "ReportCategoryDao" );
	}

	/**
	 * Method 'createReportColumnDao'
	 * 
	 * @return ReportColumnDao
	 */
	public static ReportColumnDao createReportColumnDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (ReportColumnDao) bf.getBean( "ReportColumnDao" );
	}

	/**
	 * Method 'createReportDatasetDao'
	 * 
	 * @return ReportDatasetDao
	 */
	public static ReportDatasetDao createReportDatasetDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (ReportDatasetDao) bf.getBean( "ReportDatasetDao" );
	}

	/**
	 * Method 'createReportDesignDao'
	 * 
	 * @return ReportDesignDao
	 */
	public static ReportDesignDao createReportDesignDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (ReportDesignDao) bf.getBean( "ReportDesignDao" );
	}

	/**
	 * Method 'createReportExecutionHistoryDao'
	 * 
	 * @return ReportExecutionHistoryDao
	 */
	public static ReportExecutionHistoryDao createReportExecutionHistoryDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (ReportExecutionHistoryDao) bf.getBean( "ReportExecutionHistoryDao" );
	}

	/**
	 * Method 'createReportFilterDao'
	 * 
	 * @return ReportFilterDao
	 */
	public static ReportFilterDao createReportFilterDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (ReportFilterDao) bf.getBean( "ReportFilterDao" );
	}

	/**
	 * Method 'createReportFormatDao'
	 * 
	 * @return ReportFormatDao
	 */
	public static ReportFormatDao createReportFormatDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (ReportFormatDao) bf.getBean( "ReportFormatDao" );
	}

	/**
	 * Method 'createTableMappingDao'
	 * 
	 * @return TableMappingDao
	 */
	public static TableMappingDao createTableMappingDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TableMappingDao) bf.getBean( "TableMappingDao" );
	}

	/**
	 * Method 'createUsersDao'
	 * 
	 * @return UsersDao
	 */
	public static UsersDao createUsersDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (UsersDao) bf.getBean( "UsersDao" );
	}

}
