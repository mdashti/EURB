
////////////////////////////data set grid/////////////////////////////////////

EURB.ReportColumn.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			  {name:'id', type:'int'}
			,{name:'designId', type:'int'}
			,{name:'designVersionId', type:'int'}
			,{name:'datasetId', type:'int'}
			,{name:'columnMappingId', type:'int'}
			,{name:'colOrder', type:'int'}
			,{name:'sortOrder', type:'int'}
			,{name:'sortType', type:'bool'}
			,{name:'groupLevel', type:'int'}
			,{name:'columnWidth', type:'int'}
			,{name:'columnAlign', type:'string'}
			,{name:'columnDir', type:'string'}
			,{name:'columnHeader', type:'string'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.ReportColumn.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	,baseParams:{
		reportDesign: EURB.ReportDesign.selectedDesign,
		reportVersion: EURB.ReportDesign.selectedVersion
	}
	,remoteSort:true
});

EURB.ReportColumn.cols = [{
	 header:EURB.ReportColumn.Column
	,id:'columnMappingId'
	,dataIndex:'columnMappingId'
	,width:50
	,sortable:true
	,editor:EURB.ReportColumn.columnCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportColumn.columnCombo)
},
{
	 header:EURB.ReportColumn.ColumnHeader
	,id:'columnHeader'
	,dataIndex:'columnHeader'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
},
{
	 header:EURB.ReportColumn.ColOrder
	,id:'colOrder'
	,dataIndex:'colOrder'
	,width:10
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},
{
	 header:EURB.ReportColumn.SortOrder
	,id:'sortOrder'
	,dataIndex:'sortOrder'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
},
{
	 header:EURB.ReportColumn.SortType
	,id:'sortType'
	,dataIndex:'sortType'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
},
{
	 header:EURB.ReportColumn.GroupLevel
	,id:'groupLevel'
	,dataIndex:'groupLevel'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
},
{
	 header:EURB.ReportColumn.ColumnWidth
	,id:'columnWidth'
	,dataIndex:'columnWidth'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},
{
	 header:EURB.ReportColumn.ColumnAlign
	,id:'columnAlign'
	,dataIndex:'columnAlign'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},
{
	 header:EURB.ReportColumn.ColumnDir
	,id:'columnDir'
	,dataIndex:'columnDir'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
}];

EURB.ReportColumn.ColumnGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 width: '100%'
	,height: 500
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.ReportColumn.title
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.ReportColumn.title
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true,designId:true,designVersionId:true,datasetId:true}
            ,formConfig:{
                 labelWidth:80
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.ReportColumn.reportColumnGrid.commitChanges();
            }
            ,getRowClass:function(record) {
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
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.ReportColumn.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.ReportColumn.store
			,columns:EURB.ReportColumn.cols
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
		EURB.ReportColumn.ColumnGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.ReportColumn.ColumnGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.ReportColumn.ColumnGrid.superclass.afterRender.apply(this, arguments);
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
			 url:EURB.ReportColumn.storeAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'storeData',
				data:Ext.encode(data),
				reportDesign : EURB.ReportDesign.selectedDesign,
				reportVersion: EURB.ReportDesign.selectedVersion
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
					 url:EURB.ReportColumn.removeAction
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

