package com.sharifpro.eurb.management.security.web;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Sid;

import com.sharifpro.eurb.management.security.dao.impl.ExtendedPermission;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.User;

public class ObjectSecurityRow {
	long id;
	int type;
	String name;
	Boolean view = Boolean.FALSE;
	Boolean edit = Boolean.FALSE;
	Boolean del = Boolean.FALSE;
	Boolean execute = Boolean.FALSE;
	Boolean sharing = Boolean.FALSE;	
	
	public ObjectSecurityRow(){
		
	}

	public ObjectSecurityRow(long id, int type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
	}

	@JsonIgnore
	public static void getObjectSecurityRow(List<ObjectSecurityRow> currentRows, AccessControlEntry ace, List<Groups> groups, List<User> users) {
		ObjectSecurityRow result = null;
		long id = -1;
		int type;
		String name;
		if(ace.getSid() instanceof GrantedAuthoritySid) {
			id = Long.parseLong(((GrantedAuthoritySid) ace.getSid()).getGrantedAuthority());
			type = DomainObjectController.OBJECT_SEC_TYPE_GROUP;
			name = "Group"+id;
			for(Groups g: groups) {
				if(g.getId().equals(id)) {
					name = g.getGroupName();
				}
			}
		} else { //if(sid instanceof PrincipalSid) {
			name = ((PrincipalSid) ace.getSid()).getPrincipal();
			type = DomainObjectController.OBJECT_SEC_TYPE_USER;
			for(User u: users) {
				if(u.getUsername().equals(name)) {
					id = u.getId();
				}
			}
		}
		for(ObjectSecurityRow row : currentRows) {
			if(row.id == id) {
				result = row;
			}
		}
		
		if(result == null){
			result = new ObjectSecurityRow(id, type, name);
			if(ace.isGranting()) {
				result.sharing = (ace.getPermission().getMask() & ExtendedPermission.ADMINISTRATION.getMask()) != 0;
				result.view = (ace.getPermission().getMask() & ExtendedPermission.READ.getMask()) != 0 || result.sharing;
				result.edit = result.view && (ace.getPermission().getMask() & ExtendedPermission.WRITE.getMask()) != 0;
				result.del = result.view && (ace.getPermission().getMask() & ExtendedPermission.DELETE.getMask()) != 0;
				result.execute = result.view && (ace.getPermission().getMask() & ExtendedPermission.EXECUTE.getMask()) != 0;
			}
			currentRows.add(result);
		} else {
			if(ace.isGranting()) {
				result.sharing = result.sharing || (ace.getPermission().getMask() & ExtendedPermission.ADMINISTRATION.getMask()) != 0;
				result.view = result.view || (ace.getPermission().getMask() & ExtendedPermission.READ.getMask()) != 0 || result.sharing;
				result.edit = result.view && (result.edit || (ace.getPermission().getMask() & ExtendedPermission.WRITE.getMask()) != 0);
				result.del = result.view && (result.del || (ace.getPermission().getMask() & ExtendedPermission.DELETE.getMask()) != 0);
				result.execute = result.view && (result.execute || (ace.getPermission().getMask() & ExtendedPermission.EXECUTE.getMask()) != 0);
			}
		}
		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getView() {
		return view;
	}

	public void setView(Boolean view) {
		this.view = view;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public Boolean getDel() {
		return del;
	}

	public void setDel(Boolean del) {
		this.del = del;
	}

	public Boolean getExecute() {
		return execute;
	}

	public void setExecute(Boolean execute) {
		this.execute = execute;
	}

	public Boolean getSharing() {
		return sharing;
	}

	public void setSharing(Boolean sharing) {
		this.sharing = sharing;
	}

	public CumulativePermission createPermission(boolean granting) {
		CumulativePermission perm = new CumulativePermission();
		if(granting) {
			if(this.sharing) {
				perm.set(ExtendedPermission.ADMINISTRATION);
			}
			if(this.view || this.sharing) {
				perm.set(ExtendedPermission.READ);
			}
			if(this.view && this.edit) {
				perm.set(ExtendedPermission.WRITE);
			}
			if(this.view && this.del) {
				perm.set(ExtendedPermission.DELETE);
			}
			if(this.view && this.execute) {
				perm.set(ExtendedPermission.EXECUTE);
			}
		} else {
			if(!this.sharing) {
				perm.set(ExtendedPermission.ADMINISTRATION);
			}
			if(!this.view && !this.sharing) {
				perm.set(ExtendedPermission.READ);
			}
			if(!this.view || !this.edit) {
				perm.set(ExtendedPermission.WRITE);
			}
			if(!this.view || !this.del) {
				perm.set(ExtendedPermission.DELETE);
			}
			if(!this.view || !this.execute) {
				perm.set(ExtendedPermission.EXECUTE);
			}
		}
		return perm.getMask() == 0 ? null : perm;
	}
}