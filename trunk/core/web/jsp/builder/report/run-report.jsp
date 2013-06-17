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
			EURB.RunReport.viewReport = '<spring:message code="eurb.app.builder.runreport.title" /> ${reportName}';
			EURB.RunReport.printCurrentPage = '<spring:message code="eurb.app.builder.runreport.grid.printCurrentPage" />';
			EURB.RunReport.exportAllReportToCSV = '<spring:message code="eurb.app.builder.runreport.grid.exportAllReportToCSV" />';
			EURB.RunReport.exportAllReportToExcel = '<spring:message code="eurb.app.builder.runreport.grid.exportAllReportToExcel" />';
			EURB.RunReport.exportAllReportToWord = '<spring:message code="eurb.app.builder.runreport.grid.exportAllReportToWord" />';
			EURB.RunReport.previewReportToWord = '<spring:message code="eurb.app.builder.runreport.grid.previewReportToWord" />';
			EURB.RunReport.EmptyGroupField = '<spring:message code="eurb.app.builder.runreport.group.empty" />';
			EURB.RunReport.ChartAlert = '<spring:message code="eurb.app.builder.runreport.chart.alert" />';
			EURB.RunReport.exportCharts = '<spring:message code="eurb.app.builder.runreport.chart.exportAll" />';
			EURB.RunReport.enterFullscreen = '<spring:message code="eurb.app.builder.runreport.enter.fullscreen" />';
			EURB.RunReport.exitFullscreen = '<spring:message code="eurb.app.builder.runreport.exit.fullscreen" />';
			EURB.RunReport.HasGroup = ${hasGroup};
			
			if(EURB.RunReport.HasGroup){
				EURB.RunReport.store = new Ext.ux.MultiGroupingStore({
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
					,baseParams:{start: 0, limit: EURB.defaultPageLimit}
					,groupField: ${groupFields}
					,remoteSort:false
				});
			}
			else{
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
					,baseParams:{start: 0, limit: EURB.defaultPageLimit}
					,remoteSort:false
				});
			}

			EURB.RunReport.cols = ${gridColumns};
			
			EURB.RunReport.design = ${report};
			EURB.RunReport.version = ${version};
			EURB.RunReport.hasChart = ${hasChart};
			EURB.RunReport.chartCount = ${chartCount};
			EURB.RunReport.runChart = '<spring:url value="/builder/report/get-reportchart${report}-v${version}.spy" />';			
			

		</script>
		<script src="${resourcesUrl}/js/app/builder/report/run-chart.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/run-report.js"></script>
	</body>
</html>