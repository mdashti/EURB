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
		%>
		<script type="text/javascript">

			EURB.ReportDesign = {};
			EURB.ReportDesign.selectedDesign = '<%=selectedDesign %>';
			EURB.ReportDesign.selectedVersion = '<%=selectedVersion %>';
			
			EURB.ReportDesign.reportTab = '<spring:message code="eurb.app.builder.report.design.reportTab" />';
			EURB.ReportDesign.reportChart = '<spring:message code="eurb.app.builder.report.design.reportChart" />';
			
			
			
			EURB.ReportDataset = {};
			EURB.ReportDataset.searchAction = '<spring:url value="/builder/report/reportDatasetSearch.spy" />';
			EURB.ReportDataset.removeAction = '<spring:url value="/builder/report/reportDatasetRemove.spy" />';
			EURB.ReportDataset.storeAction = '<spring:url value="/builder/report/reportDatasetStore.spy" />';
			
			EURB.ReportDataset.Table = '<spring:message code="eurb.app.builder.report.dataset.table" />';
			EURB.ReportDataset.Order = '<spring:message code="eurb.app.builder.report.dataset.order" />';
			EURB.ReportDataset.title = '<spring:message code="eurb.app.builder.report.dataset.title" />';
			
			
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
			EURB.ReportChart.xAxis = '<spring:message code="eurb.app.builder.report.chart.xAxis" />';
			EURB.ReportChart.AxisColumn = '<spring:message code="eurb.app.builder.report.chart.axisColumn" />';
			EURB.ReportChart.AxisTitle = '<spring:message code="eurb.app.builder.report.chart.axisTitle" />';
			EURB.ReportChart.yAxis = '<spring:message code="eurb.app.builder.report.chart.yAxis" />';
			EURB.ReportChart.sum = '<spring:message code="eurb.app.builder.report.chart.sum" />';
			EURB.ReportChart.count = '<spring:message code="eurb.app.builder.report.chart.count" />';
			EURB.ReportChart.average = '<spring:message code="eurb.app.builder.report.chart.average" />';
			EURB.ReportChart.Aggregation = '<spring:message code="eurb.app.builder.report.chart.aggregation" />';
			
			
			
			
			
			
			
			
			
			EURB.ReportDesign.comboRenderer = function(combo){
			    return function(value){
			        var record = combo.findRecord(combo.valueField, value);
			        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
			    }
			};

			EURB.ReportDataset.tableCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'id',
			            'mappedName'
			        ],
			        data: ${tableMappingComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'mappedName',
			    forceSelection: true,
			    allowBlank: false
			});
			
			
			EURB.ReportColumn.columnCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'id',
			            'title',
			            'datasetId',
			            'mappedName',
			            'tableMappedName'
			        ],
			        data: ${columnMappingComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'title',
			    forceSelection: true,
			    allowBlank: true,
			    listeners:{
			    	select: function(combo,record,index){
			    		rform = EURB.ReportColumn.reportColumnGrid.recordForm.form.getForm();
			    		rform.items.itemAt(1).setValue(record.get('mappedName'));
			    	}
			    }
			});
			
			EURB.ReportColumn.formulaColumnCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    fieldLabel: EURB.ReportColumn.formulaColumns,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
						'id',
						'title',
						'datasetId',
						'mappedName',
						'tableMappedName'
			        ],
			        data: ${columnMappingComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'title',
			    forceSelection: true,
			    allowBlank: true,
			    listeners:{
			    	select: function(combo,record,index){
			    		addText('[' + record.get('mappedName') + '-' + record.get('id') + ']');
			    	}
			    }
			});

			
			EURB.ReportFilter.columnCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
						'id',
						'title',
						'datasetId',
						'mappedName',
						'tableMappedName'
			        ],
			        data: ${columnMappingComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'title',
			    forceSelection: true,
			    allowBlank: false
			});
			
			EURB.ReportFilter.joinColumnCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
						'id',
						'title',
						'datasetId',
						'mappedName',
						'tableMappedName'
			        ],
			        data: ${columnMappingComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'title',
			    forceSelection: true,
			    allowBlank: false,
			    listeners:{
			    	select: function(combo,record,index){
			    		form = EURB.ReportFilter.reportFilterGrid.recordForm.form.getForm();
			    		form.findField('operand1DatasetId').setValue(record.get('datasetId'));
			    	}
			    }
			});
			
			EURB.ReportChart.xAxisColumnCombo = new Ext.form.ComboBox({
				fieldLabel:EURB.ReportChart.AxisColumn,
				hiddenName:'xColumnMapping',
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
						'id',
						'title',
						'datasetId',
						'mappedName',
						'tableMappedName'
			        ],
			        data: ${columnMappingComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'title',
			    forceSelection: true,
			    allowBlank: false,
			    listeners:{
			    	select: function(combo,record,index){
			    		dsField = EURB.ReportChart.reportChartGrid.axisForm.getForm().findField('xDataset');
			    		dsField.setValue(record.get('datasetId'));
			    	}
			    }
			});
			
			EURB.ReportChart.yAxisColumnCombo = new Ext.form.ComboBox({
				fieldLabel:EURB.ReportChart.AxisColumn,
				hiddenName:'yColumnMapping',
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
						'id',
						'title',
						'datasetId',
						'mappedName',
						'tableMappedName'
			        ],
			        data: ${columnMappingComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'title',
			    forceSelection: true,
			    allowBlank: false,
			    listeners:{
			    	select: function(combo,record,index){
			    		dsField = EURB.ReportChart.reportChartGrid.axisForm.getForm().findField('yDataset');
			    		dsField.setValue(record.get('datasetId'));
			    	}
			    }
			});

			/* updateReportColumnComboContent = function(){
				var o = {
					 url:EURB.ReportColumn.updateComboContent
					,method:'post'
					,callback:updateComboContentCallback
					,scope:this
					,params:{
						cmd: 'storeData',
						reportDesign : EURB.ReportDesign.selectedDesign,
						reportVersion: EURB.ReportDesign.selectedVersion
					}
				};
				Ext.Ajax.request(o);
			};
			
			updateComboContentCallback = function(options, success, response) {
				if(true !== success) {
					this.showError(response.responseText);
					return;
				}
				try {
					var o = Ext.decode(response.responseText);
				}
				catch(e) {
					this.showError(response.responseText, EURB.unableToDecodeJSON);
					return;
				}
				if(true !== o.success) {
					this.showError(o.error || EURB.unknownError);
					return;
				}
				/* EURB.ReportDesign.columnCombo.bindStore(new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'id',
			            'mappedName'
			        ],
			        data: o.data
			    }));
				EURB.ReportColumn.reportColumnGrid.colModel.getColumnAt(0).setEditor(EURB.ReportDesign.columnCombo); 
				window.location.href = EURB.baseURL+'builder/report/report'+EURB.ReportDesign.selectedDesign+'-design.spy';
			};
			 */
			
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
		<script src="${resourcesUrl}/js/app/builder/report/report-column.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/report-filter.js"></script>
		
		<script src="${resourcesUrl}/js/app/builder/report/report-chart.js"></script>
		
		
		<script src="${resourcesUrl}/js/app/builder/report/report-design.js"></script>
		
	</body>
</html>