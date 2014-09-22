package com.sharifpro.eurb.management.mapping.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.info.RecordStatus;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.DbConfigPk;
import com.sharifpro.eurb.management.security.dao.impl.AclServiceImpl;
import com.sharifpro.eurb.management.security.dao.impl.ExtendedPermission;
import com.sharifpro.transaction.annotation.TransactionalReadOnly;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SessionManager;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class DbConfigDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DbConfig>, DbConfigDao
{
	private AclServiceImpl aclService;
	
	private final static String QUERY_FROM_COLUMNS = "o.name, o.driver_class, AES_DECRYPT(o.driver_url, concat('durlpwd',o.id)), AES_DECRYPT(o.username, concat('usrpwd',o.id)), AES_DECRYPT(o.password, concat('pspwd',o.id)), o.test_query, o.record_status";

	private final static String QUERY_SELECT_PART_USERNAMED_BASED = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o, persistable_object p, acl_object_identity oi, acl_entry e"
			+ " WHERE p.id=o.id"
			+ " AND o.id=oi.object_id_identity"
			+ " AND oi.object_id_class="+DbConfig.ACL_CLASS_IDENTIFIER
			+ " AND oi.id=e.acl_object_identity"
			+ " AND e.mask & ?"
			+ " AND e.granting=1"
			+ " AND e.sid IN (SELECT id FROM acl_sid WHERE  (principal = 1 AND sid = ?) OR (principal = 0 AND sid IN (SELECT group_id FROM group_members WHERE username = ?)) )";
	
	private final static String COUNT_QUERY_USERNAMED_BASED = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o, acl_object_identity oi, acl_entry e"
			+ " WHERE o.record_status IN ('A', 'P')"
			+ " AND o.id=oi.object_id_identity"
			+ " AND oi.object_id_class="+DbConfig.ACL_CLASS_IDENTIFIER
			+ " AND oi.id=e.acl_object_identity"
			+ " AND e.mask & ?"
			+ " AND e.granting=1"
			+ " AND e.sid IN (SELECT id FROM acl_sid WHERE  (principal = 1 AND sid = ?) OR (principal = 0 AND sid IN (SELECT group_id FROM group_members WHERE username = ?)) )";

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DbConfigPk
	 */
	@TransactionalReadWrite
	public DbConfigPk insert(DbConfig dto)
	{
		DbConfigPk pk = new DbConfigPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, name, driver_class, driver_url, username, password, test_query, record_status ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )",pk.getId(),dto.getName(),dto.getDriverClass(),dto.getDriverUrl(),dto.getUsername(),dto.getPassword(),dto.getTestQuery(), dto.getRecordStatusString());
		
		AclServiceImpl.insertObjectIdentity(getJdbcTemplate(), pk.getId(), DbConfig.ACL_CLASS_IDENTIFIER, null, null);
		
		dto.resetDataSource();
		
		return pk;
	}

	/** 
	 * Updates a single row in the db_config table.
	 */
	@TransactionalReadWrite
	public void update(DbConfigPk pk, DbConfig dto) throws DbConfigDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		if(StringUtils.isEmpty(dto.getPassword())) {
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET name = ?, driver_class = ?, driver_url = AES_ENCRYPT(?, concat('durlpwd', ?)), username = AES_ENCRYPT(?, concat('usrpwd', ?)), test_query = ?, record_status = 'A' WHERE id = ?",dto.getName(),dto.getDriverClass(),dto.getDriverUrl(),pk.getId(),dto.getUsername(),pk.getId(),dto.getTestQuery(),pk.getId());
		} else {
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET name = ?, driver_class = ?, driver_url = AES_ENCRYPT(?, concat('durlpwd', ?)), username = AES_ENCRYPT(?, concat('usrpwd', ?)), password = AES_ENCRYPT(?, concat('pspwd', ?)), test_query = ?, record_status = 'A' WHERE id = ?",dto.getName(),dto.getDriverClass(),dto.getDriverUrl(),pk.getId(),dto.getUsername(),pk.getId(),dto.getPassword(),pk.getId(),dto.getTestQuery(),pk.getId());
		}
		dto.resetDataSource();
	}
	
	/** 
	 * Activates a single row in the db_config table.
	 */
	@TransactionalReadWrite
	public void activate(DbConfigPk pk) throws DbConfigDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET record_status = '" + RecordStatus.ACTIVE.getId() + "' WHERE id = ?",pk.getId());
	}
	
	/** 
	 * Activates multiple rows in the db_config table.
	 */
	@TransactionalReadWrite
	public void activateAll(List<DbConfigPk> pkList) throws DbConfigDaoException
	{
		for(DbConfigPk pk : pkList) {
			activate(pk);
		}
	}
	
	/** 
	 * Deactivates a single row in the db_config table.
	 */
	@TransactionalReadWrite
	public void deactivate(DbConfigPk pk) throws DbConfigDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET record_status = '" + RecordStatus.PASSIVE.getId() + "' WHERE id = ?",pk.getId());
	}
	
	/** 
	 * Deactivates multiple rows in the db_config table.
	 */
	@TransactionalReadWrite
	public void deactivateAll(List<DbConfigPk> pkList) throws DbConfigDaoException
	{
		for(DbConfigPk pk : pkList) {
			deactivate(pk);
		}
	}

	/** 
	 * Deletes a single row in the db_config table.
	 */
	@TransactionalReadWrite
	public void delete(DbConfigPk pk) throws DbConfigDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET record_status = '" + RecordStatus.DELETED.getId() + "' WHERE id = ?",pk.getId());
	}

	/** 
	 * Deletes multiple rows in the db_config table.
	 */
	@TransactionalReadWrite
	public void deleteAll(List<DbConfigPk> pkList) throws DbConfigDaoException
	{
		for(DbConfigPk pk : pkList) {
			delete(pk);
		}
	}
	
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return DbConfig
	 */
	public DbConfig mapRow(ResultSet rs, int row) throws SQLException
	{
		DbConfig dto = new DbConfig();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setName( rs.getString( ++i ) );
		dto.setDriverClass( rs.getString( ++i ) );
		dto.setDriverUrl( rs.getString( ++i ) );
		dto.setUsername( rs.getString( ++i ) );
		dto.setPassword( rs.getString( ++i ) );
		dto.setTestQuery( rs.getString( ++i ) );
		dto.setRecordStatus( rs.getString( ++i ) );
		
		try {
			dto.setAccessPreventDel(!aclService.hasPermissionFor(dto, ExtendedPermission.DELETE));
			dto.setAccessPreventEdit(!aclService.hasPermissionFor(dto, ExtendedPermission.WRITE));
			dto.setAccessPreventExecute(!aclService.hasPermissionFor(dto, ExtendedPermission.EXECUTE));
			dto.setAccessPreventSharing(!aclService.hasPermissionFor(dto, ExtendedPermission.ADMINISTRATION));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "db_config";
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public DbConfig findByPrimaryKey(Long id) throws DbConfigDaoException
	{
		try {
			List<DbConfig> list = getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.id = ?", this,ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria ''.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findAll() throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.record_status IN ('A', 'P') ORDER BY o.id", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName());
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	@TransactionalReadOnly
	public int countAll() throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY_USERNAMED_BASED, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName());
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria '' limited by start and limit.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findAll(Integer start, Integer limit, String sortBy, String sortDir) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.record_status IN ('A', 'P') ORDER BY "+getSortClause(sortBy, sortDir)+" limit ?, ?", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(), start, limit);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria like query in onFields fields limited by start and limit.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findAll(String query, List<String> onFields, Integer start, Integer limit, String sortBy, String sortDir) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.record_status IN ('A', 'P') AND (" + getMultipleFieldWhereClause(query, onFields) + ") ORDER BY "+getSortClause(sortBy, sortDir)+" limit ?, ?", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(), start, limit);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	@TransactionalReadOnly
	public int countAll(String query, List<String> onFields) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY_USERNAMED_BASED + " AND (" + getMultipleFieldWhereClause(query, onFields) + ")", ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName());
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	private static String getMultipleFieldWhereClause(String txt, List<String> fields) {
		StringBuilder query = new StringBuilder();
		String likeQuery = " LIKE '%"+txt+"%' OR ";
		if(fields.contains("name")) {
			query.append("o.name").append(likeQuery);
		}
		if(fields.contains("driverClass")) {
			query.append("o.driver_class").append(likeQuery);
		}
		if(fields.contains("driverUrl")) {
			query.append("o.driver_url").append(likeQuery);
		}
		if(fields.contains("username")) {
			query.append("o.username").append(likeQuery);
		}
		if(fields.contains("testQuery")) {
			query.append("o.test_query").append(likeQuery);
		}
		if(query.length() > 0) {
			return query.substring(0,query.length()-3);
		} else {
			return "";
		}
	}
	
	private static String getSortClause(String sortBy, String sortDir) {
		StringBuilder result = new StringBuilder();
		if("id".equals(sortBy)) {
			result.append("o.id");
		} else if("name".equals(sortBy)) {
			result.append("o.name");
		} else if("driverClass".equals(sortBy)) {
			result.append("o.driver_class");
		} else if("driverUrl".equals(sortBy)) {
			result.append("o.driver_url");
		} else if("username".equals(sortBy)) {
			result.append("o.username");
		} else if("testQuery".equals(sortBy)) {
			result.append("o.testQuery");
		} else {
			result.append("o.id");
		}
		result.append(" ").append(AbstractDAO.DESCENDING_SORT_ORDER.equals(sortDir) ? AbstractDAO.DESCENDING_SORT_ORDER : AbstractDAO.ASCENDING_SORT_ORDER);
		return result.toString();
	}

	/** 
	 * Returns all active rows from the db_config table that match the criteria ''.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findAllActive() throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.record_status='A' ORDER BY o.id", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName());
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findByPersistableObject(Long id) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.id = ?", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),id);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findWhereIdEquals(Long id) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.id = ? ORDER BY o.id", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),id);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'name = :name'.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findWhereNameEquals(String name) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.name = ? ORDER BY o.name", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),name);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'driver_class = :driverClass'.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findWhereDriverClassEquals(String driverClass) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.driver_class = ? ORDER BY o.driver_class", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),driverClass);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'driver_url = :driverUrl'.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findWhereDriverUrlEquals(String driverUrl) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.driver_url = ? ORDER BY o.driver_url", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),driverUrl);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'username = :username'.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findWhereUsernameEquals(String username) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.username = ? ORDER BY o.username", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),username);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'password = :password'.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findWherePasswordEquals(String password) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.password = ? ORDER BY o.password", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),password);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'test_query = :testQuery'.
	 */
	@TransactionalReadOnly
	public List<DbConfig> findWhereTestQueryEquals(String testQuery) throws DbConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.test_query = ? ORDER BY o.test_query", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),testQuery);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the rows from the db_config table that matches the specified primary-key value.
	 */
	public DbConfig findByPrimaryKey(DbConfigPk pk) throws DbConfigDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	@Autowired
	public void setAclService(AclServiceImpl aclService) {
		this.aclService = aclService;
	}

}
