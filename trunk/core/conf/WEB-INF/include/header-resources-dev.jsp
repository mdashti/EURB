<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
		<title><%=request.getParameter("title") %></title>
		
		<% String resourcesUrl = request.getParameter("resourcesUrl"); %>
		<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=resourcesUrl%>/js/extjs/resources/images/default/s.gif';
		</script>
		<!-- ExtJS css -->
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/resources/css/ext-all.css" />
		
		<!-- Row Editor plugin css -->
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/examples/ux/css/rowEditorCustom.css" />
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/examples/shared/examples.css" />
		<link rel="stylesheet" type="text/css" href="<%=resourcesUrl%>/js/extjs/examples/ux/css/RowEditor.css" />
		
		<!-- ExtJS js -->
		<script src="<%=resourcesUrl%>/js/extjs/adapter/ext/ext-base.js"></script>
		<script src="<%=resourcesUrl%>/js/extjs/ext-all.js"></script>
		
		<!-- Row Editor plugin js -->
		<script src="<%=resourcesUrl%>/js/extjs/examples/ux/RowEditor.js"></script>
		
		<!-- Excel DataGrid drag and drop plugin js -->
		<script src="<%=resourcesUrl%>/js/datadrop-plugin/Override.js"></script>
		<script src="<%=resourcesUrl%>/js/datadrop-plugin/Ext.ux.DataDrop.js"></script>
