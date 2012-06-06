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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/builder/schedule/schedule.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.Schedule = {};
			EURB.Schedule.searchAction = '<spring:url value="/builder/schedule/findUserScheduled.spy" />';
			EURB.Schedule.findScheduleAction = '<spring:url value="/builder/schedule/findSchedule.spy" />';
			EURB.Schedule.storeAction = '<spring:url value="/builder/schedule/scheduleStore.spy" />';
			EURB.Schedule.removeAction = '<spring:url value="/builder/schedule/scheduleRemove.spy" />';
			EURB.Schedule.reportSearchAction = '<spring:url value="/builder/report/reportSearch.spy" />';

			EURB.Schedule.reportName = '<spring:message code="eurb.app.builder.schedule.reportName" />';
			EURB.Schedule.scheduleDescription = '<spring:message code="eurb.app.builder.schedule.scheduleDescription" />';
			EURB.Schedule.startDateTimeString = '<spring:message code="eurb.app.builder.schedule.startDateTimeString" />';
			EURB.Schedule.startDate = '<spring:message code="eurb.app.builder.schedule.startDate" />';
			EURB.Schedule.startTime = '<spring:message code="eurb.app.builder.schedule.startTime" />';
			EURB.Schedule.startTimeAM = '<spring:message code="eurb.app.builder.schedule.startTimeAM" />';
			EURB.Schedule.startTimePM = '<spring:message code="eurb.app.builder.schedule.startTimePM" />';
			EURB.Schedule.scheduleTypeName = '<spring:message code="eurb.app.builder.schedule.scheduleTypeName" />';
			EURB.Schedule.nextFireDateString = '<spring:message code="eurb.app.builder.schedule.nextFireDateString" />';

			EURB.Schedule.scheduleTypeOnce = '<spring:message code="eurb.app.builder.schedule.scheduleTypeOnce" />';
			EURB.Schedule.scheduleTypeHourly = '<spring:message code="eurb.app.builder.schedule.scheduleTypeHourly" />';
			EURB.Schedule.scheduleTypeDaily = '<spring:message code="eurb.app.builder.schedule.scheduleTypeDaily" />';
			EURB.Schedule.scheduleTypeWeekdays = '<spring:message code="eurb.app.builder.schedule.scheduleTypeWeekdays" />';
			EURB.Schedule.scheduleTypeWeekly = '<spring:message code="eurb.app.builder.schedule.scheduleTypeWeekly" />';
			EURB.Schedule.scheduleTypeMonthly = '<spring:message code="eurb.app.builder.schedule.scheduleTypeMonthly" />';
			EURB.Schedule.scheduleTypeCron = '<spring:message code="eurb.app.builder.schedule.scheduleTypeCron" />';
			EURB.Schedule.cronExpression = '<spring:message code="eurb.app.builder.schedule.cronExpression" />';
			EURB.Schedule.numberOfHours = '<spring:message code="eurb.app.builder.schedule.numberOfHours" />';
			EURB.Schedule.recepients = '<spring:message code="eurb.app.builder.schedule.recepients" />';
		</script>
		<script src="${resourcesUrl}/js/app/builder/schedule/schedule.js"></script>
	</body>
</html>