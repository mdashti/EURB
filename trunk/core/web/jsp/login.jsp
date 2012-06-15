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
			<jsp:param value="false" name="menuEnabled"/>
			<jsp:param value="false" name="statusEnabled"/>
		</jsp:include>
		<script type="text/javascript">
		EURB.login = '<spring:message code="eurb.app.login" />';
		EURB.username = '<spring:message code="eurb.app.login.username" />';
		EURB.password = '<spring:message code="eurb.app.login.password" />';
		EURB.reset = '<spring:message code="eurb.app.login.reset" />';
		Ext.onReady(function(){
			document.title = EURB.title + ' - ' + EURB.login;
			var loginFormSubmitConf = {
				success: function(f,a){
					if(a.result.targetUrl && a.result.targetUrl.indexOf('http') == 0) {
						window.location.href = a.result.targetUrl;
					} else {
						window.location.href = EURB.baseURL+a.result.targetUrl;
					}
				},
				failure: function(f,a){
					if (a.failureType === Ext.form.Action.CONNECT_FAILURE){
						Ext.Msg.alert('Failure', 'Server reported:'+a.response.status+' '+a.response.statusText);
					}
					if (a.failureType === Ext.form.Action.SERVER_INVALID){
						Ext.Msg.show({
						   title: a.result.errors.	title,
						   msg: a.result.errors.errormsg,
						   icon: Ext.MessageBox.WARNING,
						   buttons: Ext.MessageBox.OK
						});
					}
				}
			}
			var loginForm = new Ext.FormPanel({ 
				url: EURB.baseURL+'j_spring_security_check',
				bodyStyle:'padding:5px;',
				items: [{
					xtype: 'textfield',
					name: 'j_username',
					fieldLabel: EURB.username,
					cls: 'left-align-txt',
					allowBlank : false,
					listeners: {
						specialkey: function(f,e){
							if (e.getKey() == e.ENTER) {
								loginForm.getForm().submit(loginFormSubmitConf);
							}
						}
					}
				},{
					xtype: 'textfield',    
					inputType: 'password',
					name: 'j_password',
					fieldLabel: EURB.password,
					cls: 'left-align-txt',
					allowBlank : false,
					listeners: {
						specialkey: function(f,e){
							if (e.getKey() == e.ENTER) {
								loginForm.getForm().submit(loginFormSubmitConf);
							}
						}
					}
				}],
				buttons: [{
					text: EURB.login,
					handler: function(){
						loginForm.getForm().submit(loginFormSubmitConf);
					}
				}, {
					text: EURB.reset,
					handler: function(){
						loginForm.getForm().reset();
					}
				}]
			});
			var w = new Ext.Window({
				title: EURB.login, 
				collapsible : false,
				maximizable : false,
				minimizable : false,
				resizable : true,
				closable : false,
				width: 300,
				items: loginForm
			});
			w.show();
			loginForm.getForm().findField('j_username').focus(true,500);
		});
		</script>
		
		
	</body>
</html>