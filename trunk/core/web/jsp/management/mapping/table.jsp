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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/management/mapping/table.css" />
		<style type="text/css">
		</style>
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.Table = {};
			<% 
			String selectedDbConfig = (String) request.getAttribute("dbconfig");
			%>
			EURB.Table.searchAction = '<spring:url value="/management/mapping/table/tableSearch.spy" />';
			EURB.Table.storeAction = '<spring:url value="/management/mapping/table/tableStore.spy" />';
			EURB.Table.activateAction = '<spring:url value="/management/mapping/table/tableActivate.spy" />';
			EURB.Table.deactivateAction = '<spring:url value="/management/mapping/table/tableDeactivate.spy" />';

			EURB.Table.catalog = '<spring:message code="eurb.app.management.table.catalog" />';
			EURB.Table.schema = '<spring:message code="eurb.app.management.table.schema" />';
			EURB.Table.tableName = '<spring:message code="eurb.app.management.table.tableName" />';
			EURB.Table.mappedName = '<spring:message code="eurb.app.management.table.mappedName" />';
			EURB.Table.mappedType = '<spring:message code="eurb.app.management.table.mappedType" />';
			EURB.Table.activeForManager = '<spring:message code="eurb.app.management.table.activeForManager" />';
			EURB.Table.activeForUser = '<spring:message code="eurb.app.management.table.activeForUser" />';
			EURB.Table.forManager = '<spring:message code="eurb.app.management.table.forManager" />';
			EURB.Table.forUser = '<spring:message code="eurb.app.management.table.forUser" />';
			EURB.Table.editColumns = '<spring:message code="eurb.app.management.table.editColumns" />';
			EURB.Table.youShouldSaveTheRecordFisrt = '<spring:message code="eurb.app.management.table.youShouldSaveTheRecordFisrt" />';
			EURB.Table.selectedDbConfig = '<%=selectedDbConfig%>';
			
			// create the combo instance
			EURB.Table.dbConfigCombo = new Ext.form.ComboBox({
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
			    		window.location.href = EURB.baseURL+'management/mapping/db'+newValue.id+'-table.spy';
			    	}
			    }
			});
		</script>
		<script src="${resourcesUrl}/js/app/management/mapping/table.js"></script>
	</body>
</html>