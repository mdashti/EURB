package com.sharifpro.eurb.management.security.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.management.mapping.dao.PersistableObjectDao;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.dao.impl.AclServiceImpl;
import com.sharifpro.eurb.management.security.dao.impl.ExtendedPermission;
import com.sharifpro.eurb.management.security.exception.AuthoritiesDaoException;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SessionManager;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class DomainObjectController {
	public static int OBJECT_SEC_TYPE_GROUP = 1;
	public static int OBJECT_SEC_TYPE_USER = 2;
	
	private AclServiceImpl aclService;

	private JsonUtil jsonUtil;
	
	private GroupsDao groupsDao;
	
	private UserDao userDao;
	
	@RequestMapping(value="/management/security/acl/loadPermission.spy")
	public @ResponseBody Map<String,? extends Object> loadPermission(@RequestParam Object data) throws Exception {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		if(data != null && data.toString().isEmpty()){
			modelMap.put("success", true);
			modelMap.put("aclEntryListTotalCount", 0);
			modelMap.put("aclEntryList", Collections.EMPTY_LIST);
			modelMap.put("objectIdListTotalCount", 0);
			modelMap.put("objectIdList", Collections.EMPTY_LIST);
			return modelMap;
		}
		Set<ObjectIdentity> destIdentities = new HashSet<ObjectIdentity>();
		List<ObjectSecurityRow> currentRows = new LinkedList<ObjectSecurityRow>();
		try{
			List<Long> targetObjects = jsonUtil.getListFromRequest(data, Long.class);
			List<Entry<ObjectIdentity,Acl>> finalAclList = aclService.getPermissionFor(targetObjects);
			List<Groups> groups = groupsDao.findAll();
			List<User> users = userDao.findAll();
			
			Acl acl;
			for(Entry<ObjectIdentity,Acl> entry : finalAclList) {
				acl = entry.getValue();
				if(acl != null) {
					destIdentities.add(entry.getKey());
					for(AccessControlEntry ace : acl.getEntries()) {
						ObjectSecurityRow.getObjectSecurityRow(currentRows, ace, groups, users);
					}
				}
			}
			
			modelMap.put("aclEntryListTotalCount", currentRows.size());
			modelMap.put("aclEntryList", currentRows);
			modelMap.put("objectIdListTotalCount", destIdentities.size());
			modelMap.put("objectIdList", destIdentities);
			
			modelMap.put("success", true);
			return modelMap;
			
		} catch (Exception e) {
			//do nothing
			return JsonUtil.getModelMapError(e);
		}
	}
	
	@RequestMapping(value="/management/security/acl/storePermission.spy")
	public @ResponseBody Map<String,? extends Object> storePermission(@RequestParam Object permData, @RequestParam Object objData) throws Exception {
		try{
			List<ObjectSecurityRow> targetObjectSecRows = jsonUtil.getListFromRequest(permData, ObjectSecurityRow.class);
			List<Long> targetObjects = jsonUtil.getListFromRequest(objData, Long.class);
			List<Serializable> destIdentities = new ArrayList<Serializable>(targetObjects.size());
			
			Map<String, ObjectSecurityRow> sidMap = new HashMap<String, ObjectSecurityRow>();
			for(ObjectSecurityRow osr : targetObjectSecRows){
				if(osr.type == OBJECT_SEC_TYPE_GROUP) {
					sidMap.put(osr.getId()+"", osr);
				} else {
					sidMap.put(osr.getName()+"", osr);
				}
			}
			
			List<Entry<ObjectIdentity,Acl>> finalAclList = aclService.getPermissionFor(targetObjects);
			
			AclImpl acl;
			Sid sid;
			List<AccessControlEntry> aceList;
			
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
						if(osr.type == OBJECT_SEC_TYPE_GROUP) {
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
			
			
			
			return JsonUtil.getSuccessfulMapAfterStore(destIdentities);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}

	@Autowired
	public void setAclService(AclServiceImpl aclService) {
		this.aclService = aclService;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
	
	@Autowired
	public void setGroupsDao(GroupsDao groupsDao) {
		this.groupsDao = groupsDao;
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}