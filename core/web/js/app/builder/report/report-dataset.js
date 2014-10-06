////////////report categories combo///////////////////
EURB.ReportDataset.categoryCombo = new Ext.form.ComboBox({
	typeAhead: true,
	triggerAction: 'all',
	lazyRender:true,
	mode: 'local',
	fieldLabel: EURB.ReportDesign.CategoryField,
	anchor:'100%',  // anchor width by percentage
	store: EURB.ReportDesign.categoryStore,
	valueField: 'id',
	displayField: 'name'
});

////////////////report designs combo////////////////
EURB.ReportDataset.designCombo = new Ext.form.ComboBox({
	typeAhead: true,
	triggerAction: 'all',
	lazyRender:true,
	mode: 'local',
	store: EURB.ReportDataset.reportStore,
	valueField: 'id',
	displayField: 'name',
	forceSelection: true,
    allowBlank: true,
    listeners:{
    	select: function(combo,record,index){
    		form = EURB.ReportDataset.reportDatasetGrid.recordForm.form.getForm();
    		form.findField('baseReportVersionId').setValue(record.get('versionId'));
    	}
    }
});


//////////////////table mappings combo/////////////////////////
EURB.ReportDataset.tableCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: EURB.ReportDataset.tableMappingStore,
    valueField: 'id',
    displayField: 'mappedName',
    forceSelection: true,
    allowBlank: true
});




///////////////////functions/////////////////////////////
tableDataset = function(){
	form = EURB.ReportDataset.reportDatasetGrid.recordForm.form.getForm();
	table = form.findField('tableMappingId');
	report = form.findField('baseReportId');
	reportVersion = form.findField('baseReportVersionId');
	
	report.setDisabled(true);
	report.allowBlank = true;
	
	hideFormField(report);
	showFormField(table);
	
	table.allowBlank = false;
	table.setDisabled(false);
	
	hideFormField(reportVersion);
}

reportDataset = function(){
	form = EURB.ReportDataset.reportDatasetGrid.recordForm.form.getForm();
	table = form.findField('tableMappingId');
	report = form.findField('baseReportId');
	
	table.setDisabled(true);
	table.allowBlank = true;
	
	hideFormField(table);
	showFormField(report);
	
	report.allowBlank = false;
	report.setDisabled(false);
	
	hideFormField(reportVersion);
}


checkType = function(){
	report = EURB.ReportDataset.reportDatasetGrid.recordForm.form.getForm().findField('baseReportId');
	if(report.getValue() && report.getValue() != 0){
		reportDataset();
	}
	else{
		tableDataset();
	}
	
}


////////////////////////////data set grid/////////////////////////////////////

EURB.ReportDataset.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'name', type: 'string'}
			,{name:'designId', type:'int'}
			,{name:'datasetType', type:'int'}
			,{name:'designVersionId', type:'int'}
			,{name:'tableMappingId', type:'int'}
			,{name:'baseReportId', type:'int'}
			,{name:'baseReportVersionId', type:'int'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.ReportDataset.searchAction
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

EURB.ReportDataset.cols = [{
	 header:EURB.ReportDataset.Type
	,id:'datasetType'
	,dataIndex:'datasetType'
	,width:20
	,sortable:true
	,hidden:true
	,editor:new Ext.form.Checkbox({listeners: {
        check: {
            fn: function(){
	            	if(this.checked){
	            		reportDataset();
	            	}
	            	else{
	            		tableDataset();
	            	}
            	}
        }
    }})
},{
	 header:EURB.ReportDataset.Name
	,id:'name'
	,dataIndex:'name'
	,width:20
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
},{
	 header:EURB.ReportDataset.Table
	,id:'tableMappingId'
	,dataIndex:'tableMappingId'
	,width:20
	,sortable:true
	,editor:EURB.ReportDataset.tableCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportDataset.tableCombo)
},{
	 header:EURB.ReportDataset.BaseReportDesign
	,id:'baseReportId'
	,dataIndex:'baseReportId'
	,width:20
	,sortable:true
	,editor:EURB.ReportDataset.designCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportDataset.designCombo)
},{
	 header:EURB.ReportDataset.BaseReportVersion
	,id:'baseReportVersionId'
	,dataIndex:'baseReportVersionId'
	,width:20
	,sortable:true
	,hidden:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
}];

EURB.ReportDataset.DatasetGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	// defaults - can be changed from outside
	 width: '100%'
	,height: 300
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.ReportDataset.title
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+ EURB.ReportDataset.title
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true,designId:true,designVersionId:true}
        	,hiddenFields:{baseReportVersionId:true}
            ,formConfig:{
                 labelWidth:150
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.ReportDataset.reportDatasetGrid.commitChanges();
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
		EURB.ReportDataset.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.ReportDataset.store
			,columns:EURB.ReportDataset.cols
			,plugins:[/*new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				//,minChars:2
				,autoFocus:true
			//				,menuStyle:'radio'
			}),*/ this.rowActions, this.recordForm]
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
			},"->"]
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// create bottom paging toolbar
		this.bbar = ['->'];

		// call parent
		EURB.ReportDataset.DatasetGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.ReportDataset.DatasetGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.ReportDataset.DatasetGrid.superclass.afterRender.apply(this, arguments);
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
                checkType();
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
			 url:EURB.ReportDataset.storeAction
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
			this.showError(o.error || o.message || EURB.unknownError);
			return;
		}
		
		updateReportColumnComboContent();
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
			,msg:String.format(EURB.areYouSureToDelete, records.length == 1 ? EURB.record : EURB.records)
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
					 url:EURB.ReportDataset.removeAction
					,method:'post'
					,callback:this.requestCallback
					,scope:this
					,params:{
						cmd: 'deleteData',
						data:Ext.encode(data),
						reportDesign : EURB.ReportDesign.selectedDesign,
						reportVersion: EURB.ReportDesign.selectedVersion
					}
				};
				Ext.Ajax.request(o);
			}
		});
	}
	,listeners: {
	}

});

