<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% String resourcesUrl = request.getParameter("resourcesUrl"); %>
		<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<title><spring:message code="eurb.app.title" /></title>

		<!-- ExtJS css -->
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/extjs_rtl.css" />
		<!-- Row Editor plugin css -->
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/rowEditorCustom.css" />
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/css/shared.css" />
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/RowEditor.css" />
		
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/Ext.ux.grid.RowActions.css"/>
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/Select.css"/>
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/Ext.ux.form.FileUploadField.css" />
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/AwesomeUploader.css" />
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/examples/ux/statusbar/css/statusbar.css" />

		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/gridsearch/css/icons.css">
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/gridsearch/css/Ext.ux.grid.RowActions.css">
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/gridsearch/css/empty.css" id="theme">
		
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/css/eurb-reports.css" />