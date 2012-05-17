package com.sharifpro.eurb.management.security.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.eurb.management.security.model.UserPk;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class UserController {

	private UserDao userDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/management/security/user/userSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields
			,@RequestParam(defaultValue="0", required=false) String start
			,@RequestParam(defaultValue=AbstractDAO.DEFAULT_PAGE_SIZE_STR, required=false) String limit
			,@RequestParam(defaultValue=PersistableObject.IDENTIFIER_FIELD, required=false) String sort
			,@RequestParam(defaultValue=AbstractDAO.ASCENDING_SORT_ORDER, required=false) String dir) throws Exception {
		
		try{
			List<User> users;
			int totalCount;
			Integer startBy = StringUtils.isEmpty(start) ? 0 : Integer.parseInt(start);
			Integer limitBy = StringUtils.isEmpty(limit) ? AbstractDAO.DEFAULT_PAGE_SIZE : Integer.parseInt(limit);
			List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
			if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
				users = userDao.findAll(startBy, limitBy, sort, dir);
				totalCount = userDao.countAll();
			} else {
				users = userDao.findAll(query, onFields, startBy, limitBy, sort, dir);
				totalCount = userDao.countAll(query, onFields);
			}

			return JsonUtil.getSuccessfulMap(users, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/management/security/user/userStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<User> users = jsonUtil.getListFromRequest(data, User.class);
			
			List<Object[]> insertIds = new ArrayList<Object[]>(users.size());
			UserPk pk;
			for(User dbConf : users) {
				if(dbConf.isNewRecord()) {
					pk = userDao.insert(dbConf);
				} else {
					pk = dbConf.createPk();
					userDao.update(pk,dbConf);
				}
				insertIds.add(new Object[] {pk.getId()});
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/management/security/user/userRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<UserPk> pkList = new ArrayList<UserPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new UserPk(new Long(id)));
			}
			userDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/management/security/user/userActivate.spy")
	public @ResponseBody Map<String,? extends Object> activate(@RequestParam Object data) throws Exception {
		try{

			List<Integer> activateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<UserPk> pkList = new ArrayList<UserPk>(activateIds.size());
			for(Integer id : activateIds) {
				pkList.add(new UserPk(new Long(id)));
			}
			userDao.activateAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(activateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/management/security/user/userDeactivate.spy")
	public @ResponseBody Map<String,? extends Object> deactivate(@RequestParam Object data) throws Exception {
		try{

			List<Integer> deactivateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<UserPk> pkList = new ArrayList<UserPk>(deactivateIds.size());
			for(Integer id : deactivateIds) {
				pkList.add(new UserPk(new Long(id)));
			}
			userDao.deactivateAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deactivateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
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