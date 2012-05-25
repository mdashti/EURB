////////////////////////////aggregation function combo box///////////////////////////////
EURB.GroupAggregation.aggregationFunctionCombo = new Ext.form.ComboBox({
	typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'functionValue',
            'functionLabel'
        ],
        data: [['sum', EURB.GroupAggregation.sum], ['count', EURB.GroupAggregation.count], ['ave', EURB.GroupAggregation.average]]
    }),
    valueField: 'functionValue',
    displayField: 'functionLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false
});

////////////////////////////aggregation place combo box///////////////////////////////
EURB.GroupAggregation.aggregationPlaceCombo = new Ext.form.ComboBox({
	typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'placeValue',
            'placeLabel'
        ],
        data: [[0, EURB.GroupAggregation.down], [1, EURB.GroupAggregation.up]]
    }),
    valueField: 'placeValue',
    displayField: 'placeLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false
});


////////////////////////////Group Aggregation grid/////////////////////////////////////

EURB.GroupAggregation.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			 ,{name:'parentColumnId', type:'int'}
			 ,{name:'aggregatedReportColumnId', type:'int'}
			 ,{name:'aggregatedColumnDatasetId', type:'int'}
			 ,{name:'aggregationFunction', type:'string'}
			 ,{name:'place', type:'int'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.GroupAggregation.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	,baseParams:{
		reportColumn: EURB.ReportColumn.selectedColumn
	}
	,remoteSort:true
});

EURB.GroupAggregation.cols = [
{
	 header:EURB.GroupAggregation.AggregatedColumn
	,id:'aggregatedReportColumnId'
	,dataIndex:'aggregatedReportColumnId'
	,width:50
	,sortable:true
	,editor:EURB.GroupAggregation.columnCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.GroupAggregation.columnCombo)
},
{
	 header:EURB.GroupAggregation.AggregateFunction
	,id:'aggregationFunction'
	,dataIndex:'aggregationFunction'
	,width:10
	,sortable:false
	,hidden:true
	,editor:EURB.GroupAggregation.aggregationFunctionCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.GroupAggregation.aggregationFunctionCombo)
},
{
	 header:EURB.GroupAggregation.Place
	,id:'place'
	,dataIndex:'place'
	,width:25
	,sortable:true
	,editor:EURB.GroupAggregation.aggregationPlaceCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.GroupAggregation.aggregationPlaceCombo)
}
];

EURB.GroupAggregation.AggregationGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	// defaults - can be changed from outside
	 width: '100%'
	,height: 300
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.GroupAggregation.title
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.GroupAggregation.title
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true, parentColumnId:true}
            ,formConfig:{
                 labelWidth:80
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.GroupAggregation.groupAggregationGrid.commitChanges();
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
		EURB.GroupAggregation.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.GroupAggregation.store
			,columns:EURB.GroupAggregation.cols
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
		EURB.GroupAggregation.AggregationGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.GroupAggregation.AggregationGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.GroupAggregation.AggregationGrid.superclass.afterRender.apply(this, arguments);
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
			 url:EURB.GroupAggregation.storeAction
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
					 url:EURB.GroupAggregation.removeAction
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

