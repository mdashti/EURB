////////////////////////////aggregation function combo box///////////////////////////////
EURB.GroupAggregation.aggregationFunctionCombo = new Ext.form.ComboBox({
	fieldLabel:EURB.GroupAggregation.AggregateFunction,
	hiddenName:'aggregationFunction',
	typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'functionValue',
            'functionLabel'
        ],
        data: [['sum', EURB.GroupAggregation.sum], ['count', EURB.GroupAggregation.count], ['avg', EURB.GroupAggregation.average]]
    }),
    valueField: 'functionValue',
    displayField: 'functionLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false
});

////////////////////////////aggregation place combo box///////////////////////////////
EURB.GroupAggregation.aggregationPlaceCombo = new Ext.form.ComboBox({
	fieldLabel: EURB.GroupAggregation.Place,
	hiddenName: 'place',
	typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'placeValue',
            'placeLabel'
        ],
        data: [[0, EURB.GroupAggregation.down], [1, EURB.GroupAggregation.up]]
    }),
    valueField: 'placeValue',
    displayField: 'placeLabel',
    forceSelection: true,
    allowBlank: false,
    editable: false
});


////////////////////////////Group Aggregation grid/////////////////////////////////////

EURB.GroupAggregation.store = new Ext.data.Store({
	proxy:new Ext.data.HttpProxy({
		url:EURB.GroupAggregation.searchAction
        ,listeners: {
        	'exception' : EURB.proxyExceptionHandler
        }
    })
	,baseParams:{
		reportColumn: EURB.ReportColumn.selectedColumn
	}
	,remoteSort:true
});

EURB.GroupAggregation.form = new Ext.form.FormPanel({
	baseCls: 'x-plain',
	labelWidth: 100,
	defaultType: 'textfield',
	reader:new Ext.data.JsonReader({
		 id:'id'
		,totalProperty:'totalCount'
		,root:'data'
		,fields:[
			 {name:'id', type:'int'}
			 ,{name:'parentColumnId', type:'int'}
			 ,{name:'aggregatedColumnMappingId', type:'int'}
			 ,{name:'aggregatedColumnDatasetId', type:'int'}
			 ,{name:'aggregationFunction', type:'string'}
			 ,{name:'place', type:'int'}
		]
	})
	,items: [{
	    name: 'id'
	    ,allowBlank:false
	    ,xtype: 'hidden'
	},
	{
	    name: 'parentColumnId'
	    ,allowBlank:false
	    ,xtype: 'hidden'
	},
	{
	    name: 'aggregatedColumnDatasetId'
	    ,allowBlank:false
	    ,xtype: 'hidden'
	},	
	EURB.GroupAggregation.columnCombo,
	EURB.GroupAggregation.aggregationFunctionCombo,
	EURB.GroupAggregation.aggregationPlaceCombo]
});

EURB.GroupAggregation.window =  new Ext.Window({
	title: EURB.ReportColumn.formulaEditor,
	width: 500,
	height:300,
	minWidth: 300,
	closeAction: 'hide',
	minHeight: 150,
	layout: 'fit',
	plain:true,
	bodyStyle:'padding:5px;',
	buttonAlign:'center',
	items: EURB.GroupAggregation.form,
    buttons: [{
        text:Ext.MessageBox.buttonText.ok,
        handler: function() {
        	if(EURB.GroupAggregation.form.getForm().isValid()) {
        		EURB.GroupAggregation.form.getForm().submit({
        			url:EURB.GroupAggregation.storeAggregationAction,
        			success:function(form, action){
        				EURB.GroupAggregation.window.hide();		        		
        			}
        		});
        	}
        }
    },{
        text: Ext.MessageBox.buttonText.cancel,
        handler: function(){
        	EURB.GroupAggregation.window.hide();
        }
    }]
});