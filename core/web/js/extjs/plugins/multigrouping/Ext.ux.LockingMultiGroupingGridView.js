Ext.ux.LockingMultiGroupingGridView = Ext.extend(Ext.ux.grid.LockingGridView, {

	lockText : 'Lock',
    unlockText : 'Unlock',
    rowBorderWidth : 1,
    lockedBorderWidth : 1,
	
	/*
     * This option ensures that height between the rows is synchronized
     * between the locked and unlocked sides. This option only needs to be used
     * when the row heights aren't predictable.
     */
    syncHeights: false,
	
	/**
     * @cfg {String} groupByText Text displayed in the grid header menu for grouping by a column
     * (defaults to 'Group By This Field').
     */
    groupByText : 'Group By This Field',
    /**
     * @cfg {String} showGroupsText Text displayed in the grid header for enabling/disabling grouping
     * (defaults to 'Show in Groups').
     */
    showGroupsText : 'Show in Groups',
    /**
     * @cfg {Boolean} hideGroupedColumn <tt>true</tt> to hide the column that is currently grouped (defaults to <tt>false</tt>)
     */
    hideGroupedColumn : false,
    /**
     * @cfg {Boolean} showGroupName If <tt>true</tt> will display a prefix plus a ': ' before the group field value
     * in the group header line.  The prefix will consist of the <tt><b>{@link Ext.grid.Column#groupName groupName}</b></tt>
     * (or the configured <tt><b>{@link Ext.grid.Column#header header}</b></tt> if not provided) configured in the
     * {@link Ext.grid.Column} for each set of grouped rows (defaults to <tt>true</tt>).
     */
    showGroupName : false,
    /**
     * @cfg {Boolean} startCollapsed <tt>true</tt> to start all groups collapsed (defaults to <tt>false</tt>)
     */
    startCollapsed : false,
    /**
     * @cfg {Boolean} enableGrouping <tt>false</tt> to disable grouping functionality (defaults to <tt>true</tt>)
     */
    enableGrouping : true,
    /**
     * @cfg {Boolean} enableGroupingMenu <tt>true</tt> to enable the grouping control in the column menu (defaults to <tt>true</tt>)
     */
    enableGroupingMenu : true,
    /**
     * @cfg {Boolean} enableNoGroups <tt>true</tt> to allow the user to turn off grouping (defaults to <tt>true</tt>)
     */
    enableNoGroups : true,
    /**
     * @cfg {String} emptyGroupText The text to display when there is an empty group value (defaults to <tt>'(None)'</tt>).
     * May also be specified per column, see {@link Ext.grid.Column}.{@link Ext.grid.Column#emptyGroupText emptyGroupText}.
     */
    emptyGroupText : '(None)',
    /**
     * @cfg {Boolean} ignoreAdd <tt>true</tt> to skip refreshing the view when new rows are added (defaults to <tt>false</tt>)
     */
    ignoreAdd : false,
    /**
     * @cfg {String} groupTextTpl The template used to render the group header (defaults to <tt>'{text}'</tt>).
     */
    groupTextTpl : '{text}',
    
    /**
     * @cfg {Function} groupRenderer This property must be configured in the {@link Ext.grid.Column} for
     * each column.
     */

    // private
    gidSeed : 1000,
   
	constructor: function(config){
	
		this.addEvents('togglegroup');
	
		Ext.ux.LockingMultiGroupingGridView.superclass.constructor.call(this, config);
		// Added so we can clear cached rows each time the view is refreshed
		this.on("beforerefresh", function() {
			//console.debug("Cleared Row Cache");
			if(this.rowsCache) delete rowsCache;
		}, this);
	}
	
	,initTemplates : function(){
		
		var ts = this.templates || {};
        this.state = {};
		
		if(!ts.master){
			ts.master = new Ext.Template(
				'<div class="x-grid3" hidefocus="true">',
					'<div class="x-grid3-locked">',
						'<div class="x-grid3-header"><div class="x-grid3-header-inner"><div class="x-grid3-header-offset" style="{lstyle}">{lockedHeader}</div></div><div class="x-clear"></div></div>',
						'<div class="x-grid3-scroller"><div class="x-grid3-body" style="{lstyle}">{lockedBody}</div><div class="x-grid3-scroll-spacer"></div></div>',
					'</div>',
					'<div class="x-grid3-viewport x-grid3-unlocked">',
						'<div class="x-grid3-header"><div class="x-grid3-header-inner"><div class="x-grid3-header-offset" style="{ostyle}">{header}</div></div><div class="x-clear"></div></div>',
						'<div class="x-grid3-scroller"><div class="x-grid3-body" style="{bstyle}">{body}</div><a href="#" class="x-grid3-focus" tabIndex="-1"></a></div>',
					'</div>',
					'<div class="x-grid3-resize-marker">&#160;</div>',
					'<div class="x-grid3-resize-proxy">&#160;</div>',
				'</div>'
			);
		}
		
		
		if(!this.startLockedGroup) {
            this.startLockedGroup = new Ext.XTemplate(
                '<div id="{groupId}" class="x-grid-group x-grid-group-locked {cls}">',
                    '<div id="{groupId}-hd" class="x-grid-group-hd" style="{lockedheaderstyle}"><div class="x-grid-group-title">' + (this.lockedGroupHeaderRenderer ? '{renderedHeader}' : this.groupTextTpl),'</div></div>',
                    '<div id="{groupId}-bd" class="x-grid-group-body">'
            );
        }
        
        if(!this.startUnlockedGroup) {
            this.startUnlockedGroup = new Ext.XTemplate(
                '<div id="{groupId}" class="x-grid-group x-grid-group-unlocked {cls}">' +
                    '<div id="{groupId}-hd" class="x-grid-group-hd {unlockedheadercls}" style="{unlockedheaderstyle}">' + 
                        '<div class="x-grid-group-title">' + (this.unlockedGroupHeaderRenderer ? '{renderedHeader}' : this.groupTextTpl) + '</div>' + 
                    '</div>' +
                '<div id="{groupId}-bd" class="x-grid-group-body">'
            );
        }
		
        this.startLockedGroup.compile();
        this.startUnlockedGroup.compile();
        this.endGroup = '</div></div>';
		
		this.templates = ts;
		Ext.ux.LockingMultiGroupingGridView.superclass.initTemplates.call(this);
	}
	

	,displayEmptyFields: false
    
	,displayFieldSeperator: ', '
    
	,renderRows: function(){
		
		//alert('renderRows');
		var groupField = this.getGroupField();
		var eg = !!groupField;
		// if they turned off grouping and the last grouped field is hidden
		if (this.hideGroupedColumn) {
			
			var colIndexes = [];
				
			for (var i = 0, len = groupField.length; i < len; ++i) {
				var cidx = this.cm.findColumnIndex(groupField[i]);
				if (cidx >= 0) {
					colIndexes.push(cidx);
				} else {
					//console.debug("Ignore unknown column : ",groupField[i]);
				}
			}
				
			if (!eg && this.lastGroupField !== undefined) {
				this.mainBody.update('');
				for (var i = 0, len = this.lastGroupField.length; i < len; ++i) {
					var cidx = this.cm.findColumnIndex(this.lastGroupField[i]);
					if(cidx >= 0) {
						this.cm.setHidden(cidx, false);
					} else { 
						//alert("Unhide Col: "+cidx);
					}
				}
				delete this.lastGroupField;
				delete this.lgflen;
			}
       
			else if (eg && colIndexes.length > 0 && this.lastGroupField === undefined) {
				this.lastGroupField = groupField;
				this.lgflen = groupField.length;
				for (var i = 0, len = colIndexes.length; i < len; ++i) {
					//alert("Hide Col: "+colIndexes[i]);
					this.cm.setHidden(colIndexes[i], true);
				}
			}

			else if (eg && this.lastGroupField !== undefined && (groupField !== this.lastGroupField || this.lgflen != this.lastGroupField.length)) {
				this.mainBody.update('');
				for (var i = 0, len = this.lastGroupField.length; i < len; ++i) {
					var cidx = this.cm.findColumnIndex(this.lastGroupField[i]);
					if (cidx >= 0) {
						this.cm.setHidden(cidx, false);
					} else {
						alert("Unhide Col: "+cidx);
					}
					this.lastGroupField = groupField;
					this.lgflen = groupField.length;
					for (var i = 0, len = colIndexes.length; i < len; ++i) {
						// alert("Hide Col: "+colIndexes[i]);
						this.cm.setHidden(colIndexes[i], true);
					}
				}
			}
		}
	
		return Ext.ux.LockingMultiGroupingGridView.superclass.renderRows.apply(this, arguments);
	
	}

    

   /** This sets up the toolbar for the grid based on what is grouped
    * It also iterates over all the rows and figures out where each group should appeaer
    * The store at this point is already stored based on the groups.
    */
	,doRender: function(cs, rs, ds, startRow, colCount, stripe){
		
		//console.debug ("doRender: ",cs, rs, ds, startRow, colCount, stripe);
		
		var ss = this.grid.getTopToolbar();
		if (rs.length < 1) {
			return '';
		}
        
		var groupField = this.getGroupField();
		var gfLen = groupField.length;
    
		// Remove all entries alreay in the toolbar
		for (var hh = 0; hh < ss.items.length; hh++) {
			Ext.removeNode(Ext.getDom(ss.items.itemAt(hh).id));
		}
     
		if(gfLen==0) {
			ss.addItem(new Ext.Toolbar.TextItem("Drop Columns Here To Group"));
			//console.debug("No Groups");
		} else {
			// Add back all entries to toolbar from GroupField[]    
			ss.addItem(new Ext.Toolbar.TextItem("Grouped By:"));
			for (var gfi = 0; gfi < gfLen; gfi++) {
				var t = groupField[gfi];
				if( gfi > 0) {
					ss.addItem(new Ext.Toolbar.Separator());
				}
				var b = new Ext.Toolbar.Button({
					//text: this.cm.lookup[this.cm.findColumnIndex(t)].header
					text: this.cm.lookup[t].header
				});
				b.fieldName = t;
				ss.addItem(b);
				//console.debug("Added Group to Toolbar :",this, t, b.text);
			}
		}

		this.enableGrouping = !!groupField;
        
		if (!this.enableGrouping || this.isUpdating) {
			return this.doRender2.apply(this, arguments);
		}
        
		var gstyle = 'width:' + this.getTotalWidth() + ';';
		var gidPrefix = this.grid.getGridEl().id;
		var groups = [], curGroup, i, len, gid;
		var lastvalues = [];
		var added = 0;
		var currGroups = [];
		
		// Create a specific style
		var st = Ext.get(gidPrefix + "-style");
		if(st) st.remove();
		
		
		Ext.getDoc().child("head").createChild({
			tag: 'style',
			id: gidPrefix + "-style",
			// html: ".x-grid-group-unlocked div#" + gidPrefix + " div.x-grid3-row {padding-left:" + (gfLen * 12) + "px}" 
			// + "div#" + gidPrefix + " div.x-grid3-header {padding-left:" + (gfLen * 12) + "px}"
			html: "div.x-grid-group-locked div.x-grid3-row {padding-left:" + (gfLen * 12) + "px}" // + "div#" + gidPrefix + " div.x-grid3-header {padding-left:" + (gfLen * 12) + "px}"
		});

		
		for (var i = 0, len = rs.length; i < len; i++) {
			added = 0;
			var rowIndex = startRow + i;
			var r = rs[i];
			var differ = 0;
			var gvalue = [];
			var fieldName;
			var fieldLabel;
			var grpFieldNames = [];
			var grpFieldLabels = [];
			var v;
			var changed = 0;
			var addGroup = [];
           
			for (var j = 0; j < gfLen; j++) {
				fieldName = groupField[j];
				//fieldLabel = this.cm.lookup[this.cm.findColumnIndex(fieldName)].header;
				fieldLabel = this.cm.lookup[fieldName].header;
				v = r.data[fieldName];
				if (v) {
					if (i == 0) {
						// First record always starts a new group
						addGroup.push({idx:j,dataIndex:fieldName,header:fieldLabel,value:v});
						lastvalues[j] = v;
             
						gvalue.push(v);
						grpFieldNames.push(fieldName);
						grpFieldLabels.push(fieldLabel + ': ' + v);
						//gvalue.push(v); ????
					} else {
						if (lastvalues[j] != v) {
							// This record is not in same group as previous one
							// console.debug("Row ",i," added group. Values differ: prev=",lastvalues[j]," curr=",v);
							addGroup.push({idx:j,dataIndex:fieldName,header:fieldLabel,value:v});
							lastvalues[j] = v;
							//differ = 1;
							changed = 1;
				   
							gvalue.push(v);
							grpFieldNames.push(fieldName);
							grpFieldLabels.push(fieldLabel + ': ' + v);
						} else {
							if (gfLen-1 == j && changed != 1) {
								// This row is in all the same groups to the previous group
								curGroup.rs.push(r);
								// console.debug("Row ",i," added to current group ",glbl);
							} else if (changed == 1) {
								// This group has changed because an earlier group changed.
								addGroup.push({idx:j,dataIndex:fieldName,header:fieldLabel,value:v});
								//console.debug("Row ",i," added group. Higher level group change");
								
								gvalue.push(v);
								grpFieldNames.push(fieldName);
								grpFieldLabels.push(fieldLabel + ': ' + v);
							} else if(j<gfLen-1) {
								// This is a parent group, and this record is part of this parent so add it
								if (currGroups[fieldName]) {
									currGroups[fieldName].rs.push(r);
								} else {
									//console.error("Missing on row ",i," current group for ",fieldName);
								}
							}
						}
					}
				} else { 
					if (this.displayEmptyFields) {
						addGroup.push({idx:j,dataIndex:fieldName,header:fieldLabel,value:this.emptyGroupText||'(none)'});
						grpFieldNames.push(fieldName);
						grpFieldLabels.push(fieldLabel + ': ');
					}
				}  
			} //for j
            
			if(addGroup.length > 0) {
				// console.debug("Added groups for row=",i,", Groups=",addGroup);
			}
       
			/*            
				   if (gvalue.length < 1 && this.emptyGroupText) 
					 g = this.emptyGroupText;
				   else 
					 g = grpFieldNames;//.join(this.displayFieldSeperator);
			*/
			
			for (var k = 0; k < addGroup.length; k++) {
				//g = grpFieldNames[k];
				//var glbl = grpFieldLabels[k];
				var gp = addGroup[k];
				g = gp.dataIndex;
				var glbl = addGroup[k].header;
         
				// console.debug("Create Group for ", glbl, r);
                
				// if (!curGroup || curGroup.group != gp.dataIndex || currGroup.gvalue != gp.value) {
				// There is no current group, or its not for the right field, so create one
				// console.info('gidPrefix=', gidPrefix, ' gp.dataIndex=', gp.dataIndex, ' gp.value=', gp.value);
				var tempID = Ext.id();
				gid = gidPrefix + '-' + tempID + '-gp-' + gp.dataIndex + '-' + Ext.util.Format.htmlEncode(gp.value);
				
				// gid = this.constructId(gp.value, gp.dataIndex, k);
                // if state is defined use it, however state is in terms of expanded
                // so negate it, otherwise use the default.
                this.state[gid] = !(Ext.isDefined(this.state[gid]) ? !this.state[gid] : this.startCollapsed);
		   
				// if state is defined use it, however state is in terms of expanded
				// so negate it, otherwise use the default.
				var isCollapsed = typeof this.state[gid] !== 'undefined' ? !this.state[gid] : this.startCollapsed;
				var gcls = isCollapsed ? 'x-grid-group-collapsed' : '';
				
				var unlockedStyle = 'width:' + (this.cm.getTotalWidth() - this.cm.getTotalLockedWidth()) + 'px; padding-left:' + (gp.idx * 12) + 'px;',
					  lockedStyle = 'width:' + this.getLockedWidth() + '; padding-left:' + (gp.idx * 12) + 'px; ';
         
				curGroup = {
					group: gp.dataIndex,
					gvalue: gp.value,
					text: gp.header,
					groupId: gid,
					startRow: rowIndex,
					rs: [r],
					cls: gcls,
                    lockedheaderstyle: lockedStyle,
                    unlockedheaderstyle: unlockedStyle
				};

				currGroups[gp.dataIndex] = curGroup;
				groups.push(curGroup);
           
				r._groupId = gid; // Associate this row to a group
			} //for k
		} //for i
		
		this.groupsMatrix = groups;

		var buf = [], res = ['', ''];
		var toEnd = 0;
		for (var ilen = 0, len = groups.length; ilen < len; ilen++) {
			
			toEnd++;
			var g = groups[ilen];
			var leaf = g.group == groupField[gfLen - 1] 
			
			this.doGroupStart(buf, g, cs, ds, colCount);
       
			// console.debug(g,buf.length,"=",buf[buf.length-1]);
       
			if (g.rs.length != 0 && leaf) {
				// buf[buf.length] = Ext.grid.GroupingView.superclass.doRender.call(this, cs, g.rs, ds, g.startRow, colCount, stripe);
				buf[buf.length] = this.doRender2.call(this, cs, g, ds, g.startRow, colCount, stripe);
				// buf[buf.length] = Ext.ux.LockingMultiGroupingGridView.superclass.doRender.call(this, cs, g.rs, ds, g.startRow, colCount, stripe);
			}
       
			/*
			if (leaf) {
				var jj;
				var gg = groups[ilen + 1];
				if (gg != null) {
					for (var jj = 0; jj < groupField.length; jj++) {
						if (gg.group == groupField[jj]) 
						break;
					}
					toEnd = groupField.length - jj;
				}
				for (var k = 0; k < toEnd; k++) {
					this.doGroupEnd(buf, g, cs, ds, colCount);
				}
				toEnd = jj;
			} else {
			*/
				this.doGroupEnd(buf, g, cs, ds, colCount);
			/*
			}
			*/
		}
		
		for(i = 0, len = buf.length; i < len; i++){
            res[0] += buf[i][0];
            res[1] += buf[i][1];
        }

		return res;
	}
   
	,doRender2 : function(cs, group, ds, startRow, colCount, stripe){
	
		var rs = group.rs;
		var gstyle = group.style;
		
		var ts = this.templates, ct = ts.cell, rt = ts.row, last = colCount - 1,
			tstyle = 'width:' + this.getTotalWidth() + ';',
			lstyle = 'width:' + this.getLockedWidth() + ';',
			buf = [], lbuf = [], cb, lcb, c, p = {}, rp = {}, r;
		
		for (var j = 0, len = rs.length; j < len; j++){
		
			added = 0;
			var differ = 0;
			var v = 0, changed = 0;
			
			r = rs[j]; cb = []; lcb = [];
			var rowIndex = (j + startRow),
			    resId = r.get('Id');
			
			for (var i = 0; i < colCount; i++) {
				c = cs[i];
				p.id = c.id;
				p.css = (i === 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '')) +
                    (this.cm.config[i].cellCls ? ' ' + this.cm.config[i].cellCls : '');
				p.attr = p.cellAttr = '';
                p.value = c.renderer(r.data[c.name], p, r, rowIndex, i, ds);
				p.style = c.style;
				if (Ext.isEmpty(p.value)){
					p.value = '&#160;';
				}
				if (this.markDirty && r.dirty && Ext.isDefined(r.modified[c.name])){
					p.css += ' x-grid3-dirty-cell';
				}
				if (c.locked){
					lcb[lcb.length] = ct.apply(p);
				} else {
					cb[cb.length] = ct.apply(p);
				}
			}
			
			var alt = [];
			if (stripe && ((rowIndex + 1) % 2 === 0)){
				alt[0] = 'x-grid3-row-alt';
			}
			if (r.dirty){
				alt[1] = ' x-grid3-dirty-row';
			}
			rp.cols = colCount;
			if (this.getRowClass){
				alt[2] = this.getRowClass(r, rowIndex, rp, ds);
			}
			rp.alt = alt.join(' ');
			rp.cells = cb.join('');
			rp.tstyle = tstyle;
			buf[buf.length] = rt.apply(rp);
			rp.cells = lcb.join('');
			rp.tstyle = lstyle;
			lbuf[lbuf.length] = rt.apply(rp);
		}
		
		return [buf.join(''), lbuf.join('')];
	}

   /** Should return an array of all elements that represent a row, it should bypass
    *  all grouping sections
    */
	,getRows: function(){
  
        // This function is called may times, so use a cache if it is available
        if(this.rowsCache) {
			r = this.rowsCache.slice(0);
		} else {
			//alert('getRows');
			if (!this.enableGrouping) {
				return Ext.grid.GroupingView.superclass.getRows.call(this);
			}
			var groupField = this.getGroupField();
			var r = [];
			var g, gs = this.getGroups();
			// this.getGroups() contains an array of DIVS for the top level groups
			//console.debug("Get Rows", groupField, gs);

			r = this.getRowsFromGroup(r, gs, groupField[groupField.length - 1]);
        }  
        //console.debug("Found ", r.length, " rows");
        return r;
    }
    
   /** Return array of records under a given group
    * @param r Record array to append to in the returned object
    * @param gs Grouping Sections, an array of DIV element that represent a set of grouped records
    * @param lsField The name of the grouping section we want to count
    */
	,getRowsFromGroup: function(r, gs, lsField){
        
		var rx = new RegExp(".*-gp-" + lsField + "-.*");

        // Loop over each section
        for (var i = 0, len = gs.length; i < len; i++) {

            // Get group name for this section
            var groupName = gs[i].id;
            if (rx.test(groupName)) {
                //console.debug(groupName, " matched ", lsField);
                g = gs[i].childNodes[1].childNodes;
                for (var j = 0, jlen = g.length; j < jlen; j++) {
                    r[r.length] = g[j];
                }
                //console.debug("Found " + g.length + " rows for group " + lsField);
            } else {
                if(!gs[i].childNodes[1]) {
                   //  console.error("Can't get rowcount for field ",lsField," from ",gs,i);
                } else 
                // if its an interim level, each group needs to be traversed as well
                r = this.getRowsFromGroup(r, gs[i].childNodes[1].childNodes, lsField);
            }
        }
        return r;
    }
	
	,getLockedRows : function() {
	
        if(!this.enableGrouping){
            return Ext.ux.grid.LockingMultiGroupingGridView.superclass.getRows.call(this);
        }
		
        var r = [];
		var g, gs = this.getLockedGroups();
		var groupField = this.getGroupField();
		
		r = this.getRowsFromGroup(r, gs, groupField[groupField.length - 1]);
		
		return r;
		
    }
	
	// private
    ,renderUI : function(){
        Ext.ux.LockingMultiGroupingGridView.superclass.renderUI.call(this);
        this.mainBody.on('mousedown', this.interceptMouse, this);
        this.lockedBody.on('mousedown', this.interceptMouse, this);
    }
	
	// private
    ,getLockedGroups : function() {
        return this.hasRows() ? this.lockedBody.dom.childNodes : [];
    }
	
	// private
    ,getGroupField : function(){
        return this.grid.store.getGroupState();
    }
	
	// private
    ,getGroups : function(){
        return this.hasRows() ? this.mainBody.dom.childNodes : [];
    }
	
	 // private
    ,doGroupStart : function(buf, g, cs, ds, colCount){
        buf[buf.length] = [this.renderUnlockedHeader(g), this.renderLockedHeader(g)];
    }
	
	 // private
    ,doGroupEnd : function(buf, g, cs, ds, colCount){
		console.dir(g);
        buf[buf.length] = [this.endGroup, this.endGroup];
    }
	
	,renderUnlockedHeader : function(groupData) {
        if (this.unlockedGroupHeaderRenderer) {
            groupData.renderedHeader = this.unlockedGroupHeaderRenderer.call(this, groupData);
        }
        
        if (this.cm.getTotalLockedWidth() === 0) {
            groupData.unlockedheadercls = 'x-grid-group-nolockedcolumns';
        }
        return this.startUnlockedGroup.apply(groupData);
    }
    
    ,renderLockedHeader : function(groupData) {
        if (this.lockedGroupHeaderRenderer) {
            groupData.renderedHeader = this.lockedGroupHeaderRenderer.call(this, groupData);
        }
        return this.startLockedGroup.apply(groupData);
    }
	
	// private
    ,interceptMouse : function(e){
        var hd = e.getTarget('.x-grid-group-hd', this.mainBody);
        if(hd) {
            e.stopEvent();
            this.toggleGroup(hd.parentNode);
        }
    }
	
	
	// private
    ,onGroupByClick : function(){
        this.grid.store.groupBy(this.cm.getDataIndex(this.hdCtxIndex));
    }

    /**
     * Toggles the specified group if no value is passed, otherwise sets the expanded state of the group to the value passed.
     * @param {String} groupId The groupId assigned to the group (see getGroupId)
     * @param {Boolean} expanded (optional)
     */
    ,toggleGroup : function(group, expanded){
        
		var para = group.parentNode;
		this.grid.stopEditing(true);
        group = Ext.getDom(group);
        var gel = Ext.fly(group);
        expanded = Ext.isDefined(expanded) ? expanded : gel.hasClass('x-grid-group-collapsed');

        this.state[gel.dom.id] = expanded;

        this.el.select('[id="' + gel.dom.id + '"]')[expanded ? 'removeClass' : 'addClass']('x-grid-group-collapsed');
        
	}

    /**
     * Toggles all groups if no value is passed, otherwise sets the expanded state of all groups to the value passed.
     * @param {Boolean} expanded (optional)
     */
    ,toggleAllGroups : function(expanded){
        var groups = this.getGroups();
        for(var i = 0, len = groups.length; i < len; i++){
            this.toggleGroup(groups[i], expanded);
        }
    }

    /**
     * Expands all grouped rows.
     */
    ,expandAllGroups : function(){
        this.toggleAllGroups(true);
    }

    /**
     * Collapses all grouped rows.
     */
    ,collapseAllGroups : function(){
        this.toggleAllGroups(false);
    }
	
	
	 // private
    ,getGroup : function(v, r, groupRenderer, rowIndex, colIndex, ds){
        var g = groupRenderer ? groupRenderer(v, {}, r, rowIndex, colIndex, ds) : String(v);
        if(g === ''){
            g = this.cm.config[colIndex].emptyGroupText || this.emptyGroupText;
        }
        return g;
    }
    
    // private
    ,getGroupField : function(){
        return this.grid.store.getGroupState();
    }
    
    // private
    ,afterRender : function(){
        if(this.grid.deferRowRender) {
            this.updateGroupWidths();
        }
    }
	
	// private
    ,updateGroupWidths : function(){
        
		if(!this.enableGrouping || !this.hasRows()){
            return;
        }
        
        var i,
            len,
            lockedGroups = this.getLockedGroups(),
            unlockedGroups = this.getGroups(),
            lockedWidth = this.getLockedWidth(),
            unlockedWidth = this.getTotalWidth();
			
		// console.debug('lockedWidth=', lockedWidth, ' | unlockedWidth=', unlockedWidth);
        
        for(i = 0, len = unlockedGroups.length; i < len; i++) {
            unlockedGroups[i].firstChild.style.width = unlockedWidth;
        }
        
        for(i = 0, len = lockedGroups.length; i < len; i++){
            lockedGroups[i].firstChild.style.width = lockedWidth;
        }
    }

    // private
    ,onColumnWidthUpdated : function(col, w, tw){
        Ext.ux.grid.LockingGroupingGridView.superclass.onColumnWidthUpdated.call(this, col, w, tw);
        this.updateGroupWidths();
    }

    // private
    ,onAllColumnWidthsUpdated : function(ws, tw){
        Ext.ux.grid.LockingGroupingGridView.superclass.onAllColumnWidthsUpdated.call(this, ws, tw);
        this.updateGroupWidths();
    }

    // private
    ,onColumnHiddenUpdated : function(col, hidden, tw){
        Ext.ux.grid.LockingGroupingGridView.superclass.onColumnHiddenUpdated.call(this, col, hidden, tw);
        this.updateGroupWidths();
    }

    // private
    ,onLayout : function(){
        this.updateGroupWidths();
    }
    
    // private
    ,getGroupEl : function(el){
        return el.up('.x-grid-group');
    }
	
	,getGroupHeaderEl : function(el){
        return this.getGroupEl(el).down('.x-grid-group-hd');
    }
	
	,getGroupEls : function(el){
        return this.el.select('.' + this.getGroupEl(el).id);
    }
    // End grouping functionality
	
});