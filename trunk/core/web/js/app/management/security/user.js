EURB.User.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'username', type:'string'}
			,{name:'password', type:'string'}
			,{name:'enabled', type:'boolean'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.User.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	//,baseParams:{}
	,remoteSort:true
	,listeners:{
		load: function(g) {
			if(EURB.User.userGrid) {
		    	EURB.User.userGrid.getSelectionModel().selectRow(0);
			}
		} // Allow rows to be rendered.
	}
});

EURB.User.cols = [{
	 header:EURB.User.username
	,id:'username'
	,dataIndex:'username'
	,width:40
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.User.password
	,id:'password'
	,dataIndex:'password'
	,width:40
	,sortable:true
	,editor:{
		xtype: 'textfield'
		,allowBlank:false
		,inputType:'password'
	}
	,hidden:true
	,renderer: function() {return '';}
},{
	 header:EURB.User.enabled
	,id:'enabled'
	,dataIndex:'enabled'
	,width:20
	,sortable:true
	,editor:new Ext.form.ComboBox({
	    typeAhead: true,
	    triggerAction: 'all',
	    lazyRender:true,
	    mode: 'local',
	    store: new Ext.data.ArrayStore({
	        id: 0,
	        fields: [
	            'myId',
	            'displayText'
	        ],
	        data: [[true, EURB.User.enabledEnabled], [false, EURB.User.enabledDisabled]]
	    }),
	    valueField: 'myId',
	    displayField: 'displayText',
	    forceSelection: true,
	    allowBlank: false,
	    editable: false
	})
	,renderer: function(value, element, record, rowIndex , colIndex) {return value ? '<a href="javascript:EURB.User.userGrid.deactivateUserInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/ok16.png" /></a>' : '<a href="javascript:EURB.User.userGrid.activateUserInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/cancel16.png" /></a>';}
}];

EURB.User.UserGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	region:'center'
	,layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.user
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.appMenu.user
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true, testCon:true}
            //,readonlyFields:{password:true}
            //,disabledFields:{name:true}
            ,formConfig:{
                 labelWidth:90
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.User.userGrid.commitChanges();
            }
            , getRowClass:function(record) {
				if(record.get('newRecord')) {
					return this.newRowCls;
				}
				
				return record.get('testCon');
			}
        });

		var changePasswordForm = new Ext.form.FormPanel({
			baseCls: 'x-plain',
			labelWidth: 100,
			url:EURB.User.changePasswordAction,
			defaultType: 'textfield',
			
			items: [{
			    name: 'id'
			    ,allowBlank:false
			    ,xtype: 'hidden'
			},{
			    fieldLabel: EURB.User.username
			    ,name: 'username'
			    ,anchor:'100%'  // anchor width by percentage
			    ,allowBlank:false
				,readOnly: true
				,disabled: true
			    ,cls: 'left-align-txt'
			},{
			    fieldLabel: EURB.User.newPassword
			    ,name: 'newpass'
			    ,anchor: '100%'  // anchor width by percentage
			    ,allowBlank:false
				,xtype: 'textfield'
				,allowBlank:false
				,inputType:'password'
			    ,cls: 'left-align-txt'
			}, {
			    fieldLabel: EURB.User.confirmNewPassword
			    ,name: 'confirmnewpass'
			    ,anchor: '100%' // anchor width by percentage
				,xtype: 'textfield'
				,allowBlank:false
				,inputType:'password'
			    ,cls: 'left-align-txt'
			    ,validator: function(value) {
			    	if(changePasswordForm.getForm().findField('newpass').getValue() == value) {
			    		return true;
			    	} else {
			    		return EURB.User.confirmDidNotMatch;
			    	}
			    }
			}]
	    });
        this.changePasswordForm = changePasswordForm;
        var changePasswordWindow = new Ext.Window({
			title: EURB.User.changePassword,
			width: 300,
			height:150,
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
	                        url: EURB.User.storeAction,
	                        success: function(form, action) {
								editSchedWindow.hide();
	                        	EURB.User.userGrid.store.reload();
	                        },
	                        failure: function(form, action) {
	                            Ext.Msg.alert(EURB.User.errorInStore);
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
                ,qtip:EURB.User.changePassword
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.User.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.User.store
			,columns:EURB.User.cols
			,sm: new Ext.grid.RowSelectionModel({
				singleSelect: false,
				listeners: {
				    rowselect: function(sm, row, rec) {
				    	EURB.User.userGrid.onRecordSelectionChange(rec);
			        }
			    }
			})
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				,disableIndexes:['password']
				//,minChars:2
				,autoFocus:true
			//				,menuStyle:'radio'
			}), this.rowActions, this.recordForm]
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
			},{
				 text:EURB.enableRecord
				,iconCls:'icon-activate'
				,listeners:{
					 scope:this
					,click:{fn:this.activateSelectedRecords,buffer:200}
				}
			},{
				 text:EURB.disableRecord
				,iconCls:'icon-deactivate'
				,listeners:{
					 scope:this
					,click:{fn:this.deactivateSelectedRecords,buffer:200}
				}
			}]
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// create bottom paging toolbar
		this.bbar = new Ext.PagingToolbar({
			 store:this.store
			,displayInfo:true
			,pageSize:EURB.defaultPageLimit
		});

		// call parent
		EURB.User.UserGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.User.UserGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.User.UserGrid.superclass.afterRender.apply(this, arguments);
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
            store.add(rec);
            this.changePasswordWindow.show(this.getView().getCell(0, 0),this.changePasswordFor(rec,true),this);
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
    	var username = rec.get('username');
    	EURB.User.Grp.otherGroupsStore.reload({params: {userName: username}});
    	EURB.User.Grp.currentGroupsStore.reload({params: {userName: username}});
    	EURB.User.Grp.currentGroupsGrid.setTitle(EURB.User.Grp.currentGroupsFor + username);
    	EURB.User.Grp.otherGroupsGrid.setTitle(EURB.User.Grp.selectableGroupsFor + username);
    }
    ,changePasswordFor:function(record, usernameEditable) {
    	var frm = this.changePasswordForm.getForm();
    	var userNameField = frm.findField('username');
    	if(usernameEditable) {
    		userNameField.enable();
    		userNameField.setReadOnly(false);
    	} else {
    		userNameField.disable();
    		userNameField.setReadOnly(true);
    	}
    	frm.reset();
    	frm.loadRecord(record);
    	if(usernameEditable) {
    		userNameField.focus(false,500);
    	} else {
    		frm.findField('newpass').focus(false,500);
    	}
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
			 url:EURB.User.storeAction
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
					 url:EURB.User.removeAction
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
	,activateUserInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.activateSelectedRecords();
	}
	,deactivateUserInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.deactivateSelectedRecords();
	}
	,activateSelectedRecords:function() {
		var records = this.getSelectionModel().getSelections();
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			data.push(r.get(this.idName));
		}, this);
		var o = {
			 url:EURB.User.activateAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'activateData',
				data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
	}
	,deactivateSelectedRecords:function() {
		var records = this.getSelectionModel().getSelections();
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			data.push(r.get(this.idName));
		}, this);
		var o = {
			 url:EURB.User.deactivateAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'deactivateData',
				data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
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

// register xtype
//Ext.reg('dbconfig.dbgrid', EURB.User.UserGrid);
EURB.User.userGrid = new EURB.User.UserGrid();

// Generic fields array to use in both store defs.
EURB.User.Grp.fields = [
	{name: 'id', mapping : 'id', type:'int'},
	{name: 'groupName', mapping : 'groupName', type:'string'}
];

// create the data store
EURB.User.Grp.currentGroupsStore = new Ext.data.JsonStore({
	fields : EURB.User.Grp.fields,
	root   : 'data',
	proxy:new Ext.data.HttpProxy({
		url:EURB.User.Grp.findCurrentGroupsAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    }),
	//baseParams:{},
	remoteSort:false
});
// Column Model shortcut array
EURB.User.Grp.cols = [
	{ id : 'groupName', header: EURB.User.Grp.groupName, width: 160, sortable: true, dataIndex: 'groupName'}
];

// declare the source Grid
EURB.User.Grp.currentGroupsGrid = new Ext.grid.GridPanel({
ddGroup          : 'secondGridDDGroup',
    store            : EURB.User.Grp.currentGroupsStore,
    columns          : EURB.User.Grp.cols,
enableDragDrop   : true,
    stripeRows       : true,
    autoExpandColumn : 'groupName',
    title            : EURB.User.Grp.currentGroupsFor + '...'
});

EURB.User.Grp.otherGroupsStore = new Ext.data.JsonStore({
    fields : EURB.User.Grp.fields,
	root   : 'data',
	proxy:new Ext.data.HttpProxy({
		url:EURB.User.Grp.findSelectableGroupsAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    }),
	//baseParams:{},
	remoteSort:false
});

// create the destination Grid
EURB.User.Grp.otherGroupsGrid = new Ext.grid.GridPanel({
ddGroup          : 'firstGridDDGroup',
    store            : EURB.User.Grp.otherGroupsStore,
    columns          : EURB.User.Grp.cols,
enableDragDrop   : true,
    stripeRows       : true,
    autoExpandColumn : 'groupName',
    title            : EURB.User.Grp.selectableGroupsFor + '...'
});
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(new Ext.Panel({
		layout: 'border',
		items: [new Ext.Panel({
			split:true,
			region:'south',
			height: 300,
			layout       : 'hbox',
			defaults     : { flex : 1 }, //auto stretch
			layoutConfig : { align : 'stretch' },
			items        : [
				EURB.User.Grp.currentGroupsGrid,
				EURB.User.Grp.otherGroupsGrid
			],
			bbar: [EURB.User.Grp.groupSelectDragDropHelp]
		}),EURB.User.userGrid]
	}));
    EURB.mainPanel.doLayout();

    /****
	 * Setup Drop Targets
	 ***/
	// This will make sure we only drop to the  view scroller element
	var firstGridDropTargetEl = EURB.User.Grp.currentGroupsGrid.getView().scroller.dom;
	var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		ddGroup : 'firstGridDDGroup',
		notifyDrop : function(ddSource, e, data) {
			var records = ddSource.dragData.selections;
			var selectedUser = EURB.User.userGrid.getSelectionModel().getSelected();
			var groupsArr = [];
			Ext.each(records, function(r, i) {
				groupsArr.push(r.get('id'));
			},this);
			Ext.Ajax.request({
				 url:EURB.User.Grp.addUserToGroupsAction
				,method:'post'
				,callback:function(options, success, response) {
						try {
							var o = Ext.decode(response.responseText);
						} catch(e) {
							EURB.User.userGrid.showError(response.responseText, EURB.unableToDecodeJSON);
							return;
						}
						if(true !== o.success) {
							EURB.User.userGrid.showError(o.error || o.message || EURB.unknownError);
							return;
						}
						Ext.each(records, ddSource.grid.store.remove,
								ddSource.grid.store);
						EURB.User.Grp.currentGroupsGrid.store.add(records);
						EURB.User.Grp.currentGroupsGrid.store.sort('groupName', 'ASC');
					}
				,scope:this
				,params:{
					cmd: 'addUserToGroupsAction',
					userName: selectedUser.get('username'),
					groupIds:Ext.encode(groupsArr)
				}
			});
			return true;
		}
	});

	// This will make sure we only drop to the view scroller element
	var secondGridDropTargetEl = EURB.User.Grp.otherGroupsGrid.getView().scroller.dom;
	var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
		ddGroup : 'secondGridDDGroup',
		notifyDrop : function(ddSource, e, data) {
			var records = ddSource.dragData.selections;
			var selectedUser = EURB.User.userGrid.getSelectionModel().getSelected();
			var groupsArr = [];
			Ext.each(records, function(r, i) {
				groupsArr.push(r.get('id'));
			},this);
			Ext.Ajax.request({
				 url:EURB.User.Grp.removeUserFromGroupsAction
				,method:'post'
				,callback:function(options, success, response) {
						try {
							var o = Ext.decode(response.responseText);
						} catch(e) {
							EURB.User.userGrid.showError(response.responseText, EURB.unableToDecodeJSON);
							return;
						}
						if(true !== o.success) {
							EURB.User.userGrid.showError(o.error || o.message || EURB.unknownError);
							return;
						}
						Ext.each(records, ddSource.grid.store.remove,
								ddSource.grid.store);
						EURB.User.Grp.otherGroupsGrid.store.add(records);
						EURB.User.Grp.otherGroupsGrid.store.sort('groupName', 'ASC');
					}
				,scope:this
				,params:{
					cmd: 'removeUserFromGroupsAction',
					userName: selectedUser.get('username'),
					groupIds:Ext.encode(groupsArr)
				}
			});
			return true;
		}
	});
});