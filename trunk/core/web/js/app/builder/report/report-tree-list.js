
EURB.Report.store = new Ext.ux.maximgb.tg.AdjacencyListStore({
	autoLoad : true
	,reader:new Ext.data.JsonReader({
		 id:'id'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			//,{name:'versionId', type:'int'}
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


 

EURB.Report.Grid = new Ext.ux.maximgb.tg.GridPanel({
	  layout:'fit',
	  border:true,
	  stateful:false,
	  store: EURB.Report.store,
	  master_column_id : 'name',
	  columns: EURB.Report.cols,
	  stripeRows: true,
	  autoExpandColumn: 'name',
	  title: EURB.appMenu.report,
	  root_title: 'Name',
	  bbar: new Ext.ux.maximgb.tg.PagingToolbar({
	  	store: EURB.Report.store,
	  	displayInfo: true,
	  	pageSize: 10
	  })
	});
	 



Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.Report.Grid);
	//grid.getSelectionModel().selectFirstRow();
	EURB.mainPanel.doLayout(); 
});



