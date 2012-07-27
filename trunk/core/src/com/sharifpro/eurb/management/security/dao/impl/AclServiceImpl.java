package com.sharifpro.eurb.management.security.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.ObjectIdentity;

import com.sharifpro.db.DatabaseUtils;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.util.SessionManager;

/**
 * Our extended implemetation for AclService
 * 
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class AclServiceImpl extends JdbcMutableAclService {
	public static int FULL_PERMISSION_MASK = BasePermission.READ.getMask() + BasePermission.WRITE.getMask()
			+ BasePermission.CREATE.getMask() + BasePermission.DELETE.getMask()
			+ BasePermission.ADMINISTRATION.getMask() /*+ ExtendedPermission.SHARING.getMask()*/;
	
	public AclServiceImpl(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
        super(dataSource, lookupStrategy, aclCache);
    }

	public static void insertObjectIdentity(JdbcTemplate jdbcTemplate, final Long objectId, final Long classId, Long parentObjectId, Long parentClassId) {
		final Long parentCategoryAclObjectIdentity = getObjectIdentityFor(jdbcTemplate, parentObjectId, parentClassId);

		final Long currentUserSID = jdbcTemplate.queryForLong("SELECT id FROM acl_sid  WHERE sid = ? AND principal = 1 ",SessionManager.getCurrentUser().getUsername());

		KeyHolder oidKeyHolder = new GeneratedKeyHolder();
		final String INSERT_OID_SQL = "INSERT INTO acl_object_identity ( object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting ) VALUES ( ?, ?, ?, ?, ? )";
		jdbcTemplate.update(
			new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(INSERT_OID_SQL, new String[] {"id"});
					
					int i=0;
					ps.setLong(++i, classId);
					ps.setLong(++i, objectId);
					DatabaseUtils.setLong(ps, ++i, parentCategoryAclObjectIdentity);
					ps.setLong(++i, currentUserSID);
					ps.setInt(++i, 1);
					return ps;
				}
			}, oidKeyHolder);
		jdbcTemplate.update("INSERT INTO acl_entry ( acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure ) VALUES ( ?, ?, ?, ?, ?, ?, ? )", oidKeyHolder.getKey().longValue(), 1, currentUserSID, AclServiceImpl.FULL_PERMISSION_MASK, 1, 1, 1 );
	}

	public static void updateObjectIdentity(JdbcTemplate jdbcTemplate, final Long objectId, final Long classId, Long parentObjectId, Long parentClassId) {
		Long parentCategoryAclObjectIdentity = getObjectIdentityFor(jdbcTemplate, parentObjectId, parentClassId);
		jdbcTemplate.update("UPDATE acl_object_identity SET parent_object = ? WHERE object_id_class = ? AND object_id_identity = ?", parentCategoryAclObjectIdentity, classId, objectId);
	}

	public static void deleteObjectIdentity(JdbcTemplate jdbcTemplate, final Long objectId, final Long classId, Long newParentObjectId, Long newParentClassId) {
		Long objectIdentity = getObjectIdentityFor(jdbcTemplate, objectId, classId);
		Long newParentObjectIdentity = getObjectIdentityFor(jdbcTemplate, newParentObjectId, newParentClassId);
		jdbcTemplate.update("UPDATE acl_object_identity SET parent_object = ? WHERE parent_object = ?", newParentObjectIdentity, objectIdentity);
		jdbcTemplate.update("DELETE FROM acl_entry WHERE acl_object_identity = ?", objectIdentity);
		jdbcTemplate.update("DELETE FROM acl_object_identity WHERE id = ? ", objectIdentity);
	}
	
	public static Long getObjectIdentityFor(JdbcTemplate jdbcTemplate, final Long objectId, final Long classId) {
		if(objectId == null) return null;
		return jdbcTemplate.queryForLong("SELECT id FROM acl_object_identity  WHERE object_id_class = ? AND object_id_identity = ? ",classId,objectId);
	}
	
	public Long getObjectIdentityFor(final Long objectId, final Long classId) {
		if(objectId == null) return null;
		return jdbcTemplate.queryForLong("SELECT id FROM acl_object_identity  WHERE object_id_class = ? AND object_id_identity = ? ",classId,objectId);
	}
	
	public Long getObjectIdentityFor(final Long objectId) {
		if(objectId == null) return null;
		return jdbcTemplate.queryForLong("SELECT id FROM acl_object_identity  WHERE object_id_identity = ? ",objectId);
	}

	public List<ObjectIdentity> convertPersistableObjectsToObjectIndentities(List<PersistableObject> objList) {
		if(objList == null || objList.isEmpty()) return new ArrayList<ObjectIdentity>(0);
		List<ObjectIdentity> result = new ArrayList<ObjectIdentity>(objList.size());
		for(PersistableObject p : objList) {
			result.add(new ObjectIdentityImpl(p.getClassBasedOnPersistedObjectType(), p.getId()));
		}
		return result;
	}
}
