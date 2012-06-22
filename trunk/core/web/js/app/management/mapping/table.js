EURB.Table.store = new Ext.data.GroupingStore({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'dbConfigId', type:'int'}
			,{name:'catalog', type:'string'}
			,{name:'schema', type:'string'}
			,{name:'tableName', type:'string'}
			,{name:'mappedName', type:'string'}
			,{name:'mappedTypeName', type:'string'}
			,{name:'activeForManager', type:'boolean'}
			,{name:'activeForUser', type:'boolean'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.Table.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	,baseParams:{
		dbconfig: EURB.Table.selectedDbConfig
	}
	,remoteSort:true
	,sortInfo:{field: 'catalog', direction: "ASC"}
	,groupField:'catalog'
});

EURB.Table.cols = [{
	 header:EURB.Table.catalog
	,id:'catalog'
	,dataIndex:'catalog'
	,width:30
	,sortable:true
	,hidden:true
},{
	 header:EURB.Table.schema
	,id:'schema'
	,dataIndex:'schema'
	,width:30
	,sortable:true
},{
	 header:EURB.Table.tableName
	,id:'tableName'
	,dataIndex:'tableName'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.Table.mappedName
	,id:'mappedName'
	,dataIndex:'mappedName'
	,width:80
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
	,renderer: function(value, element, record, rowIndex , colIndex) {return Ext.isEmpty(value) ? '---' : value;}
},{
	 header:EURB.Table.mappedType
	,id:'mappedTypeName'
	,dataIndex:'mappedTypeName'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.Table.activeForManager
	,id:'activeForManager'
	,dataIndex:'activeForManager'
	,width:25
	,sortable:true
	,editor:new Ext.form.Checkbox()
	,renderer: function(value, element, record, rowIndex , colIndex) {return value ? '<a href="javascript:EURB.Table.tableGrid.deactivateForManagerInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/ok16.png" /></a>' : '<a href="javascript:EURB.Table.tableGrid.activateForManagerInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/cancel16.png" /></a>';}
},{
	 header:EURB.Table.activeForUser
	,id:'activeForUser'
	,dataIndex:'activeForUser'
	,width:25
	,sortable:true
	,editor:new Ext.form.Checkbox()
	,renderer: function(value, element, record, rowIndex , colIndex) {return value ? '<a href="javascript:EURB.Table.tableGrid.deactivateForUserInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/ok16.png" /></a>' : '<a href="javascript:EURB.Table.tableGrid.activateForUserInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/cancel16.png" /></a>';}
}];

EURB.Table.TblGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.table
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.appMenu.table
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true, dbConfigId:true, schema:true}
            ,readonlyFields:{mappedTypeName:true, tableName: true, catalog:true}
            //,disabledFields:{name:true}
            ,formConfig:{
                 labelWidth:90
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.Table.tableGrid.commitChanges();
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
            },{
                 iconCls:'icon-grid'
                 ,qtip:EURB.Table.editColumns
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.Table.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.Table.store
			,columns:EURB.Table.cols
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				,disableIndexes:['activeForManager','activeForUser']
				//,minChars:2
				,autoFocus:true
			//				,menuStyle:'radio'
			}), this.rowActions, this.recordForm]
			,view: new Ext.grid.GroupingView({
	            forceFit:true,
	            enableGroupingMenu: false,
	            groupTextTpl: '<b>{text}</b> {[EURB.containing]} {[values.rs.length]} {[EURB.item]}'
        	})
			,loadMask:true
			,tbar:[EURB.Table.dbConfigCombo," ","-"," ",{
				 text:EURB.enableRecord + ' ' + EURB.Table.forManager
				,iconCls:'icon-activate'
				,listeners:{
					 scope:this
					,click:{fn:this.activateSelectedRecordsForManager,buffer:200}
				}
			},{
				 text:EURB.disableRecord + ' ' + EURB.Table.forManager
				,iconCls:'icon-deactivate'
				,listeners:{
					 scope:this
					,click:{fn:this.deactivateSelectedRecordsForManager,buffer:200}
				}
			},{
				 text:EURB.enableRecord + ' ' + EURB.Table.forUser
				,iconCls:'icon-activate'
				,listeners:{
					 scope:this
					,click:{fn:this.activateSelectedRecordsForUser,buffer:200}
				}
			},{
				 text:EURB.disableRecord + ' ' + EURB.Table.forUser
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
						window.location.href = EURB.baseURL+'management/mapping/dbconfig.spy';
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
		EURB.Table.TblGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.Table.TblGrid.superclass.onRender.apply(this, arguments);
		if(this.loadMask){
            this.loadMask = new Ext.LoadMask(this.bwrap,
                    Ext.apply({store:this.store}, this.loadMask));
        }
		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.Table.TblGrid.superclass.afterRender.apply(this, arguments);
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
                
            case 'icon-grid':
            	if(record.get(this.idName)) {
            		window.location.href = EURB.baseURL+'management/mapping/db'+record.get('dbConfigId')+'-table'+record.get(this.idName)+'-column.spy';
            	} else {
            		Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.Table.youShouldSaveTheRecordFisrt).setIcon(Ext.Msg.INFO);
            	}
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
			 url:EURB.Table.storeAction
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
			 url:EURB.Table.activateAction
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
			 url:EURB.Table.deactivateAction
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
//Ext.reg('table.tblGrid', EURB.Table.TblGrid);
EURB.Table.tableGrid = new EURB.Table.TblGrid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.Table.tableGrid);
    EURB.mainPanel.doLayout(); 
});