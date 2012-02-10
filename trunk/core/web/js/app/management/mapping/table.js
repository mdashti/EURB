Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

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
        	'exception' : function(proxy, type, action, options, res) {
    	    	Ext.Msg.show({
    	    		title: 'ERROR',
    	    		msg: Ext.util.JSON.decode(res.responseText).message,
    	    		icon: Ext.MessageBox.ERROR,
    	    		buttons: Ext.Msg.OK
    	    	});
    	    }
        }
    })
	//,baseParams:{}
	,remoteSort:true
	,sortInfo:{field: 'tableName', direction: "ASC"}
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
},{
	 header:EURB.Table.activeForUser
	,id:'activeForUser'
	,dataIndex:'activeForUser'
	,width:25
	,sortable:true
	,editor:new Ext.form.Checkbox()
}];

EURB.Table.DBGrid = Ext.extend(Ext.grid.GridPanel, {
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
				if(record.get('newRecord')) {
					return this.newRowCls;
				}
				
				return record.get('testCon');
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
		EURB.Table.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.Table.store
			,columns:EURB.Table.cols
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				//,disableIndexes:['password']
				//,minChars:2
				,autoFocus:true
			//				,menuStyle:'radio'
			}), this.rowActions, this.recordForm]
			,view: new Ext.grid.GroupingView({
	            forceFit:true,
	            enableGroupingMenu: false,
	            groupTextTpl: '<b>{text}</b> {[EURB.containing]} {[values.rs.length]} {[EURB.item]}'
        	})
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
		this.bbar = ['->'];

		// call parent
		EURB.Table.DBGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.Table.DBGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.Table.DBGrid.superclass.afterRender.apply(this, arguments);
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
			if(r.data.newRecord) {
				r.data.newRecord = true;
			} else {
				r.data.newRecord = false;
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
			this.showError(o.error || EURB.unknownError);
			return;
		}

		switch(options.params.cmd) {
			default:
				this.store.reload();
				this.getSelectionModel().clearSelections();
			break;
		}
	}
	,showError:EURB.showError
	,copyRecord: function(record) {
		var store = this.store;
        if(store.recordType) {
            var rec = new store.recordType({newRecord:true});
            rec.fields.each(function(f) {
            	if(f.name != this.idName && f.name != "name") {
                	rec.data[f.name] = record.data[f.name];
            	} else {
            		rec.data[f.name] = '';
            	}
            });
            store.add(rec);
            this.onRowAction(this, rec, 'icon-edit-record', 0, 0);
            return rec;
        }
        return false;
	}
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
					 url:EURB.Table.removeAction
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
			 url:EURB.Table.activateAction
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
			 url:EURB.Table.deactivateAction
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
//Ext.reg('table.dbgrid', EURB.Table.DBGrid);
EURB.Table.tableGrid = new EURB.Table.DBGrid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.Table.tableGrid);
    EURB.mainPanel.doLayout(); 
});