EURB.RunReport.Grid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,title: EURB.RunReport.viewReport
	,initComponent:function() {
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.RunReport.store
			,columns:EURB.RunReport.cols
			,viewConfig:{forceFit:true}
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// create bottom paging toolbar
		this.bbar = new Ext.PagingToolbar({
			 store:this.store
			,displayInfo:true
			,pageSize:EURB.defaultPageLimit
			,plugins : [new Ext.ux.plugin.PagingToolbarResizer( {options : [ 15, 20, 25, 50, 100 ], prependCombo: false, displayText: EURB.pageSizeDisplayText})]
		});
		
		this.tbar = ['->',{
			 text:EURB.RunReport.printCurrentPage
			,iconCls:'icon-print'
			,listeners:{
				 scope:this
				,click:{fn: function() { Ext.ux.GridPrinter.print(EURB.RunReport.runReportGrid) },buffer:200}
			}
		}];

		// call parent
		EURB.RunReport.Grid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.RunReport.Grid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.RunReport.Grid.superclass.afterRender.apply(this, arguments);
	}
});

// register xtype
//Ext.reg('report.Grid', EURB.RunReport.Grid);
EURB.RunReport.runReportGrid = new EURB.RunReport.Grid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.RunReport.runReportGrid);
    EURB.mainPanel.doLayout(); 
});