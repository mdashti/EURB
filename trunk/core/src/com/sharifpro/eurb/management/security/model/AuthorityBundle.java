package com.sharifpro.eurb.management.security.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.sharifpro.eurb.info.AuthorityType;
import com.sharifpro.util.PropertyProvider;

public class AuthorityBundle {
	String id;
	String category;
	
	boolean viewlist;
	boolean view;
	boolean edit;
	boolean create;
	boolean del;
	boolean sharing;
	boolean execute;
	
	public AuthorityBundle() {}
	
	public AuthorityBundle(String catId, boolean viewlist, boolean view,
			boolean edit, boolean create, boolean del, boolean sharing,
			boolean execute) {
		super();
		this.id = catId;
		this.category = PropertyProvider.get("eurb.app.management.authorities.auth."+catId);
		this.viewlist = viewlist;
		this.view = view;
		this.edit = edit;
		this.create = create;
		this.del = del;
		this.sharing = sharing;
		this.execute = execute;
	}

	public AuthorityBundle(String catId, SortedSet<String> authSet) {
		this(
			catId
			,authSet.contains(AuthorityType.PREFIX_ROLE + catId + AuthorityType.POSTFIX_ACTION_VIEW_LIST)
			,authSet.contains(AuthorityType.PREFIX_ROLE + catId + AuthorityType.POSTFIX_ACTION_VIEW)
			,authSet.contains(AuthorityType.PREFIX_ROLE + catId + AuthorityType.POSTFIX_ACTION_EDIT)
			,authSet.contains(AuthorityType.PREFIX_ROLE + catId + AuthorityType.POSTFIX_ACTION_CREATE)
			,authSet.contains(AuthorityType.PREFIX_ROLE + catId + AuthorityType.POSTFIX_ACTION_DELETE)
			,authSet.contains(AuthorityType.PREFIX_ROLE + catId + AuthorityType.POSTFIX_ACTION_SHARING)
			,authSet.contains(AuthorityType.PREFIX_ROLE + catId + AuthorityType.POSTFIX_ACTION_EXECUTE)
		);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isViewlist() {
		return viewlist;
	}
	public void setViewlist(boolean viewlist) {
		this.viewlist = viewlist;
	}
	public boolean isView() {
		return view;
	}
	public void setView(boolean view) {
		this.view = view;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public boolean isCreate() {
		return create;
	}
	public void setCreate(boolean create) {
		this.create = create;
	}
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	public boolean isSharing() {
		return sharing;
	}
	public void setSharing(boolean sharing) {
		this.sharing = sharing;
	}
	public boolean isExecute() {
		return execute;
	}
	public void setExecute(boolean execute) {
		this.execute = execute;
	}

	public static List<AuthorityBundle> poplulateAuthorityBundlesFromAuthorities(SortedSet<String> authSet) {
		List<AuthorityBundle> result = new ArrayList<AuthorityBundle>(12);
		
		result.add(new AuthorityBundle(AuthorityType.BASE_MANAGEMENT_MENU, authSet));
		result.add(new AuthorityBundle(AuthorityType.BASE_DB_CONFIG, authSet));
		result.add(new AuthorityBundle(AuthorityType.BASE_TABLE_MAPPING, authSet));
		result.add(new AuthorityBundle(AuthorityType.BASE_COL_MAPPING, authSet));
		result.add(new AuthorityBundle(AuthorityType.BASE_CATEGORY_MANAGEMENT, authSet));

		result.add(new AuthorityBundle(AuthorityType.SECURITY_MANAGEMENT_MENU, authSet));
		result.add(new AuthorityBundle(AuthorityType.SEC_USER_MANAGEMENT, authSet));
		result.add(new AuthorityBundle(AuthorityType.SEC_GROUP_MANAGEMENT, authSet));
		result.add(new AuthorityBundle(AuthorityType.SEC_ROLE_MANAGEMENT, authSet));

		result.add(new AuthorityBundle(AuthorityType.REPORT_GENERATOR_MENU, authSet));
		result.add(new AuthorityBundle(AuthorityType.RPG_REPORT_BUILDER, authSet));
		result.add(new AuthorityBundle(AuthorityType.RPG_REPORT_SCHEDULER, authSet));

		result.add(new AuthorityBundle(AuthorityType.RPG_DASHBOARD, authSet));
		return result;
	}

	public static SortedSet<String> extractAuthSet(List<AuthorityBundle> authBundles) {
		SortedSet<String> result = new TreeSet<String>();
		for(AuthorityBundle bundle : authBundles) {
			if(bundle.viewlist) {
				result.add(AuthorityType.PREFIX_ROLE + bundle.id + AuthorityType.POSTFIX_ACTION_VIEW_LIST);
			}
			if(bundle.view) {
				result.add(AuthorityType.PREFIX_ROLE + bundle.id + AuthorityType.POSTFIX_ACTION_VIEW);
			}
			if(bundle.edit) {
				result.add(AuthorityType.PREFIX_ROLE + bundle.id + AuthorityType.POSTFIX_ACTION_EDIT);
			}
			if(bundle.create) {
				result.add(AuthorityType.PREFIX_ROLE + bundle.id + AuthorityType.POSTFIX_ACTION_CREATE);
			}
			if(bundle.del) {
				result.add(AuthorityType.PREFIX_ROLE + bundle.id + AuthorityType.POSTFIX_ACTION_DELETE);
			}
			if(bundle.sharing) {
				result.add(AuthorityType.PREFIX_ROLE + bundle.id + AuthorityType.POSTFIX_ACTION_SHARING);
			}
			if(bundle.execute) {
				result.add(AuthorityType.PREFIX_ROLE + bundle.id + AuthorityType.POSTFIX_ACTION_EXECUTE);
			}
		}
		return result;
	}
}