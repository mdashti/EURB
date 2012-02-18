Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

EURB.Column.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'dbConfigId', type:'int'}
			,{name:'tableMappingId', type:'int'}
			,{name:'colOrder', type:'int'}
			,{name:'colTypeName', type:'string'}
			,{name:'columnName', type:'string'}
			,{name:'mappedName', type:'string'}
			,{name:'activeForManager', type:'boolean'}
			,{name:'activeForUser', type:'boolean'}
			,{name:'formatPattern', type:'string'}
			,{name:'referencedIdCol', type:'string'}
			,{name:'referencedTable', type:'string'}
			,{name:'referencedValueCol', type:'string'}
			,{name:'staticMapping', type:'string'}
			,{name:'colDataType', type:'int'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.Column.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	,baseParams:{
		dbconfig: EURB.Column.selectedDbConfig,
		table: EURB.Column.selectedTable
	}
	,remoteSort:true
	,sortInfo:{field: 'colOrder', direction: "ASC"}
});

EURB.Column.cols = [{
	 header:EURB.Column.colOrder
	,id:'colOrder'
	,dataIndex:'colOrder'
	,width:10
	,sortable:true
	,editor:new Ext.form.NumberField({
		allowBlank:false
	})
},{
	 header:EURB.Column.columnName
	,id:'columnName'
	,dataIndex:'columnName'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.Column.mappedName
	,id:'mappedName'
	,dataIndex:'mappedName'
	,width:80
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
	,renderer: function(value, element, record, rowIndex , colIndex) {return Ext.isEmpty(value) ? '---' : value;}
},{
	 header:EURB.Column.colTypeName
	,id:'colTypeName'
	,dataIndex:'colTypeName'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.Column.activeForManager
	,id:'activeForManager'
	,dataIndex:'activeForManager'
	,width:25
	,sortable:true
	,editor:new Ext.form.Checkbox()
	,renderer: function(value, element, record, rowIndex , colIndex) {return value ? '<a href="javascript:EURB.Column.columnGrid.deactivateForManagerInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/ok16.png" /></a>' : '<a href="javascript:EURB.Column.columnGrid.activateForManagerInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/cancel16.png" /></a>';}
},{
	 header:EURB.Column.activeForUser
	,id:'activeForUser'
	,dataIndex:'activeForUser'
	,width:25
	,sortable:true
	,editor:new Ext.form.Checkbox()
	,renderer: function(value, element, record, rowIndex , colIndex) {return value ? '<a href="javascript:EURB.Column.columnGrid.deactivateForUserInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/ok16.png" /></a>' : '<a href="javascript:EURB.Column.columnGrid.activateForUserInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/cancel16.png" /></a>';}
}];

EURB.Column.DBGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.column
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.appMenu.column
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true, dbConfigId:true, tableMappingId:true, formatPattern:true, referencedIdCol:true, referencedTable: true, referencedValueCol:true, staticMapping: true, colDataType:true}
            ,readonlyFields:{colTypeName:true, columnName: true}
            //,disabledFields:{name:true}
            ,formConfig:{
                 labelWidth:90
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.Column.columnGrid.commitChanges();
            }
            , getRowClass:function(record) {
				if(!record.get('id')) {
					return this.newRowCls;
				}
			}
        });

		// create row actions
		this.rowActions = new Ext.ux.grid.RowActions({
			 actions:[{
                 iconCls:'icon-edit-record'
                ,qtip:EURB.editRecord
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.Column.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.Column.store
			,columns:EURB.Column.cols
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				,disableIndexes:['activeForManager','activeForUser']
				//,minChars:2
				,autoFocus:true
			//	,menuStyle:'radio'
			}), this.rowActions, this.recordForm]
			,viewConfig:{forceFit:true}
			,tbar:[EURB.Column.dbConfigCombo," ","-"," "
			,EURB.Column.tableCombo," ","-"," "/*,{
				 text:EURB.delRecord
				,iconCls:'icon-minus'
				,listeners:{
					 scope:this
					,click:{fn:this.deleteSelectedRecords,buffer:200}
				}
			},"-"*/,{
				 text:EURB.enableRecord + ' ' + EURB.Column.forManager
				,iconCls:'icon-activate'
				,listeners:{
					 scope:this
					,click:{fn:this.activateSelectedRecordsForManager,buffer:200}
				}
			},{
				 text:EURB.disableRecord + ' ' + EURB.Column.forManager
				,iconCls:'icon-deactivate'
				,listeners:{
					 scope:this
					,click:{fn:this.deactivateSelectedRecordsForManager,buffer:200}
				}
			},{
				 text:EURB.enableRecord + ' ' + EURB.Column.forUser
				,iconCls:'icon-activate'
				,listeners:{
					 scope:this
					,click:{fn:this.activateSelectedRecordsForUser,buffer:200}
				}
			},{
				 text:EURB.disableRecord + ' ' + EURB.Column.forUser
				,iconCls:'icon-deactivate'
				,listeners:{
					 scope:this
					,click:{fn:this.deactivateSelectedRecordsForUser,buffer:200}
				}
			},"->",{
				 text:EURB.returnBack
				,iconCls:'icon-doorout'
				,listeners:{
					click: function() {
						window.location.href = EURB.baseURL+'management/mapping/db'+EURB.Column.selectedDbConfig+'-table.spy';
					}
				}
			}]
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// create bottom paging toolbar
		this.bbar = ['->'];

		// call parent
		EURB.Column.DBGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.Column.DBGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.Column.DBGrid.superclass.afterRender.apply(this, arguments);
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
            case 'icon-edit-record':
                this.recordForm.show(record, grid.getView().getCell(row, col));
            break;
        }
    }
	,commitChanges:function() {
		var records = this.store.getModifiedRecords();
		if(!records.length) {
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			if(r.data.id) {
				r.data.newRecord = false;
			} else {
				r.data.newRecord = true;
			}
			data.push(r.data);
		}, this);
		var o = {
			 url:EURB.Column.storeAction
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
			this.showError(o.error || EURB.unknownError);
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
	,deleteSelectedRecords:function() {
		var selectedRecords = this.getSelectionModel().getSelections();
		var records = [];
		for(var i=0; i<selectedRecords.length; i++) {
			if(selectedRecords[i].get('id')) {
				records.push(selectedRecords[i]);
			}
		}
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneSavedRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		Ext.Msg.show({
			 title:EURB.areYouSureToDelTitle
			,msg:String.format(EURB.areYouSureToDelete, records.length == 1 ? records[0].get('columnName') : EURB.records)
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
					 url:EURB.Column.removeAction
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
	,activateForUserInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.activateSelectedRecordsForUser();
	}
	,deactivateForUserInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.deactivateSelectedRecordsForUser();
	}
	,activateForManagerInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.activateSelectedRecordsForManager();
	}
	,deactivateForManagerInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.deactivateSelectedRecordsForManager();
	}
	,activateSelectedRecordsForManager:function() {
		this.activateSelectedRecords('manager');
	}
	,activateSelectedRecordsForUser:function() {
		this.activateSelectedRecords('user');
	}
	,activateSelectedRecords:function(target) {
		var selectedRecords = this.getSelectionModel().getSelections();
		var records = [];
		for(var i=0; i<selectedRecords.length; i++) {
			if(selectedRecords[i].get('id')) {
				records.push(selectedRecords[i]);
			}
		}
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneSavedRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			data.push(r.get(this.idName));
		}, this);
		var o = {
			 url:EURB.Column.activateAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'activateData',
				target: target,
				data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
	}
	,deactivateSelectedRecordsForManager:function() {
		this.deactivateSelectedRecords('manager');
	}
	,deactivateSelectedRecordsForUser:function() {
		this.deactivateSelectedRecords('user');
	}
	,deactivateSelectedRecords:function(target) {
		var selectedRecords = this.getSelectionModel().getSelections();
		var records = [];
		for(var i=0; i<selectedRecords.length; i++) {
			if(selectedRecords[i].get('id')) {
				records.push(selectedRecords[i]);
			}
		}
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneSavedRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			data.push(r.get(this.idName));
		}, this);
		var o = {
			 url:EURB.Column.deactivateAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'deactivateData',
				target: target,
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
//Ext.reg('column.dbgrid', EURB.Column.DBGrid);
EURB.Column.columnGrid = new EURB.Column.DBGrid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.Column.columnGrid);
    EURB.mainPanel.doLayout(); 
});