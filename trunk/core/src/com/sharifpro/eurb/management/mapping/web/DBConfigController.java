package com.sharifpro.eurb.management.mapping.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.sample.model.Contact;
import com.sharifpro.sample.service.ContactService;
import com.sharifpro.util.CollectionUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class DBConfigController {

	private DbConfigDao dbConfigDao;

	@RequestMapping(value="/management/mapping/dbconfigSearch.spy")
	public @ResponseBody Map<String,? extends Object> view() throws Exception {

		try{
			List<DbConfig> dbConfigs = dbConfigDao.findAll();

			return CollectionUtil.getSuccessfulMap(dbConfigs);

		} catch (Exception e) {

			return CollectionUtil.getModelMapError("Error retrieving Contacts from database.");
		}
	}

	/*@RequestMapping(value="/sample/create.action")
	public @ResponseBody Map<String,? extends Object> create(@RequestParam Object data) throws Exception {

		try{

			List<Contact> contacts = contactService.create(data);

			return CollectionUtil.getSuccessfulMap(contacts);

		} catch (Exception e) {

			return CollectionUtil.getModelMapError("Error trying to create contact.");
		}
	}*/

	@RequestMapping(value="/management/mapping/dbconfigStore.spy")
	public @ResponseBody Map<String,? extends Object> update(@RequestParam Object data) throws Exception {
		try{

			List<DbConfig> dbConfigs = new ArrayList<DbConfig>(); //contactService.update(data);

			return CollectionUtil.getSuccessfulMap(dbConfigs);

		} catch (Exception e) {

			return CollectionUtil.getModelMapError("Error trying to update contact.");
		}
	}

	/*@RequestMapping(value="/sample/delete.action")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{

			contactService.delete(data);

			Map<String,Object> modelMap = new HashMap<String,Object>(3);
			modelMap.put("success", true);

			return modelMap;

		} catch (Exception e) {

			return getModelMapError("Error trying to delete contact.");
		}
	}*/


	@Autowired
	public void setDbConfigDao(DbConfigDao dbConfigDao) {
		this.dbConfigDao = dbConfigDao;
	}

}