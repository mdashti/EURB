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
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<script type="text/javascript">
		EURB.Index = {};
		EURB.Index.searchAction = '<spring:url value="/index/findUserAlerts.spy" />';
		EURB.Index.removeAction = '<spring:url value="/index/alertRemove.spy" />';
		
		EURB.Index.type = '<spring:message code="eurb.app.index.alarm.type" />';
		EURB.Index.thisMessage = '<spring:message code="eurb.app.index.alarm.thisMessage" />';
		EURB.Index.noMessages = '<spring:message code="eurb.app.index.alarm.noMessages" />';
		EURB.Index.message = '<spring:message code="eurb.app.index.alarm.message" />';
		EURB.Index.alarmGridTitle = '<spring:message code="eurb.app.index.alarm.gridTitle" />';
		EURB.Index.store = new Ext.data.Store({
			reader:new Ext.data.JsonReader({
				 id:'id'
				,totalProperty:'totalCount'
				,root:'data'
				,fields:[
					 {name:'id', type:'int'}
					//,{name:'username', type:'string'}
					,{name:'message', type:'string'}
					,{name:'type', type:'int'}
					//,{name:'show', type:'boolean'}
					//,{name:'availableFrom', type:'string'}
				]
			})
			,proxy:new Ext.data.HttpProxy({
				url:EURB.Index.searchAction
		        ,listeners: {
		        	'exception' : EURB.proxyExceptionHandler
		        }
		    })
			//,baseParams:{}
			,remoteSort:false
		});
		EURB.Index.cols = [{
			 header:EURB.Index.type
				,id:'type'
				,dataIndex:'type'
				,width:10
				,sortable:true
				,renderer: function (){
					return '<img src="${resourcesUrl}/js/extjs/resources/images/default/window/icon-info.gif"/>';
				}
				,css:'vertical-align:middle;'
			}, {
			 header:EURB.Index.message
			,id:'message'
			,dataIndex:'message'
			,width:90
			,sortable:true
			,css:'vertical-align:middle;'
		}];
		Ext.onReady(function(){
		    EURB.mainPanel.items.add(new Ext.Panel({
		    	id: 'detailPanel',
		    	region:'north',
		    	bodyStyle: {
		    		background: '#ffffff!important',
		    		padding: '7px'
	    		},
		    	html: <spring:message code="eurb.app.error403PageContent" />
		    }));
		    EURB.mainPanel.doLayout();
		});
		</script>
	</body>
</html>