package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.UserMessageDao;
import com.sharifpro.eurb.builder.exception.UserMessageDaoException;
import com.sharifpro.eurb.builder.model.UserMessage;
import com.sharifpro.eurb.builder.model.UserMessagePk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class UserMessageDaoImpl extends AbstractDAO implements ParameterizedRowMapper<UserMessage>, UserMessageDao
{
	
	private final static String QUERY_FROM_COLUMNS = "o.username, o.message, o.type, o.show, o.available_from";
	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return UserMessagePk
	 */
	@Transactional
	public UserMessagePk insert(UserMessage dto) throws UserMessageDaoException
	{
		try{
			UserMessagePk pk = new UserMessagePk();
			DaoFactory.createPersistableObjectDao().insert(dto, pk);
			jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, username, message, type, `show`, available_from )" +
					" VALUES ( ?, ?, ?, ?, ?, ? )", new Object[] {pk.getId(),dto.getUsername(),dto.getMessage(),dto.getType(),
					dto.isShow() ? 1 : 0,new Date(dto.getAvailableFrom().getTime())}, new int[] {Types.BIGINT, Types.VARCHAR, Types.CLOB, Types.INTEGER, Types.TINYINT, Types.TIMESTAMP});	
			return pk;
		} catch(Exception e){
			throw new UserMessageDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Updates a single row in the user_message table.
	 */
	@Transactional
	public void update(UserMessagePk pk, UserMessage dto) throws UserMessageDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			jdbcTemplate.update("UPDATE " + getTableName() + " SET username = ?, message = ?, type = ?, `show` = ?, " +
					"available_from = ? WHERE id = ?",new Object[] {dto.getUsername(),dto.getMessage(),dto.getType(),
					dto.isShow() ? 1 : 0,new Date(dto.getAvailableFrom().getTime()),pk.getId()}, new int[] {Types.BIGINT, Types.VARCHAR, Types.CLOB, Types.INTEGER, Types.TINYINT, Types.TIMESTAMP});
		} catch(Exception e){
			throw new UserMessageDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes a single row in the user_message table.
	 */
	@Transactional
	public void delete(String username, UserMessagePk pk) throws UserMessageDaoException
	{
		try{
			jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ? and username = ?",pk.getId(), username);
		} catch (Exception e) {
			throw new UserMessageDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}
	
	/** 
	 * Deletes multiple rows in the user_message table.
	 */
	@Transactional
	public void deleteAll(String username, List<UserMessagePk> pkList) throws UserMessageDaoException
	{
		for(UserMessagePk pk : pkList){
			delete(username, pk);
		}
	}

	
	
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return UserMessage
	 */
	public UserMessage mapRow(ResultSet rs, int row) throws SQLException
	{
		UserMessage dto = new UserMessage();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setUsername( rs.getString(++i) );
		dto.setMessage( rs.getString(++i) );
		dto.setType( rs.getInt(++i) );
		dto.setShow( rs.getBoolean(++i) );
		dto.setAvailableFrom( rs.getDate( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "user_message";
	}

	/** 
	 * Returns all rows from the user_message table that match the criteria 'id = :id'.
	 */
	@Transactional
	public UserMessage findByPrimaryKey(Long id) throws UserMessageDaoException
	{
		try {
			List<UserMessage> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new UserMessageDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the user_message table that match the criteria 'parent_column_id = :parentColumn.getId()'.
	 */
	@Transactional
	public List<UserMessage> findAllForUser(String username) throws UserMessageDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.username = ? ORDER BY o.id", this, username);
		}
		catch (Exception e) {
			throw new UserMessageDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the user_message table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<UserMessage> findByPersistableObject(Long id) throws UserMessageDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
		}
		catch (Exception e) {
			throw new UserMessageDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	/** 
	 * Returns all rows from the user_message table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<UserMessage> findWhereIdEquals(Long id) throws UserMessageDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY o.id", this, id);
		}
		catch (Exception e) {
			throw new UserMessageDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	/** 
	 * Returns the rows from the user_message table that matches the specified primary-key value.
	 */
	public UserMessage findByPrimaryKey(UserMessagePk pk) throws UserMessageDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
