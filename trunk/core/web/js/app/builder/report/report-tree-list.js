
EURB.Report.store = new Ext.ux.maximgb.tg.AdjacencyListStore({
	autoLoad : true
	,reader:new Ext.data.JsonReader({
		 id:'id'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'versionId', type:'int'}
			,{name:'name', type:'string'}
			,{name:'description', type:'string'}
			,{name: '_parent', type: 'int'}
			,{name: '_is_leaf', type: 'bool'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.Report.searchReportAndCategoryAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	//,baseParams:{}
	,remoteSort:true
});

EURB.Report.cols = [{
	 header:EURB.Report.name
	,id:'name'
	,dataIndex:'name'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.Report.description
	,id:'description'
	,dataIndex:'description'
	,width:100
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
}];

EURB.Report.Grid = Ext.extend(Ext.ux.maximgb.tg.GridPanel, {
	layout:'fit',
	border:true,
	stateful:false,
	idName:'id',
	title: EURB.appMenu.report,
	initComponentPreOverride:function() {
		// create row actions
		this.rowActions = new Ext.ux.grid.RowActions({
			actions:[{
				iconCls:'icon-minus'
				,qtip:EURB.delRecord
				,style:'margin:0 0 0 3px'
			},{
	            iconCls:'icon-copy'
	            ,qtip:EURB.copyRecord
	        },{
	            iconCls:'icon-edit-record'
		        ,qtip:EURB.editRecord
		    },{
	          	iconCls:'icon-interactive'
	           	,qtip:EURB.Report.viewInteractiveReport
	        },{
	           	iconCls:'icon-calendar'
	           	,qtip:EURB.Report.scheduleReport
	        }]
			,groupActions:[]
	        ,widthIntercept:Ext.isSafari ? 4 : 2
	        ,id:'actions'
	        ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.Report.cols.push(this.rowActions);
		var config = {
				store: EURB.Report.store,
				master_column_id : 'name',
				columns: EURB.Report.cols,
				stripeRows: true,
				autoExpandColumn: 'name', 
				root_title: 'Name',
				plugins:[this.rowActions],
				tbar:[{
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
				},
				{
					text:EURB.expandAll
					,iconCls:'icon-expand'
					,listeners:{
						scope:this
						,click:{fn:this.expandAll,buffer:200}
					}
				}
				]
		  		,viewConfig:{forceFit:true}
		};
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);
		this.bbar = new Ext.ux.maximgb.tg.PagingToolbar({
			store: EURB.Report.store,
		  	displayInfo: true,
		  	pageSize: 10
		});
	}
	,onRowAction:function(grid, record, action, row, col) {
		if(record.get('_is_leaf')){
			switch(action) {
		        case 'icon-minus':
		            this.deleteRecord(record);
		        break;
		
		        case 'icon-edit-record':
		        	window.location.href = EURB.baseURL+'builder/report/report'+record.get(this.idName)+'-design.spy';
		        break;
		
		        case 'icon-copy':
		            this.copyRecord(record);
		        break;
		
		        case 'icon-interactive':
		        	window.location.href = EURB.baseURL+'builder/report/run-report'+record.get(this.idName)+'-v'+record.get('versionId')+'.spy';
		        break;
			}
		}
		else{
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.cannotApplyFunctionOnCategory).setIcon(Ext.Msg.INFO);
		}
	}
	,addRecord: function(){
		window.location.href = EURB.baseURL+'builder/report/report-design.spy';
	}
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
		this.deleteSelectedRecords();
	}
	,deleteSelectedRecords:function() {
		var selectedRecords = this.getSelectionModel().getSelections();
		var records = [];
		for(var i=0; i<selectedRecords.length; i++) {
			if(selectedRecords[i].get('id')) {
				if(selectedRecords[i].get('_is_leaf')){
					records.push(selectedRecords[i]);
				}
				else{
					Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.cannotApplyFunctionOnCategory).setIcon(Ext.Msg.INFO);
				}
				
			}
		}
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneSavedRecordFisrt).setIcon(Ext.Msg.INFO);
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
					 url:EURB.Report.removeAction
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
	,activateSelectedRecords:function() {
		var index = this.getSelectionModel().getSelectedCell();
		if(!index) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var record = this.store.getAt(index[0]);
		if(record.get('_is_leaf')){
			
			records = [record];
			var data = [];
			Ext.each(records, function(r, i) {
				data.push(r.get(this.idName));
			}, this);
			var o = {
				 url:EURB.Report.activateAction
				,method:'post'
				,callback:this.requestCallback
				,scope:this
				,params:{
					cmd: 'activateData',
					data:Ext.encode(data)
				}
			};
			Ext.Ajax.request(o);
		}
		else{
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.cannotApplyFunctionOnCategory).setIcon(Ext.Msg.INFO);
		}
		 
	}
	,deactivateSelectedRecords:function() {
		var index = this.getSelectionModel().getSelectedCell();
		if(!index) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var record = this.store.getAt(index[0]);
		if(record.get('_is_leaf')){
				
			records = [record];
			Ext.each(records, function(r, i) {
				data.push(r.get(this.idName));
			}, this);
			var o = {
				 url:EURB.Report.deactivateAction
				,method:'post'
				,callback:this.requestCallback
				,scope:this
				,params:{
					cmd: 'deactivateData',
					data:Ext.encode(data)
				}
			};
			Ext.Ajax.request(o);
		}
		else{
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.cannotApplyFunctionOnCategory).setIcon(Ext.Msg.INFO);
		}
	}
	,expandAll : function() {
		for(i = 0; i < this.store.data.length; i++){
			var record = this.store.data.get(i);
	        if(!this.store.isLeafNode(record) && this.store.isLoadedNode(record) && !this.store.isExpandedNode(record)){
	            this.store.expandNode(record);
	         }
	    }
	}
	,listeners: {
		dblclick : function() {
			var sm = this.getSelectionModel();
            var sel = sm.getSelections();
            if(sel.length > 0) {
            	this.onRowAction(this, sel[0], 'icon-interactive', 0, 0);
            }
		}
	}
});
 





Ext.onReady(function() {
	var grid = new EURB.Report.Grid();
	
	EURB.mainPanel.items.add(grid);
	//grid.getSelectionModel().selectFirstRow();
	EURB.mainPanel.doLayout();
			
});



