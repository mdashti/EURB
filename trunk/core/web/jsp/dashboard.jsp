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
		EURB.DashboardDesign = {};
		EURB.DashboardDesign.newItem = '<spring:message code="eurb.app.dashboard.newitem" />';
		EURB.DashboardDesign.newCol = '<spring:message code="eurb.app.dashboard.newcol" />';
		EURB.DashboardDesign.delCol = '<spring:message code="eurb.app.dashboard.delcol" />';
		EURB.DashboardDesign.delColTitle = '<spring:message code="eurb.app.dashboard.delcoltitle" />';
		EURB.DashboardDesign.delColMessage = '<spring:message code="eurb.app.dashboard.delcolmessage" />';
		EURB.DashboardDesign.newMessage = '<spring:message code="eurb.app.dashboard.newmessage" />';
		EURB.DashboardDesign.newTitle = '<spring:message code="eurb.app.dashboard.newtitle" />';
		
		EURB.DashboardDesign.addColAction = '<spring:url value="/dashboard/dashboard-design/dashboardAddCol.spy" />';
		EURB.DashboardDesign.delColAction = '<spring:url value="/dashboard/dashboard-design/dashboardDelCol.spy" />';
		EURB.DashboardDesign.addItemAction= '<spring:url value="/dashboard/dashboard-design/dashboardAddItem.spy" />';
		EURB.DashboardDesign.delItemAction= '<spring:url value="/dashboard/dashboard-design/dashboardDelItem.spy" />';
		EURB.DashboardDesign.moveItemAction= '<spring:url value="/dashboard/dashboard-design/dashboardMoveItem.spy" />';
		
		EURB.DashboardDesign.userDashboardId = '${userDashboardId}';
		
		Ext.onReady(function(){

		    // NOTE: This is an example showing simple state management. During development,
		    // it is generally best to disable state management as dynamically-generated ids
		    // can change across page loads, leading to unpredictable results.  The developer
		    // should ensure that stable state ids are set for stateful components in real apps.
		    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		    Ext.example = {};
		    Ext.example.shortBogusMarkup = EURB.DashboardDesign.newMessage;
		    
		    var tools = [{
		        id:'gear',
		        handler: function(){
		            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
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
		    }];
		    
		    var dashboardItems = ${dashboardInitialState};
			for(var i = 0 ; i < dashboardItems.length; i++) {
				for( var j = 0; j < dashboardItems[i].items.length; j++) {
					dashboardItems[i].items[j].tools = tools;
				}
			}
		    
		    EURB.mainPanel.items.add(new Ext.ux.Portal({
	            xtype:'portal',
	            region:'center',
	            margins:'35 5 5 0',
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
	            tbar: [{
					 text:EURB.DashboardDesign.newItem
					,iconCls:'icon-plus'
					,listeners:{
						 scope:this
						,click:{fn: function() {
							var portal = EURB.mainPanel.items.get(0);
							var cols = portal.items;
							if(!cols || cols.length <= 0) {
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
						                    html: Ext.example.shortBogusMarkup
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
					}, {
					 text:EURB.DashboardDesign.newCol
					,iconCls:'icon-add-col'
					,listeners:{
						 scope:this
						,click:{fn: function() {
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
							},buffer:200}
						}
					}, {
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
					}],
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

		updateChartData = function(charts){
			if(EURB.RunReport.chartCount > 2){
				hValue = 150;
			}
			else{
				hValue = 300;
			}
			for(i = 0; i < charts.length; i++){
				createChart(charts[i], i, hValue);
			}
		};
		</script>
	</body>
</html>