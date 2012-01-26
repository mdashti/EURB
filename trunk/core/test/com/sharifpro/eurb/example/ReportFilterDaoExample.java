package com.sharifpro.eurb.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportFilterDao;
import com.sharifpro.eurb.builder.model.ReportFilter;

public class ReportFilterDaoExample
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
		// findByReportColumn(null, null, null);
		// findByReportColumn2(null, null, null, null);
		// findWhereIdEquals(null);
		// findWhereReportColumnIdEquals(null);
		// findWhereReportColumnDatasetIdEquals(null);
		// findWhereReportColumnDesignIdEquals(null);
		// findWhereReportColumnDesignVersionIdEquals(null);
		// findWherePrefixEquals("");
		// findWhereOperatorEquals("");
		// findWhereSuffixEquals("");
		// findWhereOperand1Equals("");
		// findWhereOperand2Equals("");
		// findWhereTypeEquals(null);
		// findWhereOperand1ColumnIdEquals(null);
		// findWhereOperand1ColumnDatasetIdEquals(null);
		// findWhereOperand1ColumnDesignIdEquals(null);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findAll();
		for (ReportFilter dto : _result) {
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
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findByPersistableObject(id);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportColumn'
	 * 
	 * @param operand1ColumnId
	 * @param operand1ColumnDatasetId
	 * @param operand1ColumnDesignId
	 * @throws Exception
	 */
	public static void findByReportColumn(Long operand1ColumnId, Long operand1ColumnDatasetId, Long operand1ColumnDesignId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findByReportColumn(operand1ColumnId, operand1ColumnDatasetId, operand1ColumnDesignId);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportColumn2'
	 * 
	 * @param reportColumnId
	 * @param reportColumnDatasetId
	 * @param reportColumnDesignId
	 * @param reportColumnDesignVersionId
	 * @throws Exception
	 */
	public static void findByReportColumn2(Long reportColumnId, Long reportColumnDatasetId, Long reportColumnDesignId, Long reportColumnDesignVersionId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findByReportColumn2(reportColumnId, reportColumnDatasetId, reportColumnDesignId, reportColumnDesignVersionId);
		for (ReportFilter dto : _result) {
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
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereIdEquals(id);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReportColumnIdEquals'
	 * 
	 * @param reportColumnId
	 * @throws Exception
	 */
	public static void findWhereReportColumnIdEquals(Long reportColumnId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereReportColumnIdEquals(reportColumnId);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReportColumnDatasetIdEquals'
	 * 
	 * @param reportColumnDatasetId
	 * @throws Exception
	 */
	public static void findWhereReportColumnDatasetIdEquals(Long reportColumnDatasetId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereReportColumnDatasetIdEquals(reportColumnDatasetId);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReportColumnDesignIdEquals'
	 * 
	 * @param reportColumnDesignId
	 * @throws Exception
	 */
	public static void findWhereReportColumnDesignIdEquals(Long reportColumnDesignId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereReportColumnDesignIdEquals(reportColumnDesignId);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReportColumnDesignVersionIdEquals'
	 * 
	 * @param reportColumnDesignVersionId
	 * @throws Exception
	 */
	public static void findWhereReportColumnDesignVersionIdEquals(Long reportColumnDesignVersionId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereReportColumnDesignVersionIdEquals(reportColumnDesignVersionId);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePrefixEquals'
	 * 
	 * @param prefix
	 * @throws Exception
	 */
	public static void findWherePrefixEquals(String prefix) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWherePrefixEquals(prefix);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOperatorEquals'
	 * 
	 * @param operator
	 * @throws Exception
	 */
	public static void findWhereOperatorEquals(String operator) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereOperatorEquals(operator);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSuffixEquals'
	 * 
	 * @param suffix
	 * @throws Exception
	 */
	public static void findWhereSuffixEquals(String suffix) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereSuffixEquals(suffix);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOperand1Equals'
	 * 
	 * @param operand1
	 * @throws Exception
	 */
	public static void findWhereOperand1Equals(String operand1) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereOperand1Equals(operand1);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOperand2Equals'
	 * 
	 * @param operand2
	 * @throws Exception
	 */
	public static void findWhereOperand2Equals(String operand2) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereOperand2Equals(operand2);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereTypeEquals'
	 * 
	 * @param type
	 * @throws Exception
	 */
	public static void findWhereTypeEquals(Integer type) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereFilterTypeEquals(type);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOperand1ColumnIdEquals'
	 * 
	 * @param operand1ColumnId
	 * @throws Exception
	 */
	public static void findWhereOperand1ColumnIdEquals(Long operand1ColumnId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereOperand1ColumnIdEquals(operand1ColumnId);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOperand1ColumnDatasetIdEquals'
	 * 
	 * @param operand1ColumnDatasetId
	 * @throws Exception
	 */
	public static void findWhereOperand1ColumnDatasetIdEquals(Long operand1ColumnDatasetId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereOperand1ColumnDatasetIdEquals(operand1ColumnDatasetId);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOperand1ColumnDesignIdEquals'
	 * 
	 * @param operand1ColumnDesignId
	 * @throws Exception
	 */
	public static void findWhereOperand1ColumnDesignIdEquals(Long operand1ColumnDesignId) throws Exception
	{
		ReportFilterDao dao = DaoFactory.createReportFilterDao();
		List<ReportFilter> _result = dao.findWhereOperand1ColumnDesignIdEquals(operand1ColumnDesignId);
		for (ReportFilter dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(ReportFilter dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getReportColumnId() );
		buf.append( ", " );
		buf.append( dto.getReportColumnDatasetId() );
		buf.append( ", " );
		buf.append( dto.getReportColumnDesignId() );
		buf.append( ", " );
		buf.append( dto.getReportColumnDesignVersionId() );
		buf.append( ", " );
		buf.append( dto.getPrefix() );
		buf.append( ", " );
		buf.append( dto.getOperator() );
		buf.append( ", " );
		buf.append( dto.getSuffix() );
		buf.append( ", " );
		buf.append( dto.getOperand1() );
		buf.append( ", " );
		buf.append( dto.getOperand2() );
		buf.append( ", " );
		buf.append( dto.getFilterType() );
		buf.append( ", " );
		buf.append( dto.getOperand1ColumnId() );
		buf.append( ", " );
		buf.append( dto.getOperand1ColumnDatasetId() );
		buf.append( ", " );
		buf.append( dto.getOperand1ColumnDesignId() );
		System.out.println( buf.toString() );
	}

}
