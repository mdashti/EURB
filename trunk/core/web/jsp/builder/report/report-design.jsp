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
			
			EURB.ReportColumn.Column = '<spring:message code="eurb.app.builder.report.column.column" />';
			EURB.ReportColumn.ColumnHeader = '<spring:message code="eurb.app.builder.report.column.header" />';
			EURB.ReportColumn.ColOrder = '<spring:message code="eurb.app.builder.report.column.colorder" />';
			EURB.ReportColumn.SortOrder = '<spring:message code="eurb.app.builder.report.column.sortorder" />';
			EURB.ReportColumn.SortType = '<spring:message code="eurb.app.builder.report.column.sorttype" />';
			EURB.ReportColumn.GroupLevel = '<spring:message code="eurb.app.builder.report.column.grouplevel" />';
			EURB.ReportColumn.ColumnWidth = '<spring:message code="eurb.app.builder.report.column.width" />';
			EURB.ReportColumn.ColumnAlign = '<spring:message code="eurb.app.builder.report.column.align" />';
			EURB.ReportColumn.ColumnDir = '<spring:message code="eurb.app.builder.report.column.dir" />';
			
			EURB.ReportColumn.title = '<spring:message code="eurb.app.builder.report.column.title" />';
			
			
			EURB.ReportColumn.columnSearchAction = '<spring:url value="/management/mapping/column/mappedColumnSearch.spy" />';
			
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
			    displayField: 'mappedName'
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
			            'mappedName'
			        ],
			        data: ${columnMappingComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'mappedName'
			});

			updateReportColumnComboContent = function(){
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
				EURB.ReportColumn.columnCombo.bindStore(new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'id',
			            'mappedName'
			        ],
			        data: o.data
			    }));
				EURB.ReportColumn.reportColumnGrid.colModel.getColumnAt(0).setEditor(EURB.ReportColumn.columnCombo);
				window.location.href = EURB.baseURL+'builder/report/report'+EURB.ReportDesign.selectedDesign+'-design.spy';
			};
			
			
			
		</script>
		<script  src="${resourcesUrl}/js/app/builder/report/report-dataset.js"></script>
		<script  src="${resourcesUrl}/js/app/builder/report/report-column.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/report-design.js"></script>
	</body>
</html>