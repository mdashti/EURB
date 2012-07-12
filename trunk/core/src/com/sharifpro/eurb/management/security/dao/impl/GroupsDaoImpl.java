package com.sharifpro.eurb.management.security.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.exception.GroupsDaoException;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.GroupsPk;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GroupsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<Groups>, GroupsDao
{
	private final static String QUERY_FROM_COLUMNS = "o.group_name";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o";

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return GroupsPk
	 */
	@Transactional
	public GroupsPk insert(Groups dto)
	{
		GroupsPk pk = new GroupsPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, group_name ) VALUES ( ?, ? )", pk.getId(),dto.getGroupName());
		getJdbcTemplate().update("INSERT INTO ACL_SID (sid , principal) VALUES ( ?, ? )", pk.getId(), 0);
		return pk;
	}

	/** 
	 * Updates a single row in the groups table.
	 */
	@Transactional
	public void update(GroupsPk pk, Groups dto) throws GroupsDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET id = ?, group_name = ? WHERE id = ?",dto.getId(),dto.getGroupName(),pk.getId());
	}

	/** 
	 * Deletes a single row in the groups table.
	 */
	@Transactional
	public void delete(GroupsPk pk) throws GroupsDaoException
	{
		getJdbcTemplate().update("DELETE FROM " + GroupAuthoritiesDaoImpl.getTableName() + " WHERE group_id = ?",pk.getId());
		getJdbcTemplate().update("DELETE FROM " + GroupMembersDaoImpl.getTableName() + " WHERE group_id = ?",pk.getId());
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
		DaoFactory.createPersistableObjectDao().delete(pk);
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return Groups
	 */
	public Groups mapRow(ResultSet rs, int row) throws SQLException
	{
		Groups dto = new Groups();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setGroupName( rs.getString( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "groups";
	}

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	@Transactional
	public Groups findByPrimaryKey(Long id) throws GroupsDaoException
	{
		try {
			List<Groups> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the groups table that match the criteria ''.
	 */
	@Transactional
	public List<Groups> findAll() throws GroupsDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<Groups> findByPersistableObject(Long id) throws GroupsDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<Groups> findWhereIdEquals(Long id) throws GroupsDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the groups table that match the criteria 'group_name = :groupName'.
	 */
	@Transactional
	public List<Groups> findWhereGroupNameEquals(String groupName) throws GroupsDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE group_name = ? ORDER BY group_name", this,groupName);
		}
		catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the rows from the groups table that matches the specified primary-key value.
	 */
	@Transactional
	public Groups findByPrimaryKey(GroupsPk pk) throws GroupsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	@Transactional
	public List<Groups> findCurrentGroupsForUser(String userName) throws GroupsDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id IN (SELECT group_id FROM group_members WHERE username = ?) ORDER BY group_name", this,userName);
		}
		catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	@Transactional
	public List<Groups> findSelectableGroupsForUser(String userName) throws GroupsDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id NOT IN (SELECT group_id FROM group_members WHERE username = ?) ORDER BY group_name", this,userName);
		}
		catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	private String getSortClause(String sortBy, String sortDir) {
		StringBuilder result = new StringBuilder();
		if("id".equals(sortBy)) {
			result.append("o.id");
		} else if("groupName".equals(sortBy)) {
			result.append("o.group_name");
		} else {
			result.append("o.id");
		}
		result.append(" ").append(AbstractDAO.DESCENDING_SORT_ORDER.equals(sortDir) ? AbstractDAO.DESCENDING_SORT_ORDER : AbstractDAO.ASCENDING_SORT_ORDER);
		return result.toString();
	}
	
	private static String getMultipleFieldWhereClause(String txt, List<String> fields) {
		StringBuilder query = new StringBuilder();
		String likeQuery = " LIKE '%"+txt+"%' OR ";
		if(fields.contains("groupName")) {
			query.append("o.group_name").append(likeQuery);
		}
		if(query.length() > 0) {
			return " WHERE " + query.substring(0,query.length()-3);
		} else {
			return "";
		}
	}

	@Transactional
	public List<Groups> findAll(Integer start, Integer limit, String sortBy, String sortDir) throws GroupsDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY "+getSortClause(sortBy, sortDir)+" limit ?, ?", this, start, limit);
		} catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	@Transactional
	public int countAll() throws GroupsDaoException {
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY);
		} catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	@Transactional
	public List<Groups> findAll(String query, List<String> onFields,
			Integer start, Integer limit, String sortBy, String sortDir)
			throws GroupsDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + getMultipleFieldWhereClause(query, onFields) + " ORDER BY "+getSortClause(sortBy, sortBy)+" limit ?, ?", this, start, limit);
		} catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	@Transactional
	public int countAll(String query, List<String> onFields)
			throws GroupsDaoException {
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY + getMultipleFieldWhereClause(query, onFields));
		} catch (Exception e) {
			throw new GroupsDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	@Transactional
	public void deleteAll(List<GroupsPk> pkList) throws GroupsDaoException {
		for(GroupsPk pk : pkList) {
			delete(pk);
		}
	}

}
