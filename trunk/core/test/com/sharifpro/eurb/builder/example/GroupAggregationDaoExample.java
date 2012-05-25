package com.sharifpro.eurb.builder.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.GroupAggregationDao;
import com.sharifpro.eurb.builder.model.GroupAggregation;
import com.sharifpro.eurb.builder.model.ReportColumn;

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
	public static void findAll(ReportColumn reportColumn) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findAll(reportColumn);
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
	public static void findByReportColumn(Long parentColumnId) throws Exception
	{
		GroupAggregationDao dao = DaoFactory.createGroupAggregationDao();
		List<GroupAggregation> _result = dao.findByReportColumn(parentColumnId);
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
			buf.append( dto.getAggregationFunction() );
		buf.append( ", " );
		buf.append( dto.getPlace() );
		System.out.println( buf.toString() );
	}

}
