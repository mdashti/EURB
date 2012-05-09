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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/management/security/user.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.User = {};
			EURB.User.searchAction = '<spring:url value="/management/security/user/userSearch.spy" />';
			EURB.User.storeAction = '<spring:url value="/management/security/user/userStore.spy" />';
			EURB.User.removeAction = '<spring:url value="/management/security/user/userRemove.spy" />';
			EURB.User.activateAction = '<spring:url value="/management/security/user/userActivate.spy" />';
			EURB.User.deactivateAction = '<spring:url value="/management/security/user/userDeactivate.spy" />';
			EURB.User.changePasswordAction = '<spring:url value="/management/security/user/userChangePassword.spy" />';
			
			EURB.User.username = '<spring:message code="eurb.app.management.user.username" />';
			EURB.User.password = '<spring:message code="eurb.app.management.user.password" />';
			EURB.User.enabled = '<spring:message code="eurb.app.management.user.enabled" />';
			EURB.User.enabledEnabled = '<spring:message code="eurb.app.management.user.enabledEnabled" />';
			EURB.User.enabledDisabled = '<spring:message code="eurb.app.management.user.enabledDisabled" />';
			EURB.User.changePassword = '<spring:message code="eurb.app.management.user.changePassword" />';
			EURB.User.newPassword = '<spring:message code="eurb.app.management.user.newPassword" />';
			EURB.User.confirmNewPassword = '<spring:message code="eurb.app.management.user.confirmNewPassword" />';
			EURB.User.confirmDidNotMatch = '<spring:message code="eurb.app.management.user.confirmDidNotMatch" />';
		</script>
		<script src="${resourcesUrl}/js/app/management/security/user.js"></script>
	</body>
</html>