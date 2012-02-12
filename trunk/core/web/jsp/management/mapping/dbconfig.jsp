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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/management/mapping/dbconfig.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.DBConfig = {};
			EURB.DBConfig.searchAction = '${baseUrl}management/mapping/dbconfig/dbconfigSearch.spy';
			EURB.DBConfig.storeAction = '${baseUrl}management/mapping/dbconfig/dbconfigStore.spy';
			EURB.DBConfig.removeAction = '${baseUrl}management/mapping/dbconfig/dbconfigRemove.spy';
			EURB.DBConfig.activateAction = '${baseUrl}management/mapping/dbconfig/dbconfigActivate.spy';
			EURB.DBConfig.deactivateAction = '${baseUrl}management/mapping/dbconfig/dbconfigDeactivate.spy';
			
			EURB.DBConfig.name = '<spring:message code="eurb.app.management.dbconfig.name" />';
			EURB.DBConfig.driverClass = '<spring:message code="eurb.app.management.dbconfig.driverClass" />';
			EURB.DBConfig.driverUrl = '<spring:message code="eurb.app.management.dbconfig.driverUrl" />';
			EURB.DBConfig.username = '<spring:message code="eurb.app.management.dbconfig.username" />';
			EURB.DBConfig.password = '<spring:message code="eurb.app.management.dbconfig.password" />';
			EURB.DBConfig.testQuery = '<spring:message code="eurb.app.management.dbconfig.testQuery" />';
			
			EURB.DBConfig.editTables = '<spring:message code="eurb.app.management.dbconfig.editTables" />';
		</script>
		<script src="${resourcesUrl}/js/app/management/mapping/dbconfig.js"></script>
	</body>
</html>