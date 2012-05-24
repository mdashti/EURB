EURB.RunReport.Grid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 border:true
	,stateful:false
	,layout:'fit'
	,viewConfig: { forceFit: true }
	,autoHeight:true
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
	
	updateChartData = function(data){
		
		var options = {
	        chart: {
	           type: data[0][0],
	           renderTo: 'container',
	           height:300
	        },
	        title: {
	           text: data[0][1]
	        },
	        xAxis: {
	           categories: data[1]
	        },
	        yAxis: {
	           title: {
	              text: data[0][3]
	           }
	        },
	        series: [{
				name : data[0][3],
				data : data[2]
	        }]
	     };
		
		EURB.RunReport.chart = new Highcharts.Chart(options);
	};
	
	chartRequestCallback = function(options, success, response) {
		if(true !== success) {
			//this.showError(response.responseText);
			return;
		}
		try {
			var o = Ext.decode(response.responseText);
		}
		catch(e) {
			//this.showError(response.responseText, EURB.unableToDecodeJSON);
			return;
		}
		if(true !== o.success) {
			//this.showError(o.error || EURB.unknownError);
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
		EURB.RunReport.chartPanel = new Ext.Panel({
		    layout: 'border',
		    border: false,
		    width: '100%',
		    //html: '<div id="container" style="width: 50%; height: 400px; direction: ltr !important;"></div>'
		    items: [{
			    		region:'north',
			    		split:true,
			    		height:300,
		    			autoEl: {
			        		tag: 'div',
			        		id: 'container'
			        	}
			    	}
		    		,{
		    			region:'center',
		    			width:'100%',
		    			items:EURB.RunReport.runReportGrid
		    		}
		    	]
		});
		EURB.mainPanel.items.add(EURB.RunReport.chartPanel);
	}
	else{
		EURB.mainPanel.items.add(EURB.RunReport.runReportGrid);
	}
    EURB.mainPanel.doLayout(); 
});