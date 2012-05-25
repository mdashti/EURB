/**
 * @author chander
 */
Ext.ux.MultiGroupingStore = Ext.extend(Ext.data.GroupingStore, {
    sortInfo: [],
    
    sort: function(field, dir){
        //alert('sort MultiGroupingStore');
        var f = [];
        
        if (Ext.isArray(field)) {
            for (i = 0, len = field.length; i < len; ++i) {
                f.push(this.fields.get(field[i]));
            }
        }
        else {
            f.push(this.fields.get(field));
        }
        
        if (f.length < 1) {
            return false;
        }
        
        if (!dir) {
            if (this.sortInfo && this.sortInfo.length > 0 && this.sortInfo[0].field == f[0].name) { // toggle sort dir
                dir = (this.sortToggle[f[0].name] || "ASC").toggle("ASC", "DESC");
            }
            else {
                dir = f[0].sortDir;
            }
        }
        
        var st = (this.sortToggle) ? this.sortToggle[f[0].name] : null;
        var si = (this.sortInfo) ? this.sortInfo : null;
        
        this.sortToggle[f[0].name] = dir;
        this.sortInfo = [];
        for (i = 0, len = f.length; i < len; ++i) {
            this.sortInfo.push({
                field: f[i].name,
                direction: dir
            });
        }
        
        if (!this.remoteSort) {
            this.applySort();
            this.fireEvent("datachanged", this);
        }
        else {
            if (!this.load(this.lastOptions)) {
                if (st) {
                    this.sortToggle[f[0].name] = st;
                }
                if (si) {
                    this.sortInfo = si;
                }
            }
        }
    },
    
    setDefaultSort: function(field, dir){
        //alert('setDefault sort MultiGroupingStore '+field + dir);
        if(field){
	    	dir = dir ? dir.toUpperCase() : "ASC";
	        this.sortInfo = [];
	        
	        if (!Ext.isArray(field)) 
	            this.sortInfo.push({
	                field: field,
	                direction: dir
	            });
	        else {
	            for (i = 0, len = field.length; i < len; ++i) {
	                this.sortInfo.push({
	                    field: field[i].field,
	                    direction: dir
	                });
	                this.sortToggle[field[i]] = dir;
	            }
	        }
        }
    },
    
    constructor: function(config){
        Ext.ux.MultiGroupingStore.superclass.constructor.apply(this, arguments);
    },
    
    
    groupBy: function(field, forceRegroup){
        //alert('in groupby '+this.groupField+'  '+field);
        if (!forceRegroup && this.groupField == field) {
            return; // already grouped by this field
        }
        
        
        if (this.groupField) {
            for (z = 0; z < this.groupField.length; z++) 
                if (field == this.groupField[z]) 
                    return;
            this.groupField.push(field);
        }
        else 
            this.groupField = [field];
        
        if (this.remoteGroup) {
            if (!this.baseParams) {
                this.baseParams = {};
            }
            this.baseParams['groupBy'] = field;
        }
        if (this.groupOnSort) {
            this.sort(field);
            return;
        }
        if (this.remoteGroup) {
            this.reload();
        }
        else {
            var si = this.sortInfo || [];
            if (si.field != field) {
                this.applySort();
            }
            else {
                this.sortData(field);
            }
            this.fireEvent('datachanged', this);
        }
    },
    
    applySort: function(){
        //alert('applysort MultiGroupingStore');
        var si = this.sortInfo;
        
        if (si && si.length > 0 && !this.remoteSort) {
            this.sortData(si, si[0].direction);
        }
        
        if (!this.groupOnSort && !this.remoteGroup) {
            var gs = this.getGroupState();
            if (gs && gs != this.sortInfo) {
                //alert('group on Sort');
                this.sortData(this.groupField);
            }
        }
    },
    
    getGroupState: function(){
        //alert('getGroupState');
        //alert('this.groupOnSort '+ this.groupOnSort+"\n this.groupField "+this.groupField+'\nthis.sortInfo '+this.sortInfo+ '\n this.groupField '+this.groupField)
        //alert('Condition1: this.groupOnSort && this.groupField !== false '+ (this.groupOnSort && this.groupField !== false));
        //alert('condition 2: this.sortInfo '+ this.sortInfo);
        //var fi =this.sortInfo;;
        //fi.push(this.groupField);
        return this.groupOnSort && this.groupField !== false ? (this.sortInfo ? this.sortInfo : undefined) : this.groupField;
    },
    
    sortData: function(flist, direction){
        //alert(' In sortData '+ flist.length +"  "+flist);
        direction = direction || 'ASC';
        
        var st = [];
        
        var o;
        for (i = 0, len = flist.length; i < len; ++i) {
            o = flist[i];
          //  alert('o.field ' + o.field);
            
            st.push(this.fields.get(o.field ? o.field : o).sortType);
        }
        //alert('fn start');
        
        var fn = function(r1, r2){
        
            var v1 = [];
            var v2 = [];
            var len = flist.length;
            var o;
            var name;
            //alert('len '+len);
            for (i = 0; i < len; ++i) {
                o = flist[i];
                name = o.field ? o.field : o;
                //alert('In fn r1.data[name] '+ r1.data[name]+'r2.data[name] '+ r2.data[name]);
                v1.push(st[i](r1.data[name]));
                v2.push(st[i](r2.data[name]));
            }
            
            var result;
            for (i = 0; i < len; ++i) {
                result = v1[i] > v2[i] ? 1 : (v1[i] < v2[i] ? -1 : 0);
                if (result != 0) 
                    return result;
            }
            
            return result; //if it gets here, that means all fields are equal
        };
        ////////alert('fn finished');
        this.data.sort(direction, fn);
        if (this.snapshot && this.snapshot != this.data) {
            this.snapshot.sort(direction, fn);
        }
    }
    
});


Ext.ux.MultiGroupingView = Ext.extend(Ext.grid.GroupingView, {
    displayEmptyFields: false,
    displayFieldSeperator: ', ',
    
    renderRows: function(){
        var groupField = this.getGroupField();
        var eg = !!groupField;
        // if they turned off grouping and the last grouped field is hidden
        if (this.hideGroupedColumn) {
            var colIndexes = [];
            for (i = 0, len = groupField.length; i < len; ++i) {
                colIndexes.push(this.cm.findColumnIndex(groupField[i]));
            }
            if (!eg && this.lastGroupField !== undefined) {
                this.mainBody.update('');
                for (i = 0, len = this.lastGroupField.length; i < len; ++i) {
                    this.cm.setHidden(this.cm.findColumnIndex(this.lastGroupField[i]), false);
                }
                delete this.lastGroupField;
            }
            else 
                if (eg && colIndexes.length > 0 && this.lastGroupField === undefined) {
                    this.lastGroupField = groupField;
                    for (i = 0, len = colIndexes.length; i < len; ++i) {
                        this.cm.setHidden(colIndexes[i], true);
                    }
                }
                else 
                    if (eg && this.lastGroupField !== undefined && groupField !== this.lastGroupField) {
                        this.mainBody.update('');
                        for (i = 0, len = this.lastGroupField.length; i < len; ++i) {
                            this.cm.setHidden(this.cm.findColumnIndex(this.lastGroupField[i]), false);
                        }
                        this.lastGroupField = groupField;
                        for (i = 0, len = colIndexes.length; i < len; ++i) {
                            this.cm.setHidden(colIndexes[i], true);
                        }
                    }
        }
        return Ext.ux.MultiGroupingView.superclass.renderRows.apply(this, arguments);
    },
	getRows : function(){
		//alert('getRows');
        if(!this.enableGrouping){
            return Ext.grid.GroupingView.superclass.getRows.call(this);
        }
		var groupField=this.getGroupField();
        var r = [];
        var g, gs = this.getGroups();
		//alert(groupField[groupField.length-1]);
        for(var i = 0, len = gs.length; i < len; i++){
			//alert(gs[i].childNodes[1].childNodes);
			//alert(i+"    "+gs[i].innerHTML );
			//var groupName=gs[i].childNodes[0].childNodes[0].innerHTML;
//
//user();
			//alert(groupName.substring(0,groupName.indexOf(':')))
			//alert(gs[i].childNodes[0].childNodes[0].innerHTML);
			//alert(groupName.substring(0,groupName.indexOf(':'))+"    "+groupField[groupField.length-1])
			//var ingroups;
			//var 
			
			var groupName=gs[i].childNodes[0].childNodes[0].innerHTML;
			if(groupName.substring(0,groupName.indexOf(':'))==groupField[groupField.length-1])
			{
            g = gs[i].childNodes[1].childNodes;
            for(var j = 0, jlen = g.length; j < jlen; j++){
				
                r[r.length] = g[j];
            }
			}
			else
			{
				r=getRowsFromGroup(r,gs[i].childNodes[1].childNodes,groupField[groupField.length-1])
			}
        }
        return r;
    }
    ,
    doRender: function(cs, rs, ds, startRow, colCount, stripe){
       // alert('do Renderer MULTI GPU');
        if (rs.length < 1) {
            return '';
        }
		//alert(cs);
        var groupField = this.getGroupField();
        this.enableGrouping = !!groupField;
        
        if (!this.enableGrouping || this.isUpdating) {
            return Ext.grid.GroupingView.superclass.doRender.apply(this, arguments);
        }
        
        var gstyle = 'width:' + this.getTotalWidth() + ';';
        
        var gidPrefix = this.grid.getGridEl().id;
        
        
        
        
        
        var groups = [], curGroup, i, len, gid;
        var lastvalues = [];
        //alert(rs.length);
        for (i = 0, len = rs.length; i < len; i++) {
            var rowIndex = startRow + i;
            var r = rs[i];
            
            var gvalue = [];
            var fieldName;
            var grpDisplayValues = [];
            var prefix = [];
            var v;
            var changed = 0;
            //alert('groupField.length '+groupField.length);
            for (j = 0, gfLen = groupField.length; j < gfLen; j++) {
            
                fieldName = groupField[j];
                //alert(fieldName);
                v = r.data[fieldName];
                
                var colIndex = this.cm.findColumnIndex(fieldName);                
                var cfg = this.cm.config[colIndex];
                var p = this.showGroupName ? (cfg.groupName || cfg.header)+': ' : '';
                //var groupRenderer = cfg.groupRenderer || cfg.renderer;
                
                //alert(rowIndex+'       v  '+ fieldName+'  '+r.data[fieldName]);
                
                if (v) {
                
                    if (i == 0) {
                        lastvalues[j] = v;
                        gvalue.push(v);
                        grpDisplayValues.push(fieldName + ': ' + v);
                        prefix.push(p);
                        gvalue.push(v);
                    }
                    else {//alert((lastvalues[j] != v)+ "   "+j+"  " + (rowIndex-1)+"    "+ lastvalues[j]+  "  "+v);
                        if (lastvalues[j] != v) {
                            changed = 1;
                            gvalue.push(v);
                            grpDisplayValues.push(fieldName + ': ' + v);
                            prefix.push(p);
                            lastvalues[j] = v;
                        }
                        else {
                            //lastvalues[j] = v;
                            //gvalue.push(v);
                            if (groupField.length - 1 == j) 
                                curGroup.rs.push(r);
                            //if(j==groupField.length-1)
                            if (changed == 1){ 
                                grpDisplayValues.push(fieldName + ': ' + v);
                            	prefix.push(p);
                            }
                        }
                        
                    }
                }
                else 
                    if (this.displayEmptyFields) {
                        grpDisplayValues.push(fieldName + ': ');
                        prefix.push(p);
                    }
                
               
                
            }
            
            //alert(rowIndex-1 +"  "+grpDisplayValues.length);
            //g = this.getGroup(gvalue, r, groupRenderer, rowIndex, colIndexes[index], ds);
            if (gvalue.length < 1 && this.emptyGroupText) 
                g = this.emptyGroupText;
            else 
                g = grpDisplayValues;//.join(this.displayFieldSeperator);
            //alert(rowIndex +"  "+grpDisplayValues.length);
            for (k = 0; k < grpDisplayValues.length; k++) {
                //alert(grpDisplayValues[k]);
                g = grpDisplayValues[k];
                pre = prefix[k];
                //alert((!curGroup || curGroup.group != g)+ g+"  " +k);
                if (!curGroup || curGroup.group != g) {
                
                    gid = gidPrefix + '-gp-' + groupField[k] + '-' + Ext.util.Format.htmlEncode(g);
                    //alert(gid);
                    
                    // if state is defined use it, however state is in terms of expanded
                    // so negate it, otherwise use the default.
                    var isCollapsed = typeof this.state[gid] !== 'undefined' ? !this.state[gid] : this.startCollapsed;
                    var gcls = isCollapsed ? 'x-grid-group-collapsed' : '';
					var jjj
					for (jjj = 0; jjj < groupField.length; jjj++) {
							if (g.substring(0, g.indexOf(':')) == groupField[jjj]) 
								break;
						}
                    if (k == grpDisplayValues.length - 1) {
                        curGroup = {
                            group: g,
                            gvalue: gvalue[k],
                            text: pre + g.substring(g.indexOf(':') + 1),
                            groupId: gid,
                            startRow: rowIndex,
                            rs: [r],
                            cls: gcls,
                            style: gstyle+'padding-right:'+(jjj*12)+'px;',
                        };
                    }
                    else {
                        curGroup = {
                            group: g,
                            gvalue: gvalue[k],
                            text: pre + g.substring(g.indexOf(':') + 1),
                            groupId: gid,
                            startRow: rowIndex,
                            rs: [],
                            cls: gcls,
                            style: gstyle+'padding-right:'+(jjj*12)+'px;',
                        };
                    }
                    groups.push(curGroup);
                }
                else {
                    curGroup.rs.push(r);
                }
                r._groupId = gid;
            }
        }
        var buf = [];
		var toEnd=0;
        for (i = 0, len = groups.length; i < len; i++) {
			toEnd++;
			
            var g = groups[i];
		//	alert(g.group.substring(0,g.group.indexOf(':'))+ "   "+groupField[groupField.length-1]);
            this.doGroupStart(buf, g, cs, ds, colCount);
           // if(g.rs.length!=0)
            buf[buf.length] = Ext.grid.GroupingView.superclass.doRender.call(this, cs, g.rs, ds, g.startRow, colCount, stripe);
		
          //  if ((i+1) % groupField.length == 0 && i != 0) {
		 // alert('sdlfs');
				if(g.group.substring(0,g.group.indexOf(':'))==groupField[groupField.length-1])
				{
				var jj;
				//alert('sdfdsfds');
				var gg=groups[i+1];
				//alert(gg);
					if(gg!=null)
					{
						
						for (jj = 0; jj < groupField.length; jj++) {
							if (gg.group.substring(0, gg.group.indexOf(':')) == groupField[jj]) 
								break;
						}
						//alert(jj+"  "+gg.group.substring(0,gg.group.lastIndexOf(':')));
						toEnd=groupField.length-jj;
					}
				for (k = 0; k < toEnd; k++) {
					this.doGroupEnd(buf, g, cs, ds, colCount);
					
				}
				toEnd=jj;
			
			}
        
        }
       // alert('Group End');
        return buf.join('');
    }
});
function getRowsFromGroup(r,gs,lsField)
{
	for(var i = 0, len = gs.length; i < len; i++){
				
			var groupName=gs[i].childNodes[0].childNodes[0].innerHTML;
			//alert(groupName.substring(0,groupName.indexOf(':')));
			if(groupName.substring(0,groupName.indexOf(':'))==lsField)
			{
            g = gs[i].childNodes[1].childNodes;
            for(var j = 0, jlen = g.length; j < jlen; j++){
				
                r[r.length] = g[j];
            }
			}
			else
			{
				r=getRowsFromGroup(r,gs[i].childNodes[1].childNodes,lsField);
			}
        }
	return r;
}
