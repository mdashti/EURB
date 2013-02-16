<%@page import="com.sharifpro.eurb.info.AuthorityType"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
<un:useConstants className="com.sharifpro.eurb.info.AuthorityType" var="authorityType" />
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
			EURB.DBConfig.searchAction = '<spring:url value="/management/mapping/dbconfig/dbconfigSearch.spy" />';
			EURB.DBConfig.storeAction = '<spring:url value="/management/mapping/dbconfig/dbconfigStore.spy" />';
			EURB.DBConfig.removeAction = '<spring:url value="/management/mapping/dbconfig/dbconfigRemove.spy" />';
			EURB.DBConfig.activateAction = '<spring:url value="/management/mapping/dbconfig/dbconfigActivate.spy" />';
			EURB.DBConfig.deactivateAction = '<spring:url value="/management/mapping/dbconfig/dbconfigDeactivate.spy" />';
			EURB.DBConfig.driverSearchAction = '<spring:url value="/management/mapping/dbconfig/driverSearch.spy" />';
			
			EURB.DBConfig.title = '<spring:message code="eurb.app.management.dbconfig.title" />';
			EURB.DBConfig.name = '<spring:message code="eurb.app.management.dbconfig.name" />';
			EURB.DBConfig.driverClass = '<spring:message code="eurb.app.management.dbconfig.driverClass" />';
			EURB.DBConfig.driverUrl = '<spring:message code="eurb.app.management.dbconfig.driverUrl" />';
			EURB.DBConfig.username = '<spring:message code="eurb.app.management.dbconfig.username" />';
			EURB.DBConfig.password = '<spring:message code="eurb.app.management.dbconfig.password" />';
			EURB.DBConfig.testQuery = '<spring:message code="eurb.app.management.dbconfig.testQuery" />';
			EURB.DBConfig.conStatus = '<spring:message code="eurb.app.management.dbconfig.conStatus" />';
			EURB.DBConfig.incompletedata = '<spring:message code="eurb.app.management.dbconfig.conStatus.incompletedata" />';
			EURB.DBConfig.valid = '<spring:message code="eurb.app.management.dbconfig.conStatus.valid" />';
			EURB.DBConfig.invalid = '<spring:message code="eurb.app.management.dbconfig.conStatus.invalid" />';
			EURB.DBConfig.inactive = '<spring:message code="eurb.app.management.dbconfig.conStatus.inactive" />';
			
			EURB.DBConfig.editTables = '<spring:message code="eurb.app.management.dbconfig.editTables" />';
			
			if(!EURB.ObjSec) {
				EURB.ObjSec = {};
			}
			EURB.ObjSec.share = '<spring:message code="eurb.app.builder.report.share" />';
			EURB.ObjSec.groupSearchAction = '<spring:url value="/management/security/group/groupSearch.spy" />';
			EURB.ObjSec.userSearchAction = '<spring:url value="/management/security/user/userSearch.spy" />';
			EURB.ObjSec.objectAuthoritiesSearchAction = '<spring:url value="/management/security/acl/loadPermission.spy" />';
			EURB.ObjSec.objectAuthoritiesStoreAction = '<spring:url value="/management/security/acl/storePermission.spy" />';
			
			EURB.ObjSec.groupName = '<spring:message code="eurb.app.management.authorities.groupname" />';
			EURB.ObjSec.userName = '<spring:message code="eurb.app.management.authorities.username" />';
			EURB.ObjSec.groupOrUserName = '<spring:message code="eurb.app.management.authorities.grouporusername" />';;
			
			EURB.ObjSec.authoritiesView = '<spring:message code="eurb.app.management.authorities.view" />';
			EURB.ObjSec.authoritiesCreate = '<spring:message code="eurb.app.management.authorities.create" />';
			EURB.ObjSec.authoritiesEdit = '<spring:message code="eurb.app.management.authorities.edit" />';
			EURB.ObjSec.authoritiesDel = '<spring:message code="eurb.app.management.authorities.del" />';
			EURB.ObjSec.authoritiesExecute = '<spring:message code="eurb.app.management.authorities.execute" />';
			EURB.ObjSec.authoritiesSharing = '<spring:message code="eurb.app.management.authorities.sharing" />';
			
			EURB.DBConfig.sharingOn = false;
			<sec:authorize access="hasRole('${authorityType.ROLE_BASE_DB_CONFIG_SHARING}')">
			EURB.DBConfig.sharingOn = true;
			</sec:authorize>
		</script>
		<script src="${resourcesUrl}/js/app/objsec/object-security-ui.js"></script>
		<script src="${resourcesUrl}/js/app/management/mapping/dbconfig.js"></script>
	</body>
</html>