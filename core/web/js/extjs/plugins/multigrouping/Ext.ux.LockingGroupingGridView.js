/**
 * @class Ext.ux.grid.LockingGroupingGridView
 * @extends Ext.ux.grid.LockingGridView
 *
 * Ext.ux.grid.LockingGroupingGridView, a combination of LockingGridView and GroupingView
 *
 * @author      Mats Bryntse
 * @copyright (c) 2010, Mats Bryntse
 * @version   1.0
 *
 * @license Ext.ux.grid.LockingGroupingGridView is licensed under the terms of
 * the Open Source LGPL 3.0 license.  Commercial use is permitted to the extent
 * that the code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 * 
 * <p>License details: <a href="http://www.gnu.org/licenses/lgpl.html"
 * target="_blank">http://www.gnu.org/licenses/lgpl.html</a></p>
 *
 * @forum      http://www.extjs.com/forum/showthread.php?p=440262
 */
 
Ext.ux.grid.LockingGroupingGridView = Ext.extend(Ext.ux.grid.LockingGridView, {
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
    
    constructor : function(config) {
        this.addEvents('togglegroup');
        Ext.ux.grid.LockingGroupingGridView.superclass.constructor.call(this, config);
    },
    
	initTemplates : function(){
		var ts = this.templates || {};
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
		
        this.state = {};
		if(!this.startLockedGroup){
            this.startLockedGroup = new Ext.XTemplate(
                '<div id="{groupId}" class="x-grid-group x-grid-group-locked {cls}">',
                    '<div id="{groupId}-hd" class="x-grid-group-hd" style="{lockedheaderstyle}"><div class="x-grid-group-title">' + (this.lockedGroupHeaderRenderer ? '{renderedHeader}' : this.groupTextTpl),'</div></div>',
                    '<div id="{groupId}-bd" class="x-grid-group-body">'
            );
        }
        
        if(!this.startUnlockedGroup){
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
		Ext.ux.grid.LockingGroupingGridView.superclass.initTemplates.call(this);
	},
	
	getLockedRows : function(){
        if(!this.enableGrouping){
            return Ext.ux.grid.LockingGroupingGridView.superclass.getRows.call(this);
        }
        var r = [];
        var g, gs = this.getLockedGroups();
        for(var i = 0, len = gs.length; i < len; i++){
            g = gs[i].childNodes[1].childNodes;
            for(var j = 0, jlen = g.length; j < jlen; j++){
                r[r.length] = g[j];
            }
        }
        return r;
    },
	
	doRender2 : function(cs, rs, ds, startRow, colCount, stripe){
		var ts = this.templates, ct = ts.cell, rt = ts.row, last = colCount-1,
			tstyle = 'width:'+this.getTotalWidth()+';',
			lstyle = 'width:'+this.getLockedWidth()+';',
			buf = [], lbuf = [], cb, lcb, c, p = {}, rp = {}, r;
		for(var j = 0, len = rs.length; j < len; j++){
			r = rs[j]; cb = []; lcb = [];
			var rowIndex = (j+startRow),
			    resId = r.get('Id');
			
			for(var i = 0; i < colCount; i++){
				c = cs[i];
				p.id = c.id;
				p.css = (i === 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '')) +
                    (this.cm.config[i].cellCls ? ' ' + this.cm.config[i].cellCls : '');
				p.attr = p.cellAttr = '';
                p.value = c.renderer(r.data[c.name], p, r, rowIndex, i, ds);
				p.style = c.style;
				if(Ext.isEmpty(p.value)){
					p.value = '&#160;';
				}
				if(this.markDirty && r.dirty && Ext.isDefined(r.modified[c.name])){
					p.css += ' x-grid3-dirty-cell';
				}
				if(c.locked){
					lcb[lcb.length] = ct.apply(p);
				}else{
					cb[cb.length] = ct.apply(p);
				}
			}
			var alt = [];
			if(stripe && ((rowIndex+1) % 2 === 0)){
				alt[0] = 'x-grid3-row-alt';
			}
			if(r.dirty){
				alt[1] = ' x-grid3-dirty-row';
			}
			rp.cols = colCount;
			if(this.getRowClass){
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
	},
	
    // Grouping functionality
    // private
    findGroup : function(el){
        return Ext.fly(el).up('.x-grid-group', this.mainBody.dom);
    },

    // private
    getGroups : function(){
        return this.hasRows() ? this.mainBody.dom.childNodes : [];
    },
    
    // private
    getLockedGroups : function(){
        return this.hasRows() ? this.lockedBody.dom.childNodes : [];
    },

    // private
    onAdd : function(){
        if(this.enableGrouping && !this.ignoreAdd){
            var ss = this.getScrollState();
            this.refresh();
            this.restoreScroll(ss);
        }else if(!this.enableGrouping){
            Ext.ux.grid.LockingGroupingGridView.superclass.onAdd.apply(this, arguments);
        }
    },

    // private
    onRemove : function(ds, record, index, isUpdate){
        Ext.ux.grid.LockingGroupingGridView.superclass.onRemove.apply(this, arguments);
        var g = document.getElementById(record._groupId);
        if(g && g.childNodes[1].childNodes.length < 1){
            Ext.removeNode(g);
        }
        this.applyEmptyText();
    },

    // private,
    refreshRow : function(record){
        if(this.ds.getCount()==1){
            this.refresh();
        }else{
            this.isUpdating = true;
            Ext.ux.grid.LockingGroupingGridView.superclass.refreshRow.apply(this, arguments);
            this.isUpdating = false;
        }
    },

    // private
    renderUI : function(){
        Ext.ux.grid.LockingGroupingGridView.superclass.renderUI.call(this);
        this.mainBody.on('mousedown', this.interceptMouse, this);
        this.lockedBody.on('mousedown', this.interceptMouse, this);
    },

    // private
    onGroupByClick : function(){
        this.grid.store.groupBy(this.cm.getDataIndex(this.hdCtxIndex));
    },

    /**
     * Toggles the specified group if no value is passed, otherwise sets the expanded state of the group to the value passed.
     * @param {String} groupId The groupId assigned to the group (see getGroupId)
     * @param {Boolean} expanded (optional)
     */
    toggleGroup : function(group, expanded){
        this.grid.stopEditing(true);
        group = Ext.getDom(group);
        var gel = Ext.fly(group);
        expanded = expanded !== undefined ?
                expanded : gel.hasClass('x-grid-group-collapsed');

        this.state[gel.dom.id] = expanded;
        this.el.select('[id="' + gel.dom.id + '"]')[expanded ? 'removeClass' : 'addClass']('x-grid-group-collapsed');
        this.fireEvent('togglegroup', this);
    },

    /**
     * Toggles all groups if no value is passed, otherwise sets the expanded state of all groups to the value passed.
     * @param {Boolean} expanded (optional)
     */
    toggleAllGroups : function(expanded){
        var groups = this.getGroups();
        for(var i = 0, len = groups.length; i < len; i++){
            this.toggleGroup(groups[i], expanded);
        }
    },

    /**
     * Expands all grouped rows.
     */
    expandAllGroups : function(){
        this.toggleAllGroups(true);
    },

    /**
     * Collapses all grouped rows.
     */
    collapseAllGroups : function(){
        this.toggleAllGroups(false);
    },

    // private
    interceptMouse : function(e){
        var hd = e.getTarget('.x-grid-group-hd', this.mainBody);
        if(hd){
            e.stopEvent();
            this.toggleGroup(hd.parentNode);
        }
    },

    // private
    getGroup : function(v, r, groupRenderer, rowIndex, colIndex, ds){
        var g = groupRenderer ? groupRenderer(v, {}, r, rowIndex, colIndex, ds) : String(v);
        if(g === ''){
            g = this.cm.config[colIndex].emptyGroupText || this.emptyGroupText;
        }
        return g;
    },
    
    /**
     * Dynamically tries to determine the groupId of a specific value
     * @param {String} value
     * @return {String} The group id
     */
    getGroupId : function(value){
        var field = this.getGroupField();
        return this.constructId(value, field, this.cm.findColumnIndex(field));
    },
    
    // private
    constructId : function(value, field, idx){
        var cfg = this.cm.config[idx],
            groupRenderer = cfg.groupRenderer || cfg.renderer,
            val = (this.groupMode == 'value') ? value : this.getGroup(value, {data:{}}, groupRenderer, 0, idx, this.ds);

        return this.getPrefix(field) + Ext.util.Format.htmlEncode(val);
    },
    
    // private
    getGroupField : function(){
        return this.grid.store.getGroupState();
    },
    
    // private
    getPrefix: function(field){
        return this.grid.getGridEl().id + '-gp-' + field + '-';
    },
    
    // private
    afterRender : function(){
        if(this.grid.deferRowRender){
            this.updateGroupWidths();
        }
    },

    // private
    renderRows : function(){
        var groupField = this.getGroupField();
        var eg = !!groupField;
        // if they turned off grouping and the last grouped field is hidden
        if(this.hideGroupedColumn) {
            var colIndex = this.cm.findColumnIndex(groupField);
            if(!eg && this.lastGroupField !== undefined) {
                this.mainBody.update('');
                this.cm.setHidden(this.cm.findColumnIndex(this.lastGroupField), false);
                delete this.lastGroupField;
            }else if (eg && this.lastGroupField === undefined) {
                this.lastGroupField = groupField;
                this.cm.setHidden(colIndex, true);
            }else if (eg && this.lastGroupField !== undefined && groupField !== this.lastGroupField) {
                this.mainBody.update('');
                var oldIndex = this.cm.findColumnIndex(this.lastGroupField);
                this.cm.setHidden(oldIndex, false);
                this.lastGroupField = groupField;
                this.cm.setHidden(colIndex, true);
            }
        }
        return Ext.ux.grid.LockingGroupingGridView.superclass.renderRows.apply(
                    this, arguments);
    },

    // private
    doRender : function(cs, rs, ds, startRow, colCount, stripe){
        if(rs.length < 1){
            return '';
        }
        var groupField = this.getGroupField(),
            colIndex = this.cm.findColumnIndex(groupField),
            g;

        this.enableGrouping = this.enableGrouping && !!groupField;

        if(!this.enableGrouping || this.isUpdating){
            return this.doRender2.apply(this, arguments);
        }
        
        var unlockedStyle = 'width:'+ (this.cm.getTotalWidth() - this.cm.getTotalLockedWidth()) +'px;',
            lockedStyle = 'width:'+this.getLockedWidth()+';';

        var gidPrefix = this.grid.getGridEl().id;
        var cfg = this.cm.config[colIndex];
        var groupRenderer = cfg.groupRenderer || cfg.renderer;
        var prefix = this.showGroupName ?
                     (cfg.groupName || cfg.header)+': ' : '';

        var groups = [], curGroup, i, len, gid;
        for(i = 0, len = rs.length; i < len; i++){
            var rowIndex = startRow + i,
                r = rs[i],
                gvalue = r.data[groupField];
                
                g = this.getGroup(gvalue, r, groupRenderer, rowIndex, colIndex, ds);
            if(!curGroup || curGroup.group != g){
                gid = this.constructId(gvalue, groupField, colIndex);
               	// if state is defined use it, however state is in terms of expanded
				// so negate it, otherwise use the default.
				var isCollapsed  = typeof this.state[gid] !== 'undefined' ? !this.state[gid] : this.startCollapsed;
				var gcls = isCollapsed ? 'x-grid-group-collapsed' : '';	
                curGroup = {
                    group: g,
                    gvalue: gvalue,
                    text: prefix + g,
                    groupId: gid,
                    startRow: rowIndex,
                    rs: [r],
                    cls: gcls,
                    lockedheaderstyle: lockedStyle,
                    unlockedheaderstyle: unlockedStyle
                };
                groups.push(curGroup);
            }else{
                curGroup.rs.push(r);
            }
            r._groupId = gid;
        }

        var buf = [],
            res = ['', ''];
        
        for(i = 0, len = groups.length; i < len; i++){
            g = groups[i];
            this.doGroupStart(buf, g, cs, ds, colCount);
            buf[buf.length] = this.doRender2.call(this, cs, g.rs, ds, g.startRow, colCount, stripe);
            this.doGroupEnd(buf, g, cs, ds, colCount);
        }
        
        for(i = 0, len = buf.length; i < len; i++){
            res[0] += buf[i][0];
            res[1] += buf[i][1];
        }
        return res;
    },

    // private
    doGroupStart : function(buf, g, cs, ds, colCount){
        buf[buf.length] = [this.renderUnlockedHeader(g), this.renderLockedHeader(g)];
    },
    
    renderUnlockedHeader : function(groupData) {
        if (this.unlockedGroupHeaderRenderer) {
            groupData.renderedHeader = this.unlockedGroupHeaderRenderer.call(this, groupData);
        }
        
        if (this.cm.getTotalLockedWidth() === 0) {
            groupData.unlockedheadercls = 'x-grid-group-nolockedcolumns';
        }
        return this.startUnlockedGroup.apply(groupData);
    },
    
    renderLockedHeader : function(groupData) {
        if (this.lockedGroupHeaderRenderer) {
            groupData.renderedHeader = this.lockedGroupHeaderRenderer.call(this, groupData);
        }
        return this.startLockedGroup.apply(groupData);
    },

    // private
    doGroupEnd : function(buf, g, cs, ds, colCount){
        buf[buf.length] = [this.endGroup, this.endGroup];
    },

    // private
    getRows : function(){
        if(!this.enableGrouping){
            return Ext.ux.grid.LockingGroupingGridView.superclass.getRows.call(this);
        }
        var r = [];
        var g, gs = this.getGroups();
        for(var i = 0, len = gs.length; i < len; i++){
            g = gs[i].childNodes[1].childNodes;
            for(var j = 0, jlen = g.length; j < jlen; j++){
                r[r.length] = g[j];
            }
        }
        return r;
    },

    // private
    updateGroupWidths : function(){
        if(!this.enableGrouping || !this.hasRows()){
            return;
        }
        
        var i,
            len,
            lockedGroups = this.getLockedGroups(),        
            unlockedGroups = this.getGroups(),
            lockedWidth = this.getLockedWidth(),
            unlockedWidth = this.getTotalWidth();
        
        for(i = 0, len = unlockedGroups.length; i < len; i++){
            unlockedGroups[i].firstChild.style.width = unlockedWidth;
        }
        
        for(i = 0, len = lockedGroups.length; i < len; i++){
            lockedGroups[i].firstChild.style.width = lockedWidth;
        }
    },

    // private
    onColumnWidthUpdated : function(col, w, tw){
        Ext.ux.grid.LockingGroupingGridView.superclass.onColumnWidthUpdated.call(this, col, w, tw);
        this.updateGroupWidths();
    },

    // private
    onAllColumnWidthsUpdated : function(ws, tw){
        Ext.ux.grid.LockingGroupingGridView.superclass.onAllColumnWidthsUpdated.call(this, ws, tw);
        this.updateGroupWidths();
    },

    // private
    onColumnHiddenUpdated : function(col, hidden, tw){
        Ext.ux.grid.LockingGroupingGridView.superclass.onColumnHiddenUpdated.call(this, col, hidden, tw);
        this.updateGroupWidths();
    },

    // private
    onLayout : function(){
        this.updateGroupWidths();
    },
    
    // private
    getGroupEl : function(el){
        return el.up('.x-grid-group');
    },
    
    getGroupHeaderEl : function(el){
        return this.getGroupEl(el).down('.x-grid-group-hd');
    },
    
    getGroupEls : function(el){
        return this.el.select('.' + this.getGroupEl(el).id);
    }
    // End grouping functionality
});


