EURB.ReportDesign.tablesStore = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
		     {name:'id', type:'int'}
			,{name:'dbConfigId', type:'int'}
			,{name:'mappedName', type:'string'}
			,{name:'mappedTypeName', type:'string'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.ReportDesign.tablesListAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	//,baseParams:{}
	,remoteSort:true
});

EURB.ReportDesign.tablesCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    store: EURB.ReportDesign.tablesStore,
    forceSelection: true,
    valueField: 'id',
    displayField: 'mappedName',
    listeners: {
    	'select' : function(thiz, newValue, oldValue) {
    		alert(newValue + ':' + oldValue);
    	}
    }
});


// register xtype
//Ext.reg('report.Grid', EURB.Report.Grid);
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.ReportDesign.tablesCombo);
    EURB.mainPanel.doLayout(); 
});