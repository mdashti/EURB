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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/builder/report/report.css" />
		<style type="text/css">
		.dbconf-valid{
			background:#8eec6a;
		}
		
		.dbconf-invalidcon{
			background:#fb717e;
		}
		
		.dbconf-inactive{
			background:#ffc873;
		}
		</style>
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">

			EURB.Report = {};
			EURB.Report.searchAction = '<spring:url value="/builder/report/reportSearch.spy" />';
			EURB.Report.storeAction = '<spring:url value="/builder/report/reportStore.spy" />';
			EURB.Report.removeAction = '<spring:url value="/builder/report/reportRemove.spy" />';
			EURB.Report.activateAction = '<spring:url value="/builder/report/reportActivate.spy" />';
			EURB.Report.deactivateAction = '<spring:url value="/builder/report/reportDeactivate.spy" />';
			
			EURB.Report.name = '<spring:message code="eurb.app.builder.report.name" />';
			EURB.Report.description = '<spring:message code="eurb.app.builder.report.description" />';
			
			EURB.Report.editDesign = '<spring:message code="eurb.app.builder.report.editDesign" />';
			
		</script>
		<script src="${resourcesUrl}/js/app/builder/report/report.js"></script>
	</body>
</html>