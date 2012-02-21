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
		<% String selectedReport =  (String) request.getAttribute("report"); %>
		<script type="text/javascript">

			EURB.ReportDesign = {};
			EURB.ReportDesign.tablesListAction = '<spring:url value="/builder/report/tablesList.spy" />';
			
			
		</script>
		
		<script src="${resourcesUrl}/js/app/builder/report/report-design.js"></script>
	</body>
</html>