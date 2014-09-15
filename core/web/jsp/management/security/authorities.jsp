<%@page import="org.apache.commons.lang.StringUtils"%>
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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/management/security/authorities.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">
			EURB.Authorities = {};
			<% 
			String selectedSID = (String) request.getAttribute("sid");
			%>
			EURB.Authorities.searchAction = '<spring:url value="/management/security/authorities/authoritiesSearch.spy" />';
			EURB.Authorities.storeAction = '<spring:url value="/management/security/authorities/authoritiesStore.spy" />';
			
			EURB.Authorities.category = '<spring:message code="eurb.app.management.authorities.category" />';
			EURB.Authorities.viewlist = '<spring:message code="eurb.app.management.authorities.viewlist" />';
			EURB.Authorities.view = '<spring:message code="eurb.app.management.authorities.view" />';
			EURB.Authorities.create = '<spring:message code="eurb.app.management.authorities.create" />';
			EURB.Authorities.edit = '<spring:message code="eurb.app.management.authorities.edit" />';
			EURB.Authorities.del = '<spring:message code="eurb.app.management.authorities.del" />';
			EURB.Authorities.execute = '<spring:message code="eurb.app.management.authorities.execute" />';
			EURB.Authorities.sharing = '<spring:message code="eurb.app.management.authorities.sharing" />';
			EURB.Authorities.selectAll = '<spring:message code="eurb.app.management.authorities.selectall" />';
			EURB.Authorities.selectNone = '<spring:message code="eurb.app.management.authorities.selectnone" />';
			EURB.Authorities.saveAll = '<spring:message code="eurb.app.management.authorities.saveAll" />';
			EURB.Authorities.selectedSID = '<%=selectedSID%>';
			
			EURB.Authorities.SIDCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'sid',
			            'sidName'
			        ],
			        data: ${SIDComboContent}
			    }),
			    <%=StringUtils.isEmpty(selectedSID) ? "" : "value: "+selectedSID+","%>
			    forceSelection: true,
			    valueField: 'sid',
			    displayField: 'sidName',
			    listeners: {
			    	'select' : function(thiz, newValue, oldValue) {
			    		window.location.href = EURB.baseURL+'management/security/authorities'+newValue.id+'.spy';
			    	}
			    }
			});
		</script>
		<script src="${resourcesUrl}/js/app/management/security/authorities.js"></script>
	</body>
</html>