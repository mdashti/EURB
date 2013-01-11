package com.sharifpro.eurb.builder.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;


import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.exception.ReportCategoryDaoException;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportCategoryPk;
import com.sharifpro.eurb.info.RecordStatus;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.eurb.management.security.dao.impl.AclServiceImpl;
import com.sharifpro.eurb.management.security.dao.impl.ExtendedPermission;
import com.sharifpro.transaction.annotation.TransactionalReadOnly;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SessionManager;

@Repository
public class ReportCategoryDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportCategory>, ReportCategoryDao
{
	private final static String QUERY_FROM_COLUMNS = "o.name, o.description, o.parent_category_id";

	private AclServiceImpl aclService;
	
	//private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	
	private final static String QUERY_SELECT_PART_USERNAMED_BASED = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o, persistable_object p, acl_object_identity oi, acl_entry e"
			+ " WHERE p.id=o.id"
			+ " AND o.id=oi.object_id_identity"
			+ " AND oi.object_id_class="+ReportCategory.ACL_CLASS_IDENTIFIER
			+ " AND oi.id=e.acl_object_identity"
			+ " AND e.mask & ?"
			+ " AND e.granting=1"
			+ " AND e.sid IN (SELECT id FROM acl_sid WHERE  (principal = 1 AND sid = ?) OR (principal = 0 AND sid IN (SELECT group_id FROM group_members WHERE username = ?)) )";

	//private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o ";
	private final static String COUNT_QUERY_USERNAMED_BASED = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o, acl_object_identity oi, acl_entry e"
			+ " WHERE o.id=oi.object_id_identity"
			+ " AND oi.object_id_class="+ReportCategory.ACL_CLASS_IDENTIFIER
			+ " AND oi.id=e.acl_object_identity"
			+ " AND e.mask & ?"
			+ " AND e.granting=1"
			+ " AND e.sid IN (SELECT id FROM acl_sid WHERE  (principal = 1 AND sid = ?) OR (principal = 0 AND sid IN (SELECT group_id FROM group_members WHERE username = ?)) )";

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportCategoryPk
	 * @throws ReportCategoryDaoException 
	 */
	@TransactionalReadWrite
	public ReportCategoryPk insert(final ReportCategory dto) throws ReportCategoryDaoException
	{
		try{
			final ReportCategoryPk pk = new ReportCategoryPk();
			DaoFactory.createPersistableObjectDao().insert(dto, pk);

			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, name, description, parent_category_id ) VALUES ( ?, ?, ?, ? )",pk.getId(),dto.getName(),dto.getDescription(), dto.getParentCategory());
			
			AclServiceImpl.insertObjectIdentity(getJdbcTemplate(), pk.getId(), ReportCategory.ACL_CLASS_IDENTIFIER, dto.getParentCategory(), ReportCategory.ACL_CLASS_IDENTIFIER);
			return pk;
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the report_category table.
	 */
	@TransactionalReadWrite
	public void update(ReportCategoryPk pk, ReportCategory dto) throws ReportCategoryDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET name = ?, description = ?, parent_category_id = ? WHERE id = ?",dto.getName(), dto.getDescription(), dto.getParentCategory(), pk.getId());
			AclServiceImpl.updateObjectIdentity(getJdbcTemplate(), pk.getId(), ReportCategory.ACL_CLASS_IDENTIFIER, dto.getParentCategory(), ReportCategory.ACL_CLASS_IDENTIFIER);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Deletes a single row in the report_category table.
	 */
	@TransactionalReadWrite
	public void delete(ReportCategoryPk pk) throws ReportCategoryDaoException
	{
		try{
			ReportCategory toBeDeletedCategory = findByPrimaryKey(pk);
			//first we have to check for any active report for this category
			int countActive = getJdbcTemplate().queryForInt("SELECT count(id) FROM " + ReportDesignDaoImpl.getTableName() + " WHERE category_id = ? AND record_status <> ? ",pk.getId(), RecordStatus.DELETED.getId());
			int childCount = getJdbcTemplate().queryForInt("SELECT count(id) FROM " + getTableName() + " WHERE parent_category_id = ? ",pk.getId());
			//if there is any active report for the category, user cannot delete it
			if(countActive > 0){
				throw new ReportCategoryDaoException(PropertyProvider.get("eurb.category.hasreportexception"));
			}
			//update parent of child categories to this parent
			if(childCount > 0){
				getJdbcTemplate().update("UPDATE report_category parent, report_category child SET child.parent_category_id = parent.parent_category_id WHERE child.parent_category_id = parent.id AND parent.id = ?", pk.getId());
			}
			//else we have to change category_id for not active reports of this category
			getJdbcTemplate().update("UPDATE " + ReportDesignDaoImpl.getTableName() + " SET category_id = NULL WHERE category_id = ?", pk.getId());
			//and then delete the permissions
			AclServiceImpl.deleteObjectIdentity(getJdbcTemplate(),pk.getId(),ReportCategory.ACL_CLASS_IDENTIFIER, toBeDeletedCategory.getParentCategory(), ReportCategory.ACL_CLASS_IDENTIFIER);
			//and finally delete the category
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
			DaoFactory.createPersistableObjectDao().delete(pk);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes multiple rows in the report_category table.
	 */
	@TransactionalReadWrite
	public void deleteAll(List<ReportCategoryPk> pkList) throws ReportCategoryDaoException
	{
		for(ReportCategoryPk pk : pkList) {
			delete(pk);
		}
	}


	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportCategory
	 */
	public ReportCategory mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportCategory dto = new ReportCategory();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setName( rs.getString(++i) );
		dto.setDescription( rs.getString(++i) );
		dto.setParentCategory( new Long( rs.getLong(++i) ) );
		
		try {
			dto.setAccessPreventDel(!aclService.hasPermissionFor(dto, ExtendedPermission.DELETE));
			dto.setAccessPreventEdit(!aclService.hasPermissionFor(dto, ExtendedPermission.WRITE));
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
		return "report_category";
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public ReportCategory findByPrimaryKey(Long id) throws ReportCategoryDaoException
	{
		try {
			List<ReportCategory> list = getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.id = ?", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_category table that match the criteria ''.
	 */
	@TransactionalReadOnly
	public List<ReportCategory> findAll() throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " ORDER BY o.id", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName());
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}
	
	/** 
	 * Returns all rows from the report_category table that match the criteria 'parent_category_id = :parentCategory'.
	 */
	@TransactionalReadOnly
	public List<ReportCategory> findAll(Long parentCategory) throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.parent_category_id = ? ORDER BY o.id", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(), parentCategory);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	
	/** 
	 * Returns all rows from the report_category table that match the criteria 'parent_category_id is null'.
	 */
	@TransactionalReadOnly
	public List<ReportCategory> findAllWithoutParent() throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.parent_category_id IS NULL ORDER BY o.id", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName());
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	@TransactionalReadOnly
	public int countAll() throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY_USERNAMED_BASED, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName());
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}


	/** 
	 * Returns all rows from the report_category table that match the criteria '' limited by start and limit.
	 */
	@TransactionalReadOnly
	public List<ReportCategory> findAll(Integer start, Integer limit) throws ReportCategoryDaoException{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " ORDER BY o.id limit ?, ?", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(), start, limit);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria like query in onFields fields limited by start and limit.
	 */
	@TransactionalReadOnly
	public List<ReportCategory> findAll(String query, List<String> onFields, Integer start, Integer limit) throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND (" + getMultipleFieldWhereClause(query, onFields) + ") ORDER BY o.id limit ?, ?", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(), start, limit);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	@TransactionalReadOnly
	public int countAll(String query, List<String> onFields) throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY_USERNAMED_BASED + " AND (" + getMultipleFieldWhereClause(query, onFields) + ")", ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName());
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}



	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<ReportCategory> findByPersistableObject(Long id) throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.id = ?", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),id);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<ReportCategory> findWhereIdEquals(Long id) throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.id = ? ORDER BY id", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),id);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'name = :name'.
	 */
	@TransactionalReadOnly
	public List<ReportCategory> findWhereNameEquals(String name) throws ReportCategoryDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART_USERNAMED_BASED + " AND o.name = ? ORDER BY name", this, ExtendedPermission.READ.getMask(), SessionManager.getCurrentUserName(), SessionManager.getCurrentUserName(),name);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}



	/** 
	 * Returns the rows from the report_category table that matches the specified primary-key value.
	 */
	public ReportCategory findByPrimaryKey(ReportCategoryPk pk) throws ReportCategoryDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}



	private static String getMultipleFieldWhereClause(String txt, List<String> fields) {
		StringBuilder query = new StringBuilder();
		String likeQuery = " LIKE '%"+txt+"%' OR ";
		if(fields.contains("name")) {
			query.append("o.name").append(likeQuery);
		}
		if(fields.contains("description")) {
			query.append("o.description").append(likeQuery);
		}
		if(query.length() > 0) {
			return query.substring(0,query.length()-3);
		} else {
			return "";
		}
	}

	@Autowired
	public void setAclService(AclServiceImpl aclService) {
		this.aclService = aclService;
	}
}
