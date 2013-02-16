package com.sharifpro.eurb.management.security.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Repository;

import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.security.dao.DomainSecurityDao;
import com.sharifpro.eurb.management.security.web.DomainObjectController;
import com.sharifpro.eurb.management.security.web.ObjectSecurityRow;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;


@Repository
public class DomainSecurityDaoImpl extends AbstractDAO implements DomainSecurityDao
{
	private AclServiceImpl aclService;
	
	@TransactionalReadWrite
	public void storePermissions(List<ObjectSecurityRow> targetObjectSecRows, List<Long> targetObjects, List<Serializable> destIdentities) throws Exception{
		Map<String, ObjectSecurityRow> sidMap = new HashMap<String, ObjectSecurityRow>();
		for(ObjectSecurityRow osr : targetObjectSecRows){
			if(osr.getType() == DomainObjectController.OBJECT_SEC_TYPE_GROUP) {
				sidMap.put(osr.getId()+"", osr);
			} else {
				sidMap.put(osr.getName()+"", osr);
			}
		}
		
		List<Entry<ObjectIdentity,Acl>> finalAclList = aclService.getPermissionFor(targetObjects);
		
		AclImpl acl;
		Sid sid;
		List<AccessControlEntry> aceList;
		//List<AccessControlEntry> deletedAceList = new LinkedList<AccessControlEntry>();
		
		for(Entry<ObjectIdentity,Acl> entry : finalAclList) {
			acl = (AclImpl) entry.getValue();
			if(acl != null) {
				destIdentities.add(entry.getKey().getIdentifier());
				aceList = acl.getEntries();
				for(int i = aceList.size() - 1 ; i >= 0  ; i--) {
					acl.deleteAce(i);
				}
				
				int index = 0;
				for(ObjectSecurityRow osr : sidMap.values()) {
					if(osr.getType() == DomainObjectController.OBJECT_SEC_TYPE_GROUP) {
						sid = new GrantedAuthoritySid(osr.getId()+"");
					} else {
						sid = new PrincipalSid(osr.getName());
					}

					Permission grantingPerm = osr.createPermission(true);
					Permission nonGrantingPerm = osr.createPermission(false);
					if(grantingPerm != null) {
						acl.insertAce(index++, grantingPerm, sid, true);
					}
					if(nonGrantingPerm != null) {
						acl.insertAce(index++, nonGrantingPerm, sid, false);
					}
				}
			}
			aclService.updateAcl(acl);
		}
	}

	@Autowired
	public void setAclService(AclServiceImpl aclService) {
		this.aclService = aclService;
	}
}
