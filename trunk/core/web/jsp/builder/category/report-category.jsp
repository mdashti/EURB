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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/builder/category/report-category.css" />
		
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.ReportCategory = {};
			EURB.ReportCategory.searchAction = '<spring:url value="/builder/category/reportCategorySearch.spy" />';
			EURB.ReportCategory.storeAction = '<spring:url value="/builder/category/reportCategoryStore.spy" />';
			EURB.ReportCategory.removeAction = '<spring:url value="/builder/category/reportCategoryRemove.spy" />';
			
			EURB.ReportCategory.Name = '<spring:message code="eurb.app.builder.category.name" />';
			EURB.ReportCategory.Description = '<spring:message code="eurb.app.builder.category.description" />';
			EURB.ReportCategory.Parent = '<spring:message code="eurb.app.builder.category.parent" />';
			EURB.ReportCategory.category = '<spring:message code="eurb.app.builder.report.category" />';
			
			EURB.ReportCategory.categoriesStore = new Ext.data.ArrayStore({
				fields:['id',
				        'name',
				        'parentId'
				        ],
				data:${categoriesJson}
			});
			
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
		</script>
		<script src="${resourcesUrl}/js/app/objsec/object-security-ui.js"></script>
		<script src="${resourcesUrl}/js/app/builder/category/report-category.js"></script>
	</body>
</html>