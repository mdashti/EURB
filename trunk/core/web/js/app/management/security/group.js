EURB.Group.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'groupName', type:'string'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.Group.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	//,baseParams:{}
	,remoteSort:true
	,listeners:{
		load: function(g) {
			if(EURB.Group.groupGrid) {
		    	EURB.Group.groupGrid.getSelectionModel().selectRow(0);
			}
		} // Allow rows to be rendered.
	}
});

EURB.Group.cols = [{
	 header:EURB.Group.groupName
	,id:'groupName'
	,dataIndex:'groupName'
	,width:40
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
}];

EURB.Group.GroupGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	region:'center'
	,layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.group
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.appMenu.group
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true}
            //,readonlyFields:{password:true}
            //,disabledFields:{name:true}
            ,formConfig:{
                 labelWidth:90
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.Group.groupGrid.commitChanges();
            }
            , getRowClass:function(record) {
				if(record.get('newRecord')) {
					return this.newRowCls;
				}
				
				return '';
			}
        });
		// create row actions
		this.rowActions = new Ext.ux.grid.RowActions({
			 actions:[{
				 iconCls:'icon-minus'
				,qtip:EURB.delRecord
				,style:'margin:0 0 0 3px'
			},{
                 iconCls:'icon-edit-record'
                ,qtip:EURB.Group.changePassword
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.Group.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.Group.store
			,columns:EURB.Group.cols
			,sm: new Ext.grid.RowSelectionModel({
				singleSelect: false,
				listeners: {
				    rowselect: function(sm, row, rec) {
				    	EURB.Group.groupGrid.onRecordSelectionChange(rec);
			        }
			    }
			})
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				//,disableIndexes:['password']
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
		EURB.Group.GroupGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.Group.GroupGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.Group.GroupGrid.superclass.afterRender.apply(this, arguments);
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
            this.onRowAction(this, rec, 'icon-edit-record', 0, 0);
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
                this.recordForm.show(record, grid.getView().getCell(row, col));
            break;
        }
    }
    ,onRecordSelectionChange:function(rec) {
    	var groupId = rec.get('id');
    	var groupName = rec.get('groupName');
    	EURB.Group.Usr.otherUsersStore.reload({params: {groupId: groupId}});
    	EURB.Group.Usr.currentUsersStore.reload({params: {groupId: groupId}});
    	EURB.Group.Usr.currentUsersGrid.setTitle(EURB.Group.Usr.currentUsersFor + groupName);
    	EURB.Group.Usr.otherUsersGrid.setTitle(EURB.Group.Usr.selectableUsersFor + groupName);
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
			 url:EURB.Group.storeAction
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
			,msg:String.format(EURB.areYouSureToDelete, records.length == 1 ? records[0].get('groupName') : EURB.records)
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
					 url:EURB.Group.removeAction
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

// register xtype
//Ext.reg('dbconfig.dbgrid', EURB.Group.GroupGrid);
EURB.Group.groupGrid = new EURB.Group.GroupGrid();

// Generic fields array to use in both store defs.
EURB.Group.Usr.fields = [
	{name: 'id', mapping : 'id', type:'int'},
	{name: 'username', mapping : 'username', type:'string'}
];

// create the data store
EURB.Group.Usr.currentUsersStore = new Ext.data.JsonStore({
	fields : EURB.Group.Usr.fields,
	root   : 'data',
	proxy:new Ext.data.HttpProxy({
		url:EURB.Group.Usr.findCurrentUsersAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    }),
	//baseParams:{},
	remoteSort:false
});
// Column Model shortcut array
EURB.Group.Usr.cols = [
	{ id : 'username', header: EURB.Group.Usr.username, width: 160, sortable: true, dataIndex: 'username'}
];

// declare the source Grid
EURB.Group.Usr.currentUsersGrid = new Ext.grid.GridPanel({
ddGroup          : 'secondGridDDGroup',
    store            : EURB.Group.Usr.currentUsersStore,
    columns          : EURB.Group.Usr.cols,
enableDragDrop   : true,
    stripeRows       : true,
    autoExpandColumn : 'username',
    title            : EURB.Group.Usr.currentUsersFor + '...'
});

EURB.Group.Usr.otherUsersStore = new Ext.data.JsonStore({
    fields : EURB.Group.Usr.fields,
	root   : 'data',
	proxy:new Ext.data.HttpProxy({
		url:EURB.Group.Usr.findSelectableUsersAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    }),
	//baseParams:{},
	remoteSort:false
});

// create the destination Grid
EURB.Group.Usr.otherUsersGrid = new Ext.grid.GridPanel({
ddGroup          : 'firstGridDDGroup',
    store            : EURB.Group.Usr.otherUsersStore,
    columns          : EURB.Group.Usr.cols,
enableDragDrop   : true,
    stripeRows       : true,
    autoExpandColumn : 'username',
    title            : EURB.Group.Usr.selectableUsersFor + '...'
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
				EURB.Group.Usr.currentUsersGrid,
				EURB.Group.Usr.otherUsersGrid
			]/*,
			bbar    : [
				'->', // Fill
				{
					text    : 'Reset both grids',
					handler : function() {
						//refresh source grid
						firstGridStore.loadData(myData);
	
						//purge destination grid
						secondGridStore.removeAll();
					}
				}
			]*/
		}),EURB.Group.groupGrid]
	}));
    EURB.mainPanel.doLayout();

    /****
	 * Setup Drop Targets
	 ***/
	// This will make sure we only drop to the  view scroller element
	var firstGridDropTargetEl = EURB.Group.Usr.currentUsersGrid.getView().scroller.dom;
	var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		ddGroup : 'firstGridDDGroup',
		notifyDrop : function(ddSource, e, data) {
			var records = ddSource.dragData.selections;
			var selectedGroup = EURB.Group.groupGrid.getSelectionModel().getSelected();
			var usersArr = [];
			Ext.each(records, function(r, i) {
				usersArr.push(r.get('username'));
			},this);
			Ext.Ajax.request({
				 url:EURB.Group.Usr.addGroupToUsersAction
				,method:'post'
				,callback:function(options, success, response) {
						try {
							var o = Ext.decode(response.responseText);
						} catch(e) {
							EURB.Group.groupGrid.showError(response.responseText, EURB.unableToDecodeJSON);
							return;
						}
						if(true !== o.success) {
							EURB.Group.groupGrid.showError(o.error || o.message || EURB.unknownError);
							return;
						}
						Ext.each(records, ddSource.grid.store.remove,
								ddSource.grid.store);
						EURB.Group.Usr.currentUsersGrid.store.add(records);
						EURB.Group.Usr.currentUsersGrid.store.sort('username', 'ASC');
					}
				,scope:this
				,params:{
					cmd: 'addGroupToUsersAction',
					groupId: selectedGroup.get('id'),
					userNames:Ext.encode(usersArr)
				}
			});
			return true;
		}
	});

	// This will make sure we only drop to the view scroller element
	var secondGridDropTargetEl = EURB.Group.Usr.otherUsersGrid.getView().scroller.dom;
	var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
		ddGroup : 'secondGridDDGroup',
		notifyDrop : function(ddSource, e, data) {
			var records = ddSource.dragData.selections;
			var selectedGroup = EURB.Group.groupGrid.getSelectionModel().getSelected();
			var usersArr = [];
			Ext.each(records, function(r, i) {
				usersArr.push(r.get('username'));
			},this);
			Ext.Ajax.request({
				 url:EURB.Group.Usr.removeGroupFromUsersAction
				,method:'post'
				,callback:function(options, success, response) {
						try {
							var o = Ext.decode(response.responseText);
						} catch(e) {
							EURB.Group.groupGrid.showError(response.responseText, EURB.unableToDecodeJSON);
							return;
						}
						if(true !== o.success) {
							EURB.Group.groupGrid.showError(o.error || o.message || EURB.unknownError);
							return;
						}
						Ext.each(records, ddSource.grid.store.remove,
								ddSource.grid.store);
						EURB.Group.Usr.otherUsersGrid.store.add(records);
						EURB.Group.Usr.otherUsersGrid.store.sort('username', 'ASC');
					}
				,scope:this
				,params:{
					cmd: 'removeGroupFromUsersAction',
					groupId: selectedGroup.get('id'),
					userNames:Ext.encode(usersArr)
				}
			});
			return true;
		}
	});
});