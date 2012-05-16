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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/builder/report/run-report.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			
			
			EURB.RunReport = {};
			
			EURB.RunReport.store = new Ext.data.Store({
				reader:new Ext.data.JsonReader({
					 id:'id'
					,totalProperty:'totalCount'
					,root:'data'
					,fields: ${storeFields}
				})
				,proxy:new Ext.data.HttpProxy({
					url: '<spring:url value="/builder/report/get-reportdata${report}-v${version}.spy" />'
			        ,listeners: {
			        	'exception' : EURB.proxyExceptionHandler
			        }
			    })
				//,baseParams:{}
				,remoteSort:false
			});

			EURB.RunReport.cols = ${gridColumns};
		</script>
		<script src="${resourcesUrl}/js/app/builder/report/run-report.js"></script>
	</body>
</html>