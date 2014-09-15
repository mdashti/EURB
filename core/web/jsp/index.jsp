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
		EURB.Index.AlarmGrid = Ext.extend(Ext.grid.GridPanel, {
			// defaults - can be changed from outside
			region:'center'
			,layout:'fit'
			,border:true
			,stateful:false
			,idName:'id'
			,title: EURB.Index.alarmGridTitle
			,initComponent:function() {
				// create row actions
				this.rowActions = new Ext.ux.grid.RowActions({
					 actions:[{
						 iconCls:'icon-delete-cross'
						,qtip:EURB.delRecord
						,style:'margin:0 0 0 3px'
					}]
		            ,widthIntercept:Ext.isSafari ? 4 : 2
		            ,id:'actions'
		            ,getEditor:Ext.emptyFn
					,css:'vertical-align:middle;'
				});
				this.rowActions.on('action', this.onRowAction, this);
				EURB.Index.cols.push(this.rowActions);
				
				// hard coded - cannot be changed from outside
				var config = {
					store: EURB.Index.store
					,columns:EURB.Index.cols
					,sm: new Ext.grid.RowSelectionModel({
						singleSelect: false
					})
					,plugins:[this.rowActions]
					,viewConfig:{forceFit:true,emptyText: EURB.Index.noMessages}
				};

				// apply config
				Ext.apply(this, config);
				Ext.apply(this.initialConfig, config);

				// call parent
				EURB.Index.AlarmGrid.superclass.initComponent.apply(this, arguments);
			}
			,onRender:function() {
				// call parent
				EURB.Index.AlarmGrid.superclass.onRender.apply(this, arguments);

				// load store
				this.store.load();

			}
			,afterRender:function() {
				EURB.Index.AlarmGrid.superclass.afterRender.apply(this, arguments);
				//this.getBottomToolbar().add({text:'A test button',iconCls:'icon-info'});
			}
			,onRowAction:function(grid, record, action, row, col) {
		        switch(action) {
		            case 'icon-delete-cross':
		                this.deleteRecord(record);
		            break;
		        }
		    }
			,requestCallback:function(options, success, response) {
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

				switch(options.params.cmd) {
					default:
						this.store.commitChanges();
						this.store.reload();
						this.getSelectionModel().clearSelections();
					break;
				}
			}
			,showError:EURB.showError
			,deleteRecord:function(record) {
				this.getSelectionModel().selectRecords([record]);
				this.deleteSelectedRecords();
			}
			,deleteSelectedRecords:function() {
				var records = this.getSelectionModel().getSelections();
				if(!records.length) {
					Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneRecordFisrt).setIcon(Ext.Msg.INFO);
					return;
				}
				Ext.Msg.show({
					 title:EURB.areYouSureToDelTitle
					,msg:String.format(EURB.areYouSureToDelete, records.length == 1 ? EURB.Index.thisMessage : EURB.records)
					,icon:Ext.Msg.QUESTION
					,buttons:Ext.Msg.YESNO
					,scope:this
					,fn:function(response) {
						if('yes' !== response) {
							return;
						}
						var data = [];
						Ext.each(records, function(r, i) {
							data.push(r.get(this.idName));
						}, this);
						var o = {
							 url:EURB.Index.removeAction
							,method:'post'
							,callback:this.requestCallback
							,scope:this
							,params:{
								cmd: 'deleteData',
								data:Ext.encode(data)
							}
						};
						Ext.Ajax.request(o);
					}
				});
			}
		});
		Ext.onReady(function(){
		    EURB.mainPanel.items.add(new Ext.Panel({
		    	layout:'border',
		    	items: [new Ext.Panel({
			    	id: 'detailPanel',
			    	region:'north',
			    	bodyStyle: {
			    		background: '#ffffff!important',
			    		padding: '7px'
		    		},
			    	html: <spring:message code="eurb.app.mainPageContent" />
			    }), new EURB.Index.AlarmGrid()]
		    }));
		    EURB.mainPanel.doLayout();
		});
		</script>
	</body>
</html>