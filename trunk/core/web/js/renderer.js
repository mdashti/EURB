Ext.namespace("Ext.ux");
Ext.ux.dateRenderer = function dateRenderer(value, element, record, rowIndex , colIndex){
	if(!Ext.isDate(value)){
        value = Date.parseJalali(value);
    }
    return value == null ? null : value.format('B/Q/R');
};

Ext.ux.comboRenderer = function(combo){
    return function(value){
        var record = combo.findRecord(combo.valueField, value);
        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
    }
};