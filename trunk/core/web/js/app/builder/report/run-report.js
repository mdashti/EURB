EURB.RunReport.groupView = new Ext.ux.MultiGroupingView({ 
		hideGroupedColumn :true,
        forceFit: true,
        emptyGroupText: 'All Group Fields Empty',
        displayEmptyFields: true, //you can choose to show the group fields, even when they have no values
        groupTextTpl: '{text}', 
        displayFieldSeperator: ', ' //you can control how the display fields are seperated
});
  
 
 
 
 
 
EURB.RunReport.Grid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 border:true
	,stateful:false
	,region:'center'
	,loadMask:true
	,stripeRows:true
	,title: EURB.RunReport.viewReport
	,initComponent:function() {
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.RunReport.store
			,columns:EURB.RunReport.cols
		};
		if(EURB.RunReport.HasGroup){
			config.view = EURB.RunReport.groupView; 
		}
		else{
			config.viewConfig = {forceFit: true};
		}

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
		
		this.tbar = ['->'/*,{
			xtype: 'exportbutton'
			,component: this
			,store: EURB.RunReport.store
		}*/, {
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
	
		
	chartRequestCallback = function(options, success, response) {
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
		updateChartData(o.data);
	};
	var o = {
			 url:EURB.RunReport.runChart
			,method:'post'
			,callback:chartRequestCallback
			,scope:this
			,params:{
				cmd: 'storeData',
				report:EURB.RunReport.design,
				version:EURB.RunReport.version
			}
	};
	if(EURB.RunReport.hasChart){
		Ext.Ajax.request(o);
	}


	if(EURB.RunReport.hasChart){
		if(EURB.RunReport.chartCount > 1){
			cw = '50%';
			ew = '50%';
		}
		else{
			cw = '100%';
			ew = '0%';
		}
		if(EURB.RunReport.chartCount > 2){
			h = '50%';
		}
		else{
			h = '100%';
		}
		EURB.RunReport.chartPanel = new Ext.Panel({
		    layout: 'border',
			viewConfig: { forceFit: true },
		    border: false,
		    width: '100%',
		    //html: '<div id="container" style="width: 50%; height: 400px; direction: ltr !important;"></div>'
		    items: [{
		    	region: 'north',
		    	layout: 'border',
		    	border: false,
		    	height: 300,
		    	items: [{
		    
					region: 'center',
					split: true,
					height: h,
					width: cw,
					autoEl: 
					{
						tag: 'div',
						id: 'chart0'
			        }
			    },
			    {
			    	region: 'east',
			    	split: true,
					height: h,
					width: ew,
					autoEl: 
					{
						tag: 'div',
						id: 'chart1'
			        }
			    },
			    {
			    	region: 'west',
			    	split: true,
					autoEl: 
					{
						tag: 'div',
						id: 'chart2'
			        }
			    },
			    {
			    	region: 'south',
			    	split: true,
					autoEl: 
					{
						tag: 'div',
						id: 'chart3'
			        }
			    }
			]},
		    EURB.RunReport.runReportGrid]
		});
		EURB.mainPanel.items.add(EURB.RunReport.chartPanel);
	}
	else{
		EURB.mainPanel.items.add(EURB.RunReport.runReportGrid);
	}
    EURB.mainPanel.doLayout(); 
});