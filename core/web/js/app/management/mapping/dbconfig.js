EURB.DBConfig.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'testCon', type:'string'}
			,{name:'name', type:'string'}
			,{name:'driverClass', type:'string'}
			,{name:'driverUrl', type:'string'}
			,{name:'username', type:'string'}
			,{name:'password', type:'string'}
			,{name:'accessPreventDel', type:'boolean'}
			,{name:'accessPreventEdit', type:'boolean'}
			,{name:'accessPreventExecute', type:'boolean'}
			,{name:'accessPreventSharing', type:'boolean'}
			/*,{name:'testQuery', type:'string'}*/
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.DBConfig.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	//,baseParams:{}
	,remoteSort:true
});

EURB.DBConfig.driverStore = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'name', type:'string'}
			,{name:'fullName', type:'string'}
			,{name:'driverClassName', type:'string'}
			,{name:'url', type:'string'}
			,{name:'webSiteUrl', type:'string'}
		]
	})
	,autoLoad: true
	,proxy:new Ext.data.HttpProxy({
		url:EURB.DBConfig.driverSearchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	//,baseParams:{}
	,remoteSort:false
});

EURB.DBConfig.cols = [{
	 header:EURB.DBConfig.name
	,id:'name'
	,dataIndex:'name'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.DBConfig.driverClass
	,id:'driverClass'
	,dataIndex:'driverClass'
	,width:100
	,sortable:true
	,editor:new Ext.form.ComboBox({
	    typeAhead: true,
	    triggerAction: 'all',
	    lazyRender:true,
	    mode: 'local',
	    store: EURB.DBConfig.driverStore,
	    forceSelection: false,
	    valueField: 'driverClassName',
	    displayField: 'fullName',
		cls:'left-align-txt',
	    listeners: {
	    	'select' : function(thiz, newValue, oldValue) {
	    		Ext.ComponentMgr.get('driverUrlEditor').setRawValue(newValue.data.url);
	    	}
	    }
	})
},{
	 header:EURB.DBConfig.driverUrl
	,id:'driverUrl'
	,dataIndex:'driverUrl'
	,width:200
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false,
		cls:'left-align-txt',
		id:'driverUrlEditor'
	})
},{
	 header:EURB.DBConfig.username
	,id:'username'
	,dataIndex:'username'
	,width:40
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false,
		cls:'left-align-txt'
	})
},{
	 header:EURB.DBConfig.password
	,id:'password'
	,dataIndex:'password'
	,width:40
	,sortable:true
	,editor:{
		xtype: 'textfield'
		,allowBlank:true
		,inputType:'password'
		,cls:'left-align-txt'
	}
	,hidden:true
	,renderer: function() {return '';}
}, {
	header: EURB.DBConfig.conStatus
	,id:'testCon'
	,dataIndex:'testCon'
	,width:30
	,sortable:true
	,renderer: function(value) {
		if(value == 'dbconf-valid') {
			return EURB.DBConfig.valid;
		} else if(value == 'dbconf-invalidcon') {
			return EURB.DBConfig.invalid;
		} else if(value == 'dbconf-inactive') {
			return EURB.DBConfig.inactive;
		} else if(value == 'dbconf-incompletedata') {
			return EURB.DBConfig.incompletedata;
		}
	}
}/*,{
	 header:EURB.DBConfig.testQuery
	,id:'testQuery'
	,dataIndex:'testQuery'
	,width:100
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
	,hidden:true
}*/];

EURB.DBConfig.DBGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.dbConfig
	,initComponent:function() {
		this.sharingWindow = EURB.ObjSec.getSharingWindows();
		
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.appMenu.dbConfig
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true, testCon:true, accessPreventDel:true, accessPreventEdit: true, accessPreventExecute: true, accessPreventSharing: true }
            //,readonlyFields:{password:true}
            //,disabledFields:{name:true}
            ,formConfig:{
                 labelWidth:90
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.DBConfig.dbConfigGrid.commitChanges();
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
				 iconCls:'icon-minus'
				,qtip:EURB.delRecord
				,style:'margin:0 0 0 3px'
	           	,hideIndex: 'accessPreventDel'
			},{
                 iconCls:'icon-edit-record'
                ,qtip:EURB.editRecord
	           	,hideIndex: 'accessPreventEdit'
            },{
                 iconCls:'icon-copy'
                ,qtip:EURB.copyRecord
	           	,hideIndex: 'accessPreventEdit'
            },{
                 iconCls:'icon-grid'
                 ,qtip:EURB.DBConfig.editTables
	           	,hideIndex: 'accessPreventEdit'
            },{
	          	iconCls:'icon-share'
	           	,qtip:EURB.ObjSec.share
	           	,hideIndex: 'accessPreventSharing'
	        }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.DBConfig.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.DBConfig.store
			,columns:EURB.DBConfig.cols
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				,disableIndexes:['password']
				//,minChars:2
				,autoFocus:true
			//				,menuStyle:'radio'
			}), this.rowActions, this.recordForm]
			,viewConfig:{forceFit:true}
			,loadMask:true
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
		EURB.DBConfig.DBGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.DBConfig.DBGrid.superclass.onRender.apply(this, arguments);
		if(this.loadMask){
            this.loadMask = new Ext.LoadMask(this.bwrap,
                    Ext.apply({store:this.store}, this.loadMask));
        }
		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.DBConfig.DBGrid.superclass.afterRender.apply(this, arguments);
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

            case 'icon-copy':
                this.copyRecord(record);
            break;
                
            case 'icon-grid':
            	window.location.href = EURB.baseURL+'management/mapping/db'+record.get(this.idName)+'-table.spy';
            break;
	        
	        case 'icon-share':
	        	this.sharingWindow.show(grid.getView().getCell(row, col),this.editSharingFor(record),this);
	        break;
        }
    }
	,editSharingFor:function(record) {
    	return function() {
			var title = EURB.ObjSec.share+' '+EURB.DBConfig.title+' '+record.get('name');
			var objectType = 3;//dbconfig
    		this.sharingWindow.onShowForARecord(record.get('id'), title, objectType);
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
			 url:EURB.DBConfig.storeAction
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
            //this.bbar.pageSize++;
            this.bbar.doLayout();
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
					 url:EURB.DBConfig.removeAction
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
			 url:EURB.DBConfig.activateAction
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
			 url:EURB.DBConfig.deactivateAction
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
//Ext.reg('dbconfig.dbgrid', EURB.DBConfig.DBGrid);
EURB.DBConfig.dbConfigGrid = new EURB.DBConfig.DBGrid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.DBConfig.dbConfigGrid);
    EURB.mainPanel.doLayout(); 
});