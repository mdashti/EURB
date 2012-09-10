package com.sharifpro.eurb.index.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import com.sharifpro.eurb.builder.dao.UserMessageDao;
import com.sharifpro.eurb.builder.model.UserMessage;
import com.sharifpro.eurb.builder.model.UserMessagePk;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SessionManager;
import com.sharifpro.util.json.JsonUtil;

@Controller
public class IndexController {
	/*@RequestMapping(value="/")
	public ModelAndView handleRequest(ServletWebRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("index", model);
	}
	
	@RequestMapping(value="/index.spy")
	public ModelAndView handleRequestIndexPage(ServletWebRequest request) throws Exception {
		return handleRequest(request);
	}*/
	
	private UserMessageDao userMessageDao;
	private JsonUtil jsonUtil;
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/index/findUserAlerts.spy")
	public @ResponseBody Map<String, ? extends Object> findUserAlerts() {
		try {
			List<UserMessage> userMessages = userMessageDao.findAllForUser(SessionManager.getCurrentUserName());

			return JsonUtil.getSuccessfulMap(userMessages);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/index/alertRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<UserMessagePk> pkList = new ArrayList<UserMessagePk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new UserMessagePk(new Long(id)));
			}
			userMessageDao.deleteAll(SessionManager.getCurrentUserName(),pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(PropertyProvider.get("eurb.category.hasreportexception"));
		}
	}
	
	@Autowired
	public void setUserMessageDao(UserMessageDao userMessageDao) {
		this.userMessageDao = userMessageDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}
