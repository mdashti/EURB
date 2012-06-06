EURB.Schedule.reportsStore = new Ext.data.JsonStore({
    url: EURB.Schedule.reportSearchAction,
    fields: [
        {name:'id', type:'int'},
        {name:'name', type:'string'} 
    ]
    ,totalProperty:'totalCount'
	,root:'data'
	,autoLoad: true
});

EURB.Schedule.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'scheduleName', type:'string'}
			,{name:'username', type:'string'}
			,{name:'reportId', type:'int'}
			,{name:'reportName', type:'string'}
			,{name:'scheduleDescription', type:'string'}
			,{name:'startDateTimeString', type:'string'}
			,{name:'scheduleTypeName', type:'string'}
			,{name:'nextFireDateString', type:'string'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.Schedule.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	//,baseParams:{}
	//,remoteSort:true
});

EURB.Schedule.cols = [{
	 header:EURB.Schedule.reportName
	,id:'reportName'
	,dataIndex:'reportName'
	,width:40
	,sortable:true
},{
	 header:EURB.Schedule.scheduleDescription
	,id:'scheduleDescription'
	,dataIndex:'scheduleDescription'
	,width:40
	,sortable:true
},{
	 header:EURB.Schedule.startDateTimeString
	,id:'startDateTimeString'
	,dataIndex:'startDateTimeString'
	,width:40
	,sortable:true
},{
	 header:EURB.Schedule.scheduleTypeName
	,id:'scheduleTypeName'
	,dataIndex:'scheduleTypeName'
	,width:40
	,sortable:true
},{
	 header:EURB.Schedule.nextFireDateString
	,id:'nextFireDateString'
	,dataIndex:'nextFireDateString'
	,width:40
	,sortable:true
}];

EURB.Schedule.ScheduleGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	region:'center'
	,layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.schedule
	,initComponent:function() {

		var changePasswordForm = new Ext.form.FormPanel({
					// form as the only item in window
			xtype : 'form',
			labelWidth : 80,
			frame : true,
			items : [{
			    name: 'scheduleName'
			    ,allowBlank:true
			    ,xtype: 'hidden'
			},new Ext.form.ComboBox({
				fieldLabel:EURB.Schedule.reportName,
				hiddenName: 'reportId',
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: EURB.Schedule.reportsStore,
			    valueField: 'id',
			    displayField: 'name',
			    forceSelection: true,
			    allowBlank: false,
			    editable: false,
			    anchor:'100%'  // anchor width by percentage

			}),{
			    fieldLabel: EURB.Schedule.scheduleDescription
			    ,name: 'description'
			    ,anchor:'100%'  // anchor width by percentage
			    ,allowBlank:false
			    ,xtype: 'textfield'
			},{
		        xtype: 'radiogroup',
		        fieldLabel: EURB.Schedule.scheduleTypeName,
		        columns: 4,
		        items: [
		            {boxLabel: EURB.Schedule.scheduleTypeOnce, name: 'scheduleType', inputValue: 0},
		            {boxLabel: EURB.Schedule.scheduleTypeHourly, name: 'scheduleType', inputValue: 5, checked: true},
		            {boxLabel: EURB.Schedule.scheduleTypeDaily, name: 'scheduleType', inputValue: 1},
		            {boxLabel: EURB.Schedule.scheduleTypeWeekdays, name: 'scheduleType', inputValue: 4},
		            {boxLabel: EURB.Schedule.scheduleTypeWeekly, name: 'scheduleType', inputValue: 2},
		            {boxLabel: EURB.Schedule.scheduleTypeMonthly, name: 'scheduleType', inputValue: 3},
		            {boxLabel: EURB.Schedule.scheduleTypeCron, name: 'scheduleType', inputValue: 6}
		        ]
		    }, {
			// column layout with 2 columns
			layout : 'column'

			// defaults for columns
			,
			defaults : {
				columnWidth : 0.5,
				layout : 'form',
				border : false,
				xtype : 'panel'
			},
			items : [{
				// left column
				// defaults for fields
				defaults : {
					anchor : '100%'
				},
				items : [new Ext.form.DateField({
		            	plugins: [Ext.ux.JalaliDatePlugin],
		                format: 'B/Q/R',
		                allowBlank: false,
		                fieldLabel: EURB.Schedule.startDate
					    ,name: 'startDate'
					    ,anchor:'45%'  // anchor width by percentage
					    ,cls: 'left-align-txt'
		        }),{
			        xtype: 'compositefield',
			        fieldLabel: EURB.Schedule.startTime,
			        combineErrors: false,
			        items: [
			           {
			                //the width of this field in the HBox layout is set directly
			                //the other 2 items are given flex: 1, so will share the rest of the space
			                width:          50,
			
			
			                xtype:          'combo',
			                mode:           'local',
			                value:          'AM',
			                triggerAction:  'all',
			                forceSelection: true,
			                editable:       false,
			                name:           'startAmPm',
			                hiddenName:     'startAmPm',
			                displayField:   'name',
			                valueField:     'value',
			                store:          new Ext.data.JsonStore({
			                    fields : ['name', 'value'],
			                    data   : [
			                        {name : EURB.Schedule.startTimeAM,   value: 'AM'},
			                        {name : EURB.Schedule.startTimePM,  value: 'PM'}
			                    ]
			                })
			            }, {
			               xtype: 'displayfield',
			               value: ' '
			           },{
			               name : 'startMinute',
			               xtype: 'numberfield',
			               width: 48,
			               allowBlank: false,
			               cls: 'left-align-txt'
			           }, {
			               xtype: 'displayfield',
			               value: ':'
			           }, {
			               name : 'startHour',
			               xtype: 'numberfield',
			               width: 48,
			               allowBlank: false,
			               cls: 'left-align-txt'
			           }
			        ]
			    }]}, {
					// right column
					// defaults for fields
					defaults : {
						anchor : '100%'
					},
					bodyStyle : 'padding:0 18px 0 0',
					items : [{
						xtype : 'textfield',
						fieldLabel : EURB.Schedule.cronExpression,
						name : 'cron',
						cls: 'left-align-txt'
					},{
						xtype : 'textfield',
						fieldLabel : EURB.Schedule.numberOfHours,
						name : 'hours',
						cls: 'left-align-txt'
					}]
				}]
			}, {
				// bottom textarea
				fieldLabel : EURB.Schedule.recepients,
				xtype : 'textarea',
				anchor : '-18 -80',
				name : 'recipients',
				cls: 'left-align-txt'
			}]
		});
        this.changePasswordForm = changePasswordForm;
        var changePasswordWindow = new Ext.Window({
			title: EURB.Schedule.changePassword,
			width: 600,
			height:350,
			minWidth: 300,
			closeAction: 'hide',
			minHeight: 150,
			layout: 'fit',
			plain:true,
			bodyStyle:'padding:5px;',
			buttonAlign:'center',
			items: changePasswordForm,
		    buttons: [{
		        text:Ext.MessageBox.buttonText.ok,
		        handler: function() {
		        	if(changePasswordForm.getForm().isValid()) {
		        		changePasswordForm.getForm().submit({
	                        url: EURB.Schedule.storeAction,
	                        success: function(form, action) {
	                            alert('Yes! Success!');  // I don't get this
	                        },
	                        failure: function(form, action) {
	                            alert('failed.');  // I don't get this
	                        }
	                    });
		        	}
		        }
		    },{
		        text: Ext.MessageBox.buttonText.cancel,
		        handler: function(){
		            changePasswordWindow.hide();
		        }
		    }]
		});
		this.changePasswordWindow = changePasswordWindow;
		
		// create row actions
		this.rowActions = new Ext.ux.grid.RowActions({
			 actions:[{
				 iconCls:'icon-minus'
				,qtip:EURB.delRecord
				,style:'margin:0 0 0 3px'
			},{
                 iconCls:'icon-edit-record'
                ,qtip:EURB.Schedule.changePassword
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.Schedule.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.Schedule.store
			,columns:EURB.Schedule.cols
			,sm: new Ext.grid.RowSelectionModel({
				singleSelect: false,
				listeners: {
				    rowselect: function(sm, row, rec) {
				    	EURB.Schedule.scheduleGrid.onRecordSelectionChange(rec);
			        }
			    }
			})
			,plugins:[this.rowActions]
			,viewConfig:{forceFit:true}
			,tbar:[{
				 text:EURB.addRecord
				,iconCls:'icon-plus'
				,listeners:{
					 scope:this
					,click:{fn:this.addRecord,buffer:200}
				}
			},{
				 text:EURB.delRecord
				,iconCls:'icon-minus'
				,listeners:{
					 scope:this
					,click:{fn:this.deleteSelectedRecords,buffer:200}
				}
			}]
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// call parent
		EURB.Schedule.ScheduleGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.Schedule.ScheduleGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.Schedule.ScheduleGrid.superclass.afterRender.apply(this, arguments);
		//this.getBottomToolbar().add({text:'A test button',iconCls:'icon-info'});
	}
	,addRecord:function() {
        var store = this.store;
        if(store.recordType) {
            var rec = new store.recordType({newRecord:true});
            rec.fields.each(function(f) {
                rec.data[f.name] = f.defaultValue || null;
            });
            rec.commit();
            this.changePasswordWindow.show(this,this.changePasswordFor(rec,true),this);
            return rec;
        }
        return false;
    }
	,onRowAction:function(grid, record, action, row, col) {
        switch(action) {
            case 'icon-minus':
                this.deleteRecord(record);
            break;

            case 'icon-edit-record':
                this.changePasswordWindow.show(grid.getView().getCell(row, col),this.changePasswordFor(record, false),this);
            break;
        }
    }
    ,onRecordSelectionChange:function(rec) {
    	var schedulename = rec.get('schedulename');
    	EURB.Schedule.Grp.otherGroupsStore.reload({params: {scheduleName: schedulename}});
    	EURB.Schedule.Grp.currentGroupsStore.reload({params: {scheduleName: schedulename}});
    	EURB.Schedule.Grp.currentGroupsGrid.setTitle(EURB.Schedule.Grp.currentGroupsFor + schedulename);
    	EURB.Schedule.Grp.otherGroupsGrid.setTitle(EURB.Schedule.Grp.selectableGroupsFor + schedulename);
    }
    ,changePasswordFor:function(record, schedulenameEditable) {
    	var frm = this.changePasswordForm.getForm();
    	var reportField = frm.findField('reportId');
    	frm.reset();
    	frm.loadRecord(record);
		if(reportField) reportField.focus(false,500);
    }
	,commitChanges:function() {
		var records = this.store.getModifiedRecords();
		if(!records.length) {
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			if(r.data.newRecord) {
				r.data.newRecord = true;
			} else {
				r.data.newRecord = false;
			}
			data.push(r.data);
		}, this);
		var o = {
			 url:EURB.Schedule.storeAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'storeData',
				data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
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
			,msg:String.format(EURB.areYouSureToDelete, records.length == 1 ? records[0].get('name') : EURB.records)
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
					 url:EURB.Schedule.removeAction
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
	,listeners: {
		dblclick : function() {
			var sm = this.getSelectionModel();
            var sel = sm.getSelections();
            if(sel.length > 0) {
            	this.onRowAction(this, sel[0], 'icon-edit-record', 0, 0);
            }
		}
	}

});

EURB.Schedule.scheduleGrid = new EURB.Schedule.ScheduleGrid();

// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.Schedule.scheduleGrid);
    EURB.mainPanel.doLayout();
});