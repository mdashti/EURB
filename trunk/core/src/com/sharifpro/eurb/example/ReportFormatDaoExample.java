package com.sharifpro.eurb.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.sharifpro.eurb.dao.ReportFormatDao;
import com.sharifpro.eurb.dto.ReportFormat;
import com.sharifpro.eurb.exceptions.ReportFormatDaoException;
import com.sharifpro.eurb.factory.DaoFactory;

public class ReportFormatDaoExample
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
		// findByReportDesign(null, null);
		// findByPersistableObject(null);
		// findWhereIdEquals(null);
		// findWhereVersionIdEquals(null);
		// findWhereFormatFileEquals("");
		// findWhereIsCurrentEquals(null);
		// findWhereRecordStatusEquals("");
		// findWhereReportDesignIdEquals(null);
		// findWhereReportDesignVersionIdEquals(null);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findAll();
		for (ReportFormat dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportDesign'
	 * 
	 * @param reportDesignId
	 * @param reportDesignVersionId
	 * @throws Exception
	 */
	public static void findByReportDesign(Long reportDesignId, Long reportDesignVersionId) throws Exception
	{
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findByReportDesign(reportDesignId, reportDesignVersionId);
		for (ReportFormat dto : _result) {
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
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findByPersistableObject(id);
		for (ReportFormat dto : _result) {
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
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findWhereIdEquals(id);
		for (ReportFormat dto : _result) {
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
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findWhereVersionIdEquals(versionId);
		for (ReportFormat dto : _result) {
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
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findWhereFormatFileEquals(formatFile);
		for (ReportFormat dto : _result) {
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
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findWhereIsCurrentEquals(isCurrent);
		for (ReportFormat dto : _result) {
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
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findWhereRecordStatusEquals(recordStatus);
		for (ReportFormat dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReportDesignIdEquals'
	 * 
	 * @param reportDesignId
	 * @throws Exception
	 */
	public static void findWhereReportDesignIdEquals(Long reportDesignId) throws Exception
	{
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findWhereReportDesignIdEquals(reportDesignId);
		for (ReportFormat dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReportDesignVersionIdEquals'
	 * 
	 * @param reportDesignVersionId
	 * @throws Exception
	 */
	public static void findWhereReportDesignVersionIdEquals(Long reportDesignVersionId) throws Exception
	{
		ReportFormatDao dao = DaoFactory.createReportFormatDao();
		List<ReportFormat> _result = dao.findWhereReportDesignVersionIdEquals(reportDesignVersionId);
		for (ReportFormat dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(ReportFormat dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getVersionId() );
		buf.append( ", " );
		buf.append( dto.getFormatFile() );
		buf.append( ", " );
		buf.append( dto.getIsCurrent() );
		buf.append( ", " );
		buf.append( dto.getRecordStatus() );
		buf.append( ", " );
		buf.append( dto.getReportDesignId() );
		buf.append( ", " );
		buf.append( dto.getReportDesignVersionId() );
		System.out.println( buf.toString() );
	}

}
