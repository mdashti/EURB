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
			EURB.Table.searchAction = '${baseUrl}management/mapping/table/tableSearch.spa<%=request.getParameter("dbconfig") == null ? "" : "?dbconfig="+request.getParameter("dbconfig")%>';
			EURB.Table.storeAction = '${baseUrl}management/mapping/table/tableStore.spa';
			EURB.Table.removeAction = '${baseUrl}management/mapping/table/tableRemove.spa';
			EURB.Table.activateAction = '${baseUrl}management/mapping/table/tableActivate.spa';
			EURB.Table.deactivateAction = '${baseUrl}management/mapping/table/tableDeactivate.spa';

			EURB.Table.catalog = '<spring:message code="eurb.app.management.table.catalog" />';
			EURB.Table.tableName = '<spring:message code="eurb.app.management.table.tableName" />';
			EURB.Table.mappedName = '<spring:message code="eurb.app.management.table.mappedName" />';
			EURB.Table.mappedType = '<spring:message code="eurb.app.management.table.mappedType" />';
			EURB.Table.activeForManager = '<spring:message code="eurb.app.management.table.activeForManager" />';
			EURB.Table.activeForUser = '<spring:message code="eurb.app.management.table.activeForUser" />';
		</script>
		<script src="${resourcesUrl}/js/app/management/mapping/table.js"></script>
	</body>
</html>