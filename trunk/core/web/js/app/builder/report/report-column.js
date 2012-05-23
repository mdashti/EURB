////////////////////////////alignment combo box///////////////////////////////
EURB.ReportColumn.alignCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'alignValue',
            'alignLabel'
        ],
        data: [['left', EURB.ReportColumn.leftAlign], ['center', EURB.ReportColumn.middleAlign], ['right', EURB.ReportColumn.rightAlign]]
    }),
    valueField: 'alignValue',
    displayField: 'alignLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false
});

////////////////////////////direction combo box///////////////////////////////
EURB.ReportColumn.dirCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'dirValue',
            'dirLabel'
        ],
        data: [['ltr', EURB.ReportColumn.ltrDir], ['rtl', EURB.ReportColumn.rtlDir]]
    }),
    valueField: 'dirValue',
    displayField: 'dirLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false
});

////////////////////////////sort order combo box///////////////////////////////
EURB.ReportColumn.sortTypeCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'sortTypeValue',
            'sortTypeLabel'
        ],
        data: [[0, EURB.ReportColumn.noSort], [1, EURB.ReportColumn.ascendingSort], [2, EURB.ReportColumn.descendingSort]]
    }),
    valueField: 'sortTypeValue',
    displayField: 'sortTypeLabel',
    forceSelection: true,
    allowBlank: true,
    editable: false
});





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
			,{name:'columnHeader', type:'string'}
			,{name:'formula', type:'string'}
			,{name:'colOrder', type:'int'}
			,{name:'sortOrder', convert: function(value, row) {
			        return (value == null) ? null : parseInt(value);
			    }
			 }
			,{name:'sortType', type:'int'}
			,{name:'groupLevel', convert: function(value, row) {
			        return (value == null) ? null : parseInt(value);
			    }
			 }
			,{name:'columnWidth', type:'int'}
			,{name:'columnAlign', type:'string'}
			,{name:'columnDir', type:'string'}
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
	,width:30
	,sortable:true
	,editor:EURB.ReportColumn.columnCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportColumn.columnCombo)
},
{
	 header:EURB.ReportColumn.ColumnHeader
	,id:'columnHeader'
	,dataIndex:'columnHeader'
	,width:30
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
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
	 header:EURB.ReportColumn.Formula
	,id:'formula'
	,dataIndex:'formula'
	,width:30
	,sortable:false
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
	,renderer: function(value) { return value == null ? '' : value;}
},
{
	 header:EURB.ReportColumn.SortType
	,id:'sortType'
	,dataIndex:'sortType'
	,width:25
	,sortable:true
	,editor:EURB.ReportColumn.sortTypeCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportColumn.sortTypeCombo)
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
	,editor:EURB.ReportColumn.alignCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportColumn.alignCombo)
},
{
	 header:EURB.ReportColumn.ColumnDir
	,id:'columnDir'
	,dataIndex:'columnDir'
	,width:25
	,sortable:true
	,editor:EURB.ReportColumn.dirCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportColumn.dirCombo)
}];

EURB.ReportColumn.ColumnGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	// defaults - can be changed from outside
	 width: '100%'
	,height: 300
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.ReportColumn.title
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.ReportColumn.title
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true,designId:true,designVersionId:true,datasetId:true,formula:true}
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
        
        var editFormulaForm = new Ext.form.FormPanel({
			baseCls: 'x-plain',
			labelWidth: 100,
			defaultType: 'textfield',
			items: [{
			    name: 'id'
			    ,allowBlank:false
			    ,xtype: 'hidden'
			},{
			    fieldLabel: EURB.ReportColumn.ColumnHeader
			    ,name: 'columnHeader'
			    ,anchor:'100%'  // anchor width by percentage
			    ,xtype: 'textfield'
				,allowBlank:false
				
			},EURB.ReportColumn.formulaFieldSet]
	    });
        this.editFormulaForm = editFormulaForm;
        var editFormulaWindow = new Ext.Window({
			title: EURB.ReportColumn.formulaEditor,
			width: 500,
			height:300,
			minWidth: 300,
			closeAction: 'hide',
			minHeight: 150,
			layout: 'fit',
			plain:true,
			bodyStyle:'padding:5px;',
			buttonAlign:'center',
			items: editFormulaForm,
		    buttons: [{
		        text:Ext.MessageBox.buttonText.ok,
		        handler: function() {
		        	if(editFormulaForm.getForm().isValid()) {
		        		editFormulaForm.getForm().submit({
		        			url:EURB.ReportColumn.editFormulaAction,
		        			success:function(form, action){
		        				editFormulaWindow.hide();
				        		EURB.ReportColumn.reportColumnGrid.store.reload();
		        			}
		        		});
		        	}
		        }
		    },{
		        text: Ext.MessageBox.buttonText.cancel,
		        handler: function(){
		            editFormulaWindow.hide();
		        }
		    }]
		});
		this.editFormulaWindow = editFormulaWindow;

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
            	iconCls:'icon-calculator'
            	,qtip:EURB.editFormula
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
			},"->"]
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// create bottom paging toolbar
		this.bbar = ['->'];
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
            	{
            		this.recordForm.show(record, grid.getView().getCell(row, col));
            	}
            break;
            case 'icon-calculator':
            	this.editFormulaWindow.show(grid.getView().getCell(row, col),this.editFormulaFor(record),this);
            break;

        }
    }
	,editFormulaFor:function(record) {
    	var frm = this.editFormulaForm.getForm();
    	frm.reset();
    	frm.loadRecord(record);
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
		//data[0].formula = Ext.getCmp('report-column-formula').getValue();
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
			,msg:String.format(EURB.areYouSureToDelete, records.length == 1 ? records[0].get('columnHeader') : EURB.records)
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
	}

});

