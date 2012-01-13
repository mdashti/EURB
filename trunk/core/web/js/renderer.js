Ext.namespace("Ext.ux");
Ext.ux.dateRenderer = function dateRenderer(value, element, record, rowIndex , colIndex){
	if(!Ext.isDate(value)){
        value = Date.parseJalali(value);
    }
    return value == null ? null : value.format('B/Q/R');
};