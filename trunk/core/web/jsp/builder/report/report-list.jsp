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
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/css/app/builder/report/report-list.css" />
		
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- App js -->
		<script type="text/javascript">

			EURB.Report = {};
			EURB.Report.removeAction = '<spring:url value="/builder/report/reportRemove.spy" />';
			EURB.Report.activateAction = '<spring:url value="/builder/report/reportActivate.spy" />';
			EURB.Report.deactivateAction = '<spring:url value="/builder/report/reportDeactivate.spy" />';
			
			EURB.Report.name = '<spring:message code="eurb.app.builder.report.name" />';
			EURB.Report.description = '<spring:message code="eurb.app.builder.report.description" />';
			EURB.Report.category = '<spring:message code="eurb.app.builder.report.category" />';
			
			EURB.Report.editDesign = '<spring:message code="eurb.app.builder.report.editDesign" />';
			EURB.Report.editReport = '<spring:message code="eurb.app.builder.report.editReport" />';
			EURB.Report.viewInteractiveReport = '<spring:message code="eurb.app.builder.report.viewInteractiveReport" />';
			EURB.Report.scheduleReport = '<spring:message code="eurb.app.builder.report.scheduleReport" />';
			EURB.Report.comboRenderer = function(combo){
			    return function(value){
			        var record = combo.findRecord(combo.valueField, value);
			        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
			    }
			};

			EURB.Report.categoryCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'id',
			            'name'
			        ],
			        data: ${categoriesComboContent}
			    }),
			    valueField: 'id',
			    displayField: 'name'
			});
			
		</script>
		<script src="${resourcesUrl}/js/app/builder/report/report-list.js"></script>
	</body>
</html>