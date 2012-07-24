/////////////////////////formula column combo///////////////////////////
EURB.ReportColumn.formulaColumnCombo = new Ext.form.ComboBox({
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    fieldLabel: EURB.ReportColumn.formulaColumns,
    mode: 'local',
    store: EURB.ReportDesign.columnMappingStore,
    valueField: 'id',
    displayField: 'title',
    forceSelection: true,
    allowBlank: true,
    width:300,
    listeners:{
    	select: function(combo,record,index){
    		addText('[' + record.get('mappedName') + '__t' + record.get('datasetId') + '.' + record.get('columnName') + ']');
    	}
    }
});


EURB.ReportColumn.formulaFieldSet = new Ext.form.FieldSet({
	title: EURB.ReportColumn.formulaEditor,
	   collapsible: true,
	   id : 'report-formula-collapsible',
	   items: [EURB.ReportColumn.formulaColumnCombo,
	           {
	        	   xtype : 'compositefield',
	        	   anchor: '-20',
	        	   msgTarget: 'side',
	        	   fieldLabel: EURB.ReportColumn.formula,
	        	   items : [{
	        	            	xtype : 'textarea',
	        	            	flex : 1,
	        	            	name : 'formula',
	        	            	allowBlank: false,
	        	            	id : 'report-column-formula'
	        	            }]
	           }
	           ]
		,buttons:[{text : '+', width : 30 , handler:function(){addText('+');}},{text : '-', width : 30 , handler:function(){addText('-');}},
		          {text : '*', width : 30 , handler:function(){addText('*');}},{text : '/', width : 30 , handler:function(){addText('/');}}]
});

addText = function(textValue){
	ta = Ext.getCmp('report-column-formula');
	val = ta.getValue() + textValue;
	ta.setValue(val);
};

