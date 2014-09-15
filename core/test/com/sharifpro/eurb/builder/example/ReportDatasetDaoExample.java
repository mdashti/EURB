package com.sharifpro.eurb.builder.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.model.ReportDataset;

public class ReportDatasetDaoExample
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
		// findByReportDesign(null, null);
		// findByReportDesign2(null, null);
		// findByTableMapping(null);
		// findWhereIdEquals(null);
		// findWhereDesignIdEquals(null);
		// findWhereDesignVersionIdEquals(null);
		// findWhereTableMappingIdEquals(null);
		// findWhereBaseReportIdEquals(null);
		// findWhereBaseReportVersionIdEquals(null);
		// findWhereOrderEquals(null);
		// findWhereOperatorEquals(null);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findAll();
		for (ReportDataset dto : _result) {
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
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findByPersistableObject(id);
		for (ReportDataset dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportDesign'
	 * 
	 * @param baseReportId
	 * @param baseReportVersionId
	 * @throws Exception
	 */
	public static void findByReportDesign(Long baseReportId, Long baseReportVersionId) throws Exception
	{
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findByBaseReportDesign(baseReportId, baseReportVersionId);
		for (ReportDataset dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportDesign2'
	 * 
	 * @param designId
	 * @param designVersionId
	 * @throws Exception
	 */
	public static void findByReportDesign2(Long designId, Long designVersionId) throws Exception
	{
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findByReportDesign(designId, designVersionId);
		for (ReportDataset dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByTableMapping'
	 * 
	 * @param tableMappingId
	 * @throws Exception
	 */
	public static void findByTableMapping(Long tableMappingId) throws Exception
	{
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findByTableMapping(tableMappingId);
		for (ReportDataset dto : _result) {
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
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findWhereIdEquals(id);
		for (ReportDataset dto : _result) {
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
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findWhereDesignIdEquals(designId);
		for (ReportDataset dto : _result) {
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
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findWhereDesignVersionIdEquals(designVersionId);
		for (ReportDataset dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereTableMappingIdEquals'
	 * 
	 * @param tableMappingId
	 * @throws Exception
	 */
	public static void findWhereTableMappingIdEquals(Long tableMappingId) throws Exception
	{
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findWhereTableMappingIdEquals(tableMappingId);
		for (ReportDataset dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereBaseReportIdEquals'
	 * 
	 * @param baseReportId
	 * @throws Exception
	 */
	public static void findWhereBaseReportIdEquals(Long baseReportId) throws Exception
	{
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findWhereBaseReportIdEquals(baseReportId);
		for (ReportDataset dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereBaseReportVersionIdEquals'
	 * 
	 * @param baseReportVersionId
	 * @throws Exception
	 */
	public static void findWhereBaseReportVersionIdEquals(Long baseReportVersionId) throws Exception
	{
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findWhereBaseReportVersionIdEquals(baseReportVersionId);
		for (ReportDataset dto : _result) {
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
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findWhereDsOrderEquals(order);
		for (ReportDataset dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOperatorEquals'
	 * 
	 * @param operator
	 * @throws Exception
	 */
	public static void findWhereOperatorEquals(Integer operator) throws Exception
	{
		ReportDatasetDao dao = DaoFactory.createReportDatasetDao();
		List<ReportDataset> _result = dao.findWhereOperatorEquals(operator);
		for (ReportDataset dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(ReportDataset dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getDesignId() );
		buf.append( ", " );
		buf.append( dto.getDesignVersionId() );
		buf.append( ", " );
		buf.append( dto.getTableMappingId() );
		buf.append( ", " );
		buf.append( dto.getBaseReportId() );
		buf.append( ", " );
		buf.append( dto.getBaseReportVersionId() );
		buf.append( ", " );
		buf.append( dto.getDsOrder() );
		buf.append( ", " );
		buf.append( dto.getOperator() );
		System.out.println( buf.toString() );
	}

}
