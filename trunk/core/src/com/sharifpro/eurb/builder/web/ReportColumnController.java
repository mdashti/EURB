package com.sharifpro.eurb.builder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportColumnPk;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportColumnController {

	private ReportDesignDao reportDesignDao;
	private ReportColumnDao reportColumnDao;
	private ColumnMappingDao columnMappingDao;
	private ReportDatasetDao reportDatasetDao;

	private JsonUtil jsonUtil;

	@RequestMapping(value="/builder/report/reportColumnSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=true) Long reportDesign) throws Exception {

		try{
			ReportDesign design = null;
			if(reportDesign != null){
				design = reportDesignDao.findByPrimaryKey(reportDesign);
			}
			else{
				throw new NullPointerException("Report Design is null");
			}
			List<ReportColumn> reportColumns = new ArrayList<ReportColumn>(0);
			int totalCount = 0;

			if(design != null){
				reportColumns = reportColumnDao.findAll(design);
				totalCount = reportColumnDao.countAll(design);
			}

			return JsonUtil.getSuccessfulMap(reportColumns, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}


	@RequestMapping(value="/builder/report/reportColumnStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data, @RequestParam(required=true) Long reportDesign
			, @RequestParam(required=true) Long reportVersion) throws Exception {
		try{

			List<ReportColumn> reportColumns = jsonUtil.getListFromRequest(data, ReportColumn.class);

			List<Long> insertIds = new ArrayList<Long>(reportColumns.size());
			ReportColumnPk pk;
			for(ReportColumn reportColumn : reportColumns) {
				reportColumn.setDesignId(reportDesign);
				reportColumn.setDesignVersionId(reportVersion);
				if(reportColumn.isNewRecord()) {
					//@ TODO : set col  type correctly
					reportColumn.setColType(1);
					reportColumn.setCustom(false);
					pk = reportColumnDao.insert(reportColumn);
				} else {
					pk = reportColumn.createPk();
					//@ TODO : set col  type correctly
					reportColumn.setColType(1);
					reportColumnDao.update(pk,reportColumn);
				}
				insertIds.add(pk.getId());
			}

			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/reportColumnFormulaStore.spy")
	public  @ResponseBody Map<String,? extends Object> storeFormula(@RequestParam(required=true) Long id, @RequestParam(required=true) String columnHeader
			, @RequestParam(required=true) String formula) throws Exception {
		ReportColumnPk pk;
		List<Long> insertIds = new ArrayList<Long>(1);
		try{

			ReportColumn reportColumn = reportColumnDao.findWhereIdEquals(id).get(0);

			reportColumn.setColumnHeader(columnHeader);
			reportColumn.setFormula(formula);
			reportColumn.setCustom(true);

			pk = reportColumn.createPk();


			reportColumnDao.update(pk,reportColumn);
			insertIds.add(pk.getId());

			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/reportColumnRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ReportColumnPk> pkList = new ArrayList<ReportColumnPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new ReportColumnPk(new Long(id)));
			}
			reportColumnDao.deleteAll(pkList);

			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}
	
	/*
	@RequestMapping(value="/builder/report/reportColumnComboContent.spy")
	public @ResponseBody Map<String,? extends Object> getComboContent(@RequestParam(required=true) Long reportDesign) throws Exception {
		
		try{
			ReportDesign design = null;
			if(reportDesign != null){
				design = reportDesignDao.findByPrimaryKey(reportDesign);
			}
			else{
				throw new NullPointerException("Report Design is null");
			}
			List<ColumnMapping> columnMappings = columnMappingDao.findAllMapped(design);
			Object[][] columnMappingArr = new Object[columnMappings.size()][2];
			for(int i = 0; i < columnMappings.size(); i++){
				columnMappingArr[i][0] = columnMappings.get(i).getId();
				columnMappingArr[i][1] = columnMappings.get(i).getMappedName();
			}
			
			Map<String,Object> modelMap = new HashMap<String,Object>(3);
			modelMap.put("totalCount", columnMappings.size());
			modelMap.put("data", columnMappingArr);
			modelMap.put("success", true);

			return modelMap;

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}
*/

	@Autowired
	public void setColumnMappingDao(ColumnMappingDao columnMappingDao) {
		this.columnMappingDao = columnMappingDao;
	}

	@Autowired
	public void setReportDatasetDao(ReportDatasetDao reportDatasetDao) {
		this.reportDatasetDao = reportDatasetDao;
	}

	@Autowired
	public void setReportColumnDao(ReportColumnDao reportColumnDao) {
		this.reportColumnDao = reportColumnDao;
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