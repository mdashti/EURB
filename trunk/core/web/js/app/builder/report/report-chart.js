///////////////////xAxis column combo////////////////////////////////			
EURB.ReportChart.xAxisColumnCombo = new Ext.form.ComboBox({
	fieldLabel:EURB.ReportChart.AxisColumn,
	hiddenName:'xColumnMapping',
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store:EURB.ReportDesign.columnMappingStore,
    valueField: 'id',
    displayField: 'title',
    forceSelection: true,
    allowBlank: false,
    width:300,
    listeners:{
    	select: function(combo,record,index){
    		dsField = EURB.ReportChart.reportChartGrid.axisForm.getForm().findField('xDataset');
    		dsField.setValue(record.get('datasetId'));
    	}
    }
});

/////////////////yAxis column combo////////////////////////////////
EURB.ReportChart.yAxisColumnCombo = new Ext.form.ComboBox({
	fieldLabel:EURB.ReportChart.AxisColumn,
	hiddenName:'yColumnMapping',
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store:EURB.ReportDesign.columnMappingStore,
    valueField: 'id',
    displayField: 'title',
    forceSelection: true,
    allowBlank: false,
    width:300,
    listeners:{
    	select: function(combo,record,index){
    		dsField = EURB.ReportChart.reportChartGrid.axisForm.getForm().findField('yDataset');
    		dsField.setValue(record.get('datasetId'));
    	}
    }
});
////////////////////////////chart type combo box///////////////////////////////
EURB.ReportChart.chartTypeCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'typeValue',
            'typeLabel'
        ],
        data: [['line', EURB.ReportChart.line], ['column', EURB.ReportChart.column], ['bar', EURB.ReportChart.bar], ['pie', EURB.ReportChart.pie], ['gauge', EURB.ReportChart.gauge]]
    }),
    valueField: 'typeValue',
    displayField: 'typeLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false
});


////////////////////////////aggregation type combo box///////////////////////////////
EURB.ReportChart.aggregationTypeCombo = new Ext.form.ComboBox({
	fieldLabel:EURB.ReportChart.Aggregation,
	hiddenName:'yAggregation',
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'aggregationValue',
            'aggregationLabel'
        ],
        data: [['sum', EURB.ReportChart.sum], ['count', EURB.ReportChart.count], ['avg', EURB.ReportChart.average]]
    }),
    valueField: 'aggregationValue',
    displayField: 'aggregationLabel',
    forceSelection: true,
    allowBlank: true,
    editable: false
});

////////////////////////////font type combo box///////////////////////////////
EURB.ReportChart.fontStore = new Ext.data.ArrayStore({
    id: 0,
    fields: [
        'fontValue',
        'fontLabel'
    ],
    data: [['Tahoma', 'Tahoma'], ['Arial', 'Arial']]
});
EURB.ReportChart.titleFontCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: EURB.ReportChart.fontStore,
    valueField: 'fontValue',
    displayField: 'fontLabel',
    forceSelection: true,
    allowBlank: true,
    editable: false
});
EURB.ReportChart.xAxisFontCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: EURB.ReportChart.fontStore,
    valueField: 'fontValue',
    displayField: 'fontLabel',
    forceSelection: true,
    allowBlank: true,
    editable: false
});
EURB.ReportChart.yAxisFontCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: EURB.ReportChart.fontStore,
    valueField: 'fontValue',
    displayField: 'fontLabel',
    forceSelection: true,
    allowBlank: true,
    editable: false
});


////////////////////////////font size combo box///////////////////////////////
EURB.ReportChart.sizeStore = new Ext.data.ArrayStore({
    id: 0,
    fields: [
        'sizeValue',
        'sizeLabel'
    ],
    data: [['10',  '10'],['11',  '11'], ['12', '12'], ['14',  '14'],['16',  '16'],['18',  '18'],['20',  '20'],['22',  '22'],['24',  '24']]
});
EURB.ReportChart.titleSizeCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store:EURB.ReportChart.sizeStore,
    valueField: 'sizeValue',
    displayField: 'sizeLabel',
    forceSelection: false,
    allowBlank: true,
    editable: false
});
EURB.ReportChart.xAxisSizeCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store:EURB.ReportChart.sizeStore,
    valueField: 'sizeValue',
    displayField: 'sizeLabel',
    forceSelection: false,
    allowBlank: true,
    editable: false
});
EURB.ReportChart.yAxisSizeCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store:EURB.ReportChart.sizeStore,
    valueField: 'sizeValue',
    displayField: 'sizeLabel',
    forceSelection: false,
    allowBlank: true,
    editable: false
});


EURB.ReportChart.cellClickListener =  function(grid, rowIndex, columnIndex, e){
	var record = grid.getStore().getAt(rowIndex);  // Get the Record
	if(columnIndex == 0){
		configMap = record.get("configMap");
		if(configMap && configMap.titleFont){
			EURB.ReportChart.chartPropertyGrid.setSource(configMap);
		}
		else{
			//set default config values
			EURB.ReportChart.chartPropertyGrid.setSource({titleFont:'Tahoma', titleColor: '000000', titleSize:'12',
				xAxisFont:'Tahoma', xAxisColor: '000000', xAxisSize:'12',
				yAxisFont:'Tahoma', yAxisColor: '000000', yAxisSize:'12',
				chartColor: '000000'
				});
		}
	}
};

EURB.ReportChart.colorRenderer = function(val, element){
	if (/^[0-9a-fA-F]{6}$/.test(val)) {
		element.style = '{background: #'+val+'; !important}';
	}
};
///////////////chart properties///////////////////////////////

EURB.ReportChart.chartPropertyGrid = new Ext.grid.PropertyGrid({
    height: 200,
    width: '30%',
    source: {},
    propertyNames: {
        titleFont : EURB.ReportChart.titleFont
		,titleSize : EURB.ReportChart.titleSize
		,titleColor : EURB.ReportChart.titleColor
		,xAxisFont : EURB.ReportChart.xAxisFont
		,xAxisSize : EURB.ReportChart.xAxisSize
		,xAxisColor : EURB.ReportChart.xAxisColor
		,yAxisFont : EURB.ReportChart.yAxisFont
		,yAxisSize : EURB.ReportChart.yAxisSize
		,yAxisColor : EURB.ReportChart.yAxisColor
		,chartColor: EURB.ReportChart.chartColor
		
	}
   ,region:'west'
    ,customRenderers: {
        titleFont: Ext.ux.comboRenderer(EURB.ReportChart.titleFontCombo)
        ,titleSize: Ext.ux.comboRenderer(EURB.ReportChart.titleSizeCombo)
        ,titleColor: EURB.ReportChart.colorRenderer
        ,xAxisFont: Ext.ux.comboRenderer(EURB.ReportChart.xAxisFontCombo)
        ,xAxisSize: Ext.ux.comboRenderer(EURB.ReportChart.xAxisSizeCombo)
        ,xAxisColor: EURB.ReportChart.colorRenderer
        ,yAxisFont: Ext.ux.comboRenderer(EURB.ReportChart.yAxisFontCombo)
        ,yAxisSize: Ext.ux.comboRenderer(EURB.ReportChart.yAxisSizeCombo)
        ,yAxisColor: EURB.ReportChart.colorRenderer
        ,chartColor: EURB.ReportChart.colorRenderer
    }
    ,customEditors: {
        titleFont: new Ext.grid.GridEditor(EURB.ReportChart.titleFontCombo)
		,titleSize: new Ext.grid.GridEditor(EURB.ReportChart.titleSizeCombo)
    	,titleColor : new Ext.grid.GridEditor(new Ext.ux.form.ColorPickerField({
    		listeners:{
    			select:function(picker, value){
    				source = EURB.ReportChart.chartPropertyGrid.propStore.getSource();
    				source['titleColor'] = value;
    				EURB.ReportChart.chartPropertyGrid.setSource(source);
    			}
    		}
    	}))
    	,xAxisFont: new Ext.grid.GridEditor(EURB.ReportChart.xAxisFontCombo)
		,xAxisSize: new Ext.grid.GridEditor(EURB.ReportChart.xAxisSizeCombo)
    	,xAxisColor : new Ext.grid.GridEditor(new Ext.ux.form.ColorPickerField({
    		listeners:{
    			select:function(picker, value){
    				source = EURB.ReportChart.chartPropertyGrid.propStore.getSource();
    				source['xAxisColor'] = value;
    				EURB.ReportChart.chartPropertyGrid.setSource(source);
    			}
    		}
    	}))
    	,yAxisFont: new Ext.grid.GridEditor(EURB.ReportChart.yAxisFontCombo)
		,yAxisSize: new Ext.grid.GridEditor(EURB.ReportChart.yAxisSizeCombo)
    	,yAxisColor : new Ext.grid.GridEditor(new Ext.ux.form.ColorPickerField({
    		listeners:{
    			select:function(picker, value){
    				source = EURB.ReportChart.chartPropertyGrid.propStore.getSource();
    				source['yAxisColor'] = value;
    				EURB.ReportChart.chartPropertyGrid.setSource(source);
    			}
    		}
    	}))
    	,chartColor : new Ext.grid.GridEditor(new Ext.ux.form.ColorPickerField({
    		listeners:{
    			select:function(picker, value){
    				source = EURB.ReportChart.chartPropertyGrid.propStore.getSource();
    				source['chartColor'] = value;
    				EURB.ReportChart.chartPropertyGrid.setSource(source);
    			}
    		}
    	}))
    }
    ,tbar: ['->',{
    	iconCls: 'icon-cancel'
    	,text: EURB.ReportChart.cancelAll
    	,handler: function() {
    		var rec = EURB.ReportChart.reportChartGrid.getSelectionModel().getSelected();
    		rec.reject();
    		EURB.ReportChart.reportChartGrid.onRecordSelectionChange(rec);
    	}
    },{
    	iconCls: 'icon-save-table',
        text: EURB.ReportChart.saveAll,
        handler : function(){
        	var index = EURB.ReportChart.reportChartGrid.getSelectionModel().getSelectedCell();
    		var record = EURB.ReportChart.reportChartGrid.store.getAt(index[0]);
    		var source = EURB.ReportChart.chartPropertyGrid.propStore.source; 
        	record.set('configMap', source);
    		var sendRec = new EURB.ReportChart.reportChartGrid.store.recordType({newRecord:false});
            sendRec.fields.each(function(f) {
            		sendRec.data[f.name] = record.data[f.name];
            });
    		var o = {
				 url:EURB.ReportChart.storeAction
				,method:'post'
				,callback:this.requestCallback
				,scope:this
				,params:{
					cmd: 'storeData',
					data:Ext.encode([sendRec.data]),
					reportDesign : EURB.ReportDesign.selectedDesign,
					reportVersion: EURB.ReportDesign.selectedVersion
				}
			};
			Ext.Ajax.request(o);
    		record.commit();
    	}
    }]
});



////////////////////////////chart grid/////////////////////////////////////

EURB.ReportChart.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'reportDesignId', type:'int'}
			,{name:'reportDesignVersionId', type:'int'}
			,{name:'name', type:'string'}
			,{name:'type', type:'string'}
			,{name:'configMap', type: 'auto'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.ReportChart.searchAction
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

EURB.ReportChart.cols = [{
	 header:EURB.ReportChart.Name
	,id:'name'
	,dataIndex:'name'
	,width:20
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},
{
	header:EURB.ReportChart.Type
	,id:'type'
	,dataIndex:'type'
	,width:20
	,sortable:true
	,editor:EURB.ReportChart.chartTypeCombo
	,renderer: EURB.ReportDesign.comboRenderer(EURB.ReportChart.chartTypeCombo)
}];

EURB.ReportChart.ChartGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	// defaults - can be changed from outside
	 width: '70%'
	,height: 300
	,border:true
	,stateful:false
	,region:'center'
	,idName:'id'
	,title: EURB.ReportChart.title
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+ EURB.ReportChart.title
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true,reportDesignId:true,reportDesignVersionId:true,configMap:true}
            ,formConfig:{
                 labelWidth:80
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.ReportChart.reportChartGrid.commitChanges();
            }
            ,getRowClass:function(record) {
				if(record.get('newRecord')) {
					return this.newRowCls;
				}				
			}
        });
        
        var axisForm = new Ext.form.FormPanel({
			baseCls: 'x-plain',
			labelWidth: 100,
			defaultType: 'textfield',
			items: [{
			    name: 'id'
			    ,allowBlank:false
			    ,xtype: 'hidden'
			},
			{
				xtype:'fieldset'
				,title: EURB.ReportChart.xAxis
				,collapsible:false
				,items:[
					{
					    name: 'xAxisId'
					    ,allowBlank:true
					    ,xtype: 'hidden'
					},
					{
					    name: 'xDataset'
					    ,allowBlank:true
					    ,xtype: 'hidden'
					},
				    {
						fieldLabel: EURB.ReportChart.AxisTitle
						,name: 'xTitle'
						,xtype:'textfield'
						,allowBlank:true
					},
					EURB.ReportChart.xAxisColumnCombo
				]
				
			},{
				xtype:'fieldset'
				,title: EURB.ReportChart.yAxis
				,collapsible:false
				,items:[
				    {
					    name: 'yAxisId'
					    ,allowBlank:true
					    ,xtype: 'hidden'
					},
					{
					    name: 'yDataset'
					    ,allowBlank:true
					    ,xtype: 'hidden'
					},
					{
						fieldLabel: EURB.ReportChart.AxisTitle
						,name: 'yTitle'
						,xtype:'textfield'
						,allowBlank:true
					},
					EURB.ReportChart.aggregationTypeCombo,
					EURB.ReportChart.yAxisColumnCombo
				]
					
			}]
	    });
        this.axisForm = axisForm;
        var axisWindow = new Ext.Window({
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
			items: axisForm,
		    buttons: [{
		        text:Ext.MessageBox.buttonText.ok,
		        handler: function() {
		        	if(axisForm.getForm().isValid()) {
		        		axisForm.getForm().submit({
		        			url:EURB.ReportChart.editAxisAction,
		        			success:function(form, action){
		        				axisWindow.hide();
		        			}
		        		});
		        	}
		        }
		    },{
		        text: Ext.MessageBox.buttonText.cancel,
		        handler: function(){
		            axisWindow.hide();
		        }
		    }]
		});
		this.axisWindow = axisWindow;


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
            	iconCls:'icon-chart'
                ,qtip:EURB.editChartAxis
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.ReportChart.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.ReportChart.store
			,columns:EURB.ReportChart.cols
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
		EURB.ReportChart.ChartGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.ReportChart.ChartGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.ReportChart.ChartGrid.superclass.afterRender.apply(this, arguments);
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
            case 'icon-chart':
            	this.axisWindow.show(grid.getView().getCell(row, col),this.axisFor(record),this);
            break;

        }
    }
	
	,axisFor:function(record) {
		var records = [record];
		if(!records.length) {
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			data.push(r.data);
		}, this);
		var o = {
			 url:EURB.ReportChart.chartAxisSearch
			,method:'post'
			,callback:this.axisRequestCallback
			,scope:this
			,params:{
				cmd: 'storeData',
				data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
    }
	,axisRequestCallback:function(options, success, response) {
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
		data = o.data;
		var RecordCons = Ext.data.Record.create(['id', 'xAxisId', 'xColumnMapping', 'xDataset', 'xTitle' , 'yAxisId', 'yDataset', 'yColumnMapping' , 'yTitle', 'yAggregation']);
		var record = new RecordCons(
			{
				id: data[0],
				xAxisId: data[1],
				xColumnMapping: data[2],
				xDataset: data[3],
				xTitle: data[4],
				yAxisId: data[5],
				yColumnMapping: data[6],
				yDataset: data[7],
				yTitle: data[8],
				yAggregation: data[9]
			}
		); 
		var frm = this.axisForm.getForm();
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
		var o = {
			 url:EURB.ReportChart.storeAction
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
					 url:EURB.ReportChart.removeAction
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
		cellclick: EURB.ReportChart.cellClickListener
	}

});

