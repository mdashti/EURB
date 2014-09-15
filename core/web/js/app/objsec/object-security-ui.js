if(!EURB.ObjSec) {
	EURB.ObjSec = {};
}
EURB.ObjSec.getSharingWindows = function() {
	//var CheckBoxClass = Ext.ux.form.TriCheckbox;
	var CheckBoxClass = Ext.form.Checkbox;
	var groupStore = new Ext.data.Store({
		reader:new Ext.data.JsonReader({
			 id:'id'
			,totalProperty:'totalCount'
			,root:'data'
			,fields:[
				 {name:'id', type:'int'}
				,{name:'groupName', type:'string'}
			]
		})
		,proxy:new Ext.data.HttpProxy({
			url:EURB.ObjSec.groupSearchAction
	        ,listeners: {
	        	'exception' : EURB.proxyExceptionHandler
	        }
	    })
		,autoLoad : false
	});
	
	var groupCols = [{
		 header:EURB.ObjSec.groupName
		,id:'groupName'
		,dataIndex:'groupName'
		,width:40
		,sortable:true
		,editor:new Ext.form.TextField({
			allowBlank:false
		})
	}];
	
	var groupGrid = new Ext.grid.GridPanel({
		ddGroup          : 'groupGridDDGroup',
	    store            : groupStore,
	    columns          : groupCols,
		enableDragDrop   : true,
	    stripeRows       : true,
	    autoExpandColumn : 'groupName'
	    //,title            : EURB.ObjSec.availableGroups
	});
	
	var userStore = new Ext.data.Store({
		reader:new Ext.data.JsonReader({
			 id:'id'
			,totalProperty:'totalCount'
			,root:'data'
			,fields:[
				 {name: 'id', mapping : 'id', type:'int'},
			{name: 'username', mapping : 'username', type:'string'}
			]
		})
		,proxy:new Ext.data.HttpProxy({
			url:EURB.ObjSec.userSearchAction
	        ,listeners: {
	        	'exception' : EURB.proxyExceptionHandler
	        }
	    })
		,autoLoad : false
	});
	// Column Model shortcut array
	var userCols = [
		{ id : 'username', header: EURB.ObjSec.userName, width: 160, sortable: true, dataIndex: 'username'}
	];
	
	// declare the source Grid
	var userGrid = new Ext.grid.GridPanel({
	    ddGroup          : 'userGridDDGroup',
	    store            : userStore,
	    columns          : userCols,
	    enableDragDrop   : true,
	    stripeRows       : true,
	    autoExpandColumn : 'username'
	    //,title            : EURB.ObjSec.availableUsers
	});
	
	
	var authoritiesStore = new Ext.data.Store({
		reader:new Ext.data.JsonReader({
			 id:'id'
			,totalProperty:'aclEntryListTotalCount'
			,root:'aclEntryList'
			,fields:[
				{name: 'id', type: 'number'},
				{name: 'type', type: 'number'},
				{name: 'name', type: 'string'},
				{name: 'view', type: 'boolean'},
				{name: 'edit', type: 'boolean'},
				{name: 'del', type: 'boolean'},
				{name: 'execute', type: 'boolean'},
				{name: 'sharing', type: 'boolean'}
			]
		})
		,proxy:new Ext.data.HttpProxy({
			url:EURB.ObjSec.objectAuthoritiesSearchAction
	        ,listeners: {
	        	'exception' : function(proxy, type, action, options, res) {
			    	Ext.Msg.show({
			    		title: Ext.MessageBox.title.error,
			    		msg: Ext.util.JSON.decode(res.responseText).message,
			    		icon: Ext.MessageBox.ERROR,
			    		buttons: Ext.Msg.OK
			    	});
			    	window.hide();
			    }
	        }
	    })
		,baseParams: {
			data: ''
		}
		,remoteSort:false
		,autoLoad:false
	});
	
	var viewCheckColumn = new Ext.grid.CheckColumn({
		 header:EURB.ObjSec.authoritiesView
		,id:'view'
		,dataIndex:'view'
		,editor:new CheckBoxClass()
		,align:'center'
	});
	var editCheckColumn = new Ext.grid.CheckColumn({
		 header:EURB.ObjSec.authoritiesEdit
		,id:'edit'
		,dataIndex:'edit'
		,editor:new CheckBoxClass()
		,align:'center'
	});
	var delCheckColumn = new Ext.grid.CheckColumn({
		 header:EURB.ObjSec.authoritiesDel
		,id:'del'
		,dataIndex:'del'
		,editor:new CheckBoxClass()
		,align:'center'
	});
	var executeCheckColumn = new Ext.grid.CheckColumn({
		 header:EURB.ObjSec.authoritiesExecute
		,id:'execute'
		,dataIndex:'execute'
		,width:20
		,editor:new CheckBoxClass()
		,align:'center'
	});
	var sharingCheckColumn = new Ext.grid.CheckColumn({
		 header:EURB.ObjSec.authoritiesSharing
		,id:'sharing'
		,dataIndex:'sharing'
		,editor:new CheckBoxClass()
		,align:'center'
	});
	var authoritiesColModel = new Ext.grid.ColumnModel({
		defaults: {
			sortable: true
			,width:20
		},
		columns: [{
			 header: EURB.ObjSec.groupOrUserName
			,id:'name'
			,dataIndex:'name'
			,width:40
			,renderer: function(value, metaData, record, rowIndex, colIndex, store) {
				if(record.get('type') == 1) {
					return '<div class="ux-row-action-item icon-group" style="float: right"></div> ' + value;
				} else {
					return '<div class="ux-row-action-item icon-user" style="float: right"></div> ' + value;
				}
			}
		},viewCheckColumn,editCheckColumn,delCheckColumn,executeCheckColumn,sharingCheckColumn]
	});
	var AuthoritiesGrid = Ext.extend(Ext.grid.GridPanel, {
		ddGroup          : 'authGridDDGroup',
		enableDragDrop   : true,
		// defaults - can be changed from outside
		region:'center'
		,layout:'fit'
		,border:true
		,stateful:false
		,idName:'id'
		//,title: EURB.appMenu.authorities
		,initComponent:function() {
	        // hard coded - cannot be changed from outside
			var config = {
				store: authoritiesStore
				,cm: authoritiesColModel
				,viewConfig: {forceFit:true}
				,plugins: [viewCheckColumn,editCheckColumn,delCheckColumn,executeCheckColumn,sharingCheckColumn]
			};
	
			// apply config
			Ext.apply(this, config);
			Ext.apply(this.initialConfig, config);
			// call parent
			AuthoritiesGrid.superclass.initComponent.apply(this, arguments);
		}
		,onRender:function() {
			// call parent
			AuthoritiesGrid.superclass.onRender.apply(this, arguments);
	
			// load store
			this.store.load();
	
		}
		,afterRender:function() {
			AuthoritiesGrid.superclass.afterRender.apply(this, arguments);
			//this.getBottomToolbar().add({text:'A test button',iconCls:'icon-info'});
		}
		,commitChanges:function() {
			/*var data = [];
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
			Ext.Ajax.request(o);*/
		}
		,requestCallback:function(options, success, response) {
			if(true !== success) {
				this.showError(response.responseText);
				return false;
			}
			try {
				var o = Ext.decode(response.responseText);
			}
			catch(e) {
				this.showError(response.responseText, EURB.unableToDecodeJSON);
				return false;
			}
			if(true !== o.success) {
				this.showError(o.error || o.message || EURB.unknownError);
				return false;
			}
	
			switch(options.params.cmd) {
				default:
					this.store.commitChanges();
					return true;
				break;
			}
		}
		,showError:EURB.showError
		,selectAllRecords: function() {
			/*this.store.each(function(rec) {
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
			}, this);*/
		}
		,selectNoneRecords: function() {
			/*this.store.each(function(rec) {
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
			}, this);*/
		}
	});

	var authGrid = new AuthoritiesGrid();
	var window = new Ext.Window({
    	modal: true,
		title: EURB.ObjSec.share,
		width: 600,
		height:350,
		minWidth: 300,
		closeAction: 'hide',
		minHeight: 150,
		layout: 'fit',
		plain:true,
		bodyStyle:'padding:5px;',
		buttonAlign:'center',
		items: new Ext.Panel({
			layout: 'border',
			items: [new Ext.Panel({
				split:true,
				region:'south',
				height: 100,
				layout       : 'hbox',
				defaults     : { flex : 1 }, //auto stretch
				layoutConfig : { align : 'stretch' },
				items        : [
					userGrid,
					groupGrid
				]
				//,bbar: [EURB.Group.Usr.userSelectDragDropHelp]
			}),authGrid]
		}),
	    buttons: [{
	        text:Ext.MessageBox.buttonText.ok,
	        handler: function() {
	        	var data = [];
				authoritiesStore.each(function(r) {
					data.push(r.data);
				}, this);
				
				var o = {
					 url:EURB.ObjSec.objectAuthoritiesStoreAction
					,method:'post'
					,callback: function(options, success, response){
						if(authGrid.requestCallback(options, success, response)) {
							window.hide();
						} 
					}
					,scope:authGrid
					,params:{
						cmd:'storeData',
						permData:Ext.encode(data),
						objData:[window.currentObjectId]
					}
				};
				Ext.Ajax.request(o);
	        }
	        //, tabIndex: 1
	    },{
	        text: Ext.MessageBox.buttonText.cancel,
	        handler: function(){
	            window.hide();
	        }
	        //, tabIndex: 2
	    }],
	    listeners: {
	    	hide: function(thiz) {
	    		authoritiesStore.removeAll();
	    	}
	    }
	});
	window.render(Ext.getBody());
	
	var notifyDropIntoAuthGrid = function(ddSource, e, data) {
		var records = ddSource.dragData.selections;
		var sidArr = [];
		var username;
		Ext.each(records, function(r, i) {
			username = r.get('username');
			if(username) { //it is a user
				sidArr.push(new authoritiesStore.recordType({
					id: r.get('id')
					,type: 2
					,name: username
					,view: false
					,edit: false
					,del: false
					,execute: false
					,sharing: false
				}, r.get('id')));
			} else { //it is a group
				sidArr.push(new authoritiesStore.recordType({
					id: r.get('id')
					,type: 1
					,name: r.get('groupName')
					,view: false
					,edit: false
					,del: false
					,execute: false
					,sharing: false
				}, r.get('id')));
			}
		},this);
		userStore.remove(records);
		groupStore.remove(records);
		authoritiesStore.add(sidArr);
		return true;
	};
	
	var notifyDropIntoUserOrGroupGrid = function(ddSource, e, data) {
		var records = ddSource.dragData.selections;
		var usersArr = [];
		var groupsArr = [];
		var type;
		Ext.each(records, function(r, i) {
			type = r.get('type');
			if(type == 2) { //it is a user
				usersArr.push(new userStore.recordType({
					id: r.get('id')
					,username: r.get('name')
				}, r.get('id')));
			} else { // if(type == 1)//it is a group
				groupsArr.push(new groupStore.recordType({
					id: r.get('id')
					,groupName: r.get('name')
				}, r.get('id')));
			}
		},this);
		authoritiesStore.remove(records);
		userStore.add(usersArr);
		groupStore.add(groupsArr);
		return true;
	};
	//drop a user to authGrid
	var authGridDropTargetEl = authGrid.getView().scroller.dom;
	var authGridDropTarget = new Ext.dd.DropTarget(authGridDropTargetEl, {
		ddGroup : 'userGridDDGroup',
		notifyDrop : notifyDropIntoAuthGrid
	});
	
	var authGridDropTarget = new Ext.dd.DropTarget(authGridDropTargetEl, {
		ddGroup : 'groupGridDDGroup',
		notifyDrop : notifyDropIntoAuthGrid
	});
	
	var groupGridDropTargetEl = groupGrid.getView().scroller.dom;
	var groupGridDropTarget = new Ext.dd.DropTarget(groupGridDropTargetEl, {
		ddGroup : 'authGridDDGroup',
		notifyDrop : notifyDropIntoUserOrGroupGrid
	});
	
	var userGridDropTargetEl = userGrid.getView().scroller.dom;
	var userGridDropTarget = new Ext.dd.DropTarget(userGridDropTargetEl, {
		ddGroup : 'authGridDDGroup',
		notifyDrop : notifyDropIntoUserOrGroupGrid
	});
	
	window.onShowForARecord = function(identifier, title, objectType) {
		this.setTitle(title);
		this.currentObjectType = objectType;
		this.currentObjectId = identifier;
		authoritiesStore.load({
			params:{
				data: identifier
			},
			callback: function(){
				userStore.load({
					callback: function() {
						groupStore.load({
							callback: function() {
								var usersArr = [];
								var groupsArr = [];
								authoritiesStore.each(function(rec){
									var theId = rec.get('id');
									var theName = rec.get('name');
									if(rec.get('type') == 2) {//user
										userStore.each(function(r){
											if(r.get('id') === theId) {
												usersArr.push(r);
											}
										});
									} else {//if(theId.get('type') == 1) { //group
										groupStore.each(function(r){
											if(r.get('id') === theId) {
												groupsArr.push(r);
											}
										});
									}
								});
								userStore.remove(usersArr);
								groupStore.remove(groupsArr);
							}
						});
					}
				});
			}
		});
		
	};
	
	return window;
}