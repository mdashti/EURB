package com.sharifpro.eurb.example;

import java.util.List;
import com.sharifpro.eurb.dao.ReportColumnDao;
import com.sharifpro.eurb.dto.ReportColumn;
import com.sharifpro.eurb.factory.DaoFactory;

public class ReportColumnDaoExample
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
		// findByColumnMapping(null);
		// findByReportColumn(null);
		// findByReportDataset(null, null, null);
		// findWhereIdEquals(null);
		// findWhereDatasetIdEquals(null);
		// findWhereDesignIdEquals(null);
		// findWhereDesignVersionIdEquals(null);
		// findWhereTypeEquals(null);
		// findWhereColumnMappingIdEquals(null);
		// findWhereReportColumnIdEquals(null);
		// findWhereOrderEquals(null);
		// findWhereSortOrderEquals(null);
		// findWhereSortTypeEquals(null);
		// findWhereGroupLevelEquals(null);
		// findWhereColumnWidthEquals(null);
		// findWhereColumnAlignEquals("");
		// findWhereColumnDirEquals("");
		// findWhereColumnHeaderEquals("");
		// findWhereIsCustomEquals(null);
		// findWhereFormulaEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findAll();
		for (ReportColumn dto : _result) {
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
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findByPersistableObject(id);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByColumnMapping'
	 * 
	 * @param columnMappingId
	 * @throws Exception
	 */
	public static void findByColumnMapping(Long columnMappingId) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findByColumnMapping(columnMappingId);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportColumn'
	 * 
	 * @param reportColumnId
	 * @throws Exception
	 */
	public static void findByReportColumn(Long reportColumnId) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findByReportColumn(reportColumnId);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportDataset'
	 * 
	 * @param datasetId
	 * @param designId
	 * @param designVersionId
	 * @throws Exception
	 */
	public static void findByReportDataset(Long datasetId, Long designId, Long designVersionId) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findByReportDataset(datasetId, designId, designVersionId);
		for (ReportColumn dto : _result) {
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
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereIdEquals(id);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDatasetIdEquals'
	 * 
	 * @param datasetId
	 * @throws Exception
	 */
	public static void findWhereDatasetIdEquals(Long datasetId) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereDatasetIdEquals(datasetId);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDesignIdEquals'
	 * 
	 * @param designId
	 * @throws Exception
	 */
	public static void findWhereDesignIdEquals(Long designId) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereDesignIdEquals(designId);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDesignVersionIdEquals'
	 * 
	 * @param designVersionId
	 * @throws Exception
	 */
	public static void findWhereDesignVersionIdEquals(Long designVersionId) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereDesignVersionIdEquals(designVersionId);
		for (ReportColumn dto : _result) {
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
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereColTypeEquals(type);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereColumnMappingIdEquals'
	 * 
	 * @param columnMappingId
	 * @throws Exception
	 */
	public static void findWhereColumnMappingIdEquals(Long columnMappingId) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereColumnMappingIdEquals(columnMappingId);
		for (ReportColumn dto : _result) {
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
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereReportColumnIdEquals(reportColumnId);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOrderEquals'
	 * 
	 * @param order
	 * @throws Exception
	 */
	public static void findWhereOrderEquals(Integer order) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereColOrderEquals(order);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSortOrderEquals'
	 * 
	 * @param sortOrder
	 * @throws Exception
	 */
	public static void findWhereSortOrderEquals(Integer sortOrder) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereSortOrderEquals(sortOrder);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSortTypeEquals'
	 * 
	 * @param sortType
	 * @throws Exception
	 */
	public static void findWhereSortTypeEquals(Short sortType) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereSortTypeEquals(sortType);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereGroupLevelEquals'
	 * 
	 * @param groupLevel
	 * @throws Exception
	 */
	public static void findWhereGroupLevelEquals(Integer groupLevel) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereGroupLevelEquals(groupLevel);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereColumnWidthEquals'
	 * 
	 * @param columnWidth
	 * @throws Exception
	 */
	public static void findWhereColumnWidthEquals(Integer columnWidth) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereColumnWidthEquals(columnWidth);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereColumnAlignEquals'
	 * 
	 * @param columnAlign
	 * @throws Exception
	 */
	public static void findWhereColumnAlignEquals(String columnAlign) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereColumnAlignEquals(columnAlign);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereColumnDirEquals'
	 * 
	 * @param columnDir
	 * @throws Exception
	 */
	public static void findWhereColumnDirEquals(String columnDir) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereColumnDirEquals(columnDir);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereColumnHeaderEquals'
	 * 
	 * @param columnHeader
	 * @throws Exception
	 */
	public static void findWhereColumnHeaderEquals(String columnHeader) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereColumnHeaderEquals(columnHeader);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIsCustomEquals'
	 * 
	 * @param isCustom
	 * @throws Exception
	 */
	public static void findWhereIsCustomEquals(Short isCustom) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereIsCustomEquals(isCustom);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFormulaEquals'
	 * 
	 * @param formula
	 * @throws Exception
	 */
	public static void findWhereFormulaEquals(String formula) throws Exception
	{
		ReportColumnDao dao = DaoFactory.createReportColumnDao();
		List<ReportColumn> _result = dao.findWhereFormulaEquals(formula);
		for (ReportColumn dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(ReportColumn dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getDatasetId() );
		buf.append( ", " );
		buf.append( dto.getDesignId() );
		buf.append( ", " );
		buf.append( dto.getDesignVersionId() );
		buf.append( ", " );
		buf.append( dto.getColType() );
		buf.append( ", " );
		buf.append( dto.getColumnMappingId() );
		buf.append( ", " );
		buf.append( dto.getReportColumnId() );
		buf.append( ", " );
		buf.append( dto.getColOrder() );
		buf.append( ", " );
		buf.append( dto.getSortOrder() );
		buf.append( ", " );
		buf.append( dto.isSortType() );
		buf.append( ", " );
		buf.append( dto.getGroupLevel() );
		buf.append( ", " );
		buf.append( dto.getColumnWidth() );
		buf.append( ", " );
		buf.append( dto.getColumnAlign() );
		buf.append( ", " );
		buf.append( dto.getColumnDir() );
		buf.append( ", " );
		buf.append( dto.getColumnHeader() );
		buf.append( ", " );
		buf.append( dto.isCustom() );
		buf.append( ", " );
		buf.append( dto.getFormula() );
		System.out.println( buf.toString() );
	}

}
