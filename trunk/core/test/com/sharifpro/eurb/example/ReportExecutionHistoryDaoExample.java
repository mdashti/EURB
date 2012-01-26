package com.sharifpro.eurb.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportExecutionHistoryDao;
import com.sharifpro.eurb.builder.model.ReportExecutionHistory;

public class ReportExecutionHistoryDaoExample
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
		// findWhereExecutionResultEquals("");
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findAll();
		for (ReportExecutionHistory dto : _result) {
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findByReportDesign(reportDesignId, reportDesignVersionId);
		for (ReportExecutionHistory dto : _result) {
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findByPersistableObject(id);
		for (ReportExecutionHistory dto : _result) {
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findWhereIdEquals(id);
		for (ReportExecutionHistory dto : _result) {
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findWhereVersionIdEquals(versionId);
		for (ReportExecutionHistory dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereExecutionResultEquals'
	 * 
	 * @param executionResult
	 * @throws Exception
	 */
	public static void findWhereExecutionResultEquals(String executionResult) throws Exception
	{
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findWhereExecutionResultEquals(executionResult);
		for (ReportExecutionHistory dto : _result) {
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findWhereIsCurrentEquals(isCurrent);
		for (ReportExecutionHistory dto : _result) {
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findWhereRecordStatusEquals(recordStatus);
		for (ReportExecutionHistory dto : _result) {
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findWhereReportDesignIdEquals(reportDesignId);
		for (ReportExecutionHistory dto : _result) {
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
		ReportExecutionHistoryDao dao = DaoFactory.createReportExecutionHistoryDao();
		List<ReportExecutionHistory> _result = dao.findWhereReportDesignVersionIdEquals(reportDesignVersionId);
		for (ReportExecutionHistory dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(ReportExecutionHistory dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getVersionId() );
		buf.append( ", " );
		buf.append( dto.getExecutionResult() );
		buf.append( ", " );
		buf.append( dto.isCurrent() );
		buf.append( ", " );
		buf.append( dto.getRecordStatus() );
		buf.append( ", " );
		buf.append( dto.getReportDesignId() );
		buf.append( ", " );
		buf.append( dto.getReportDesignVersionId() );
		System.out.println( buf.toString() );
	}

}
