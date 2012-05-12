EURB.ReportDataset.reportDatasetGrid = new EURB.ReportDataset.DatasetGrid();
EURB.ReportColumn.reportColumnGrid = new EURB.ReportColumn.ColumnGrid();
EURB.ReportFilter.reportFilterGrid = new EURB.ReportFilter.FitlerGrid();
// application main entry point
Ext.onReady(function() {
	EURB.ReportDesign.panel = new Ext.Panel({
            layout: 'border',
            border: false,
            width: '100%',
            items: [{
                region: 'east',
                width: '30%',
                split: true,
                items: [EURB.ReportDataset.reportDatasetGrid]
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
	});
	EURB.mainPanel.items.add(EURB.ReportDesign.panel);
    EURB.mainPanel.doLayout();
});