package com.sharifpro.eurb.example;

import java.util.Date;
import java.util.List;
import com.sharifpro.eurb.dao.PersistableObjectDao;
import com.sharifpro.eurb.dto.PersistableObject;
import com.sharifpro.eurb.factory.DaoFactory;

public class PersistableObjectDaoExample
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
		
		 findAll();
		// findByUsers("");
		// findByUsers2("");
		// findWhereIdEquals(null);
		// findWhereTypeEquals(null);
		// findWhereCreatorEquals("");
		// findWhereCreateDateEquals(null);
		// findWhereModifierEquals("");
		// findWhereModifyDateEquals(null);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findAll();
		for (PersistableObject dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByUsers'
	 * 
	 * @param creator
	 * @throws Exception
	 */
	public static void findByUsers(String creator) throws Exception
	{
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findByUsers(creator);
		for (PersistableObject dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByUsers2'
	 * 
	 * @param modifier
	 * @throws Exception
	 */
	public static void findByUsers2(String modifier) throws Exception
	{
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findByUsers2(modifier);
		for (PersistableObject dto : _result) {
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
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findWhereIdEquals(id);
		for (PersistableObject dto : _result) {
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
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findWhereTypeEquals(type);
		for (PersistableObject dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereCreatorEquals'
	 * 
	 * @param creator
	 * @throws Exception
	 */
	public static void findWhereCreatorEquals(String creator) throws Exception
	{
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findWhereCreatorEquals(creator);
		for (PersistableObject dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereCreateDateEquals'
	 * 
	 * @param createDate
	 * @throws Exception
	 */
	public static void findWhereCreateDateEquals(Date createDate) throws Exception
	{
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findWhereCreateDateEquals(createDate);
		for (PersistableObject dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereModifierEquals'
	 * 
	 * @param modifier
	 * @throws Exception
	 */
	public static void findWhereModifierEquals(String modifier) throws Exception
	{
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findWhereModifierEquals(modifier);
		for (PersistableObject dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereModifyDateEquals'
	 * 
	 * @param modifyDate
	 * @throws Exception
	 */
	public static void findWhereModifyDateEquals(Date modifyDate) throws Exception
	{
		PersistableObjectDao dao = DaoFactory.createPersistableObjectDao();
		List<PersistableObject> _result = dao.findWhereModifyDateEquals(modifyDate);
		for (PersistableObject dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(PersistableObject dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getType() );
		buf.append( ", " );
		buf.append( dto.getCreator() );
		buf.append( ", " );
		buf.append( dto.getCreateDate() );
		buf.append( ", " );
		buf.append( dto.getModifier() );
		buf.append( ", " );
		buf.append( dto.getModifyDate() );
		System.out.println( buf.toString() );
	}

}
