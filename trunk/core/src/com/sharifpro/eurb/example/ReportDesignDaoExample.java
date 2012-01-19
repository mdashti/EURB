package com.sharifpro.eurb.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.sharifpro.eurb.dao.ReportDesignDao;
import com.sharifpro.eurb.dto.ReportDesign;
import com.sharifpro.eurb.exceptions.ReportDesignDaoException;
import com.sharifpro.eurb.factory.DaoFactory;

public class ReportDesignDaoExample
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
		// findByReportCategory(null);
		// findWhereIdEquals(null);
		// findWhereVersionIdEquals(null);
		// findWhereNameEquals("");
		// findWhereDescriptionEquals("");
		// findWhereCategoryIdEquals(null);
		// findWhereQueryTextEquals("");
		// findWhereSelectPartEquals("");
		// findWhereResultDataEquals("");
		// findWhereFormatFileEquals("");
		// findWhereIsCurrentEquals(null);
		// findWhereRecordStatusEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findAll();
		for (ReportDesign dto : _result) {
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
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findByPersistableObject(id);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportCategory'
	 * 
	 * @param categoryId
	 * @throws Exception
	 */
	public static void findByReportCategory(Long categoryId) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findByReportCategory(categoryId);
		for (ReportDesign dto : _result) {
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
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereIdEquals(id);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereVersionIdEquals'
	 * 
	 * @param versionId
	 * @throws Exception
	 */
	public static void findWhereVersionIdEquals(Long versionId) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereVersionIdEquals(versionId);
		for (ReportDesign dto : _result) {
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
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereNameEquals(name);
		for (ReportDesign dto : _result) {
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
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereDescriptionEquals(description);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereCategoryIdEquals'
	 * 
	 * @param categoryId
	 * @throws Exception
	 */
	public static void findWhereCategoryIdEquals(Long categoryId) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereCategoryIdEquals(categoryId);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereQueryTextEquals'
	 * 
	 * @param queryText
	 * @throws Exception
	 */
	public static void findWhereQueryTextEquals(String queryText) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereQueryTextEquals(queryText);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSelectPartEquals'
	 * 
	 * @param selectPart
	 * @throws Exception
	 */
	public static void findWhereSelectPartEquals(String selectPart) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereSelectPartEquals(selectPart);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereResultDataEquals'
	 * 
	 * @param resultData
	 * @throws Exception
	 */
	public static void findWhereResultDataEquals(String resultData) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereResultDataEquals(resultData);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFormatFileEquals'
	 * 
	 * @param formatFile
	 * @throws Exception
	 */
	public static void findWhereFormatFileEquals(String formatFile) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereFormatFileEquals(formatFile);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIsCurrentEquals'
	 * 
	 * @param isCurrent
	 * @throws Exception
	 */
	public static void findWhereIsCurrentEquals(Short isCurrent) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereIsCurrentEquals(isCurrent);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereRecordStatusEquals'
	 * 
	 * @param recordStatus
	 * @throws Exception
	 */
	public static void findWhereRecordStatusEquals(String recordStatus) throws Exception
	{
		ReportDesignDao dao = DaoFactory.createReportDesignDao();
		List<ReportDesign> _result = dao.findWhereRecordStatusEquals(recordStatus);
		for (ReportDesign dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(ReportDesign dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getVersionId() );
		buf.append( ", " );
		buf.append( dto.getName() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		buf.append( ", " );
		buf.append( dto.getCategoryId() );
		buf.append( ", " );
		buf.append( dto.getQueryText() );
		buf.append( ", " );
		buf.append( dto.getSelectPart() );
		buf.append( ", " );
		buf.append( dto.getResultData() );
		buf.append( ", " );
		buf.append( dto.getFormatFile() );
		buf.append( ", " );
		buf.append( dto.getIsCurrent() );
		buf.append( ", " );
		buf.append( dto.getRecordStatus() );
		System.out.println( buf.toString() );
	}

}
