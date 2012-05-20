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
        data: [['line', EURB.ReportChart.line], ['bar', EURB.ReportChart.bar], ['pie', EURB.ReportChart.pie]]
    }),
    valueField: 'typeValue',
    displayField: 'typeLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false
});

////////////////////////////data set grid/////////////////////////////////////

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
	 width: '100%'
	,height: 300
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.ReportChart.title
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+ EURB.ReportChart.title
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true,reportDesignId:true,reportDesignVersionId:true}
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

				    EURB.ReportChart.xAxisColumnCombo,
					{
						fieldLabel: EURB.ReportChart.AxisTitle
						,name: 'xTitle'
						,xtype:'textfield'
						,allowBlank:true
					}
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
					EURB.ReportChart.yAxisColumnCombo,
					{
						fieldLabel: EURB.ReportChart.AxisTitle
						,name: 'yTitle'
						,xtype:'textfield'
						,allowBlank:true
					}
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
			this.showError(o.error || EURB.unknownError);
			return;
		}
		data = o.data;
		var RecordCons = Ext.data.Record.create(['id', 'xAxisId', 'xColumnMapping', 'xDataset', 'xTitle' , 'yAxisId', 'yDataset', 'yColumnMapping' , 'yTitle']);
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
				yTitle: data[8]
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
	}

});

