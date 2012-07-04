package com.sharifpro.eurb.management.security.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.management.security.dao.AuthoritiesDao;
import com.sharifpro.eurb.management.security.dao.GroupAuthoritiesDao;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.dao.UserDao;
//import com.sharifpro.eurb.management.security.dao.impl.UserDetailsServiceImpl;
import com.sharifpro.eurb.management.security.exception.AuthoritiesDaoException;
import com.sharifpro.eurb.management.security.model.Authorities;
import com.sharifpro.eurb.management.security.model.AuthorityBundle;
import com.sharifpro.eurb.management.security.model.GroupAuthorities;
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
public class AuthoritiesController {
	private GroupAuthoritiesDao groupAuthoritiesDao;
	
	private AuthoritiesDao authoritiesDao;
	
	private GroupsDao groupsDao;
	
	private UserDao userDao;
	
	private JsonUtil jsonUtil;

	//private UserDetailsServiceImpl userDetailsServiceImpl;

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_ROLE_MANAGEMENT_VIEW_LIST)")
	@RequestMapping(value="/management/security/authorities.spy")
	public ModelAndView executeAuthoritiesSpy() throws Exception {
		return executeAuthoritiesSpy(null);
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_ROLE_MANAGEMENT_VIEW_LIST)")
	@RequestMapping(value="/management/security/authorities{sid}.spy")
	public ModelAndView executeAuthoritiesSpy(@PathVariable Long sid) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<User> userList = userDao.findAllActive();
		List<Groups> groupList = groupsDao.findAll();
		
		if(sid == null) {
			if(!userList.isEmpty()) {
				sid = userList.get(0).getId();
			} else if(!groupList.isEmpty()) {
				sid = groupList.get(0).getId();
			} else { //if(userList.isEmpty() && groupList.isEmpty()) {
				mv.addObject("sid", "");
				mv.addObject("SIDComboContent", "[]");
				return mv;
			}
		} else {
			User foundUser = userDao.findByPrimaryKey(sid);
			if(foundUser != null) {
				sid = foundUser.getId();
			} else {
				Groups foundGroup = groupsDao.findByPrimaryKey(sid);
				if(foundGroup != null) {
					sid = foundGroup.getId();
				} else { // SID not found, we will just do the same as sid == null
					if(!userList.isEmpty()) {
						sid = userList.get(0).getId();
					} else if(!groupList.isEmpty()) {
						sid = groupList.get(0).getId();
					} else { //if(userList.isEmpty() && groupList.isEmpty()) {
						mv.addObject("sid", "");
						mv.addObject("SIDComboContent", "[]");
						return mv;
					}
				}
			}
		}
		mv.addObject("sid", sid+"");
		
		Object[][] sidArr = new Object[userList.size()+groupList.size()][2];
		for(int i = 0; i < groupList.size(); i++) {
			sidArr[i][0] = groupList.get(i).getId();
			sidArr[i][1] = groupList.get(i).getGroupName();
		}
		for(int i = 0; i < userList.size(); i++) {
			sidArr[groupList.size()+i][0] = userList.get(i).getId();
			sidArr[groupList.size()+i][1] = userList.get(i).getUsername();
		}
		mv.addObject("SIDComboContent", jsonUtil.getJSONFromObject(sidArr));
		mv.setViewName("/management/security/authorities");
		return mv;
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_ROLE_MANAGEMENT_VIEW)")
	@RequestMapping(value="/management/security/authorities/authoritiesSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=true) Long sid) throws Exception {
		
		try{
			User foundUser = null;
			Groups foundGroup = null;
			boolean isPrincipal;
			if(sid == null) {
				throw new AuthoritiesDaoException(PropertyProvider.get("eurb.app.management.authorities.noUserOrGroupSelected"));
			} else {
				foundUser = userDao.findByPrimaryKey(sid);
				if(foundUser != null) {
					sid = foundUser.getId();
					isPrincipal = true;
				} else {
					foundGroup = groupsDao.findByPrimaryKey(sid);
					if(foundGroup != null) {
						sid = foundGroup.getId();
						isPrincipal = false;
					} else { // SID not found, we will just do the same as sid == null
						throw new AuthoritiesDaoException(PropertyProvider.get("eurb.app.management.authorities.noUserOrGroupSelected"));
					}
				}
			}
			SortedSet<String> authSet = new TreeSet<String>();
			if(isPrincipal) {
				List<Authorities> authList = authoritiesDao.findByUsers(foundUser.getUsername());
				for(Authorities a : authList) {
					authSet.add(a.getAuthority());
				}
			} else {
				List<GroupAuthorities> authList = groupAuthoritiesDao.findByGroups(foundGroup.getId());
				for(GroupAuthorities a : authList) {
					authSet.add(a.getAuthority());
				}
			}
			List<AuthorityBundle> authBundles = AuthorityBundle.poplulateAuthorityBundlesFromAuthorities(authSet);
			return JsonUtil.getSuccessfulMap(authBundles);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_ROLE_MANAGEMENT_EDIT)")
	@RequestMapping(value="/management/security/authorities/authoritiesStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam(required=true) Long sid, @RequestParam Object data) throws Exception {
		try{
			User foundUser = null;
			Groups foundGroup = null;
			boolean isPrincipal;
			if(sid == null) {
				throw new AuthoritiesDaoException(PropertyProvider.get("eurb.app.management.authorities.noUserOrGroupSelected"));
			} else {
				foundUser = userDao.findByPrimaryKey(sid);
				if(foundUser != null) {
					sid = foundUser.getId();
					isPrincipal = true;
				} else {
					foundGroup = groupsDao.findByPrimaryKey(sid);
					if(foundGroup != null) {
						sid = foundGroup.getId();
						isPrincipal = false;
					} else { // SID not found, we will just do the same as sid == null
						throw new AuthoritiesDaoException(PropertyProvider.get("eurb.app.management.authorities.noUserOrGroupSelected"));
					}
				}
			}
			List<AuthorityBundle> authBundles = jsonUtil.getListFromRequest(data, AuthorityBundle.class);
			SortedSet<String> authSet = AuthorityBundle.extractAuthSet(authBundles);
			if(authSet != null) {
				List<User> affectedUsers;
				if(isPrincipal) {
					authoritiesDao.clearUserAuthorities(foundUser.getUsername());
					Authorities tmpAuth;
					for(String a : authSet) {
						tmpAuth = new Authorities();
						tmpAuth.setAuthority(a);
						tmpAuth.setUsername(foundUser.getUsername());
						authoritiesDao.insert(tmpAuth);
					}
					affectedUsers = Arrays.asList(foundUser);
				} else {
					groupAuthoritiesDao.clearGroupAuthorities(foundGroup.getId());
					GroupAuthorities tmpAuth;
					for(String a : authSet) {
						tmpAuth = new GroupAuthorities();
						tmpAuth.setAuthority(a);
						tmpAuth.setGroupId(foundGroup.getId());
						groupAuthoritiesDao.insert(tmpAuth);
					}
					affectedUsers = userDao.findCurrentUsersForGroup(foundGroup.getId());
				}

				// Make this change to affect online users
				SessionRegistry sessionRegistry = (SessionRegistry) org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext().getBean("sessionRegistry");
				for(User u : affectedUsers) {
					if(!u.getUsername().equals(SessionManager.getCurrentUserName()) && sessionRegistry != null) {
						List<SessionInformation> sessionInfoList = sessionRegistry.getAllSessions(u, false);
						
						if(sessionInfoList != null && !sessionInfoList.isEmpty()) {
							for(SessionInformation si : sessionInfoList) {
								//if(si.getPrincipal() instanceof User) {
								//	User principal = ((User)si.getPrincipal());
								//	principal.setAuthorities(userDetailsServiceImpl.loadUserByUsername(principal.getUsername()).getAuthorities());
								//} else {
									si.expireNow();
								//}
							}
						}
					}
				}
			}

			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(sid));

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@Autowired
	public void setGroupsDao(GroupsDao groupsDao) {
		this.groupsDao = groupsDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setGroupAuthoritiesDao(GroupAuthoritiesDao groupAuthoritiesDao) {
		this.groupAuthoritiesDao = groupAuthoritiesDao;
	}

	@Autowired
	public void setAuthoritiesDao(AuthoritiesDao authoritiesDao) {
		this.authoritiesDao = authoritiesDao;
	}

	/*@Autowired
	public void setUserDetailsServiceImpl(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}*/

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}