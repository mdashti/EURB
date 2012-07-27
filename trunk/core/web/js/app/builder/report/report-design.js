EURB.ReportDataset.reportDatasetGrid = new EURB.ReportDataset.DatasetGrid();
EURB.ReportColumn.reportColumnGrid = new EURB.ReportColumn.ColumnGrid();
EURB.ReportFilter.reportFilterGrid = new EURB.ReportFilter.FitlerGrid();
EURB.ReportChart.reportChartGrid = new EURB.ReportChart.ChartGrid();
// application main entry point
Ext.onReady(function() {
	
	EURB.ReportDesign.tabPanel = new Ext.TabPanel({
		layoutOnTabChange:true,
		activeTab: 0,
		tbar: [{
            //xtype:'splitbutton',
            text: EURB.ReportDesign.saveDesign,
            iconCls:  'icon-report-save-big',
            scale: 'large',
            iconAlign: 'top'
            //menu: [{text: 'Menu Button 1'}]
        },{
            text: EURB.ReportDesign.runReport,
            iconCls: 'icon-report-run-big',
            scale: 'large',
            iconAlign: 'top',
            listeners: {
            	click:function(){
            		window.location.href = EURB.baseURL + 'builder/report/run-report' + EURB.ReportDesign.selectedDesign + '-v' + EURB.ReportDesign.selectedVersion + '.spy';
            	}
            }
        },{
            text: EURB.ReportDesign.returnToList,
            iconCls: 'icon-report-return-big',
            scale: 'large',
            iconAlign: 'top',
            listeners: {
            	click:function(){
            		window.location.href = EURB.baseURL + 'builder/report/report-tree-list.spy';
            	}
            }
        }],
		items:[{
			title: EURB.ReportDesign.reportTab,
			layout: 'border',
			items: [{
				region: 'east',
                width: '30%',
                split: true,
                items: [EURB.ReportDesign.InfoForm,EURB.ReportDataset.reportDatasetGrid]
            },{
                region: 'center',
                width: '70%',
                split: true,
                items: [{
                    region: 'center',
                    width: '100%',
                    split: true,
                    items: [EURB.ReportColumn.reportColumnGrid]
                },{
                    region: 'south',
                    width: '100%',
                    split: true,
                    items: [EURB.ReportFilter.reportFilterGrid]
                }]
            }]
		}
		,{
			title: EURB.ReportDesign.reportChart,
			layout: 'border',
			items: [EURB.ReportChart.reportChartGrid, EURB.ReportChart.chartPropertyGrid]
		}]
	});
	
	EURB.ReportDesign.InfoForm.getForm().load({url:EURB.ReportDesign.searchAction, params:{reportDesign : EURB.ReportDesign.selectedDesign,reportVersion: EURB.ReportDesign.selectedVersion}} );

	
	
	EURB.mainPanel.items.add(EURB.ReportDesign.tabPanel);
    EURB.mainPanel.doLayout();
});