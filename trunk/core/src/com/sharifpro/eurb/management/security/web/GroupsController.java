package com.sharifpro.eurb.management.security.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.info.AuthorityType;
import com.sharifpro.eurb.management.security.dao.GroupMembersDao;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.security.model.GroupMembers;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.GroupsPk;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SecurityUtil;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class GroupsController {
	private GroupMembersDao groupMembersDao;
	
	private GroupsDao groupsDao;
	
	private UserDao userDao;
	
	private JsonUtil jsonUtil;

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_VIEW_LIST)")
	@RequestMapping(value="/management/security/group.spy")
	public ModelAndView executeDbConfigSpy() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/management/security/group");
		return mv;
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_VIEW_LIST)")
	@RequestMapping(value="/management/security/group/groupSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields
			,@RequestParam(defaultValue="0", required=false) String start
			,@RequestParam(defaultValue=AbstractDAO.DEFAULT_PAGE_SIZE_STR, required=false) String limit
			,@RequestParam(defaultValue=PersistableObject.IDENTIFIER_FIELD, required=false) String sort
			,@RequestParam(defaultValue=AbstractDAO.ASCENDING_SORT_ORDER, required=false) String dir) throws Exception {
		
		try{
			List<Groups> groups;
			int totalCount;
			Integer startBy = StringUtils.isEmpty(start) ? 0 : Integer.parseInt(start);
			Integer limitBy = StringUtils.isEmpty(limit) ? AbstractDAO.DEFAULT_PAGE_SIZE : Integer.parseInt(limit);
			List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
			if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
				groups = groupsDao.findAll(startBy, limitBy, sort, dir);
				totalCount = groupsDao.countAll();
			} else {
				groups = groupsDao.findAll(query, onFields, startBy, limitBy, sort, dir);
				totalCount = groupsDao.countAll(query, onFields);
			}

			return JsonUtil.getSuccessfulMap(groups, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_EDIT, T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_CREATE)")
	@RequestMapping(value="/management/security/group/groupStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<Groups> groups = jsonUtil.getListFromRequest(data, Groups.class);
			
			List<Object[]> insertIds = new ArrayList<Object[]>(groups.size());
			GroupsPk pk;
			for(Groups group : groups) {
				if(group.isNewRecord()) {
					if(SecurityUtil.hasRole(AuthorityType.ROLE_SEC_GROUP_MANAGEMENT_CREATE)) {
						pk = groupsDao.insert(group);
					} else {
						throw new DbConfigDaoException(PropertyProvider.ERROR_NOT_AUTHORIZED_TO_CREATE);
					}
				} else {
					if(SecurityUtil.hasRole(AuthorityType.ROLE_SEC_GROUP_MANAGEMENT_EDIT)) {
						pk = group.createPk();
						groupsDao.update(pk,group);
					} else {
						throw new DbConfigDaoException(PropertyProvider.ERROR_NOT_AUTHORIZED_TO_EDIT);
					}
				}
				insertIds.add(new Object[] {pk.getId()});
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_DELETE)")
	@RequestMapping(value="/management/security/group/groupRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<GroupsPk> pkList = new ArrayList<GroupsPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new GroupsPk(new Long(id)));
			}
			groupsDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_VIEW)")
	@RequestMapping(value="/management/security/group/currentGroupUsers.spy")
	public @ResponseBody Map<String,? extends Object> findCurrentUsersForGroup(@RequestParam Long groupId) throws Exception {
		try{
			List<User> userList = userDao.findCurrentUsersForGroup(groupId);
			return JsonUtil.getSuccessfulMap(userList);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_VIEW)")
	@RequestMapping(value="/management/security/group/selectableGroupUsers.spy")
	public @ResponseBody Map<String,? extends Object> findSelectableUsersForGroup(@RequestParam Long groupId) throws Exception {
		try{
			List<User> userList = userDao.findSelectableUsersForGroup(groupId);
			return JsonUtil.getSuccessfulMap(userList);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_EDIT)")
	@RequestMapping(value="/management/security/group/addGroupToUsersAction.spy")
	public @ResponseBody Map<String,? extends Object> addGroupToUsers(@RequestParam Long groupId, @RequestParam Object userNames) throws Exception {
		try{

			List<String> userNameList = jsonUtil.getListFromRequest(userNames, String.class);
			for(String userName : userNameList) {
				groupMembersDao.insert(new GroupMembers(userName, groupId));
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(userNameList);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_SEC_GROUP_MANAGEMENT_EDIT)")
	@RequestMapping(value="/management/security/group/removeGroupFromUsersAction.spy")
	public @ResponseBody Map<String,? extends Object> removeGroupFrom(@RequestParam Long groupId, @RequestParam Object userNames) throws Exception {
		try{

			List<String> userNameList = jsonUtil.getListFromRequest(userNames, String.class);
			for(String userName : userNameList) {
				groupMembersDao.delete(new GroupMembers(userName, groupId));
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(userNameList);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@Autowired
	public void setGroupMembersDao(GroupMembersDao groupMembersDao) {
		this.groupMembersDao = groupMembersDao;
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
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}