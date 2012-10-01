package com.sharifpro.eurb.dashboard.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.dashboard.dao.DashboardColDao;
import com.sharifpro.eurb.dashboard.dao.DashboardDao;
import com.sharifpro.eurb.dashboard.dao.DashboardItemDao;
import com.sharifpro.eurb.dashboard.exceptions.DashboardDaoException;
import com.sharifpro.eurb.dashboard.exceptions.PersistableObjectDaoException;
import com.sharifpro.eurb.dashboard.model.Dashboard;
import com.sharifpro.eurb.dashboard.model.DashboardCol;
import com.sharifpro.eurb.dashboard.model.DashboardColPk;
import com.sharifpro.eurb.dashboard.model.DashboardItem;
import com.sharifpro.eurb.dashboard.model.DashboardItemPk;
import com.sharifpro.eurb.dashboard.model.DashboardPk;
import com.sharifpro.util.PropertyProvider;
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
	
	private ReportDesignDao reportDesignDao;
	
	private JsonUtil jsonUtil;

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard.spy")
	public ModelAndView executeDashboardSpy() throws Exception {
		return executeDashboardSpy(null);
	}

	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard{dash}.spy")
	public ModelAndView executeDashboardSpy(@PathVariable Long dash) throws Exception {
		ModelAndView mv = executeDashboardGeneral(dash);
		List<Dashboard> dashboardList = dashboardDao.findAll();
		Object[][] dashboardArr = new Object[dashboardList.size()][2];
		
		for(int i = 0; i < dashboardList.size(); i++) {
			dashboardArr[i][0] = dashboardList.get(i).getId();
			dashboardArr[i][1] = dashboardList.get(i).getTitle();
		}
		mv.addObject("dashboardComboContent", jsonUtil.getJSONFromObject(dashboardArr));
		mv.addObject("isDesignMode", Boolean.FALSE);
		
		return mv;
	}
	
	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard-design.spy")
	public ModelAndView executeDashboardDesignSpy() throws Exception {
		return executeDashboardDesignSpy(null);
	}
	
	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard{dash}-design.spy")
	public ModelAndView executeDashboardDesignSpy(@PathVariable Long dash) throws Exception {
		ModelAndView mv = executeDashboardGeneral(dash);
		List<Dashboard> dashboardList = dashboardDao.findAll();
		Object[][] dashboardArr = new Object[dashboardList.size()+1][2];
		dashboardArr[0][0] = -1L;
		dashboardArr[0][1] = PropertyProvider.get("eurb.app.dashboard.newdashboard");
		for(int i = 1; i <= dashboardList.size(); i++) {
			dashboardArr[i][0] = dashboardList.get(i-1).getId();
			dashboardArr[i][1] = dashboardList.get(i-1).getTitle();
		}
		mv.addObject("dashboardComboContent", jsonUtil.getJSONFromObject(dashboardArr));
		mv.addObject("isDesignMode", Boolean.TRUE);
		return mv;
	}

	private ModelAndView executeDashboardGeneral(Long dash) throws Exception{
		ModelAndView mv = new ModelAndView();
		Dashboard dashboard;
		if(dash != null) {
			dashboard = dashboardDao.findByPrimaryKey(dash);
		} else {
			dashboard = getUserDashboard();
		}
		if(dashboard != null) {
			dash = dashboard.getId();
		} else {
			throw new PersistableObjectDaoException("Dashboard not found!");
		}
		mv.addObject("userDashboardId", dash);
		mv.addObject("selectedDashboardTitle", dashboard.getTitle());
		
		List<DashboardCol> cols = dashboardColDao.findByDashboard(dash);
		
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
				dashboardItem.put("itemClosed", colItems.get(j).getItemClosed());
				dashboardItem.put("itemCollapsed", colItems.get(j).getItemCollapsed());
				dashboardItem.put("itemOrder", colItems.get(j).getItemOrder());
				if(colItems.get(j).getItemHeight() != null) {
					dashboardItem.put("height", colItems.get(j).getItemHeight());
				}
				dashboardItem.put("itemContent", colItems.get(j).getItemContent());
				if(colItems.get(j).getReportDesignId() == null ){
					dashboardItem.put("html", colItems.get(j).getItemContent());
				} else {
					dashboardItem.put("reportDesign", colItems.get(j).getReportDesignId());
					dashboardItem.put("reportChart", colItems.get(j).getReportChartId());
					dashboardItem.put("showTable", colItems.get(j).getIsShowTable());
				}
				dashboardColItems.add(dashboardItem);
			}
			
			dashboardCol.put("items", dashboardColItems);
			dashboardInitialState.add(dashboardCol);
		}
		mv.addObject("dashboardInitialState", jsonUtil.getJSONFromObject(dashboardInitialState));
		
		//report design combo content
		List<ReportDesign> reportDesigns = reportDesignDao.findAll();
		Object[][] reportsArray = new Object[reportDesigns.size()][4];
		for(int i = 0; i < reportDesigns.size(); i++){
			reportsArray[i][0] = reportDesigns.get(i).getId();
			reportsArray[i][1] = reportDesigns.get(i).getVersionId();
			reportsArray[i][2] = reportDesigns.get(i).getName();
			reportsArray[i][3] = reportDesigns.get(i).getCategoryId();
		}

		mv.addObject("reportDesignsComboContent", jsonUtil.getJSONFromObject(reportsArray));
		
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
			/*if(column == null) {
				Map<String,? extends Object> result = dashboardAddCol(dashboard);
				List<Long> affectedIds = (List<Long>) result.get("affectedIds");
				column = affectedIds.get(0);
			}*/
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
			List<DashboardItem> items = dashboardItemDao.findWhereDashboardColIdEquals(itemToMove.getDashboardColId());
			boolean afterCont = false;
			if(items.size() > 0) {
				for(int i = 0 ; i < items.size(); i++) {
					if(items.get(i).getId().equals(itemToMove.getId())) {
						afterCont = true;
						continue;
					}
					if(afterCont) {
						items.get(i).setItemOrder(i);
					} else {
						items.get(i).setItemOrder(i+1);
					}
					dashboardItemDao.update(items.get(i).createPk(), items.get(i));
				}
			}
			
			
			items = dashboardItemDao.findWhereDashboardColIdEquals(toColumn);
			items.remove(itemToMove);
			itemToMove.setItemOrder(toPosition + 1);
			itemToMove.setDashboardColId(toColumn);
			dashboardItemDao.update(itemToMove.createPk(), itemToMove);
			if(items.size() > 0) {
				for(int i = 0 ; i < items.size() + 1; i++) {
					if(i > toPosition) {
						items.get(i-1).setItemOrder(i + 1);
						dashboardItemDao.update(items.get(i-1).createPk(), items.get(i-1));
					} else { // i < toPosition
						items.get(i).setItemOrder(i + 1);
						dashboardItemDao.update(items.get(i).createPk(), items.get(i));
					}
				}
			}
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(item));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}
	
	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/dashboardEditItem.spy")
	public @ResponseBody Map<String,? extends Object> dashboardEditItem(@RequestParam(required=true) Long dashboard, @RequestParam(required=true) Long item,
			@RequestParam(required=false) Double height, @RequestParam(required=false) String title,
			@RequestParam(required=false) String content,
			@RequestParam(required=false) Long reportDesign, @RequestParam(required=false) Long reportChart) throws Exception {
		try{
			DashboardItem dashboardItem = dashboardItemDao.findByPrimaryKey(item);
			
			dashboardItem.setItemHeight(height);
			dashboardItem.setItemTitle(title);
			dashboardItem.setItemContent(content);
			dashboardItem.setReportDesignId(reportDesign);
			dashboardItem.setReportChartId(reportChart);
			
			dashboardItemDao.update(dashboardItem.createPk(), dashboardItem);
			
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(item));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}
	
	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/newDashboard.spy")
	public @ResponseBody Map<String,? extends Object> newDashboard(@RequestParam(required=false) String dashboardName) throws Exception {
		try{
			Dashboard dash = new Dashboard();
			dash.setTitle(dashboardName);
			DashboardPk pk = dashboardDao.insert(dash);
			
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(pk.getId()));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}
	
	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/editDashboard.spy")
	public @ResponseBody Map<String,? extends Object> editDashboard(@RequestParam(required=true) Long dashboard, @RequestParam(required=false) String dashboardName) throws Exception {
		try{
			Dashboard dash = dashboardDao.findByPrimaryKey(dashboard);
			dash.setTitle(dashboardName);
			dashboardDao.update(dash.createPk(),dash);
			
			return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(dash.getId()));
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}
	
	//@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType)....)")
	@RequestMapping(value="/dashboard/dashboard-design/deleteDashboard.spy")
	public @ResponseBody Map<String,? extends Object> deleteDashboard(@RequestParam(required=true) Long dashboard) throws Exception {
		try{
			if(dashboardDao.findAll().size() > 1) {
				Dashboard dash = dashboardDao.findByPrimaryKey(dashboard);
				List<DashboardCol> dashCols = dashboardColDao.findByDashboard(dashboard);
				for(DashboardCol dashCol : dashCols) {
					dashboardDelCol(dashboard, dashCol.getId());
				}
				dashboardDao.delete(dash.createPk());
				return JsonUtil.getSuccessfulMapAfterStore(Arrays.asList(dash.getId()));
			} else {
				throw new DashboardDaoException(PropertyProvider.get("eurb.app.dashboard.deletingLastDashboardNotPossible"));
			}
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
	public void setReportDesignDao(ReportDesignDao reportDesignDao) {
		this.reportDesignDao = reportDesignDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}