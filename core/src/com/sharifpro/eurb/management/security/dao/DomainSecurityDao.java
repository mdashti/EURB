package com.sharifpro.eurb.management.security.dao;

import com.sharifpro.eurb.management.security.dao.DomainSecurityDao;
import com.sharifpro.eurb.management.security.dao.impl.AclServiceImpl;
import com.sharifpro.eurb.management.security.web.ObjectSecurityRow;

import java.io.Serializable;
import java.util.List;

public interface DomainSecurityDao
{
	public void storePermissions(List<ObjectSecurityRow> targetObjectSecRows, List<Long> targetObjects, List<Serializable> destIdentities) throws Exception;
	
	public void setAclService(AclServiceImpl aclService);	
}
