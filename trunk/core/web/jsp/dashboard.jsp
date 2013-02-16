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
<% 
Long selectedDashboard = (Long) request.getAttribute("userDashboardId");
%>
<html>
	<head>
		<jsp:include page="/WEB-INF/include/common-header.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="${resourcesUrl}/js/extjs/examples/ux/css/Portal.css" />
	</head>
	<body scroll="no" id="docs" style="direction: rtl">
		<jsp:include page="/WEB-INF/include/common-body.jsp">
			<jsp:param value="${resourcesUrl}" name="resourcesUrl"/>
			<jsp:param value="${baseUrl}" name="baseUrl"/>
		</jsp:include>
		<!-- extensions -->
	    <script type="text/javascript" src="${resourcesUrl}/js/extjs/examples/ux/Portal.js"></script>
	    <script type="text/javascript" src="${resourcesUrl}/js/extjs/examples/ux/PortalColumn.js"></script>
	    <script type="text/javascript" src="${resourcesUrl}/js/extjs/examples/ux/Portlet.js"></script>
		<script type="text/javascript">
		var showError = EURB.showError;
		EURB.DashboardDesign = {};
		EURB.DashboardDesign.newItem = '<spring:message code="eurb.app.dashboard.newitem" />';
		EURB.DashboardDesign.newCol = '<spring:message code="eurb.app.dashboard.newcol" />';
		EURB.DashboardDesign.delCol = '<spring:message code="eurb.app.dashboard.delcol" />';
		EURB.DashboardDesign.delColTitle = '<spring:message code="eurb.app.dashboard.delcoltitle" />';
		EURB.DashboardDesign.delColMessage = '<spring:message code="eurb.app.dashboard.delcolmessage" />';
		EURB.DashboardDesign.newMessage = '<spring:message code="eurb.app.dashboard.newmessage" />';
		EURB.DashboardDesign.newTitle = '<spring:message code="eurb.app.dashboard.newtitle" />';
		EURB.DashboardDesign.itemTitle = '<spring:message code="eurb.app.dashboard.itemTitle" />';
		EURB.DashboardDesign.itemContent = '<spring:message code="eurb.app.dashboard.itemContent" />';
		EURB.DashboardDesign.itemHeight = '<spring:message code="eurb.app.dashboard.itemHeight" />';
		EURB.DashboardDesign.colWidth = '<spring:message code="eurb.app.dashboard.colWidth" />';
		EURB.DashboardDesign.editDashboardItem = '<spring:message code="eurb.app.dashboard.editDashboardItem" />';
		EURB.DashboardDesign.reportDesign = '<spring:message code="eurb.app.dashboard.reportDesign" />';
		EURB.DashboardDesign.reportChart = '<spring:message code="eurb.app.dashboard.reportChart" />';
		EURB.DashboardDesign.selectDashboard = '<spring:message code="eurb.app.dashboard.selectDashboard" />';
		EURB.DashboardDesign.newdashboardtitle = '<spring:message code="eurb.app.dashboard.newdashboardtitle" />';
		EURB.DashboardDesign.newdashboardmsg = '<spring:message code="eurb.app.dashboard.newdashboardmsg" />';
		EURB.DashboardDesign.editdashboardtitle = '<spring:message code="eurb.app.dashboard.editdashboardtitle" />';
		EURB.DashboardDesign.editdashboardmsg = '<spring:message code="eurb.app.dashboard.editdashboardmsg" />';
		EURB.DashboardDesign.editdashboard = '<spring:message code="eurb.app.dashboard.editdashboard" />';
		EURB.DashboardDesign.deldashboardtitle = '<spring:message code="eurb.app.dashboard.deldashboardtitle" />';
		EURB.DashboardDesign.deldashboardmsg = '<spring:message code="eurb.app.dashboard.deldashboardmsg" />';
		EURB.DashboardDesign.deletedashboard = '<spring:message code="eurb.app.dashboard.deletedashboard" />';
		
		EURB.DashboardDesign.addColAction = '<spring:url value="/dashboard/dashboard-design/dashboardAddCol.spy" />';
		EURB.DashboardDesign.delColAction = '<spring:url value="/dashboard/dashboard-design/dashboardDelCol.spy" />';
		EURB.DashboardDesign.addItemAction= '<spring:url value="/dashboard/dashboard-design/dashboardAddItem.spy" />';
		EURB.DashboardDesign.delItemAction= '<spring:url value="/dashboard/dashboard-design/dashboardDelItem.spy" />';
		EURB.DashboardDesign.moveItemAction= '<spring:url value="/dashboard/dashboard-design/dashboardMoveItem.spy" />';
		EURB.DashboardDesign.editItemAction = '<spring:url value="/dashboard/dashboard-design/dashboardEditItem.spy" />';
		EURB.DashboardDesign.findChartsInReportAction = '<spring:url value="/builder/report/reportChartSearch.spy" />';
		EURB.DashboardDesign.newDashboardAction = '<spring:url value="/dashboard/dashboard-design/newDashboard.spy" />';
		EURB.DashboardDesign.editDashboardAction = '<spring:url value="/dashboard/dashboard-design/editDashboard.spy" />';
		EURB.DashboardDesign.delDashboardAction = '<spring:url value="/dashboard/dashboard-design/deleteDashboard.spy" />';
		EURB.DashboardDesign.runChart='${baseUrl}builder/report/get-reportchart';
		
		EURB.DashboardDesign.userDashboardId = '${userDashboardId}';
		
		EURB.DashboardDesign.isDesignMode = ${isDesignMode};
		
		EURB.DashboardDesign.reportStore = new Ext.data.ArrayStore({
	        id: 0,
	        fields: [
	            'id',
	            'versionId',
	            'name',
	            'categoryId'
	        ],
	        data: ${reportDesignsComboContent}
	    });
		
		EURB.DashboardDesign.chartStore = new Ext.data.JsonStore({
		    // store configs
		    id: 0,
		    url: EURB.DashboardDesign.findChartsInReportAction,
		    storeId: 'chartStore',
		    // reader configs
		    root: 'data',
		    idProperty: 'id',
		    fields: ['id', 'name']
		});
		
		Ext.onReady(function(){

			////////////////report designs combo////////////////
			EURB.DashboardDesign.designCombo = new Ext.form.ComboBox({
				name: 'reportDesign',
				hiddenName: 'reportDesign',
				fieldLabel: EURB.DashboardDesign.reportDesign,
				typeAhead: true,
				triggerAction: 'all',
				lazyRender:true,
				mode: 'local',
				store: EURB.DashboardDesign.reportStore,
				valueField: 'id',
				displayField: 'name',
				forceSelection: true,
			    allowBlank: true,
			    listeners:{
			    	select: function(combo,record,index){
			    		EURB.DashboardDesign.chartStore.removeAll();
			    		EURB.DashboardDesign.chartStore.reload({ params: {reportDesign: record.id} });
			    		EURB.DashboardDesign.chartCombo.clearValue();
			    	}
			    }
			});

			EURB.DashboardDesign.chartCombo = new Ext.form.ComboBox({
				name: 'reportChart',
				hiddenName: 'reportChart',
				fieldLabel: EURB.DashboardDesign.reportChart,
				typeAhead: true,
				triggerAction: 'all',
				lazyRender:true,
				mode: 'local',
				store: EURB.DashboardDesign.chartStore,
				valueField: 'id',
				displayField: 'name',
				forceSelection: true,
			    allowBlank: true
			});
			
		    // NOTE: This is an example showing simple state management. During development,
		    // it is generally best to disable state management as dynamically-generated ids
		    // can change across page loads, leading to unpredictable results.  The developer
		    // should ensure that stable state ids are set for stateful components in real apps.
		    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		    Ext.example = {};
		    Ext.example.shortBogusMarkup = EURB.DashboardDesign.newMessage;
		    
		    var addNewColHandler = function() {
				var o = {
					callback: function(options, success, response) {
						if(true !== success) {
							showError(response.responseText);
							return;
						}
						try {
							var o = Ext.decode(response.responseText);
						}
						catch(e) {
							showError(response.responseText, EURB.unableToDecodeJSON);
							return;
						}
						if(true !== o.success) {
							showError(o.error || o.message || EURB.unknownError);
							return;
						}
						var portal = EURB.mainPanel.items.get(0);
						var cols = portal.items;
						var colWidth = 1.0 / (cols.length + 1)
						for(var i = 0 ; i < cols.length; i++) {
							cols.get(i).columnWidth = colWidth;
						}
						if(portal.items && portal.items.length > 0) {
							portal.add({
								id: o.affectedIds[0],
				                columnWidth:colWidth,
				                style:'padding:10px 0 10px 10px'
				            });
						} else {
							portal.add({
								id: o.affectedIds[0],
				                columnWidth:colWidth,
				                style:'padding:10px'
				            });
						}
						portal.doLayout();
					}
					,url:EURB.DashboardDesign.addColAction
					,method:'post'
					,scope:this
					,params:{
						cmd: 'addColumn',
						dashboard: EURB.DashboardDesign.userDashboardId
					}
				};
				Ext.Ajax.request(o);
			};
		    
		    var editDashboardItemForm = new Ext.form.FormPanel({
				baseCls: 'x-plain',
				labelWidth: 120,
				url:EURB.DashboardDesign.editItemAction,
				defaultType: 'textfield',
				
				items: [{
			    name: 'dashboard'
				    ,xtype: 'hidden'
				},{
				    name: 'item'
				    ,xtype: 'hidden'
				},{
				    fieldLabel: EURB.DashboardDesign.itemTitle
				    ,name: 'title'
				    ,hiddenName: 'title'
				    ,anchor:'100%'  // anchor width by percentage
				    ,allowBlank:false
				},{
				    fieldLabel: EURB.DashboardDesign.colWidth
				    ,name: 'colWidth'
				    ,anchor: '100%'  // anchor width by percentage
					,xtype: 'numberfield'
					,allowBlank:true
				},{
				    fieldLabel: EURB.DashboardDesign.itemHeight
				    ,name: 'height'
				    ,anchor: '100%'  // anchor width by percentage
					,xtype: 'numberfield'
					,allowBlank:true
				},EURB.DashboardDesign.designCombo, EURB.DashboardDesign.chartCombo,{
				    fieldLabel: EURB.DashboardDesign.itemContent
				    ,name: 'content'
				    ,anchor:'100%'  // anchor width by percentage
				    ,allowBlank:true
					,xtype: 'htmleditor'
				}]
		    });
	        var editDashboardItemWindow = new Ext.Window({
				title: EURB.DashboardDesign.editDashboardItem,
				width: 650,
				height:380,
				minWidth: 300,
				closeAction: 'hide',
				minHeight: 180,
				layout: 'fit',
				plain:true,
				bodyStyle:'padding:5px;',
				buttonAlign:'center',
				items: editDashboardItemForm,
			    buttons: [{
			        text:Ext.MessageBox.buttonText.ok,
			        handler: function() {
			        	if(editDashboardItemForm.getForm().isValid()) {
			        		editDashboardItemForm.getForm().submit({
		                        url: EURB.DashboardDesign.editItemAction,
		                        success: function(form, action) {
		                        	/*var item = form.findField('item').getValue();
		                        	var panel = Ext.getCmp(item+'');
		                        	panel.title = form.findField('title').getValue();
		                        	panel.height = form.findField('height').getValue();
		                        	panel.colWidth = form.findField('colWidth').getValue();
		                        	panel.itemContent = form.findField('content').getValue();
		                        	panel.reportDesign = form.findField('reportDesign').getValue();
		                        	panel.reportChart = form.findField('reportChart').getValue();
		                        	if(!panel.reportDesign) {
		                        		panel.html = form.findField('content').getValue();
		                        	}
		                        	var portal = EURB.mainPanel.items.get(0);
									editDashboardItemWindow.hide();
									Ext.get(options.itemId+'').dom.childNodes[1]
									portal.doLayout();*/
									location.reload(true);
		                        },
		                        failure: function(form, action) {
		                            Ext.Msg.alert('Error!');
		                        }
		                    });
			        	}
			        }
			    },{
			        text: Ext.MessageBox.buttonText.cancel,
			        handler: function(){
			            editDashboardItemWindow.hide();
			        }
			    }]
			});
		    
		    var tools = EURB.DashboardDesign.isDesignMode ? [{
		        id:'gear',
		        handler: function(e, target, panel){
		        	var frm = editDashboardItemForm.getForm();
		        	EURB.DashboardDesign.chartStore.removeAll();
		        	if(panel.reportDesign) {
		        		EURB.DashboardDesign.chartStore.reload({ params: {reportDesign: panel.reportDesign} });
		        	}
		        	frm.reset();
		        	var record = new Ext.data.Record({dashboard:EURB.DashboardDesign.userDashboardId, item: panel.id, height: panel.height, colWidth: panel.colWidth, content: panel.itemContent, title: panel.title, reportDesign: panel.reportDesign, reportChart: panel.reportChart});
		        	frm.loadRecord(record);
		        	editDashboardItemWindow.show(panel.id+'', function(){EURB.DashboardDesign.chartCombo.setValue(panel.reportChart);});

		        }
		    },{
		        id:'close',
		        handler: function(e, target, panel){
		            
		            var o = {
						callback: function(options, success, response) {
							if(true !== success) {
								showError(response.responseText);
								return;
							}
							try {
								var o = Ext.decode(response.responseText);
							}
							catch(e) {
								showError(response.responseText, EURB.unableToDecodeJSON);
								return;
							}
							if(true !== o.success) {
								showError(o.error || o.message || EURB.unknownError);
								return;
							}
							panel.ownerCt.remove(panel, true);
						}
						,url:EURB.DashboardDesign.delItemAction
						,method:'post'
						,scope:this
						,params:{
							cmd: 'delItem',
							dashboard: EURB.DashboardDesign.userDashboardId,
							item: panel.id
						}
					};
					Ext.Ajax.request(o);
		        }
		    }] : [];
		    
		    var dashboardItems = ${dashboardInitialState};
			for(var i = 0 ; i < dashboardItems.length; i++) {
				for( var j = 0; j < dashboardItems[i].items.length; j++) {
					dashboardItems[i].items[j].tools = tools;
					if(dashboardItems[i].items[j].reportDesign) {
						if(dashboardItems[i].items[j].reportChart) {
							var o = {
								 url:EURB.DashboardDesign.runChart + dashboardItems[i].items[j].reportDesign + '-c' + dashboardItems[i].items[j].reportChart + '.spy'
								,method:'get'
								,callback: function(options, success, response) {
									if(true !== success) {
										this.showError(response.responseText);
										return;
									}
									try {
										var o = Ext.decode(response.responseText);
									}
									catch(e) {
										this.showError(response.responseText, EURB.unableToDecodeJSON);
										return;
									}
									if(true !== o.success) {
										this.showError(o.error || o.message || EURB.unknownError);
										return;
									}
									createChart(o.data, Ext.get(options.itemId+'').dom.childNodes[1], options.itemHeight);
								}
								,scope:this
								,itemId: dashboardItems[i].items[j].id
								,itemHeight: dashboardItems[i].items[j].height
								,colWidth: dashboardItems[i].items[j].colWidth
								,itemContent: dashboardItems[i].items[j].html
								,reportDesign: dashboardItems[i].items[j].reportDesign
								,reportChart: dashboardItems[i].items[j].reportChart
							};
							Ext.Ajax.request(o);
						}
					}
				}
			}
		    
			var topToolbar = new Array();
			topToolbar.push(new Ext.Toolbar.TextItem(EURB.DashboardDesign.selectDashboard));
			topToolbar.push(new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'dashId',
			            'dashName'
			        ],
			        data: ${dashboardComboContent}
			    }),
			    <%=selectedDashboard == null ? "" : "value: "+selectedDashboard+","%>
			    forceSelection: true,
			    valueField: 'dashId',
			    displayField: 'dashName',
			    listeners: {
			    	'select' : function(thiz, newValue, oldValue) {
			    		if(EURB.DashboardDesign.isDesignMode) {
			    			if(newValue.id == -1) {
			    				Ext.Msg.prompt(EURB.DashboardDesign.newdashboardtitle, EURB.DashboardDesign.newdashboardmsg, function(btn, text){
			    				    if (btn == 'ok'){
			    				    	var o = {
		    								callback: function(options, success, response) {
		    									if(true !== success) {
		    										showError(response.responseText);
		    										return;
		    									}
		    									try {
		    										var o = Ext.decode(response.responseText);
		    									}
		    									catch(e) {
		    										showError(response.responseText, EURB.unableToDecodeJSON);
		    										return;
		    									}
		    									if(true !== o.success) {
		    										showError(o.error || o.message || EURB.unknownError);
		    										return;
		    									}
		    									window.location.href = EURB.baseURL+'dashboard'+o.affectedIds[0]+'-design.spy';
		    								}
		    								,url:EURB.DashboardDesign.newDashboardAction
		    								,method:'post'
		    								,scope:this
		    								,params:{
		    									cmd: 'newDashboard',
		    									dashboardName: text
		    								}
		    							};
		    							Ext.Ajax.request(o);
			    				    }
			    				});
			    			} else {
			    				window.location.href = EURB.baseURL+'dashboard'+newValue.id+'-design.spy';
			    			}
			    		} else {
			    			window.location.href = EURB.baseURL+'dashboard'+newValue.id+'.spy';
			    		}
			    	}
			    }
			}));
			
			if(EURB.DashboardDesign.isDesignMode) {
				topToolbar.push({
				 text:EURB.DashboardDesign.editdashboard
				,iconCls:'icon-pencil'
				,listeners:{
					 scope:this
					,click:{fn: function() {
						Ext.Msg.prompt(EURB.DashboardDesign.editdashboardtitle + "\"" + "${selectedDashboardTitle}" + "\"", EURB.DashboardDesign.editdashboardmsg, function(btn, text){
	    				    if (btn == 'ok'){
	    				    	var o = {
    								callback: function(options, success, response) {
    									if(true !== success) {
    										showError(response.responseText);
    										return;
    									}
    									try {
    										var o = Ext.decode(response.responseText);
    									}
    									catch(e) {
    										showError(response.responseText, EURB.unableToDecodeJSON);
    										return;
    									}
    									if(true !== o.success) {
    										showError(o.error || o.message || EURB.unknownError);
    										return;
    									}
    									window.location.href = EURB.baseURL+'dashboard'+o.affectedIds[0]+'-design.spy';
    								}
    								,url:EURB.DashboardDesign.editDashboardAction
    								,method:'post'
    								,scope:this
    								,params:{
    									cmd: 'editDashboard',
    									dashboard: EURB.DashboardDesign.userDashboardId,
    									dashboardName: text
    								}
    							};
    							Ext.Ajax.request(o);
	    				    }
	    				});
						},buffer:200}
					}
				});
				topToolbar.push({
				 text:EURB.DashboardDesign.deletedashboard
				,iconCls:'icon-delete'
				,listeners:{
					 scope:this
					,click:{fn: function() {
						Ext.Msg.show({
						   title:EURB.DashboardDesign.deldashboardtitle,
						   msg: EURB.DashboardDesign.deldashboardmsg,
						   buttons: Ext.Msg.OKCANCEL,
						   fn: function(btn, text){
		    				    if (btn == 'ok'){
		    				    	var o = {
	    								callback: function(options, success, response) {
	    									if(true !== success) {
	    										showError(response.responseText);
	    										return;
	    									}
	    									try {
	    										var o = Ext.decode(response.responseText);
	    									}
	    									catch(e) {
	    										showError(response.responseText, EURB.unableToDecodeJSON);
	    										return;
	    									}
	    									if(true !== o.success) {
	    										showError(o.error || o.message || EURB.unknownError);
	    										return;
	    									}
	    									window.location.href = EURB.baseURL+'dashboard-design.spy';
	    								}
	    								,url:EURB.DashboardDesign.delDashboardAction
	    								,method:'post'
	    								,scope:this
	    								,params:{
	    									cmd: 'delDashboard',
	    									dashboard: EURB.DashboardDesign.userDashboardId
	    								}
	    							};
	    							Ext.Ajax.request(o);
		    				    }
		    				},
						   icon: Ext.MessageBox.QUESTION
						});
					}
				}
				}
				});
				topToolbar.push('->');
				topToolbar.push({
				 text:EURB.DashboardDesign.newItem
				,iconCls:'icon-plus'
				,listeners:{
					 scope:this
					,click:{fn: function() {
						var portal = EURB.mainPanel.items.get(0);
						var cols = portal.items;
						if(!cols || cols.length <= 0) {
							addNewColHandler();
							return;
						}
						var minCol, minColIndex;
						for(var i = 0 ; i < cols.length; i++) {
							if(i == 0 || minCol > cols.get(i).items.length) {
								minCol = cols.get(i).items.length;
								minColIndex = i;
							}
						}
						var o = {
								callback: function(options, success, response) {
									if(true !== success) {
										showError(response.responseText);
										return;
									}
									try {
										var o = Ext.decode(response.responseText);
									}
									catch(e) {
										showError(response.responseText, EURB.unableToDecodeJSON);
										return;
									}
									if(true !== o.success) {
										showError(o.error || o.message || EURB.unknownError);
										return;
									}
									cols.get(minColIndex).add({
										id: o.affectedIds[0],
					                    title: EURB.DashboardDesign.newTitle,
					                    html: Ext.example.shortBogusMarkup,
										tools: tools,
										itemContent: Ext.example.shortBogusMarkup,
										itemId: o.affectedIds[0]
					                });
									portal.doLayout();
								}
								,url:EURB.DashboardDesign.addItemAction
								,method:'post'
								,scope:this
								,params:{
									cmd: 'addItem',
									dashboard: EURB.DashboardDesign.userDashboardId,
									column: cols.get(minColIndex).id,
									title: EURB.DashboardDesign.newTitle,
									content: Ext.example.shortBogusMarkup
								}
							};
							Ext.Ajax.request(o);
						},buffer:200}
					}
				});
				
				topToolbar.push({
				 text:EURB.DashboardDesign.newCol
				,iconCls:'icon-add-col'
				,listeners:{
					 scope:this
					,click:{fn: addNewColHandler,buffer:200}
					}
				});
				
				topToolbar.push({
				 text:EURB.DashboardDesign.delCol
				,iconCls:'icon-del-col'
				,listeners:{
					 scope:this
					,click:{fn: function() {
							Ext.Msg.prompt(EURB.DashboardDesign.delColTitle, EURB.DashboardDesign.delColMessage, function(btn, text){
							    if (btn == 'ok'){
									var portal = EURB.mainPanel.items.get(0);
									var cols = portal.items;
									var toDelete = parseInt(text);
									if(toDelete > cols.length || toDelete <= 0) {
										return;
									}
							    	var o = {
										callback: function(options, success, response) {
											if(true !== success) {
												showError(response.responseText);
												return;
											}
											try {
												var o = Ext.decode(response.responseText);
											}
											catch(e) {
												showError(response.responseText, EURB.unableToDecodeJSON);
												return;
											}
											if(true !== o.success) {
												showError(o.error || o.message || EURB.unknownError);
												return;
											}
											if(cols.length > 1) {
												var colWidth = 1.0 / (cols.length - 1)
												for(var i = 0 ; i < cols.length; i++) {
													cols.get(i).columnWidth = colWidth;
												}
											}
											portal.remove(toDelete - 1);
											
											if(toDelete == 1) {
												cols.get(toDelete - 1).getEl().dom.style.padding = '10px';
											}
											
											portal.doLayout();
										}
										,url:EURB.DashboardDesign.delColAction
										,method:'post'
										,scope:this
										,params:{
											cmd: 'deleteColumn',
											dashboard: EURB.DashboardDesign.userDashboardId,
											column: cols.get(toDelete - 1).id
										}
									};
									Ext.Ajax.request(o);
							    }
							});
						},buffer:200}
					}
				});
			}
			
		    EURB.mainPanel.items.add(new Ext.ux.Portal({
	            xtype:'portal',
	            region:'center',
	            margins:'35 0 0 0',
	            listeners:{
					 scope:this,
					 drop: function(dropEvent) {
						 var o = {
							callback: function(options, success, response) {
								if(true !== success) {
									showError(response.responseText);
									return;
								}
								try {
									var o = Ext.decode(response.responseText);
								}
								catch(e) {
									showError(response.responseText, EURB.unableToDecodeJSON);
									return;
								}
								if(true !== o.success) {
									showError(o.error || o.message || EURB.unknownError);
									return;
								}
							}
							,url:EURB.DashboardDesign.moveItemAction
							,method:'post'
							,scope:this
							,params:{
								cmd: 'moveItem',
								dashboard: EURB.DashboardDesign.userDashboardId,
								item: dropEvent.panel.id,
								toColumn: dropEvent.column.id,
								toPosition: dropEvent.position
							}
						};
						Ext.Ajax.request(o);
					 }
			    },
	            tbar: topToolbar,
	            items: dashboardItems
	            
	            /*
	             * Uncomment this block to test handling of the drop event. You could use this
	             * to save portlet position state for example. The event arg e is the custom 
	             * event defined in Ext.ux.Portal.DropZone.
	             */
//	            ,listeners: {
//	                'drop': function(e){
//	                    Ext.Msg.alert('Portlet Dropped', e.panel.title + '<br />Column: ' + 
//	                        e.columnIndex + '<br />Position: ' + e.position);
//	                }
//	            }
	        }));
		    EURB.mainPanel.doLayout();
		});
		
		createChart = function(data, index, heightValue){
			var options;
			if(data[0][0] == 'pie'){
				var pieData = [];
				for(i = 0; i < data[1].length; i++){
					pieData.push([data[1][i], data[2][i]/500]);
				}
				options = {
					chart :{
						type: data[0][0],
				        renderTo: index,
				        height:heightValue
					},
					title: {
			           text: data[0][1]
			        },
			        plotOptions: {
		                pie: {
		                    allowPointSelect: true,
		                    cursor: 'pointer',
		                    dataLabels: {
		                        enabled: true,
		                        formatter: function() {
		                            return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
		                        }
		                    }
		                }
		            },
			        series:[{
			        	name: data[0][3],
			        	data: pieData,
			        	type: 'pie'
			        }]
				};
			}
			else if(data[0][0] == 'gauge'){
				count = 0;
				options = {
						chart : {
							type: data[0][count++],
					        renderTo: index,
					        height:heightValue
						},
						title: {
					           text: data[0][count++],
					           style:{
					        	   fontFamily: data[0][count++],
					        	   fontSize: data[0][count++] + 'px',
					        	   color: '#' + data[0][count++]
					           }
					    },
					    series:[{
				        	name: data[0][count++],
				        	data: data[2],
				        	dataLabels: {
				                formatter: function () {
				                    return '<span style="font-family:' +  data[0][(11)] + '!important;font-size: ' + data[0][12] + 'px!important;color: #' + data[0][13] +'!important;">' 
				                    + data[0][10] + ':' + this.y + '</span><br/>';
				                },
				                backgroundColor: {
				                    linearGradient: {
				                        x1: 0,
				                        y1: 0,
				                        x2: 0,
				                        y2: 1
				                    },
				                    stops: [
				                        [0, '#DDD'],
				                        [1, '#FFF']
				                    ]
				                }
				            }
				        }],
				        pane: {
				            startAngle: -150,
				            endAngle: 150
				        },            
				    
					    yAxis: [{
					    	min: 0,
				           	max: 1000,
				           	lineColor: '#' + data[0][count],
				           	tickColor: '#' + data[0][count],
				           	minorTickColor: '#' + data[0][count++],
				           	offset: -25,
				           	lineWidth: 2,
				            labels: {
				                distance: 10,
				                rotation: 'auto'
				            },
				            tickLength: 5,
				            minorTickLength: 5,
				            endOnTick: false
				        }]
				};
			}
			else{
				count = 0;
				options = {
			        chart: {
			           type: data[0][count++],
			           renderTo: index,
			           height:heightValue
			        },
			        title: {
			           text: data[0][count++],
			           style:{
			        	   fontFamily: data[0][count++],
			        	   fontSize: data[0][count++] + 'px',
			        	   color: '#' + data[0][count++]
			           }
			        },
			        series: [{
						name : data[0][count++],
						data : data[2]
			        }],
			        colors : [('#' + data[0][count++]),'#4572A7','#AA4643','#89A54E','#80699B','#3D96AE','#DB843D','#92A8CD','#A47D7C'],
			        xAxis: {
			           categories: data[1]
			           ,labels : {
			        	   style:{
			        		   fontFamily: data[0][(count)],
				        	   fontSize: data[0][(count + 1)] + 'px',
				        	   color: '#' + data[0][(count + 2)]  
			        	   }
			           }
			        },
			        legend: {
			            padding: 3,
			            itemMarginTop: 5,
			            itemMarginBottom: 5,	            
			            layout: 'vertical',
			            itemStyle: {
				           fontFamily: data[0][count++],
					       fontSize: data[0][count++] + 'px',
					       color: '#' + data[0][count++]
				        }            
			        },
			        yAxis: {
			        	title: {
			        		text: data[0][count++]
			        		,style: {
				        		   fontFamily: data[0][(count)],
					        	   fontSize: data[0][(count + 1)] + 'px',
					        	   color: '#' + data[0][(count + 2)] 
				        	   }
				         }, 
				         labels : {
			        	   style:{
			        		   fontFamily: data[0][count++],
				        	   fontSize: data[0][count++] + 'px',
				        	   color: '#' + data[0][count++] 
			        	   }
			           }
			           	
			        }
			     };
			}
			
			chart = new Highcharts.Chart(options);
		};
		
		</script>
	</body>
</html>