<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% String resourcesUrl = request.getParameter("resourcesUrl"); %>
		<div id="loading-mask" style=""></div>
		<div id="loading">
			<div class="loading-indicator">
				<spring:message code="eurb.loading" /><img src="<%=resourcesUrl%>/img/icon/extanim32.gif" width="32" height="32" style="margin-right: 8px;" align="absmiddle" /></div>
		</div>
		<!-- include everything after the loading indicator -->
		
		<!-- ExtJS js -->
		<script src="<%=resourcesUrl%>/js/extjs/adapter/ext/ext-base-debug.js"></script>
		<script src="<%=resourcesUrl%>/js/extjs/ext-all-debug.js"></script>
		<script src="<%=resourcesUrl%>/js/extjs/extjs_rtl.js"></script>
		<script src="<%=resourcesUrl%>/js/extjs/locale/ext-lang-fa.js"></script>
		<!-- Row Editor plugin js -->
		<script src="<%=resourcesUrl%>/js/extjs/plugins/roweditor/RowEditor.js"></script>
		
		<!-- Excel DataGrid drag and drop plugin js -->
		<script src="<%=resourcesUrl%>/js/extjs/plugins/datadrop-plugin/Override.js"></script>
		<script src="<%=resourcesUrl%>/js/extjs/plugins/datadrop-plugin/Ext.ux.DataDrop.js"></script>
		
		<!-- Other Plugins -->
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/searchfield.js"></script>
	    <script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Jalali.js"></script>
	    <script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/JalaliDate.js"></script>
	    <script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/JalaliDatePlugin.js"></script>
	    <script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/JalaliDatePlugin-fa_IR.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/TabScrollerMenu.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Ext.ux.menu.IconMenu.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Ext.ux.grid.RowActions.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Ext.ux.grid.RecordForm.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Select.js"></script>
		<%--<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Ext.ux.form.FileUploadField.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Ext.ux.XHRUpload.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/swfupload.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/swfupload.swfobject.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/AwesomeUploader.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/CascadeWindows.js"></script>--%>
	
		<!-- Renderers -->
		<script src="<%=resourcesUrl%>/js/renderer.js"></script>
		
		<!-- Body Template -->
		<script src="<%=resourcesUrl%>/js/eurb-reports.js"></script>
		<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=resourcesUrl%>/js/extjs/resources/images/default/s.gif';
		EURB = {};
		EURB.loading = '<spring:message code="eurb.loading" />';
		
		</script>
		
		<div id="header">
			<a href="http://www.sharifpro.com" style="float: left; margin-right: 10px;"><img
				src="<%=resourcesUrl%>/img/icon/eurb.gif"
				style="width: 83px; height: 24px; margin-top: 1px;" /></a>
	
			<div class="api-title"><spring:message code="eurb.app.title" /></div>
		</div>
	
		<div id="classes"></div>
	
		<div id="main"></div>