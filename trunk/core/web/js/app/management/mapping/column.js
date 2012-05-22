EURB.Column.mappingTypeCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'myId',
            'displayText'
        ],
        data: [[0, EURB.Column.mappingTypeNone], [1, EURB.Column.mappingTypeTable], [2, EURB.Column.mappingTypeStatic]]
    }),
    valueField: 'myId',
    displayField: 'displayText',
    forceSelection: true,
    allowBlank: false,
    editable: false,
    listeners:{
         'change': function(combo, newValue, oldValue){
         	var rec = EURB.Column.columnGrid.getSelectionModel().getSelected();
         	rec.data['mappingType'] = newValue;
			if(newValue == 2) {
				EURB.Column.staticMappingGrid.show();
			} else {
				EURB.Column.staticMappingGrid.hide();
			}
			EURB.Column.mappingPropertyGrid.nonEditableCells.referenced2IdCol = true;
         	EURB.Column.mappingPropertyGrid.nonEditableCells.referenced3ValueCol = true;
			if(newValue == 1) {
				EURB.Column.mappingPropertyGrid.setProperty('referenced1Table',rec.get('referencedTable'),true);
				EURB.Column.mappingPropertyGrid.setProperty('referenced2IdCol',rec.get('referencedIdCol'),true);
				EURB.Column.mappingPropertyGrid.setProperty('referenced3ValueCol',rec.get('referencedValueCol'),true);
	    	} else if(oldValue == 1){
	    		EURB.Column.mappingPropertyGrid.removeProperty('referenced1Table');
				EURB.Column.mappingPropertyGrid.removeProperty('referenced2IdCol');
				EURB.Column.mappingPropertyGrid.removeProperty('referenced3ValueCol');
	    	}
         }
    }
});
EURB.Column.referencedTableStore = new Ext.data.JsonStore({
    url: EURB.Column.tableSearchAction,
    baseParams: {onlyMappedTables:'true', dbconfig: EURB.Column.selectedDbConfig},
    fields: [
        {name:'id', type:'int'},
        'mappedName' 
    ]
    ,totalProperty:'totalCount'
	,root:'data'
	,autoLoad: true
});
EURB.Column.referencedTableCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: EURB.Column.referencedTableStore,
    valueField: 'id',
    displayField: 'mappedName',
    forceSelection: true,
    allowBlank: false,
    editable: false,
    listeners:{
         'change': function(combo, newValue, oldValue){
			EURB.Column.referencedColIdCombo.setDisabled(true);
			EURB.Column.referencedColValueCombo.setDisabled(true);
			EURB.Column.referencedColIdCombo.setValue('');
			EURB.Column.referencedColValueCombo.setValue('');

			EURB.Column.referencedColStore.removeAll();
			//reload region store and enable region 
			EURB.Column.referencedColStore.reload({
				params: { onlyMappedTables:'true', table: newValue }
			});

         	EURB.Column.mappingPropertyGrid.setProperty('referenced2IdCol' , null, true);
         	EURB.Column.mappingPropertyGrid.setProperty('referenced3ValueCol' , null, true);
         	EURB.Column.referencedColIdCombo.setDisabled(false);
			EURB.Column.referencedColValueCombo.setDisabled(false);
         	EURB.Column.mappingPropertyGrid.nonEditableCells.referenced2IdCol = false;
         	EURB.Column.mappingPropertyGrid.nonEditableCells.referenced3ValueCol = false;
         }
    }
});
EURB.Column.referencedColStore = new Ext.data.JsonStore({
    url: EURB.Column.searchAction,
    baseParams: {onlyMappedTables:'true'},
    fields: [
        {name:'id', type:'int'},
        'mappedName' 
    ]
    ,totalProperty:'totalCount'
	,root:'data'
});
EURB.Column.referencedColIdCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: EURB.Column.referencedColStore,
    valueField: 'id',
    displayField: 'mappedName',
    forceSelection: true,
    allowBlank: false,
    editable: false
});
EURB.Column.referencedColValueCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: EURB.Column.referencedColStore,
    valueField: 'id',
    displayField: 'mappedName',
    forceSelection: true,
    allowBlank: false,
    editable: false
});
EURB.Column.store = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			,{name:'dbConfigId', type:'int'}
			,{name:'tableMappingId', type:'int'}
			,{name:'colOrder', type:'int'}
			,{name:'colTypeName', type:'string'}
			,{name:'columnName', type:'string'}
			,{name:'mappedName', type:'string'}
			,{name:'activeForManager', type:'boolean'}
			,{name:'activeForUser', type:'boolean'}
			,{name:'formatPattern', type:'string'}
			,{name:'referencedIdCol', type:'string'}
			,{name:'referencedTable', type:'string'}
			,{name:'referencedValueCol', type:'string'}
			,{name:'staticMapping', type:'string'}
			,{name:'colDataType', type:'int'}
			,{name:'mappingType', type:'int'}
		]
	})
	,proxy:new Ext.data.HttpProxy({
		url:EURB.Column.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	,baseParams:{
		dbconfig: EURB.Column.selectedDbConfig,
		table: EURB.Column.selectedTable
	}
	,remoteSort:true
	,sortInfo:{field: 'colOrder', direction: "ASC"}
	,listeners:{
		load: function(g) {
			if(EURB.Column.columnGrid) {
		    	EURB.Column.columnGrid.getSelectionModel().selectRow(0);
			}
		} // Allow rows to be rendered.
	}
});

EURB.Column.cols = [{
	 header:EURB.Column.colOrder
	,id:'colOrder'
	,dataIndex:'colOrder'
	,width:10
	,sortable:true
	,editor:new Ext.form.NumberField({
		allowBlank:false
	})
},{
	 header:EURB.Column.columnName
	,id:'columnName'
	,dataIndex:'columnName'
	,width:50
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.Column.mappedName
	,id:'mappedName'
	,dataIndex:'mappedName'
	,width:80
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
	,renderer: function(value, element, record, rowIndex , colIndex) {return Ext.isEmpty(value) ? '---' : value;}
},{
	 header:EURB.Column.colTypeName
	,id:'colTypeName'
	,dataIndex:'colTypeName'
	,width:25
	,sortable:true
	,editor:new Ext.form.TextField({
		allowBlank:false
	})
},{
	 header:EURB.Column.activeForManager
	,id:'activeForManager'
	,dataIndex:'activeForManager'
	,width:25
	,sortable:true
	,editor:new Ext.form.Checkbox()
	,renderer: function(value, element, record, rowIndex , colIndex) {return value ? '<a href="javascript:EURB.Column.columnGrid.deactivateForManagerInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/ok16.png" /></a>' : '<a href="javascript:EURB.Column.columnGrid.activateForManagerInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/cancel16.png" /></a>';}
},{
	 header:EURB.Column.activeForUser
	,id:'activeForUser'
	,dataIndex:'activeForUser'
	,width:25
	,sortable:true
	,editor:new Ext.form.Checkbox()
	,renderer: function(value, element, record, rowIndex , colIndex) {return value ? '<a href="javascript:EURB.Column.columnGrid.deactivateForUserInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/ok16.png" /></a>' : '<a href="javascript:EURB.Column.columnGrid.activateForUserInRow('+rowIndex+')"><img src="'+EURB.resourcesURL+'/img/icon/cancel16.png" /></a>';}
}];

EURB.Column.ColGrid = Ext.extend(Ext.grid.GridPanel, {
	// defaults - can be changed from outside
	 layout:'fit'
	,border:true
	,stateful:false
	,idName:'id'
	,title: EURB.appMenu.column
	,initComponent:function() {
        this.recordForm = new Ext.ux.grid.RecordForm({
             title:EURB.addEdit+' '+EURB.appMenu.column
            ,iconCls:'icon-edit-record'
            ,columnCount:1
            ,ignoreFields:{id:true, dbConfigId:true, tableMappingId:true, formatPattern:true, referencedIdCol:true, referencedTable: true, referencedValueCol:true, staticMapping: true, colDataType:true, mappingType: true}
            ,readonlyFields:{colOrder:true, colTypeName:true, columnName: true}
            //,disabledFields:{name:true}
            ,formConfig:{
                 labelWidth:90
                ,buttonAlign:'right'
                ,bodyStyle:'padding-top:10px'
            }
            ,afterUpdateRecord: function(rec) {
            	EURB.Column.columnGrid.commitChanges();
            }
            , getRowClass:function(record) {
				if(!record.get('id')) {
					return this.newRowCls;
				}
			}
        });

		// create row actions
		this.rowActions = new Ext.ux.grid.RowActions({
			 actions:[{
                 iconCls:'icon-edit-record'
                ,qtip:EURB.editRecord
            }, {
                 iconCls:'icon-up'
                ,qtip:EURB.up
            }, {
                 iconCls:'icon-down'
                ,qtip:EURB.down
            }]
            ,widthIntercept:Ext.isSafari ? 4 : 2
            ,id:'actions'
            ,getEditor:Ext.emptyFn
		});
		this.rowActions.on('action', this.onRowAction, this);
		EURB.Column.cols.push(this.rowActions);
		
		// hard coded - cannot be changed from outside
		var config = {
			store: EURB.Column.store
			,columns:EURB.Column.cols
			,sm: new Ext.grid.RowSelectionModel({
				singleSelect: false,
				listeners: {
				    rowselect: function(sm, row, rec) {
				    	EURB.Column.columnGrid.onRecordSelectionChange(rec);
			        }
			    }
			})
			,plugins:[new Ext.ux.grid.Search({
				iconCls:'icon-zoom'
				//,readonlyIndexes:['name']
				,disableIndexes:['activeForManager','activeForUser']
				//,minChars:2
				,autoFocus:true
			//	,menuStyle:'radio'
			}), this.rowActions, this.recordForm]
			,viewConfig:{forceFit:true}
			,tbar:[EURB.Column.dbConfigCombo," ","-"," "
			,EURB.Column.tableCombo," ","-"," "/*,{
				 text:EURB.delRecord
				,iconCls:'icon-minus'
				,listeners:{
					 scope:this
					,click:{fn:this.deleteSelectedRecords,buffer:200}
				}
			},"-"*/,{
				 text:EURB.enableRecord + ' ' + EURB.Column.forManager
				,iconCls:'icon-activate'
				,listeners:{
					 scope:this
					,click:{fn:this.activateSelectedRecordsForManager,buffer:200}
				}
			},{
				 text:EURB.disableRecord + ' ' + EURB.Column.forManager
				,iconCls:'icon-deactivate'
				,listeners:{
					 scope:this
					,click:{fn:this.deactivateSelectedRecordsForManager,buffer:200}
				}
			},{
				 text:EURB.enableRecord + ' ' + EURB.Column.forUser
				,iconCls:'icon-activate'
				,listeners:{
					 scope:this
					,click:{fn:this.activateSelectedRecordsForUser,buffer:200}
				}
			},{
				 text:EURB.disableRecord + ' ' + EURB.Column.forUser
				,iconCls:'icon-deactivate'
				,listeners:{
					 scope:this
					,click:{fn:this.deactivateSelectedRecordsForUser,buffer:200}
				}
			},"->",{
				 text:EURB.returnBack
				,iconCls:'icon-doorout'
				,listeners:{
					click: function() {
						window.location.href = EURB.baseURL+'management/mapping/db'+EURB.Column.selectedDbConfig+'-table.spy';
					}
				}
			}]
		};

		// apply config
		Ext.apply(this, config);
		Ext.apply(this.initialConfig, config);

		// create bottom paging toolbar
		this.bbar = ['->'];

		// call parent
		EURB.Column.ColGrid.superclass.initComponent.apply(this, arguments);
	}
	,onRender:function() {
		// call parent
		EURB.Column.ColGrid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load();

	}
	,afterRender:function() {
		EURB.Column.ColGrid.superclass.afterRender.apply(this, arguments);
		//this.getBottomToolbar().add({text:'A test button',iconCls:'icon-info'});
	}
	,addRecord:function() {
        var store = this.store;
        if(store.recordType) {
            var rec = new store.recordType({newRecord:true});
            rec.fields.each(function(f) {
                rec.data[f.name] = f.defaultValue || null;
            });
            rec.commit();
            store.add(rec);
            this.onRowAction(this, rec, 'icon-edit-record', 0, 0);
            return rec;
        }
        return false;
    }
	,onRowAction:function(grid, record, action, row, col) {
        switch(action) {
            case 'icon-edit-record':
                this.recordForm.show(record, grid.getView().getCell(row, col));
            break;

            case 'icon-up':
                this.moveColumn(grid, record, action, row, col, 'up');
            break;

            case 'icon-down':
                this.moveColumn(grid, record, action, row, col, 'down');
            break;
        }
    }
    ,onRecordSelectionChange:function(rec) {
    	var mappedName = rec.get('mappedName');
    	var mappedType = rec.get('mappingType');
		if(mappedType == 2) {
			EURB.Column.staticMappingGrid.show();
		} else {
			EURB.Column.staticMappingGrid.hide();
		}
		if(!rec.get('id')) {
			EURB.Column.mappingPropertyGrid.setSource({
	    		columnName : rec.get('columnName')
	    		,mappedName : (mappedName ? mappedName : '---')
	    	});
		} else if(mappedType == 1) {
    		EURB.Column.mappingPropertyGrid.setSource({
	    		columnName : rec.get('columnName')
	    		,mappedName : (mappedName ? mappedName : '---')
	    		,mappingType : mappedType
				,referenced1Table: rec.get('referencedTable')
				,referenced2IdCol: rec.get('referencedIdCol')
				,referenced3ValueCol: rec.get('referencedValueCol')
	    	});
	    	EURB.Column.mappingPropertyGrid.nonEditableCells.referenced2IdCol = false;
         	EURB.Column.mappingPropertyGrid.nonEditableCells.referenced3ValueCol = false;
    	} else {
	    	EURB.Column.mappingPropertyGrid.setSource({
	    		columnName : rec.get('columnName')
	    		,mappedName : (mappedName ? mappedName : '---')
	    		,mappingType : mappedType
	    	});
    	}
    }
	,commitChanges:function() {
		var records = this.store.getModifiedRecords();
		if(!records.length) {
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			if(r.data.id) {
				r.data.newRecord = false;
			} else {
				r.data.newRecord = true;
			}
			data.push(r.data);
		}, this);
		var o = {
			 url:EURB.Column.storeAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'storeData',
				data:Ext.encode(data)
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
			this.showError(o.error || EURB.unknownError);
			return;
		}

		switch(options.params.cmd) {
			default:
				this.store.commitChanges();
				this.store.reload();
				this.getSelectionModel().clearSelections();
			break;
		}
	}
	,showError:EURB.showError
	,deleteSelectedRecords:function() {
		var selectedRecords = this.getSelectionModel().getSelections();
		var records = [];
		for(var i=0; i<selectedRecords.length; i++) {
			if(selectedRecords[i].get('id')) {
				records.push(selectedRecords[i]);
			}
		}
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneSavedRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		Ext.Msg.show({
			 title:EURB.areYouSureToDelTitle
			,msg:String.format(EURB.areYouSureToDelete, records.length == 1 ? records[0].get('columnName') : EURB.records)
			,icon:Ext.Msg.QUESTION
			,buttons:Ext.Msg.YESNO
			,scope:this
			,fn:function(response) {
				if('yes' !== response) {
					return;
				}
				var data = [];
				Ext.each(records, function(r, i) {
					data.push(r.get(this.idName));
				}, this);
				var o = {
					 url:EURB.Column.removeAction
					,method:'post'
					,callback:this.requestCallback
					,scope:this
					,params:{
						cmd: 'deleteData',
						data:Ext.encode(data)
					}
				};
				Ext.Ajax.request(o);
			}
		});
	}
	,moveColumn: function(grid, record, action, row, col, direction) {
		if(record.get(this.idName)) {
			var colOrder = record.get('colOrder');
			var isUpDir = (direction == 'up');
			var total = grid.store.getCount();
			var rec;
			if(isUpDir) {
				var hasUpperRec = false;
				for(var i = 0 ; i < total; i++) {
					rec = grid.store.getAt(i);
					if(rec.get('colOrder') < colOrder && rec.get(this.idName)) {
						hasUpperRec = true;
						break;
					}
				}
				if(!hasUpperRec) {
					Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.Column.cannotMoveFirstRecordUpward).setIcon(Ext.Msg.INFO);
					return;
				}
			} else {
				var hasLowerRec = false;
				for(var i = 0 ; i < total; i++) {
					rec = grid.store.getAt(i);
					if(rec.get('colOrder') > colOrder && rec.get(this.idName)) {
						hasLowerRec = true;
						break;
					}
				}
				if(!hasLowerRec) {
					Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.Column.cannotMoveLastRecordDownward).setIcon(Ext.Msg.INFO);
					return;
				}
			}
			var o = {
				 url:EURB.Column.moveColumnAction
				,method:'post'
				,callback:this.requestCallback
				,scope:this
				,params:{
					cmd: 'moveColumn',
					isUpDir: isUpDir,
					id: record.get(this.idName)
				}
			};
			Ext.Ajax.request(o);
		} else {
			var rf = this.recordForm;
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.Column.saveRecordFisrt, function() {rf.show(record, grid.getView().getCell(row, col));}).setIcon(Ext.Msg.INFO);
		}
	}
	,activateForUserInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.activateSelectedRecordsForUser();
	}
	,deactivateForUserInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.deactivateSelectedRecordsForUser();
	}
	,activateForManagerInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.activateSelectedRecordsForManager();
	}
	,deactivateForManagerInRow:function(row) {
		this.getSelectionModel().selectRow(row);
		this.deactivateSelectedRecordsForManager();
	}
	,activateSelectedRecordsForManager:function() {
		this.activateSelectedRecords('manager');
	}
	,activateSelectedRecordsForUser:function() {
		this.activateSelectedRecords('user');
	}
	,activateSelectedRecords:function(target) {
		var selectedRecords = this.getSelectionModel().getSelections();
		var records = [];
		for(var i=0; i<selectedRecords.length; i++) {
			if(selectedRecords[i].get(this.idName)) {
				records.push(selectedRecords[i]);
			}
		}
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneSavedRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			data.push(r.get(this.idName));
		}, this);
		var o = {
			 url:EURB.Column.activateAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'activateData',
				target: target,
				data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
	}
	,deactivateSelectedRecordsForManager:function() {
		this.deactivateSelectedRecords('manager');
	}
	,deactivateSelectedRecordsForUser:function() {
		this.deactivateSelectedRecords('user');
	}
	,deactivateSelectedRecords:function(target) {
		var selectedRecords = this.getSelectionModel().getSelections();
		var records = [];
		for(var i=0; i<selectedRecords.length; i++) {
			if(selectedRecords[i].get(this.idName)) {
				records.push(selectedRecords[i]);
			}
		}
		if(!records.length) {
			Ext.Msg.alert(Ext.MessageBox.title.warning, EURB.selectAtLeastOneSavedRecordFisrt).setIcon(Ext.Msg.INFO);
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			data.push(r.get(this.idName));
		}, this);
		var o = {
			 url:EURB.Column.deactivateAction
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				cmd: 'deactivateData',
				target: target,
				data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
	}
	,listeners: {
		dblclick : function() {
			var sm = this.getSelectionModel();
            var sel = sm.getSelections();
            if(sel.length > 0) {
            	this.onRowAction(this, sel[0], 'icon-edit-record', 0, 0);
            }
		},
		viewready: function(g) {
		    g.getSelectionModel().selectRow(0);
		} // Allow rows to be rendered.
	}

});

Ext.grid.PropertyColumnModel.prototype.isCellEditable = function(colIndex, rowIndex){
	if(!(colIndex == 1)) {
		return false;
	}
	var p = this.store.getProperty(rowIndex),
        n = p.data.name;
    if(this.grid.nonEditableCells && this.grid.nonEditableCells[n]){
        return false;
    }
    return true;
};

EURB.Column.mappingPropertyGrid = new Ext.grid.PropertyGrid({
    autoHeight: true,
    source: {},
    propertyNames: {
        columnName : EURB.Column.columnName
		,mappedName : EURB.Column.mappedName
		,mappingType: EURB.Column.mappingType
		,referenced1Table: EURB.Column.referencedTable
		,referenced2IdCol: EURB.Column.referencedIdCol
		,referenced3ValueCol: EURB.Column.referencedValueCol
    }
    ,nonEditableCells : {
    	columnName : true
    	,mappedName : true
    	,mappingType : false
    	,referenced1Table : false
    	,referenced2IdCol : true
    	,referenced3ValueCol : true
    }
    ,viewConfig : {
        forceFit: true
        //,scrollOffset: 2 // the grid will never have scrollbars
    }
    ,customRenderers: {
        mappingType: Ext.ux.comboRenderer(EURB.Column.mappingTypeCombo)
        ,referenced1Table: Ext.ux.comboRenderer(EURB.Column.referencedTableCombo)
        ,referenced2IdCol: Ext.ux.comboRenderer(EURB.Column.referencedColIdCombo)
        ,referenced3ValueCol: Ext.ux.comboRenderer(EURB.Column.referencedColValueCombo)
    }
    ,customEditors: {
        mappingType: new Ext.grid.GridEditor(EURB.Column.mappingTypeCombo)
		,referenced1Table: new Ext.grid.GridEditor(EURB.Column.referencedTableCombo)
		,referenced2IdCol: new Ext.grid.GridEditor(EURB.Column.referencedColIdCombo)
		,referenced3ValueCol: new Ext.grid.GridEditor(EURB.Column.referencedColValueCombo)
    }
    ,tbar: ['->',{
    	iconCls: 'icon-cancel'
    	,text: EURB.Column.cancelAll
    	,handler: function() {
    		var rec = EURB.Column.columnGrid.getSelectionModel().getSelected();
    		rec.set('referencedTable', EURB.Column.referencedTableCombo.getValue());
    		rec.set('referencedIdCol', EURB.Column.referencedColIdCombo.getValue());
    		rec.set('referencedValueCol', EURB.Column.referencedColValueCombo.getValue());
    		var o = {
				 url:EURB.Column.storeAction
				,method:'post'
				,callback:this.requestCallback
				,scope:this
				,params:{
					cmd: 'storeData',
					data:Ext.encode([rec.data])
				}
			};
			Ext.Ajax.request(o);
    		rec.commit();
    	}
    },{
    	iconCls: 'icon-save-table',
        text: EURB.Column.saveAll,
        handler : function(){
            // access the Record constructor through the grid's store
            var Plant = EURB.Column.staticMappingGrid.getStore().recordType;
            var p = new Plant({
                idValue: '',
                valueValue: ''
            });
            EURB.Column.staticMappingGrid.stopEditing();
            store.insert(0, p);
            EURB.Column.staticMappingGrid.startEditing(0, 0);
        }
    }]
});

// the column model has information about grid columns
// dataIndex maps the column to the specific data field in
// the data store (created below)
var cm = new Ext.grid.ColumnModel({
    // specify any defaults for each column
    defaults: {
        sortable: true // columns are not sortable by default           
    },
    columns: [
        {
            id: 'idValue',
            header: EURB.Column.origVal,
            dataIndex: 'idValue',
            width: 50,
            // use shorthand alias defined above
            editor: new Ext.form.TextField({
                allowBlank: false
            })
        }, {
            id: 'valueValue',
            header: EURB.Column.mappedVal,
            dataIndex: 'valueValue',
            width: 50,
            // use shorthand alias defined above
            editor: new Ext.form.TextField({
                allowBlank: false
            })
        }
    ]
});

// create the Data Store
var store = new Ext.data.Store({
    // destroy the store if the grid is destroyed
    autoDestroy: true,
    
    // load remote data using HTTP
    url: 'plants.xml',

    // specify a XmlReader (coincides with the XML format of the returned data)
    reader: new Ext.data.XmlReader({
        // records will have a 'plant' tag
        record: 'map',
        // use an Array of field definition objects to implicitly create a Record constructor
        fields: [
            // the 'name' below matches the tag name to read, except 'availDate'
            // which is mapped to the tag 'availability'
            {name: 'idValue', type: 'string'},
            {name: 'valueValue', type: 'string'}
        ]
    }),

    sortInfo: {field:'idValue', direction:'ASC'}
});

// create the editor grid
EURB.Column.staticMappingGrid = new Ext.grid.EditorGridPanel({
    store: store,
    cm: cm,
    width: '100%',
    height: 450,
    viewConfig: {
    	forceFit: true
    },
    autoExpandColumn: 'valueValue', // column with this id will be expanded
    frame: false,
    clicksToEdit: 1,
    tbar: [{
    	iconCls: 'icon-form-add',
        text: EURB.addRecord,
        handler : function(){
            // access the Record constructor through the grid's store
            var Plant = EURB.Column.staticMappingGrid.getStore().recordType;
            var p = new Plant({
                idValue: '',
                valueValue: ''
            });
            EURB.Column.staticMappingGrid.stopEditing();
            store.insert(0, p);
            EURB.Column.staticMappingGrid.startEditing(0, 0);
        }
    }]
});

// register xtype
//Ext.reg('column.colGrid', EURB.Column.ColGrid);
EURB.Column.columnGrid = new EURB.Column.ColGrid();
// application main entry point
Ext.onReady(function() {
	EURB.mainPanel.items.add(EURB.Column.columnGrid);
    EURB.mainPanel.doLayout();
    wrc = Ext.getCmp('viewport-info-panel');
	wrc.add(new Ext.TabPanel({
	    activeTab: 0,
	    tabPosition: 'bottom',
	    items: [{
	        title: EURB.Column.mappingValues,
	        items : [EURB.Column.mappingPropertyGrid,EURB.Column.staticMappingGrid]
	    }]
	}));
	wrc.doLayout();
});