Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

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
			,{name:'testQuery', type:'string'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.DBConfig.searchAction
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
});

EURB.DBConfig.cols = [{
	 header:'Name'
	,id:'name'
	,dataIndex:'name'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:'Driver Class'
	,id:'driverClass'
	,dataIndex:'driverClass'
	,width:100
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:'Driver URL'
	,id:'driverUrl'
	,dataIndex:'driverUrl'
	,width:200
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:'Username'
	,id:'username'
	,dataIndex:'username'
	,width:40
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:'Password'
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
	 header:'Test Query'
	,id:'testQuery'
	,dataIndex:'testQuery'
	,width:100
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
	,hidden:true
}];

EURB.DBConfig.DBGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.dbConfig
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.appMenu.dbConfig
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true, testCon:true}
            //,readonlyFields:{password:true}
            //,disabledFields:{name:true}
            ,formConfig:{
                 labelWidth:80
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
			},{
                 iconCls:'icon-edit-record'
                ,qtip:EURB.editRecord
            },{
                 iconCls:'icon-copy'
                ,qtip:EURB.copyRecord
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
        }
    }
	,commitChanges:function() {
		var records = this.store.getModifiedRecords();
		if(!records.length) {
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
//            var o = r.getChanges();
//            if(r.data.newRecord) {
//                o.newRecord = true;
//            }
//            o[this.idName] = r.get(this.idName);
//            data.push(o);
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
			this.showError(o.error || EURB.unknownError);
			return;
		}

		switch(options.params.cmd) {
			case 'storeData':
				var records = this.store.getModifiedRecords();
				Ext.each(records, function(r, i) {
					if(o.affectedIds && o.affectedIds[i]) {
						r.set(this.idName, o.affectedIds[i][0]);
						r.set("testCon", o.affectedIds[i][1]);
						delete(r.data.newRecord);
					}
				});
				this.store.each(function(r) {
                    r.commit();
                });
                this.store.modified = [];
				this.onRender();
//              this.store.commitChanges();
			break;

			case 'deleteData':
				if(o.affectedIds) {
					for(var i=0; i<o.affectedIds.length; i++) {
						this.store.removeAt(this.store.indexOfId(o.affectedIds[i]));
					}
				}
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
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneRecordFisrt).setIcon(Ext.Msg.INFO).minWidth = 120;
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