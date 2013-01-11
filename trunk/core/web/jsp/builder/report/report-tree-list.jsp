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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/js/extjs/plugins/tricheck/tricheckbox.css">
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/builder/report/report-list.css" />
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
		<script type="text/javascript" src="${resourcesUrl}/js/extjs/plugins/tricheck/tricheckbox.js"></script>
		<script type="text/javascript">

			EURB.Report = {};
			EURB.Report.searchAction = '<spring:url value="/builder/report/reportSearch.spy" />';
			EURB.Report.storeAction = '<spring:url value="/builder/report/reportStore.spy" />';
			EURB.Report.removeAction = '<spring:url value="/builder/report/reportRemove.spy" />';
			EURB.Report.activateAction = '<spring:url value="/builder/report/reportActivate.spy" />';
			EURB.Report.deactivateAction = '<spring:url value="/builder/report/reportDeactivate.spy" />';
			EURB.Report.searchReportAndCategoryAction = '<spring:url value="/builder/report/reportAndCategorySearch.spy" />';
			
			EURB.Report.name = '<spring:message code="eurb.app.builder.report.name" />';
			EURB.Report.description = '<spring:message code="eurb.app.builder.report.description" />';
			EURB.Report.category = '<spring:message code="eurb.app.builder.report.category" />';
			EURB.Report.share = '<spring:message code="eurb.app.builder.report.share" />';
			EURB.Report.report = '<spring:message code="eurb.app.builder.report.reportof" />';
			EURB.Report.category = '<spring:message code="eurb.app.builder.report.categoryof" />';
			EURB.Report.viewInteractiveReport = '<spring:message code="eurb.app.builder.report.viewInteractiveReport" />';
			
			EURB.Report.editDesign = '<spring:message code="eurb.app.builder.report.editDesign" />';
			
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
			EURB.Report.sharingOn = false;
			<sec:authorize access="hasRole('${authorityType.ROLE_RPG_REPORT_BUILDER_SHARING}')">
			EURB.Report.sharingOn = true;
			</sec:authorize>
		</script>
		<script src="${resourcesUrl}/js/app/objsec/object-security-ui.js"></script>
		<script src="${resourcesUrl}/js/app/builder/report/report-tree-list.js"></script>
	</body>
</html>