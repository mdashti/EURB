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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/dashboard/dashboard-design.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.DashboardDesign = {};
			EURB.DashboardDesign.searchAction = '<spring:url value="/dashboard/dashboard-design/dbconfigSearch.spy" />';
			EURB.DashboardDesign.storeAction = '<spring:url value="/dashboard/dashboard-design/dbconfigStore.spy" />';
			EURB.DashboardDesign.removeAction = '<spring:url value="/dashboard/dashboard-design/dbconfigRemove.spy" />';
			
			EURB.DashboardDesign.title = '<spring:message code="eurb.app.dashboard.title" />';
			EURB.DashboardDesign.isDefault = '<spring:message code="eurb.app.dashboard.isdefault" />';
			EURB.DashboardDesign.colWidth = '<spring:message code="eurb.app.dashboard.colwidth" />';
			EURB.DashboardDesign.colOrder = '<spring:message code="eurb.app.dashboard.colorder" />';
			EURB.DashboardDesign.itemHeight = '<spring:message code="eurb.app.dashboard.itemheight" />';
			EURB.DashboardDesign.itemTitle = '<spring:message code="eurb.app.dashboard.itemTitle" />';
			EURB.DashboardDesign.itemContent = '<spring:message code="eurb.app.dashboard.itemContent" />';
			EURB.DashboardDesign.itemReport = '<spring:message code="eurb.app.dashboard.itemReport" />';
			EURB.DashboardDesign.itemChart = '<spring:message code="eurb.app.dashboard.itemChart" />';
		</script>
		<script src="{resourcesUrl}/js/app/dashboard/dashboard-design.js"></script>
	</body>
</html>