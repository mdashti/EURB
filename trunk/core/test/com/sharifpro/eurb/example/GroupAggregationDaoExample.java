package com.sharifpro.eurb.example;

import java.util.List;
import com.sharifpro.eurb.dao.GroupAggregationDao;
import com.sharifpro.eurb.dto.GroupAggregation;
import com.sharifpro.eurb.factory.DaoFactory;

public class GroupAggregationDaoExample
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
		// findByReportColumn(null, null, null, null);
		// findByReportColumn2(null, null, null, null);
		// findWhereIdEquals(null);
		// findWhereParentColumnIdEquals(null);
		// findWhereParentColumnDatasetIdEquals(null);
		// findWhereParentColumnDesignIdEquals(null);
		// findWhereParentColumnDesignVersionIdEquals(null);
		// findWhereAggregatedReportColumnIdEquals(null);
		// findWhereAggregatedReportColumnDatasetIdEquals(null);
		// findWhereAggregatedReportColumnDesignIdEquals(null);
		// findWhereAggregatedReportColumnDesignVersionIdEquals(null);
		// findWhereAggregationFunctionEquals("");
		// findWherePlaceEquals(null);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findAll();
		for (GroupAggregation dto : _result) {
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
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findByPersistableObject(id);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportColumn'
	 * 
	 * @param parentColumnId
	 * @param parentColumnDatasetId
	 * @param parentColumnDesignId
	 * @param parentColumnDesignVersionId
	 * @throws Exception
	 */
	public static void findByReportColumn(Long parentColumnId, Long parentColumnDatasetId, Long parentColumnDesignId, Long parentColumnDesignVersionId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findByReportColumn(parentColumnId, parentColumnDatasetId, parentColumnDesignId, parentColumnDesignVersionId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByReportColumn2'
	 * 
	 * @param aggregatedReportColumnId
	 * @param aggregatedReportColumnDatasetId
	 * @param aggregatedReportColumnDesignId
	 * @param aggregatedReportColumnDesignVersionId
	 * @throws Exception
	 */
	public static void findByReportColumn2(Long aggregatedReportColumnId, Long aggregatedReportColumnDatasetId, Long aggregatedReportColumnDesignId, Long aggregatedReportColumnDesignVersionId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findByReportColumn2(aggregatedReportColumnId, aggregatedReportColumnDatasetId, aggregatedReportColumnDesignId, aggregatedReportColumnDesignVersionId);
		for (GroupAggregation dto : _result) {
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
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereIdEquals(id);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereParentColumnIdEquals'
	 * 
	 * @param parentColumnId
	 * @throws Exception
	 */
	public static void findWhereParentColumnIdEquals(Long parentColumnId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereParentColumnIdEquals(parentColumnId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereParentColumnDatasetIdEquals'
	 * 
	 * @param parentColumnDatasetId
	 * @throws Exception
	 */
	public static void findWhereParentColumnDatasetIdEquals(Long parentColumnDatasetId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereParentColumnDatasetIdEquals(parentColumnDatasetId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereParentColumnDesignIdEquals'
	 * 
	 * @param parentColumnDesignId
	 * @throws Exception
	 */
	public static void findWhereParentColumnDesignIdEquals(Long parentColumnDesignId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereParentColumnDesignIdEquals(parentColumnDesignId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereParentColumnDesignVersionIdEquals'
	 * 
	 * @param parentColumnDesignVersionId
	 * @throws Exception
	 */
	public static void findWhereParentColumnDesignVersionIdEquals(Long parentColumnDesignVersionId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereParentColumnDesignVersionIdEquals(parentColumnDesignVersionId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereAggregatedReportColumnIdEquals'
	 * 
	 * @param aggregatedReportColumnId
	 * @throws Exception
	 */
	public static void findWhereAggregatedReportColumnIdEquals(Long aggregatedReportColumnId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereAggregatedReportColumnIdEquals(aggregatedReportColumnId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereAggregatedReportColumnDatasetIdEquals'
	 * 
	 * @param aggregatedReportColumnDatasetId
	 * @throws Exception
	 */
	public static void findWhereAggregatedReportColumnDatasetIdEquals(Long aggregatedReportColumnDatasetId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereAggregatedReportColumnDatasetIdEquals(aggregatedReportColumnDatasetId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereAggregatedReportColumnDesignIdEquals'
	 * 
	 * @param aggregatedReportColumnDesignId
	 * @throws Exception
	 */
	public static void findWhereAggregatedReportColumnDesignIdEquals(Long aggregatedReportColumnDesignId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereAggregatedReportColumnDesignIdEquals(aggregatedReportColumnDesignId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereAggregatedReportColumnDesignVersionIdEquals'
	 * 
	 * @param aggregatedReportColumnDesignVersionId
	 * @throws Exception
	 */
	public static void findWhereAggregatedReportColumnDesignVersionIdEquals(Long aggregatedReportColumnDesignVersionId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereAggregatedReportColumnDesignVersionIdEquals(aggregatedReportColumnDesignVersionId);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereAggregationFunctionEquals'
	 * 
	 * @param aggregationFunction
	 * @throws Exception
	 */
	public static void findWhereAggregationFunctionEquals(String aggregationFunction) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWhereAggregationFunctionEquals(aggregationFunction);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePlaceEquals'
	 * 
	 * @param place
	 * @throws Exception
	 */
	public static void findWherePlaceEquals(Integer place) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findWherePlaceEquals(place);
		for (GroupAggregation dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(GroupAggregation dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getParentColumnId() );
		buf.append( ", " );
		buf.append( dto.getParentColumnDatasetId() );
		buf.append( ", " );
		buf.append( dto.getParentColumnDesignId() );
		buf.append( ", " );
		buf.append( dto.getParentColumnDesignVersionId() );
		buf.append( ", " );
		buf.append( dto.getAggregatedReportColumnId() );
		buf.append( ", " );
		buf.append( dto.getAggregatedReportColumnDatasetId() );
		buf.append( ", " );
		buf.append( dto.getAggregatedReportColumnDesignId() );
		buf.append( ", " );
		buf.append( dto.getAggregatedReportColumnDesignVersionId() );
		buf.append( ", " );
		buf.append( dto.getAggregationFunction() );
		buf.append( ", " );
		buf.append( dto.getPlace() );
		System.out.println( buf.toString() );
	}

}
