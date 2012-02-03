<%@page import="com.sharifpro.util.SessionManager,
				com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% String resourcesUrl = request.getParameter("resourcesUrl"); %>
<% String baseUrl = request.getParameter("baseUrl"); %>
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
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/examples/ux/statusbar/StatusBar.js"></script>
		<%--<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Ext.ux.form.FileUploadField.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/Ext.ux.XHRUpload.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/swfupload.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/swfupload.swfobject.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/AwesomeUploader.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/extjs/plugins/CascadeWindows.js"></script>--%>

		<%-- ExtJS Grid Search Plugin --%>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/gridsearch/js/Ext.ux.util.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/gridsearch/js/Ext.ux.form.ThemeCombo.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/gridsearch/js/Ext.ux.Toast.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/gridsearch/js/Ext.ux.grid.Search.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/gridsearch/js/Ext.ux.grid.RowActions.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/gridsearch/js/Ext.ux.grid.RecordForm.js"></script>

		<script src="<%=resourcesUrl%>/js/extjs/locale/ext-lang-fa.js"></script>
		
		<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=resourcesUrl%>/js/extjs/resources/images/default/s.gif';
		EURB = {};
		EURB.baseURL = '<%=baseUrl%>';
		EURB.resourcesURL = '<%=resourcesUrl%>';
		EURB.currentUser = '<%=SessionManager.getCurrentUserName() == null ? "admin" : SessionManager.getCurrentUserName() %>';
		EURB.currentIpAddress = '<%=request.getRemoteAddr()%>';
		
		EURB.defaultPageLimit = <%=AbstractDAO.DEFAULT_PAGE_SIZE%>;
		
		EURB.loading = '<spring:message code="eurb.loading" />';
		EURB.user = '<spring:message code="eurb.user" />';
		EURB.ipAddress = '<spring:message code="eurb.ipAddress" />';
		EURB.logout = '<spring:message code="eurb.logout" />';
		EURB.search = '<spring:message code="eurb.search" />';
		EURB.addRecord = '<spring:message code="eurb.addRecord" />';
		EURB.delRecord = '<spring:message code="eurb.delRecord" />';
		EURB.editRecord = '<spring:message code="eurb.editRecord" />';
		EURB.copyRecord = '<spring:message code="eurb.copyRecord" />';
		EURB.disableRecord = '<spring:message code="eurb.disableRecord" />';
		EURB.enableRecord = '<spring:message code="eurb.enableRecord" />';
		EURB.unknownError = '<spring:message code="eurb.unknownError" />';
		EURB.unableToDecodeJSON = '<spring:message code="eurb.unableToDecodeJSON" />';
		EURB.areYouSureToDelTitle = '<spring:message code="eurb.areYouSureToDelTitle" />';
		EURB.areYouSureToDelete = '<spring:message code="eurb.areYouSureToDelete" />';
		EURB.selectAtLeastOneRecordFisrt = '<spring:message code="eurb.selectAtLeastOneRecordFisrt" />';
		EURB.addEdit = '<spring:message code="eurb.addEdit" />';
		EURB.records = '<spring:message code="eurb.records" />';
		
		EURB.appMenu = {};
		EURB.appMenu.dbConfig = '<spring:message code="eurb.app.menu.management.db" />';
		
		EURB.showError = function(msg, title) {
			Ext.Msg.show({
				 title:title || 'Error'
				,msg:Ext.util.Format.ellipsis(msg, 2000)
				,icon:Ext.Msg.ERROR
				,buttons:Ext.Msg.OK
				,minWidth:1200 > String(msg).length ? 360 : 600
			});
		};
		</script>
		<!-- App Menu -->
		<script type="text/javascript" src="<%=resourcesUrl%>/js/app/menu/jquery.min.js"></script>
		<script type="text/javascript" src="<%=resourcesUrl%>/js/app/menu/ddaccordion.js">
		
		<!-- Renderers -->
		<script src="<%=resourcesUrl%>/js/renderer.js"></script>
		
		<!-- Body Template -->
		<script src="<%=resourcesUrl%>/js/eurb-reports.js"></script>
		
		<div id="header">
			<a href="http://www.sharifpro.com" style="float: left; margin-right: 10px;"><img
				src="<%=resourcesUrl%>/img/icon/eurb.gif"
				style="width: 83px; height: 24px; margin-top: 1px;" /></a>
	
			<div class="api-title"><spring:message code="eurb.app.title" /></div>
		</div>
	
		<div id="classes"></div>
	
		<div id="main"></div>
		<script type="text/javascript">
		Ext.onReady(function(){
			Ext.QuickTips.init();
//			var tip = Ext.QuickTips.getQuickTip();
//			Ext.apply(tip, {
//				autoHide:false
//			});
			/*var item1 = new Ext.Panel({
		        title: '<spring:message code="eurb.app.menu.management" />',
		        html: '&lt;empty panel&gt;',
		        cls:'empty'
		    });
		    EURB.mainMenu.items.add(item1);
		
		    var item2 = new Ext.Panel({
		        title: '<spring:message code="eurb.app.menu.reportbuilder" />',
		        html: '&lt;empty panel&gt;',
		        cls:'empty'
		    });
		    EURB.mainMenu.items.add(item2);
		    
		    */

		    EURB.mainMenu.add({
		    	bodyBorder : false,
		    	html:'<div class="urbangreymenu">'+
		    	'<h3 class="headerbar"><a href="'+EURB.baseURL+'"><spring:message code="eurb.app.menu.management" /></a></h3>'+
		    	'<ul class="submenu">'+
		    	'<li><a href="'+EURB.baseURL+'/management/mapping">'+EURB.appMenu.dbConfig+'</a></li>'+
		    	'<li><a href="'+EURB.baseURL+'"><spring:message code="eurb.app.menu.management.mapping" /></a></li>'+
		    	'<li><a href="'+EURB.baseURL+'"><spring:message code="eurb.app.menu.management.users" /></a></li>'+
		    	'</ul>'+
		    	'<h3 class="headerbar"><a href="'+EURB.baseURL+'"><spring:message code="eurb.app.menu.reportbuilder" /></a></h3>'+
		    	'<ul class="submenu">'+
		    	'<li><a href="'+EURB.baseURL+'" ><spring:message code="eurb.app.menu.reportbuilder.reports" /></a></li>'+
		    	'</ul>'+
		    	'<h3 class="headerbar"><a href="'+EURB.baseURL+'"><spring:message code="eurb.app.menu.sample" /></a></h3>'+
		    	'<ul class="submenu">'+
		    	'<li><a href="'+EURB.baseURL+'/sample" ><spring:message code="eurb.app.menu.sample.samplepage" /></a></li>'+	
		    	'</ul>'+
		    	'</div>'});
		    EURB.mainMenu.doLayout();

	        /*ddaccordion.init({
	        	headerclass: "headerbar", //Shared CSS class name of headers group
	        	contentclass: "submenu", //Shared CSS class name of contents group
	        	revealtype: "mouseover", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
	        	mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
	        	collapseprev: true, //Collapse previous content (so only one open at any time)? true/false
	        	defaultexpanded: [0], //index of content(s) open by default [index1, index2, etc] [] denotes no content
	        	onemustopen: true, //Specify whether at least one header should be open always (so never all headers closed)
	        	animatedefault: false, //Should contents open by default be animated into view?
	        	persiststate: true, //persist state of opened contents within browser session?
	        	toggleclass: ["", "selected"], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
	        	togglehtml: ["", "", ""], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
	        	animatespeed: "normal", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
	        	oninit:function(headers, expandedindices){ //custom code to run when headers have initalized
	        		//do nothing
	        	},
	        	onopenclose:function(header, index, state, isuseractivated){ //custom code to run whenever a header is opened or closed
	        		//do nothing
	        	}
	        });*/
		});
		</script>