Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

EURB.ReportCategory.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'name', type:'string'}
			,{name:'description', type:'string'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.ReportCategory.searchAction
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

EURB.ReportCategory.cols = [{
	 header:'Name'
	,id:'name'
	,dataIndex:'name'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:'Description'
	,id:'description'
	,dataIndex:'description'
	,width:100
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
}];

EURB.ReportCategory.CategoryGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.category
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.appMenu.category
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true}
            //,readonlyFields:{password:true}
            //,disabledFields:{name:true}
            ,formConfig:{
                 labelWidth:80
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.ReportCategory.reportCategoryGrid.commitChanges();
            }
            , getRowClass:function(record) {
				if(record.get('newRecord')) {
					return this.newRowCls;
				}
				
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
		EURB.ReportCategory.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.ReportCategory.store
			,columns:EURB.ReportCategory.cols
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
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
		EURB.ReportCategory.CategoryGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.ReportCategory.CategoryGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.ReportCategory.CategoryGrid.superclass.afterRender.apply(this, arguments);
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
			data.push(r.data);
		}, this);
		var o = {
			 url:EURB.ReportCategory.storeAction
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
					 url:EURB.ReportCategory.removeAction
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
EURB.ReportCategory.reportCategoryGrid = new EURB.ReportCategory.CategoryGrid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.ReportCategory.reportCategoryGrid);
    EURB.mainPanel.doLayout(); 
});