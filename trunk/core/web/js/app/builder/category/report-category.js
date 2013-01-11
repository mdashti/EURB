/////////////////////////////categories combo box///////////////////
EURB.ReportCategory.categoriesCombo = new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: EURB.ReportCategory.categoriesStore,
			    valueField: 'id',
			    displayField: 'name',
			    forceSelection: true,
			    allowBlank: true
			});
EURB.ReportCategory.comboRenderer = function(combo){
    return function(value){
        var record = combo.findRecord(combo.valueField, value);
        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
    }
};

EURB.ReportCategory.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'name', type:'string'}
			,{name:'description', type:'string'}
			,{name:'parentCategory', type:'int'}
			,{name:'accessPreventDel', type:'boolean'}
			,{name:'accessPreventEdit', type:'boolean'}
			,{name:'accessPreventSharing', type:'boolean'}
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
	 header:EURB.ReportCategory.Name
	,id:'name'
	,dataIndex:'name'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.ReportCategory.Description
	,id:'description'
	,dataIndex:'description'
	,width:100
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
},
{
	header:EURB.ReportCategory.Parent
	,id:'parentCategory'
	,dataIndex:'parentCategory'
	,width:50
	,sortable:true
	,editor:EURB.ReportCategory.categoriesCombo
	,renderer:EURB.ReportCategory.comboRenderer(EURB.ReportCategory.categoriesCombo)
}];

EURB.ReportCategory.CategoryGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.category
	,initComponent:function() {
		this.sharingWindow = EURB.ObjSec.getSharingWindows();
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
	           	,hideIndex: 'accessPreventDel'
			},{
                 iconCls:'icon-edit-record'
                ,qtip:EURB.editRecord
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
			},{
				 text:EURB.saveRecords
				,iconCls:'icon-save'
				,listeners:{
					 scope:this
					,click:{fn:this.commitChanges,buffer:200}
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
            
            case 'icon-share':
	        	this.sharingWindow.show(grid.getView().getCell(row, col),this.editSharingFor(record),this);
	        break;

        }
    }
    ,editSharingFor:function(record) {
    	return function() {
			var title = EURB.ObjSec.share+' '+EURB.ReportCategory.category+' '+record.get('name');
			var objectType = 2;//category
			
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
		this.deleteSelectedRecords();
	}
	,deleteSelectedRecords:function() {
		var index = this.getSelectionModel().getSelectedCell();
		if(!index) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var record = this.store.getAt(index[0]);
		records = [record];
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
	}

});

EURB.ReportCategory.reportCategoryGrid = new EURB.ReportCategory.CategoryGrid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.ReportCategory.reportCategoryGrid);
    EURB.mainPanel.doLayout(); 
});