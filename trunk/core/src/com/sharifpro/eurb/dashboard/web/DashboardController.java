package com.sharifpro.eurb.dashboard.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.dashboard.dao.DashboardColDao;
import com.sharifpro.eurb.dashboard.dao.DashboardDao;
import com.sharifpro.eurb.dashboard.dao.DashboardItemDao;
import com.sharifpro.eurb.dashboard.model.Dashboard;
import com.sharifpro.eurb.dashboard.model.DashboardCol;
import com.sharifpro.eurb.dashboard.model.DashboardColPk;
import com.sharifpro.eurb.dashboard.model.DashboardItem;
import com.sharifpro.eurb.dashboard.model.DashboardItemPk;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class DashboardController {

	private DashboardDao dashboardDao;
	
	private DashboardColDao dashboardColDao;
	
	private DashboardItemDao dashboardItemDao;
	
	private JsonUtil jsonUtil;

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard.spy")
	public ModelAndView executeDbConfigSpy() throws Exception {
		ModelAndView mv = new ModelAndView();
		Dashboard dashboard = getUserDashboard();
		mv.addObject("userDashboardId", dashboard.getId());
		
		List<DashboardCol> cols = dashboardColDao.findByDashboard(dashboard.getId());
		
		List<Map<String,Object>> dashboardInitialState = new ArrayList<Map<String,Object>>();
		Map<String,Object> dashboardCol;
		for(int i = 0 ; i < cols.size() ; i++) {
			dashboardCol = new HashMap<String, Object>();
			dashboardCol.put("columnWidth", cols.get(i).getColWidth());
			dashboardCol.put("id", cols.get(i).getId());
			if(i == 0) {
				dashboardCol.put("style", "padding:10px");
			} else {
				dashboardCol.put("style", "padding:10px 0 10px 10px");
			}
			
			List<DashboardItem> colItems = dashboardItemDao.findByDashboardCol(cols.get(i).getId());
			
			List<Map<String,Object>> dashboardColItems = new ArrayList<Map<String,Object>>();
			Map<String,Object> dashboardItem;
			
			for(int j = 0 ; j < colItems.size(); j++) {
				dashboardItem = new HashMap<String, Object>();
				dashboardItem.put("id", colItems.get(j).getId());
				dashboardItem.put("title", colItems.get(j).getItemTitle());
				if(colItems.get(j).getReportDesignId() == null ){
					dashboardItem.put("html", colItems.get(j).getItemContent());
				} else {
					
				}
				dashboardColItems.add(dashboardItem);
			}
			
			dashboardCol.put("items", dashboardColItems);
			dashboardInitialState.add(dashboardCol);
		}
		mv.addObject("dashboardInitialState", jsonUtil.getJSONFromObject(dashboardInitialState));
		mv.setViewName("dashboard");
		return mv;
	}

	private Dashboard getUserDashboard() throws Exception {
		return dashboardDao.findAll().get(0);
	}

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/defaultDashboard.spy")
	public @ResponseBody Map<String,? extends Object> defaultDashboard() throws Exception {
		
		try{
			return JsonUtil.getSuccessfulMap(null, 0);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/dashboardAddCol.spy")
	public @ResponseBody Map<String,? extends Object> dashboardAddCol(@RequestParam(required=true) Long dashboard) throws Exception {
		try{

			//Dashboard dashb = dashboardDao.findByPrimaryKey(dashboard);

			List<DashboardCol> cols = dashboardColDao.findWhereDashboardIdEquals(dashboard);
			
			Double newWidth = 1D / (cols.size() + 1);
			for(int i = 0 ; i < cols.size(); i++) {
				cols.get(i).setColWidth(newWidth);
				dashboardColDao.update(cols.get(i).createPk(), cols.get(i));
			}
			DashboardCol newCol = new DashboardCol();
			newCol.setColOrder(cols.size()+1);
			newCol.setColWidth(newWidth);
			newCol.setDashboardId(dashboard);

			dashboardColDao.insert(newCol);
			
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(newCol.getId()));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/dashboardDelCol.spy")
	public @ResponseBody Map<String,? extends Object> dashboardDelCol(@RequestParam(required=true) Long dashboard, @RequestParam(required=true) Long column) throws Exception {
		try{

			List<DashboardItem> items = dashboardItemDao.findByDashboardCol(column);
			for(DashboardItem i : items) {
				dashboardItemDao.delete(i.createPk());
			}
			dashboardColDao.delete(new DashboardColPk(column));	

			List<DashboardCol> cols = dashboardColDao.findWhereDashboardIdEquals(dashboard);
			
			if(cols.size() > 0) {
				Double newWidth = 1D / cols.size();
				for(int i = 0 ; i < cols.size(); i++) {
					cols.get(i).setColOrder(i+1);
					cols.get(i).setColWidth(newWidth);
					dashboardColDao.update(cols.get(i).createPk(), cols.get(i));
				}
			}		
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(column));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/dashboardAddItem.spy")
	public @ResponseBody Map<String,? extends Object> dashboardAddItem(@RequestParam(required=true) Long dashboard, @RequestParam(required=true) Long column, String title, String content) throws Exception {
		try{
			int itemsInCol = dashboardItemDao.findByDashboardCol(column).size();
			
			DashboardItem newItem = new DashboardItem();
			newItem.setDashboardId(dashboard);
			newItem.setDashboardColId(column);
			newItem.setItemOrder(itemsInCol+1);
			newItem.setItemHeight(null);
			newItem.setItemCollapsed(false);
			newItem.setItemClosed(false);

			newItem.setItemTitle(title);
			newItem.setItemContent(content);
			newItem.setDashboardId(dashboard);

			dashboardItemDao.insert(newItem);
			
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(newItem.getId()));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/dashboardDelItem.spy")
	public @ResponseBody Map<String,? extends Object> dashboardDelItem(@RequestParam(required=true) Long dashboard, @RequestParam(required=true) Long item) throws Exception {
		try{
			DashboardItem itemToDelete = dashboardItemDao.findByPrimaryKey(item);
			
			dashboardItemDao.delete(new DashboardItemPk(item));			
			
			List<DashboardItem> items = dashboardItemDao.findWhereDashboardColIdEquals(itemToDelete.getDashboardColId());
			
			if(items.size() > 0) {
				for(int i = 0 ; i < items.size(); i++) {
					items.get(i).setItemOrder(i+1);
					dashboardItemDao.update(items.get(i).createPk(), items.get(i));
				}
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(item));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/dashboardMoveItem.spy")
	public @ResponseBody Map<String,? extends Object> dashboardMoveItem(@RequestParam(required=true) Long dashboard, @RequestParam(required=true) Long item,
			@RequestParam(required=true) Long toColumn, @RequestParam(required=true) Integer toPosition) throws Exception {
		try{
			DashboardItem itemToMove = dashboardItemDao.findByPrimaryKey(item);
			Integer currentPosition = itemToMove.getItemOrder();
			List<DashboardItem> items = dashboardItemDao.findWhereDashboardColIdEquals(itemToMove.getDashboardColId());
			
			if(items.size() > 0) {
				for(int i = 0 ; i < items.size(); i++) {
					if(items.get(i).getItemOrder() > currentPosition) {
						items.get(i).setItemOrder(items.get(i).getItemOrder() - 1);
						dashboardItemDao.update(items.get(i).createPk(), items.get(i));
					}
				}
			}
			itemToMove.setItemOrder(toPosition+1);
			itemToMove.setDashboardColId(toColumn);
			dashboardItemDao.update(itemToMove.createPk(), itemToMove);
			
			items = dashboardItemDao.findWhereDashboardColIdEquals(toColumn);
			if(items.size() > 0) {
				for(int i = 0 ; i < items.size(); i++) {
					if(items.get(i).getItemOrder() > toPosition+1) {
						items.get(i).setItemOrder(items.get(i).getItemOrder() + 1);
						dashboardItemDao.update(items.get(i).createPk(), items.get(i));
					}
				}
			}
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(item));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}
	
	@Autowired
	public void setDashboardDao(DashboardDao dashboardDao) {
		this.dashboardDao = dashboardDao;
	}
	
	@Autowired
	public void setDashboardColDao(DashboardColDao dashboardColDao) {
		this.dashboardColDao = dashboardColDao;
	}
	
	@Autowired
	public void setDashboardItemDao(DashboardItemDao dashboardItemDao) {
		this.dashboardItemDao = dashboardItemDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}