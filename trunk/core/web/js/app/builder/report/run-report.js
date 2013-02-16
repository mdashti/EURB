EURB.RunReport.groupView = new Ext.ux.LockingMultiGroupingGridView({ 
		hideGroupedColumn :true,
        forceFit: true,
        emptyGroupText: EURB.RunReport.EmptyGroupField,
        displayEmptyFields: true, //you can choose to show the group fields, even when they have no values
        groupTextTpl: '{text} : {gvalue}', 
        displayFieldSeperator: ', ' //you can control how the display fields are seperated
});

//Instantiate LockingGridGroupSummary
var groupSummary = new Ext.grid.LockingGridGroupSummary();
 
 
 
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
		}, {
			 text:EURB.RunReport.exportAllReportToExcel
			,iconCls:'icon-excel'
			,listeners:{
				 scope:this
				,click:{fn: function() { window.location.href = EURB.baseURL+'builder/report/report'+EURB.RunReport.design+'-v'+EURB.RunReport.version+(new Date().format('-B-Q-R-G-i'))+'.xls'; },buffer:200}
			}
		}, {
			 text:EURB.RunReport.exportAllReportToWord
			,iconCls:'icon-word'
			,listeners:{
				 scope:this
				,click:{fn: function() { window.location.href = EURB.baseURL+'builder/report/report'+EURB.RunReport.design+'-v'+EURB.RunReport.version+(new Date().format('-B-Q-R-G-i'))+'.docx'; },buffer:200}
			}
		}];

		// call parent
		EURB.RunReport.Grid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.RunReport.Grid.superclass.onRender.apply(this, arguments);
		if(this.loadMask){
            this.loadMask = new Ext.LoadMask(this.bwrap,
                    Ext.apply({store:this.store}, this.loadMask));
        }
		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.RunReport.Grid.superclass.afterRender.apply(this, arguments);
	}
});



// register xtype
//Ext.reg('report.Grid', EURB.RunReport.Grid);
if(EURB.RunReport.HasGroup){
	EURB.RunReport.runReportGrid = new Ext.Panel({
	    layout: 'border',
		viewConfig: { forceFit: true },
	    border: false,
	    width: '100%',
	    items: [{
	    	region: 'center',
			split: true,
			width: '100%',
			height: '100%',
			autoEl: 
			{
				tag: 'div',
				id: 'group'
	        }
	    }]
	});
}
else{
	EURB.RunReport.runReportGrid = new EURB.RunReport.Grid();
}

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
		EURB.RunReport.chartPanel = new Ext.FormPanel({
		    layout: 'border',
			viewConfig: { forceFit: true },
		    border: false,
		    width: '100%',
		    items: [{
		    	region: 'north',
		    	layout: 'border',
		    	border: false,
		    	height: 320,
		    	items: [{
		    		region: 'north',
		    		layout: 'border',
			    	border: false,
		    		height: 25,
			    	items: [{
					    
			    		region: 'center',
				    	xtype: 'button',
						text: EURB.RunReport.exportCharts,
						id: 'export',
						width:100,
						hidden: false,
						handler : function(){
							Highcharts.testExport();
						}
				    },
	    	        {
				    	region: 'center',
			    		xtype: 'textfield',
						name: 'chart0_formula',
						id: 'chart0_formula',
						fieldLabel: 'formula',
						allowBlank : true,
						width:150,
						hidden:true,
						style:{
							direction : 'ltr'
						}
			    	},
			    	{
						region: 'east',
				    	xtype: 'button',
						text: 'click',
						id: 'chart0_formula_button',
						width:100,
						hidden: true,
						handler: function(){
							formulaTextfield = EURB.RunReport.chartPanel.find('name', 'chart0_formula')[0];
							newFormula = formulaTextfield.getValue();
							var o = {
									 url:EURB.baseURL + 'builder/report/get-reportchart' + EURB.RunReport.design + '-i0.spy'
									,method:'post'
									,callback:function(options, success, response) {
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
										EURB.RunReport.chartPanel.find('name', 'center-div')[0].setWidth('50%');
										EURB.RunReport.chartPanel.find('name', 'east-div')[0].setWidth('50%');
										EURB.RunReport.chartCount++;
										updateChartData(o.data);
										EURB.mainPanel.doLayout();
									}
									,params:{
										formula: newFormula
									}
							};
							Ext.Ajax.request(o);
						}
					}
		    	]},	
				{
		    
					region: 'center',
					split: true,
					height: h,
					width: cw,
					name: 'center-div',
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
					name: 'east-div',
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
    
    if(EURB.RunReport.HasGroup){
	    EURB.RunReport.multiGroup = new Ext.ux.MultiGroupingPanel({
		   	 store: EURB.RunReport.store
		   	,cm:new Ext.ux.grid.LockingColumnModel(EURB.RunReport.cols)
		   	,view: EURB.RunReport.groupView
		   	,plugins:[groupSummary]
		   	,width: '100%'
		   	,height: 700
		   	,border:true
		   	,collapsible: true
		   	,animCollapse: true
		   	,title: EURB.RunReport.viewReport
			,iconCls: 'icon-grid'
		   	,renderTo: 'group'
	   }); 
	
	   EURB.RunReport.store.load();
    }

	 
});