////////////////////////////operator combo box///////////////////////////////
EURB.ReportFilter.operatorCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'operatorValue',
            'operatorLabel',
            'operatorNumberOfOperands'
        ],
        data: [['=' , EURB.ReportFilter.equal , 1] , ['<' , EURB.ReportFilter.smallerThan , 1] , ['>' , EURB.ReportFilter.biggerThan , 1] , ['<>' , EURB.ReportFilter.notEqual , 1]
        	   ,['between' , EURB.ReportFilter.between , 2 ] , ['like' , EURB.ReportFilter.like , 1] , ['is not null' , EURB.ReportFilter.notNull , 0] , ['is null' , EURB.ReportFilter.nul , 0]        	   
        	  ]
    }),
    valueField: 'operatorValue',
    displayField: 'operatorLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false,
    listeners:{
    	select: function(combo,record,index){
    		rform = EURB.ReportFilter.reportFilterGrid.recordForm.form.getForm();
    		numOperands = record.get('operatorNumberOfOperands');
    		if(numOperands == 0){
    			rform.items.itemAt(2).setDisabled(true);
    			rform.items.itemAt(3).setDisabled(true);
    		}
    		else if(numOperands == 1){
    			rform.items.itemAt(2).setDisabled(false);
    			rform.items.itemAt(3).setDisabled(true);
    		}
    		else if(numOperands == 2){
    			rform.items.itemAt(2).setDisabled(false);
    			rform.items.itemAt(3).setDisabled(false);
    		}
    	}
    }
});


////////////////////////////data set grid/////////////////////////////////////

EURB.ReportFilter.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			  {name:'id', type:'int'}
			,{name:'reportDesignId', type:'int'}
			,{name:'reportDesignVersionId', type:'int'}
			,{name:'reportDatasetId', type:'int'}
			,{name:'columnMappingId', type:'int'}
			,{name:'operator', type:'string'}
			,{name:'operand1', type:'string'}
			,{name:'operand2', type:'string'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.ReportFilter.searchAction
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

EURB.ReportFilter.cols = [{
	 header:EURB.ReportFilter.Column
	,id:'columnMappingId'
	,dataIndex:'columnMappingId'
	,width:50
	,sortable:true
	,editor:EURB.ReportFilter.columnCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportFilter.columnCombo)
},
{
	 header:EURB.ReportFilter.Operator
	,id:'operator'
	,dataIndex:'operator'
	,width:25
	,sortable:true
	,editor:EURB.ReportFilter.operatorCombo
	,renderer:EURB.ReportDesign.comboRenderer(EURB.ReportFilter.operatorCombo)
},
{
	 header:EURB.ReportFilter.Operand1
	,id:'operand1'
	,dataIndex:'operand1'
	,width:10
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},
{
	 header:EURB.ReportFilter.Operand2
	,id:'operand2'
	,dataIndex:'operand2'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
}];

EURB.ReportFilter.FitlerGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	// defaults - can be changed from outside
	 width: '100%'
	,height: 300
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.ReportFilter.title
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.ReportFilter.title
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true,reportDesignId:true,reportDesignVersionId:true,reportDatasetId:true}
            ,formConfig:{
                 labelWidth:80
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.ReportFilter.reportFilterGrid.commitChanges();
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
		EURB.ReportFilter.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.ReportFilter.store
			,columns:EURB.ReportFilter.cols
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
				},"->"]
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// create bottom paging toolbar
		this.bbar = ['->'];
		// call parent
		EURB.ReportFilter.FitlerGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.ReportFilter.FitlerGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.ReportFilter.FitlerGrid.superclass.afterRender.apply(this, arguments);
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
			 url:EURB.ReportFilter.storeAction
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
					 url:EURB.ReportFilter.removeAction
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

