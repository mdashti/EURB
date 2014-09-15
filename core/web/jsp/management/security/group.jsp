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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/management/security/group.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.Group = {};
			EURB.Group.searchAction = '<spring:url value="/management/security/group/groupSearch.spy" />';
			EURB.Group.storeAction = '<spring:url value="/management/security/group/groupStore.spy" />';
			EURB.Group.removeAction = '<spring:url value="/management/security/group/groupRemove.spy" />';
			
			EURB.Group.groupName = '<spring:message code="eurb.app.management.group.groupName" />';

			EURB.Group.Usr = {};
			EURB.Group.Usr.selectableUsersFor = '<spring:message code="eurb.app.management.group.selectableUsersFor" />';
			EURB.Group.Usr.currentUsersFor = '<spring:message code="eurb.app.management.group.currentUsersFor" />';
			EURB.Group.Usr.userName = '<spring:message code="eurb.app.management.group.userName" />';
			EURB.Group.Usr.userSelectDragDropHelp = '<spring:message code="eurb.app.management.group.userSelectDragDropHelp" />';

			EURB.Group.Usr.findCurrentUsersAction = '<spring:url value="/management/security/group/currentGroupUsers.spy" />';
			EURB.Group.Usr.findSelectableUsersAction = '<spring:url value="/management/security/group/selectableGroupUsers.spy" />';
			EURB.Group.Usr.removeGroupFromUsersAction = '<spring:url value="/management/security/group/removeGroupFromUsersAction.spy" />';
			EURB.Group.Usr.addGroupToUsersAction = '<spring:url value="/management/security/group/addGroupToUsersAction.spy" />';
		</script>
		<script src="${resourcesUrl}/js/app/management/security/group.js"></script>
	</body>
</html>