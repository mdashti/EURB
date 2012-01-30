Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
EURB.DBConfig.version = '1.1';
 
// {{{
EURB.DBConfig.DBGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:false
	,stateful:false
	,idName:'id'

	// {{{
	,initComponent:function() {

		// create row actions
		this.rowActions = new Ext.ux.grid.RowActions({
			 actions:[{
				 iconCls:'icon-minus'
				,qtip:'Delete Record'
				,style:'margin:0 0 0 3px'
			}]
		});
		this.rowActions.on('action', this.onRowAction, this);

		// hard coded - cannot be changed from outside
		// {{{
		var config = {
			// {{{
			store:new Ext.data.Store({
				reader:new Ext.data.JsonReader({
					 id:'id'
					,totalProperty:'totalCount'
					,root:'data'
					,fields:[
						 {name:'id', type:'int'}
						,{name:'name', type:'string'}
						,{name:'driverClass', type:'string'}
						,{name:'driverUrl', type:'string'}
						,{name:'username', type:'string'}
						,{name:'password', type:'string'}
						,{name:'testQuery', type:'string'}
					]
				})
				,proxy:new Ext.data.HttpProxy({
					url:EURB.DBConfig.searchAction,
			        listeners: {
			        	'exception' : function(proxy, type, action, options, res) {
			    	    	Ext.Msg.show({
			    	    		title: 'ERROR',
			    	    		msg: Ext.util.JSON.decode(res.responseText).message,
			    	    		icon: Ext.MessageBox.ERROR,
			    	    		buttons: Ext.Msg.OK
			    	    	});
			    	    }
			        }
			    })
				,baseParams:{}
				,remoteSort:true
			})
			// }}}
			// {{{
			,columns:[{
				 header:'Name'
				,id:'name'
				,dataIndex:'name'
				,width:160
				,sortable:true
				,editor:new Ext.form.TextField({
					allowBlank:false
				})
			},{
				 header:'Driver Class'
				,id:'driverClass'
				,dataIndex:'driverClass'
				,width:160
				,sortable:true
				,editor:new Ext.form.TextField({
					allowBlank:false
				})
			},{
				 header:'Driver URL'
				,id:'driverUrl'
				,dataIndex:'driverUrl'
				,width:160
				,sortable:true
				,editor:new Ext.form.TextField({
					allowBlank:false
				})
			},{
				 header:'Username'
				,id:'username'
				,dataIndex:'username'
				,width:160
				,sortable:true
				,editor:new Ext.form.TextField({
					allowBlank:false
				})
			}, this.rowActions]
			// }}}
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				,readonlyIndexes:['testQuery']
				,disableIndexes:['testQuery']
				,minChars:2
				,autoFocus:true
//				,menuStyle:'radio'
			}), this.rowActions]
			,viewConfig:{forceFit:true}
			,buttons:[{
				 text:'Save'
				,iconCls:'icon-disk'
				,scope:this
				,handler:this.commitChanges
			},{
				 text:'Reset'
				,iconCls:'icon-undo'
				,scope:this
				,handler:function() {
					this.store.rejectChanges();
				}
			}]
			,tbar:[{
				 text:'Add Record'
				,iconCls:'icon-plus'
				,listeners:{
					 scope:this
					,click:{fn:this.addRecord,buffer:200}
				}
			}]
		}; // eo config object
		// }}}

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// create bottom paging toolbar
		this.bbar = new Ext.PagingToolbar({
			 store:this.store
			,displayInfo:true
			,pageSize:10
		});

		// call parent
		EURB.DBConfig.DBGrid.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
	// }}}
	// {{{
	,onRender:function() {
		// call parent
		EURB.DBConfig.DBGrid.superclass.onRender.apply(this, arguments);

		this.bbar2 = new Ext.Toolbar({
			renderTo:this.bbar
			,items:['Example of second toolbar', '-', {
				 text:'Button'
				,iconCls:'icon-key'
			}, '-', {
				 xtype:'checkbox'
				,boxLabel:'A Checkbox'
				,checked:true
			}]
		});

		// load store
		this.store.load({params:{start:0,limit:10}});

	} // eo function onRender
	// }}}
	// {{{
	,afterRender:function() {
		EURB.DBConfig.DBGrid.superclass.afterRender.apply(this, arguments);
		this.getBottomToolbar().add({text:'A test button',iconCls:'icon-info'});
	} // eo function afterRender
	// }}}
	// {{{
	,addRecord:function() {
//		console.info("adding record");
	} // eo function addRecord
	// }}}
	// {{{
	,onRowAction:function(grid, record, action, row, col) {
		switch(action) {
			case 'icon-minus':
				this.deleteRecord(record);
			break;

			default:
			break;
		}
	} // eo onRowAction
	// }}}
	// {{{
	,commitChanges:function() {
		var records = this.store.getModifiedRecords();
		if(!records.length) {
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			data.push(r.data);
		}, this);
		var o = {
			 url:EURB.DBConfig.storeAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				 data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
	} // eo function commitChanges
	// }}}
	// {{{
	,requestCallback:function(options, success, response) {
		if(true !== success) {
			this.showError(response.responseText);
			return;
		}
		try {
			var o = Ext.decode(response.responseText);
		}
		catch(e) {
			this.showError(response.responseText, 'Cannot decode JSON object');
			return;
		}
		if(true !== o.success) {
			this.showError(o.error || 'Unknown error');
			return;
		}

		switch(options.params.cmd) {
			case 'saveData':
				var records = this.store.getModifiedRecords();
				Ext.each(records, function(r, i) {
					if(o.insertIds && o.insertIds[i]) {
						r.set(this.idName, o.insertIds[i]);
						delete(r.data.newRecord);
					}
				});
				this.store.commitChanges();
			break;

			case 'deleteData':
			break;
		}
	} // eo function requestCallback
	// }}}
	// {{{
	,showError:function(msg, title) {
		Ext.Msg.show({
			 title:title || 'Error'
			,msg:Ext.util.Format.ellipsis(msg, 2000)
			,icon:Ext.Msg.ERROR
			,buttons:Ext.Msg.OK
			,minWidth:1200 > String(msg).length ? 360 : 600
		});
	} // eo function showError
	// }}}
	// {{{
	,deleteRecord:function(record) {
		Ext.Msg.show({
			 title:'Delete record?'
			,msg:'Do you really want to delete <b>' + record.get('company') + '</b><br/>There is no undo.'
			,icon:Ext.Msg.QUESTION
			,buttons:Ext.Msg.YESNO
			,scope:this
			,fn:function(response) {
				if('yes' !== response) {
					return;
				}
//				console.info('Deleting record');
			}
		});
	} // eo function deleteRecord
	// }}}

}); // eo extend

// register xtype
Ext.reg('dbconfig.dbgrid', EURB.DBConfig.DBGrid);
// }}}

// application main entry point
Ext.onReady(function() {
    Ext.QuickTips.init();
//	var tip = Ext.QuickTips.getQuickTip();
//	Ext.apply(tip, {
//		autoHide:false
//	});
	EURB.mainPanel.items.add(new EURB.DBConfig.DBGrid());
    EURB.mainPanel.doLayout();
 
}); // eo function onReady
 
// eof
