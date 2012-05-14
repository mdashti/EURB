EURB.ViewReport.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'versionId', type:'int'}
			,{name:'name', type:'string'}
			,{name:'categoryId', type:'int'}
			,{name:'description', type:'string'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.ViewReport.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	//,baseParams:{}
	,remoteSort:true
});

EURB.ViewReport.cols = [{
	 header:EURB.ViewReport.name
	,id:'name'
	,dataIndex:'name'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.ViewReport.category
	,id:'categoryId'
	,dataIndex:'categoryId'
	,width:50
	,sortable:true
	,editor:EURB.ViewReport.categoryCombo
	,renderer:EURB.ViewReport.comboRenderer(EURB.ViewReport.categoryCombo)
},{
	 header:EURB.ViewReport.description
	,id:'description'
	,dataIndex:'description'
	,width:100
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:true
	})
}];

EURB.ViewReport.Grid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.showReport
	,initComponent:function() {

		// create row actions
		this.rowActions = new Ext.ux.grid.RowActions({
			 actions:[{
                 iconCls:'icon-grid'
                 ,qtip:EURB.ViewReport.editTables
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.ViewReport.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.ViewReport.store
			,columns:EURB.ViewReport.cols
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				//,minChars:2
				,autoFocus:true
			//				,menuStyle:'radio'
			}), this.rowActions]
			,viewConfig:{forceFit:true}
			,tbar:[]
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
		EURB.ViewReport.Grid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.ViewReport.Grid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.ViewReport.Grid.superclass.afterRender.apply(this, arguments);
		//this.getBottomToolbar().add({text:'A test button',iconCls:'icon-info'});
	}
	,onRowAction:function(grid, record, action, row, col) {
        switch(action) {
            case 'icon-grid':
            	window.location.href = EURB.baseURL+'builder/report/run-report'+record.get(this.idName)+'-v'+record.get('versionId')+'.spy';
            break;
        }
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
	,listeners: {
		dblclick : function() {
			var sm = this.getSelectionModel();
            var sel = sm.getSelections();
            if(sel.length > 0) {
            	this.onRowAction(this, sel[0], 'icon-grid', 0, 0);
            }
		}
	}

});

// register xtype
//Ext.reg('report.Grid', EURB.ViewReport.Grid);
EURB.ViewReport.viewReportGrid = new EURB.ViewReport.Grid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.ViewReport.viewReportGrid);
    EURB.mainPanel.doLayout(); 
});