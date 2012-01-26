package com.sharifpro.eurb.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;

public class ColumnMappingDaoExample
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
		// findByTableMapping(null);
		// findWhereIdEquals(null);
		// findWhereTableMappingIdEquals(null);
		// findWhereColumnNameEquals("");
		// findWhereMappedNameEquals("");
		// findWhereTypeEquals("");
		// findWhereFormatPatternEquals("");
		// findWhereStaticMappingEquals("");
		// findWhereReferencedTableEquals("");
		// findWhereReferencedIdColEquals("");
		// findWhereReferencedValueColEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findAll();
		for (ColumnMapping dto : _result) {
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
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findByPersistableObject(id);
		for (ColumnMapping dto : _result) {
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
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findByTableMapping(tableMappingId);
		for (ColumnMapping dto : _result) {
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
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereIdEquals(id);
		for (ColumnMapping dto : _result) {
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
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereTableMappingIdEquals(tableMappingId);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereColumnNameEquals'
	 * 
	 * @param columnName
	 * @throws Exception
	 */
	public static void findWhereColumnNameEquals(String columnName) throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereColumnNameEquals(columnName);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMappedNameEquals'
	 * 
	 * @param mappedName
	 * @throws Exception
	 */
	public static void findWhereMappedNameEquals(String mappedName) throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereMappedNameEquals(mappedName);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereTypeEquals'
	 * 
	 * @param type
	 * @throws Exception
	 */
	public static void findWhereTypeEquals(String type) throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereColTypeEquals(type);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFormatPatternEquals'
	 * 
	 * @param formatPattern
	 * @throws Exception
	 */
	public static void findWhereFormatPatternEquals(String formatPattern) throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereFormatPatternEquals(formatPattern);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereStaticMappingEquals'
	 * 
	 * @param staticMapping
	 * @throws Exception
	 */
	public static void findWhereStaticMappingEquals(String staticMapping) throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereStaticMappingEquals(staticMapping);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReferencedTableEquals'
	 * 
	 * @param referencedTable
	 * @throws Exception
	 */
	public static void findWhereReferencedTableEquals(String referencedTable) throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereReferencedTableEquals(referencedTable);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReferencedIdColEquals'
	 * 
	 * @param referencedIdCol
	 * @throws Exception
	 */
	public static void findWhereReferencedIdColEquals(String referencedIdCol) throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereReferencedIdColEquals(referencedIdCol);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReferencedValueColEquals'
	 * 
	 * @param referencedValueCol
	 * @throws Exception
	 */
	public static void findWhereReferencedValueColEquals(String referencedValueCol) throws Exception
	{
		ColumnMappingDao dao = DaoFactory.createColumnMappingDao();
		List<ColumnMapping> _result = dao.findWhereReferencedValueColEquals(referencedValueCol);
		for (ColumnMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(ColumnMapping dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getTableMappingId() );
		buf.append( ", " );
		buf.append( dto.getColumnName() );
		buf.append( ", " );
		buf.append( dto.getMappedName() );
		buf.append( ", " );
		buf.append( dto.getColType() );
		buf.append( ", " );
		buf.append( dto.getColOrder() );
		buf.append( ", " );
		buf.append( dto.getFormatPattern() );
		buf.append( ", " );
		buf.append( dto.getStaticMapping() );
		buf.append( ", " );
		buf.append( dto.getReferencedTable() );
		buf.append( ", " );
		buf.append( dto.getReferencedIdCol() );
		buf.append( ", " );
		buf.append( dto.getReferencedValueCol() );
		System.out.println( buf.toString() );
	}

}
