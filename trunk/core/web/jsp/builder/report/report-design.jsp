<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<spring:eval expression="@applicationProps['application.version']" var="appVersion"/>
<spring:eval expression="@applicationProps['application.mode']" var="appMode"/>
<spring:url value="/resources-{appVersion}" var="resourcesUrl">
    <spring:param name="appVersion" value="${appVersion}"/>
</spring:url>
<spring:url value="/" var="baseUrl"/>
<html>
	<head>
		<jsp:include page="/WEB-INF/include/common-header.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App custom css -->
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/builder/report/report-design.css" />
		
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<% 	String selectedDesign = (String) request.getAttribute("report");
			String selectedVersion = (String) request.getAttribute("version"); 
			String name = (String) request.getAttribute("name");
		%>
		
		
		<script type="text/javascript">

			EURB.ReportDesign = {};
			EURB.ReportDesign.selectedDesign = '<%=selectedDesign %>';
			EURB.ReportDesign.selectedVersion = '<%=selectedVersion %>';
			EURB.ReportDesign.name = '<%= name %>';
			
			EURB.ReportDesign.reportTab = '<spring:message code="eurb.app.builder.report.design.reportTab" />';
			EURB.ReportDesign.reportChart = '<spring:message code="eurb.app.builder.report.design.reportChart" />';
			EURB.ReportDesign.NameField = '<spring:message code="eurb.app.builder.report.name" />';
			EURB.ReportDesign.DescriptionField = '<spring:message code="eurb.app.builder.report.description" />';
			EURB.ReportDesign.CategoryField = '<spring:message code="eurb.app.builder.report.category" />';
			EURB.ReportDesign.Info = '<spring:message code="eurb.app.builder.report.design.info" />';
			
			EURB.ReportDesign.searchAction = '<spring:url value="/builder/report/reportSearch.spy" />';
			EURB.ReportDesign.storeAction = '<spring:url value="/builder/report/reportStore.spy" />';
			
			EURB.ReportDesign.runReport = '<spring:message code="eurb.app.builder.report.design.runReport" />';
			EURB.ReportDesign.saveDesign = '<spring:message code="eurb.app.builder.report.design.saveDesign" />';
			EURB.ReportDesign.returnToList = '<spring:message code="eurb.app.builder.report.design.returnToList" />';
			
			
			EURB.ReportDataset = {};
			EURB.ReportDataset.searchAction = '<spring:url value="/builder/report/reportDatasetSearch.spy" />';
			EURB.ReportDataset.removeAction = '<spring:url value="/builder/report/reportDatasetRemove.spy" />';
			EURB.ReportDataset.storeAction = '<spring:url value="/builder/report/reportDatasetStore.spy" />';
			
			EURB.ReportDataset.Table = '<spring:message code="eurb.app.builder.report.dataset.table" />';
			EURB.ReportDataset.Order = '<spring:message code="eurb.app.builder.report.dataset.order" />';
			EURB.ReportDataset.title = '<spring:message code="eurb.app.builder.report.dataset.title" />';
			EURB.ReportDataset.BaseReportCategory = '<spring:message code="eurb.app.builder.report.dataset.category" />';
			EURB.ReportDataset.BaseReportDesign = '<spring:message code="eurb.app.builder.report.dataset.report" />';
			EURB.ReportDataset.Type = '<spring:message code="eurb.app.builder.report.dataset.type" />';
			
			
			EURB.ReportColumn = {};
			EURB.ReportColumn.searchAction = '<spring:url value="/builder/report/reportColumnSearch.spy" />';
			EURB.ReportColumn.removeAction = '<spring:url value="/builder/report/reportColumnRemove.spy" />';
			EURB.ReportColumn.storeAction = '<spring:url value="/builder/report/reportColumnStore.spy" />';
			EURB.ReportColumn.updateComboContent = '<spring:url value="/builder/report/reportColumnComboContent.spy" />';
			EURB.ReportColumn.columnSearchAction = '<spring:url value="/management/mapping/column/mappedColumnSearch.spy" />';
			EURB.ReportColumn.editFormulaAction = '<spring:url value="/builder/report/reportColumnFormulaStore.spy" />';
			
			EURB.ReportColumn.title = '<spring:message code="eurb.app.builder.report.column.title" />';
			
			EURB.ReportColumn.Column = '<spring:message code="eurb.app.builder.report.column.column" />';
			EURB.ReportColumn.ColumnHeader = '<spring:message code="eurb.app.builder.report.column.header" />';
			EURB.ReportColumn.ColOrder = '<spring:message code="eurb.app.builder.report.column.colorder" />';
			EURB.ReportColumn.SortOrder = '<spring:message code="eurb.app.builder.report.column.sortorder" />';
			EURB.ReportColumn.SortType = '<spring:message code="eurb.app.builder.report.column.sorttype" />';
			EURB.ReportColumn.GroupLevel = '<spring:message code="eurb.app.builder.report.column.grouplevel" />';
			EURB.ReportColumn.ColumnWidth = '<spring:message code="eurb.app.builder.report.column.width" />';
			EURB.ReportColumn.ColumnAlign = '<spring:message code="eurb.app.builder.report.column.align" />';
			EURB.ReportColumn.ColumnDir = '<spring:message code="eurb.app.builder.report.column.dir" />';
			EURB.ReportColumn.Formula = '<spring:message code="eurb.app.builder.report.column.formula" />';
			EURB.ReportColumn.IsCustom = '<spring:message code="eurb.app.builder.report.column.isCustom" />';
			
			EURB.ReportColumn.leftAlign = '<spring:message code="eurb.app.builder.report.column.leftAlign" />';
			EURB.ReportColumn.middleAlign = '<spring:message code="eurb.app.builder.report.column.middleAlign" />';
			EURB.ReportColumn.rightAlign = '<spring:message code="eurb.app.builder.report.column.rightAlign" />';
			EURB.ReportColumn.ltrDir = '<spring:message code="eurb.app.builder.report.column.ltrDir" />';
			EURB.ReportColumn.rtlDir = '<spring:message code="eurb.app.builder.report.column.rtlDir" />';
			EURB.ReportColumn.noSort = '<spring:message code="eurb.app.builder.report.column.noSort" />';
			EURB.ReportColumn.ascendingSort = '<spring:message code="eurb.app.builder.report.column.ascSort" />';
			EURB.ReportColumn.descendingSort = '<spring:message code="eurb.app.builder.report.column.descSort" />';
			
			EURB.ReportColumn.formula = '<spring:message code="eurb.app.builder.report.column.formulaTitle" />';
			EURB.ReportColumn.formulaEditor = '<spring:message code="eurb.app.builder.report.column.formulaEditor" />';
			EURB.ReportColumn.formulaColumns = '<spring:message code="eurb.app.builder.report.column.formulaColumns" />';
			
			
			EURB.ReportFilter = {};
			
			EURB.ReportFilter.searchAction = '<spring:url value="/builder/report/reportFilterSearch.spy" />';
			EURB.ReportFilter.storeAction = '<spring:url value="/builder/report/reportFilterStore.spy" />';
			EURB.ReportFilter.removeAction = '<spring:url value="/builder/report/reportFilterRemove.spy" />';
			
			EURB.ReportFilter.title = '<spring:message code="eurb.app.builder.report.filter.title" />';
			EURB.ReportFilter.Column = '<spring:message code="eurb.app.builder.report.filter.column" />';
			EURB.ReportFilter.Operator = '<spring:message code="eurb.app.builder.report.filter.operator" />';
			EURB.ReportFilter.Operand1 = '<spring:message code="eurb.app.builder.report.filter.operand1" />';
			EURB.ReportFilter.Operand2 = '<spring:message code="eurb.app.builder.report.filter.operand2" />';
			EURB.ReportFilter.Operand1Column = '<spring:message code="eurb.app.builder.report.filter.operand1Column" />';
			EURB.ReportFilter.IsJoin = '<spring:message code="eurb.app.builder.report.filter.isJoin" />';
			
			EURB.ReportFilter.equal = '<spring:message code="eurb.app.builder.report.filter.equal" />';
			EURB.ReportFilter.smallerThan = '<spring:message code="eurb.app.builder.report.filter.smallerThan" />';
			EURB.ReportFilter.biggerThan = '<spring:message code="eurb.app.builder.report.filter.biggerThan" />';
			EURB.ReportFilter.notEqual = '<spring:message code="eurb.app.builder.report.filter.notEqual" />';
			EURB.ReportFilter.between = '<spring:message code="eurb.app.builder.report.filter.between" />';
			EURB.ReportFilter.like = '<spring:message code="eurb.app.builder.report.filter.like" />';
			EURB.ReportFilter.notNull = '<spring:message code="eurb.app.builder.report.filter.notNull" />';
			EURB.ReportFilter.nul = '<spring:message code="eurb.app.builder.report.filter.nul" />';
			
			
			EURB.ReportChart = {};
			EURB.ReportChart.searchAction = '<spring:url value="/builder/report/reportChartSearch.spy" />';
			EURB.ReportChart.removeAction = '<spring:url value="/builder/report/reportChartRemove.spy" />';
			EURB.ReportChart.storeAction = '<spring:url value="/builder/report/reportChartStore.spy" />';
			EURB.ReportChart.editAxisAction = '<spring:url value="/builder/report/chartAxisStore.spy" />';
			EURB.ReportChart.chartAxisSearch = '<spring:url value="/builder/report/chartAxisSearch.spy" />';
				
			EURB.ReportChart.Name = '<spring:message code="eurb.app.builder.report.chart.name" />';
			EURB.ReportChart.Type = '<spring:message code="eurb.app.builder.report.chart.type" />';
			EURB.ReportChart.title = '<spring:message code="eurb.app.builder.report.chart.title" />';
			
			EURB.ReportChart.line = '<spring:message code="eurb.app.builder.report.chart.line" />';
			EURB.ReportChart.column = '<spring:message code="eurb.app.builder.report.chart.column" />';
			EURB.ReportChart.bar = '<spring:message code="eurb.app.builder.report.chart.bar" />';
			EURB.ReportChart.pie = '<spring:message code="eurb.app.builder.report.chart.pie" />';
			EURB.ReportChart.gauge = '<spring:message code="eurb.app.builder.report.chart.gauge" />';
			EURB.ReportChart.xAxis = '<spring:message code="eurb.app.builder.report.chart.xAxis" />';
			EURB.ReportChart.AxisColumn = '<spring:message code="eurb.app.builder.report.chart.axisColumn" />';
			EURB.ReportChart.AxisTitle = '<spring:message code="eurb.app.builder.report.chart.axisTitle" />';
			EURB.ReportChart.yAxis = '<spring:message code="eurb.app.builder.report.chart.yAxis" />';
			EURB.ReportChart.sum = '<spring:message code="eurb.app.builder.report.chart.sum" />';
			EURB.ReportChart.count = '<spring:message code="eurb.app.builder.report.chart.count" />';
			EURB.ReportChart.average = '<spring:message code="eurb.app.builder.report.chart.average" />';
			EURB.ReportChart.Aggregation = '<spring:message code="eurb.app.builder.report.chart.aggregation" />';
			EURB.ReportChart.titleFont = '<spring:message code="eurb.app.builder.report.chart.titleFont" />';
			EURB.ReportChart.titleSize = '<spring:message code="eurb.app.builder.report.chart.titleSize" />';
			EURB.ReportChart.titleColor = '<spring:message code="eurb.app.builder.report.chart.titleColor" />';
			EURB.ReportChart.xAxisFont = '<spring:message code="eurb.app.builder.report.chart.xAxisFont" />';
			EURB.ReportChart.xAxisSize = '<spring:message code="eurb.app.builder.report.chart.xAxisSize" />';
			EURB.ReportChart.xAxisColor = '<spring:message code="eurb.app.builder.report.chart.xAxisColor" />';
			EURB.ReportChart.yAxisFont = '<spring:message code="eurb.app.builder.report.chart.yAxisFont" />';
			EURB.ReportChart.yAxisSize = '<spring:message code="eurb.app.builder.report.chart.yAxisSize" />';
			EURB.ReportChart.yAxisColor = '<spring:message code="eurb.app.builder.report.chart.yAxisColor" />';
			EURB.ReportChart.chartColor = '<spring:message code="eurb.app.builder.report.chart.chartColor" />';
			
			EURB.ReportChart.saveAll = '<spring:message code="eurb.app.builder.report.chart.saveAll" />';
			EURB.ReportChart.cancelAll = '<spring:message code="eurb.app.builder.report.chart.cancelAll" />';
			
			EURB.GroupAggregation = {};
			EURB.GroupAggregation.AggregatedColumn = '<spring:message code="eurb.app.builder.report.aggregation.column" />';
			EURB.GroupAggregation.AggregateFunction = '<spring:message code="eurb.app.builder.report.aggregation.function" />';
			EURB.GroupAggregation.sum = '<spring:message code="eurb.app.builder.report.aggregation.sum" />';
			EURB.GroupAggregation.count = '<spring:message code="eurb.app.builder.report.aggregation.count" />';
			EURB.GroupAggregation.average = '<spring:message code="eurb.app.builder.report.aggregation.average" />';
			EURB.GroupAggregation.Place = '<spring:message code="eurb.app.builder.report.aggregation.place" />';
			EURB.GroupAggregation.down = '<spring:message code="eurb.app.builder.report.aggregation.down" />';
			EURB.GroupAggregation.up = '<spring:message code="eurb.app.builder.report.aggregation.up" />';
			
			EURB.GroupAggregation.searchAction = '<spring:url value="/builder/report/groupAggregationSearch.spy" />';
			EURB.GroupAggregation.storeAggregationAction = '<spring:url value="/builder/report/groupAggregationStore.spy" />';
			
			EURB.ReportDesign.categoryStore = new Ext.data.ArrayStore({
		        id: 0,
		        fields: [
		            'id',
		            'name'
		        ],
		        data: ${categoriesComboContent}
		    });
			
			EURB.ReportDataset.reportStore = new Ext.data.ArrayStore({
		        id: 1,
		        fields: [
		            'id',
		            'versionId',
		            'name',
		            'categoryId'
		        ],
		        data: ${reportDesignsComboContent}
		    });
			
			
			EURB.ReportDataset.tableMappingStore =  new Ext.data.ArrayStore({
		        id: 2,
		        fields: [
		            'id',
		            'mappedName'
		        ],
		        data: ${tableMappingComboContent}
		    });
			
			
			EURB.ReportDesign.columnMappingStore = new Ext.data.ArrayStore({
		        id: 3,
		        fields: [
		            'id',
		            'title',
		            'datasetId',
		            'mappedName',
		            'tableMappedName',
		            'columnName',
		            'type'
		        ],
		        data: ${columnMappingComboContent}
		    });
			

			EURB.ReportDesign.comboRenderer = function(combo){
			    return function(value){
			        var record = combo.findRecord(combo.valueField, value);
			        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
			    }
			};	
						
			updateReportColumnComboContent = function(){
				 window.location.href = EURB.baseURL+'builder/report/report'+EURB.ReportDesign.selectedDesign+'-design.spy';
			}
			
			hideFormField = function(field){
				field.hide();
				field.container.up('div.x-form-item').setStyle('display', 'none');
			}

			showFormField = function(field){
				field.show();
				field.container.up('div.x-form-item').setStyle('display', 'block');
			}

		</script>
		<script src="${resourcesUrl}/js/app/builder/report/formula-editor.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/report-dataset.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/group-aggregation.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/report-column.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/report-filter.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/report-info.js"></script>
		
		<script src="${resourcesUrl}/js/app/builder/report/report-chart.js"></script>
		
		
		<script src="${resourcesUrl}/js/app/builder/report/report-design.js"></script>
		
	</body>
</html>