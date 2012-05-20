<%@page import="org.apache.commons.lang.StringUtils"%>
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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/management/mapping/column.css" />
		<style type="text/css">
		</style>
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<script type="text/javascript">
		if(typeof EURB == 'undefined') {
			EURB = {};
		}
		EURB.westPanel = {
	        border: true,
	        layout: 'fit',
	        id: 'viewport-info-panel',
	        region:'west',
	        cls: 'info-panel',
	        split:true,
	        collapsible: true,
	        width: 210,
	        listeners: {
	        	afterlayout: function(thiz, adjWidth, adjHeight, rawWidth, rawHeight ) {
	        		if(EURB.Column.mappingPropertyGrid && EURB.Column.mappingPropertyGrid.colModel.store && EURB.Column.mappingPropertyGrid.colModel.store) {
	        			EURB.Column.mappingPropertyGrid.colModel.store.store.sort('name','ASC');
	        		}
	        	}
	        }
	    };
		</script>
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.Column = {};
			<% 
			String selectedDbConfig = (String) request.getAttribute("dbconfig");
			String selectedTable = (String) request.getAttribute("table");
			%>
			EURB.Column.searchAction = '<spring:url value="/management/mapping/column/columnSearch.spy" />';
			EURB.Column.storeAction = '<spring:url value="/management/mapping/column/columnStore.spy" />';
			EURB.Column.removeAction = '<spring:url value="/management/mapping/column/columnRemove.spy" />';
			EURB.Column.activateAction = '<spring:url value="/management/mapping/column/columnActivate.spy" />';
			EURB.Column.deactivateAction = '<spring:url value="/management/mapping/column/columnDeactivate.spy" />';
			EURB.Column.moveColumnAction = '<spring:url value="/management/mapping/column/columnMove.spy" />';
			EURB.Column.tableSearchAction = '<spring:url value="/management/mapping/table/tableSearch.spy" />';

			EURB.Column.columnName = '<spring:message code="eurb.app.management.column.columnName" />';
			EURB.Column.mappedName = '<spring:message code="eurb.app.management.column.mappedName" />';
			EURB.Column.activeForManager = '<spring:message code="eurb.app.management.column.activeForManager" />';
			EURB.Column.activeForUser = '<spring:message code="eurb.app.management.column.activeForUser" />';
			EURB.Column.forManager = '<spring:message code="eurb.app.management.column.forManager" />';
			EURB.Column.forUser = '<spring:message code="eurb.app.management.column.forUser" />';
			EURB.Column.colTypeName = '<spring:message code="eurb.app.management.column.colTypeName" />';
			EURB.Column.formatPattern = '<spring:message code="eurb.app.management.column.formatPattern" />';
			EURB.Column.referencedIdCol = '<spring:message code="eurb.app.management.column.referencedIdCol" />';
			EURB.Column.referencedTable = '<spring:message code="eurb.app.management.column.referencedTable" />';
			EURB.Column.referencedValueCol = '<spring:message code="eurb.app.management.column.referencedValueCol" />';
			EURB.Column.staticMapping = '<spring:message code="eurb.app.management.column.staticMapping" />';
			EURB.Column.mappingType = '<spring:message code="eurb.app.management.column.mappingType" />';
			EURB.Column.mappingTypeStatic = '<spring:message code="eurb.app.management.column.mappingTypeStatic" />';
			EURB.Column.mappingTypeTable = '<spring:message code="eurb.app.management.column.mappingTypeTable" />';
			EURB.Column.mappingTypeNone = '<spring:message code="eurb.app.management.column.mappingTypeNone" />';
			EURB.Column.colDataType = '<spring:message code="eurb.app.management.column.colDataType" />';
			EURB.Column.colOrder = '<spring:message code="eurb.app.management.column.colOrder" />';
			EURB.Column.saveRecordFisrt = '<spring:message code="eurb.app.management.column.saveRecordFisrt" />';
			EURB.Column.cannotMoveFirstRecordUpward = '<spring:message code="eurb.app.management.column.cannotMoveFirstRecordUpward" />';
			EURB.Column.cannotMoveLastRecordDownward = '<spring:message code="eurb.app.management.column.cannotMoveLastRecordDownward" />';
			EURB.Column.mappingValues = '<spring:message code="eurb.app.management.column.mappingValues" />';
			EURB.Column.saveAll = '<spring:message code="eurb.app.management.column.saveAll" />';
			EURB.Column.cancelAll = '<spring:message code="eurb.app.management.column.cancelAll" />';
			EURB.Column.origVal = '<spring:message code="eurb.app.management.column.origVal" />';
			EURB.Column.mappedVal = '<spring:message code="eurb.app.management.column.mappedVal" />';

			EURB.Column.selectedDbConfig = '<%=selectedDbConfig%>';
			EURB.Column.selectedTable = '<%=selectedTable%>';
			// create the combo instance
			EURB.Column.dbConfigCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'dbConfId',
			            'dbConfName'
			        ],
			        data: ${dbConfigComboContent}
			    }),
			    <%=StringUtils.isEmpty(selectedDbConfig) ? "" : "value: "+selectedDbConfig+","%>
			    forceSelection: true,
			    valueField: 'dbConfId',
			    displayField: 'dbConfName',
			    listeners: {
			    	'select' : function(thiz, newValue, oldValue) {
			    		window.location.href = EURB.baseURL+'management/mapping/db'+newValue.id+'-table-column.spy';
			    	}
			    }
			});
			
			EURB.Column.tableCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'tableId',
			            'tableName'
			        ],
			        data: ${tableComboContent}
			    }),
			    <%=StringUtils.isEmpty(selectedTable) ? "" : "value: "+selectedTable+","%>
			    forceSelection: true,
			    valueField: 'tableId',
			    displayField: 'tableName',
			    listeners: {
			    	'select' : function(thiz, newValue, oldValue) {
			    		window.location.href = EURB.baseURL+'management/mapping/db<%=selectedDbConfig%>-table'+newValue.id+'-column.spy';
			    	}
			    }
			});
		</script>
		<script src="${resourcesUrl}/js/app/management/mapping/column.js"></script>
	</body>
</html>