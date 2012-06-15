package com.sharifpro.eurb.management.security.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.info.AuthorityType;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.security.exception.AuthoritiesDaoException;
import com.sharifpro.eurb.management.security.exception.UserDaoException;
import com.sharifpro.eurb.management.security.model.Authorities;
import com.sharifpro.eurb.management.security.model.AuthoritiesPk;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.eurb.management.security.dao.AuthoritiesDao;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.model.UserPk;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SecurityUtil;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.OperationNotSupportedException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.transaction.annotation.Transactional;

public class UserDaoImpl extends AbstractDAO implements ParameterizedRowMapper<User>, UserDao
{
	private final static String QUERY_FROM_COLUMNS = "o.username, o.password, o.enabled, o.email";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o";

	AuthoritiesDao authoritiesDao;
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return UsersPk
	 */
	@Transactional(readOnly=false)
	public UserPk insert(User dto)
	{
		UserPk pk = new UserPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, username, password, enabled, email ) VALUES ( ?, ?, ?, ?, ? )", pk.getId(), dto.getUsername(),SecurityUtil.generatePassword(dto.getPassword(), dto.getUsername()),dto.isEnabled(), dto.getEmail());
		Authorities auth = new Authorities();
		auth.setUsername(dto.getUsername());
		auth.setAuthority(AuthorityType.USER);
		authoritiesDao.insert(auth);
		return pk;
	}

	/** 
	 * Updates a single row in the users table.
	 */
	@Transactional(readOnly=false)
	public void update(UserPk pk, User dto) throws UserDaoException {
		throw new RuntimeException(new OperationNotSupportedException("User can not be updated! use setEnabled and setPassword instead"));
	}
	/** 
	 * Sets enabled flag for a single user in the users table.
	 */
	@Transactional(readOnly=false)
	public void setEnabled(UserPk pk, boolean enabled)
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET enabled = ? WHERE id = ?", enabled, pk.getId());
	}
	
	/** 
	 * Sets password for a single user in the users table.
	 */
	@Transactional(readOnly=false)
	public void setPassword(UserPk pk, User dto)
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET password = ?, email = ? WHERE id = ?", SecurityUtil.generatePassword(dto.getPassword(), dto.getUsername()), dto.getEmail(), pk.getId());
		try {
			Authorities auth = authoritiesDao.findByPrimaryKey(new AuthoritiesPk(dto.getUsername(), AuthorityType.USER));
			if(auth == null) {
				auth = new Authorities();
				auth.setUsername(dto.getUsername());
				auth.setAuthority(AuthorityType.USER);
				authoritiesDao.insert(auth);
			}
		} catch (AuthoritiesDaoException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Sets password for a single user in the users table.
	 */
	@Transactional(readOnly=false)
	public void setEmail(UserPk pk, User dto)
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET email = ? WHERE id = ?", dto.getEmail(), pk.getId());
		try {
			Authorities auth = authoritiesDao.findByPrimaryKey(new AuthoritiesPk(dto.getUsername(), AuthorityType.USER));
			if(auth == null) {
				auth = new Authorities();
				auth.setUsername(dto.getUsername());
				auth.setAuthority(AuthorityType.USER);
				authoritiesDao.insert(auth);
			}
		} catch (AuthoritiesDaoException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Deletes a single row in the users table.
	 */
	@Transactional(readOnly=false)
	public void delete(UserPk pk)
	{
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
		DaoFactory.createPersistableObjectDao().delete(pk);
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return Users
	 */
	public User mapRow(ResultSet rs, int row) throws SQLException
	{
		PersistableObject dto = new PersistableObject();
    	PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
        String username = rs.getString(++i);
        String password = rs.getString(++i);
        boolean enabled = rs.getBoolean(++i);
        String email = rs.getString(++i);
        User u = new User(dto, username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
        u.setEmail(email);
        return u;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "users";
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	@Transactional(readOnly=true)
	public User findByPrimaryKey(Long id) throws UserDaoException
	{
		try {
			List<User> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria ''.
	 */
	@Transactional(readOnly=true)
	public List<User> findAll() throws UserDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY username", this);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	@Transactional(readOnly=true)
	public List<User> findAllActive() throws UserDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.enabled=1 ORDER BY o.username", this);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly=true)
	public List<User> findByPersistableObject(Long id) throws UserDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly=true)
	public List<User> findWhereIdEquals(Long id) throws UserDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the row from the users table that match the criteria 'username = :username'.
	 */
	@Transactional(readOnly=true)
	public User findWhereUsernameEquals(String username) throws UserDaoException
	{
		try {
			List<User> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE username = ? ORDER BY username", this,username);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the row from the users table that match the criteria 'email = :email'.
	 */
	@Transactional(readOnly=true)
	public User findByEmail(String email) throws UserDaoException
	{
		try {
			List<User> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE email = ? ORDER BY username", this,email);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'password = :password'.
	 */
	@Transactional(readOnly=true)
	public List<User> findWherePasswordEquals(String password) throws UserDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE password = ? ORDER BY password", this,password);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'enabled = :enabled'.
	 */
	@Transactional(readOnly=true)
	public List<User> findWhereEnabledEquals(Short enabled) throws UserDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE enabled = ? ORDER BY enabled", this,enabled);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the rows from the users table that matches the specified primary-key value.
	 */
	public User findByPrimaryKey(UserPk pk) throws UserDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}
	
	@Transactional
	public int countAll() throws UserDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the user table that match the criteria '' limited by start and limit.
	 */
	@Transactional
	public List<User> findAll(Integer start, Integer limit, String sortBy, String sortDir) throws UserDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY "+getSortClause(sortBy, sortDir)+" limit ?, ?", this, start, limit);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the user table that match the criteria like query in onFields fields limited by start and limit.
	 */
	@Transactional
	public List<User> findAll(String query, List<String> onFields, Integer start, Integer limit, String sortBy, String sortDir) throws UserDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + getMultipleFieldWhereClause(query, onFields) + " ORDER BY "+getSortClause(sortBy, sortDir)+" limit ?, ?", this, start, limit);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	@Transactional
	public int countAll(String query, List<String> onFields) throws UserDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY + getMultipleFieldWhereClause(query, onFields));
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	private static String getMultipleFieldWhereClause(String txt, List<String> fields) {
		StringBuilder query = new StringBuilder();
		String likeQuery = " LIKE '%"+txt+"%' OR ";
		if(fields.contains("username")) {
			query.append("o.username").append(likeQuery);
		}
		if(fields.contains("email")) {
			query.append("o.email").append(likeQuery);
		}
		if(query.length() > 0) {
			return " WHERE " + query.substring(0,query.length()-3);
		} else {
			return "";
		}
	}
	
	private static String getSortClause(String sortBy, String sortDir) {
		StringBuilder result = new StringBuilder();
		if("id".equals(sortBy)) {
			result.append("o.id");
		} else if("username".equals(sortBy)) {
			result.append("o.username");
		} else if("email".equals(sortBy)) {
			result.append("o.email");
		} else {
			result.append("o.id");
		}
		result.append(" ").append(AbstractDAO.DESCENDING_SORT_ORDER.equals(sortDir) ? AbstractDAO.DESCENDING_SORT_ORDER : AbstractDAO.ASCENDING_SORT_ORDER);
		return result.toString();
	}

	/** 
	 * Deletes multiple rows in the user table.
	 */
	@Transactional
	public void deleteAll(List<UserPk> pkList) throws UserDaoException
	{
		for(UserPk pk : pkList) {
			delete(pk);
		}
	}

	/** 
	 * Activates a single row in the user table.
	 */
	@Transactional
	public void activate(UserPk pk) throws UserDaoException
	{
		setEnabled(pk, true);
	}
	
	/** 
	 * Activates multiple rows in the user table.
	 */
	@Transactional
	public void activateAll(List<UserPk> pkList) throws UserDaoException
	{
		for(UserPk pk : pkList) {
			activate(pk);
		}
	}
	
	/** 
	 * Deactivates a single row in the user table.
	 */
	@Transactional
	public void deactivate(UserPk pk) throws UserDaoException
	{
		setEnabled(pk, false);
	}
	
	/** 
	 * Deactivates multiple rows in the user table.
	 */
	@Transactional
	public void deactivateAll(List<UserPk> pkList) throws UserDaoException
	{
		for(UserPk pk : pkList) {
			deactivate(pk);
		}
	}

	@Transactional(readOnly=true)
	public List<User> findCurrentUsersForGroup(Long groupId) throws UserDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.username IN (SELECT username FROM group_members WHERE group_id = ?) ORDER BY o.username", this,groupId);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	@Transactional(readOnly=true)
	public List<User> findSelectableUsersForGroup(Long groupId) throws UserDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.username NOT IN (SELECT username FROM group_members WHERE group_id = ?) ORDER BY o.username", this,groupId);
		}
		catch (Exception e) {
			throw new UserDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	@Transactional(readOnly=true)
	public boolean userWithEmailExists(String exceptUsername, String email) throws UserDaoException {
		User u = findByEmail(email);
		return u != null && !u.getUsername().equals(exceptUsername);
	}

	@Transactional(readOnly=true)
	public boolean userWithEmailExists(String email) throws UserDaoException {
		return findByEmail(email) != null;
	}

	@Transactional(readOnly=true)
	public boolean userWithUsernameExists(String username) throws UserDaoException {
		return findWhereUsernameEquals(username) != null;
	}

	public void setAuthoritiesDao(AuthoritiesDao authoritiesDao) {
		this.authoritiesDao = authoritiesDao;
	}
}
