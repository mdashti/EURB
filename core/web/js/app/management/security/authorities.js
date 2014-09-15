EURB.Authorities.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			{name: 'id', type: 'string'},
			{name: 'category', type: 'string'},
			{name: 'viewlist', type: 'boolean'},
			{name: 'view', type: 'boolean'},
			{name: 'create', type: 'boolean'},
			{name: 'edit', type: 'boolean'},
			{name: 'del', type: 'boolean'},
			{name: 'execute', type: 'boolean'},
			{name: 'sharing', type: 'boolean'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.Authorities.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	,baseParams:{
		sid:EURB.Authorities.selectedSID
	}
	,remoteSort:false
});

var VIEWLIST = 1;
var VIEW = 2;
var CREATE = 3;
var EDIT = 4;
var DEL = 5;
var EXECUTE = 6;
var SHARING = 7;
EURB.Authorities.isEditableCell = function(catId, colIndex) {
	if(catId == 'BMM' || catId == 'SMM' || catId == 'RGM') {
		if(colIndex == VIEWLIST || colIndex == CREATE || colIndex == EDIT || colIndex == DEL || colIndex == EXECUTE || colIndex == SHARING) {
			return false;
		}
	} else if(catId == 'DBM') {
		if(colIndex == EXECUTE) {
			return false;
		}
	} else if(catId == 'TM' || catId == 'CM') {
		if(colIndex == DEL || colIndex == EXECUTE) {
			return false;
		}
	} else if(catId == 'UM' || catId == 'GM' || catId == 'RS') {
		if(colIndex == SHARING || colIndex == EXECUTE) {
			return false;
		}
	} else if(catId == 'RM') {
		if(colIndex == CREATE || colIndex == DEL || colIndex == SHARING || colIndex == EXECUTE) {
			return false;
		}
	}
	return true;
};
EURB.Authorities.AuthoritiesCheckColumn = Ext.extend(Ext.grid.CheckColumn, {
	renderer: function(value, metaData, record, rowIndex, colIndex, store) {
		if(EURB.Authorities.isEditableCell(record.get('id'), colIndex)) {
			return EURB.Authorities.AuthoritiesCheckColumn.superclass.renderer.apply(this, arguments);
		} else {
			return '';
		}
	}
});
var viewlistCheckColumn = new EURB.Authorities.AuthoritiesCheckColumn({
	 header:EURB.Authorities.viewlist
	,id:'viewlist'
	,dataIndex:'viewlist'
	,editor:new Ext.form.Checkbox()
	,align:'center'
});
var viewCheckColumn = new EURB.Authorities.AuthoritiesCheckColumn({
	 header:EURB.Authorities.view
	,id:'view'
	,dataIndex:'view'
	,editor:new Ext.form.Checkbox()
	,align:'center'
});
var createCheckColumn = new EURB.Authorities.AuthoritiesCheckColumn({
	 header:EURB.Authorities.create
	,id:'create'
	,dataIndex:'create'
	,editor:new Ext.form.Checkbox()
	,align:'center'
});
var editCheckColumn = new EURB.Authorities.AuthoritiesCheckColumn({
	 header:EURB.Authorities.edit
	,id:'edit'
	,dataIndex:'edit'
	,editor:new Ext.form.Checkbox()
	,align:'center'
});
var delCheckColumn = new EURB.Authorities.AuthoritiesCheckColumn({
	 header:EURB.Authorities.del
	,id:'del'
	,dataIndex:'del'
	,editor:new Ext.form.Checkbox()
	,align:'center'
});
var executeCheckColumn = new EURB.Authorities.AuthoritiesCheckColumn({
	 header:EURB.Authorities.execute
	,id:'execute'
	,dataIndex:'execute'
	,width:20
	,editor:new Ext.form.Checkbox()
	,align:'center'
});
var sharingCheckColumn = new EURB.Authorities.AuthoritiesCheckColumn({
	 header:EURB.Authorities.sharing
	,id:'sharing'
	,dataIndex:'sharing'
	,editor:new Ext.form.Checkbox()
	,align:'center'
});
EURB.Authorities.colModel = new Ext.grid.ColumnModel({
	defaults: {
		sortable: true
		,width:20
	},
	columns: [{
		 header:EURB.Authorities.category
		,id:'category'
		,dataIndex:'category'
		,width:40
	},viewlistCheckColumn,viewCheckColumn,createCheckColumn,editCheckColumn,delCheckColumn,executeCheckColumn,sharingCheckColumn]
});
EURB.Authorities.AuthoritiesGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	region:'center'
	,layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.authorities
	,initComponent:function() {
        // hard coded - cannot be changed from outside
		var config = {
			store: EURB.Authorities.store
			,cm:EURB.Authorities.colModel
			,viewConfig:{forceFit:true}
			,plugins: [viewlistCheckColumn,viewCheckColumn,createCheckColumn,editCheckColumn,delCheckColumn,executeCheckColumn,sharingCheckColumn]
			,tbar:[EURB.Authorities.SIDCombo,{
				 text:EURB.Authorities.saveAll
				,iconCls:'icon-save-table'
				,listeners:{
					 scope:this
					,click:{fn:this.commitChanges,buffer:200}
				}
			},'->',{
				 text:EURB.Authorities.selectAll
				,iconCls:'icon-activate'
				,listeners:{
					 scope:this
					,click:{fn:this.selectAllRecords,buffer:200}
				}
			},{
				 text:EURB.Authorities.selectNone
				,iconCls:'icon-delete'
				,listeners:{
					 scope:this
					,click:{fn:this.selectNoneRecords,buffer:200}
				}
			}]
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);
		// call parent
		EURB.Authorities.AuthoritiesGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.Authorities.AuthoritiesGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.Authorities.AuthoritiesGrid.superclass.afterRender.apply(this, arguments);
		//this.getBottomToolbar().add({text:'A test button',iconCls:'icon-info'});
	}
	,commitChanges:function() {
		var data = [];
		this.store.each(function(r) {
			data.push(r.data);
		}, this);
		if(!data.length) {
			return;
		}
		var o = {
			 url:EURB.Authorities.storeAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd:'storeData',
				data:Ext.encode(data),
				sid:EURB.Authorities.selectedSID
			}
		};
		Ext.Ajax.request(o);
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
			this.showError(o.error || o.message || EURB.unknownError);
			return;
		}

		switch(options.params.cmd) {
			default:
				this.store.commitChanges();
			break;
		}
	}
	,showError:EURB.showError
	,selectAllRecords: function() {
		this.store.each(function(rec) {
			var catId = rec.get('id');
			if(!rec.get('viewlist') && EURB.Authorities.isEditableCell(catId, VIEWLIST)) {
				rec.set('viewlist',true);
			}
			if(!rec.get('view') && EURB.Authorities.isEditableCell(catId, VIEW)) {
				rec.set('view',true);
			}
			if(!rec.get('create') && EURB.Authorities.isEditableCell(catId, CREATE)) {
				rec.set('create',true);
			}
			if(!rec.get('edit') && EURB.Authorities.isEditableCell(catId, EDIT)) {
				rec.set('edit',true);
			}
			if(!rec.get('del') && EURB.Authorities.isEditableCell(catId, DEL)) {
				rec.set('del',true);
			}
			if(!rec.get('execute') && EURB.Authorities.isEditableCell(catId, EXECUTE)) {
				rec.set('execute',true);
			}
			if(!rec.get('sharing') && EURB.Authorities.isEditableCell(catId, SHARING)) {
				rec.set('sharing',true);
			}
			return true;
		}, this);
	}
	,selectNoneRecords: function() {
		this.store.each(function(rec) {
			var catId = rec.get('id');
			if(rec.get('viewlist') && EURB.Authorities.isEditableCell(catId, VIEWLIST)) {
				rec.set('viewlist',false);
			}
			if(rec.get('view') && EURB.Authorities.isEditableCell(catId, VIEW)) {
				rec.set('view',false);
			}
			if(rec.get('create') && EURB.Authorities.isEditableCell(catId, CREATE)) {
				rec.set('create',false);
			}
			if(rec.get('edit') && EURB.Authorities.isEditableCell(catId, EDIT)) {
				rec.set('edit',false);
			}
			if(rec.get('del') && EURB.Authorities.isEditableCell(catId, DEL)) {
				rec.set('del',false);
			}
			if(rec.get('execute') && EURB.Authorities.isEditableCell(catId, EXECUTE)) {
				rec.set('execute',false);
			}
			if(rec.get('sharing') && EURB.Authorities.isEditableCell(catId, SHARING)) {
				rec.set('sharing',false);
			}
			return true;
		}, this);
	}
});

// register xtype
//Ext.reg('dbconfig.dbgrid', EURB.Authorities.AuthoritiesGrid);
EURB.Authorities.authoritiesGrid = new EURB.Authorities.AuthoritiesGrid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.Authorities.authoritiesGrid);
    EURB.mainPanel.doLayout();
});