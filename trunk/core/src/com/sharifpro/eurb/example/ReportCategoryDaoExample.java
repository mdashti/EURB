package com.sharifpro.eurb.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.sharifpro.eurb.dao.ReportCategoryDao;
import com.sharifpro.eurb.dto.ReportCategory;
import com.sharifpro.eurb.exceptions.ReportCategoryDaoException;
import com.sharifpro.eurb.factory.DaoFactory;

public class ReportCategoryDaoExample
{
	/**
	 * Method 'main'
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception
	{
		// Uncomment one of the lines below to test the generated code
		
		// findAll();
		// findByPersistableObject(null);
		// findWhereIdEquals(null);
		// findWhereNameEquals("");
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		ReportCategoryDao dao = DaoFactory.createReportCategoryDao();
		List<ReportCategory> _result = dao.findAll();
		for (ReportCategory dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByPersistableObject'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findByPersistableObject(Long id) throws Exception
	{
		ReportCategoryDao dao = DaoFactory.createReportCategoryDao();
		List<ReportCategory> _result = dao.findByPersistableObject(id);
		for (ReportCategory dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIdEquals'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findWhereIdEquals(Long id) throws Exception
	{
		ReportCategoryDao dao = DaoFactory.createReportCategoryDao();
		List<ReportCategory> _result = dao.findWhereIdEquals(id);
		for (ReportCategory dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNameEquals'
	 * 
	 * @param name
	 * @throws Exception
	 */
	public static void findWhereNameEquals(String name) throws Exception
	{
		ReportCategoryDao dao = DaoFactory.createReportCategoryDao();
		List<ReportCategory> _result = dao.findWhereNameEquals(name);
		for (ReportCategory dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDescriptionEquals'
	 * 
	 * @param description
	 * @throws Exception
	 */
	public static void findWhereDescriptionEquals(String description) throws Exception
	{
		ReportCategoryDao dao = DaoFactory.createReportCategoryDao();
		List<ReportCategory> _result = dao.findWhereDescriptionEquals(description);
		for (ReportCategory dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(ReportCategory dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getName() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
