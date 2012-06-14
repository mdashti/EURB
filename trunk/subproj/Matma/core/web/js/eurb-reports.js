Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

DocPanel = Ext.extend(Ext.Panel, {
    closable: true,
    autoScroll:true,

    initComponent : function(){
        var ps = this.cclass.split('.');
        this.title = ps[ps.length-1];

        DocPanel.superclass.initComponent.call(this);
    },

    scrollToMember : function(member){
        var el = Ext.fly(this.cclass + '-' + member);
        if(el){
            var top = (el.getOffsetsTo(this.body)[1]) + this.body.dom.scrollTop;
            this.body.scrollTo('top', top-25, {duration:.75, callback: this.hlMember.createDelegate(this, [member])});
        }
    },

	scrollToSection : function(id){
		var el = Ext.getDom(id);
		if(el){
			var top = (Ext.fly(el).getOffsetsTo(this.body)[1]) + this.body.dom.scrollTop;
			this.body.scrollTo('top', top-25, {duration:.5, callback: function(){
                Ext.fly(el).next('h2').pause(.2).highlight('#8DB2E3', {attr:'color'});
            }});
        }
	},

    hlMember : function(member){
        var el = Ext.fly(this.cclass + '-' + member);
        if(el){
            el.up('tr').highlight('#cadaf9');
        }
    }
});


MainPanel = function(){
	var currentUser = new Ext.Toolbar.TextItem(EURB.user+': '+EURB.currentUser);
    var currentIP = new Ext.Toolbar.TextItem(EURB.ipAddress+': '+EURB.currentIpAddress);
    var clock = new Ext.Toolbar.TextItem('');

    MainPanel.superclass.constructor.call(this, {
        id:'doc-body',
        region:'center',
        //margins:'0 5 5 0',
        minTabWidth: 135,
        layout: 'fit',
        items: [],
        bbar: EURB.menuEnabled ? new Ext.ux.StatusBar({
            id: 'eurb-status',
            //statusAlign: 'right', // the magic config
            // These are just the standard toolbar TextItems we created above.  They get
            // custom classes below in the render handler which is what gives them their
            // customized inset appearance.
            items: [{
					 text:EURB.logout
					,iconCls:'icon-key'
					,handler: function() {
						window.location.href = EURB.baseURL+'logout.spy'
					}
				}, ' ', ' ', ' ', '-', ' ', ' ', ' ', 
            	currentUser, ' ', ' ', ' ', '-', ' ', ' ', ' ', 
            	currentIP, ' ', ' ', ' ', ' ', '-', ' ', ' ', ' ', 
            	clock, ' ', ' ', ' ']
        }) : [],
        listeners: EURB.menuEnabled ? {
            render: {
                fn: function(){
                    // Add a class to the parent TD of each text item so we can give them some custom inset box
                    // styling. Also, since we are using a greedy spacer, we have to add a block level element
                    // into each text TD in order to give them a fixed width (TextItems are spans).  Insert a
                    // spacer div into each TD using createChild() so that we can give it a width in CSS.
                    Ext.fly(currentUser.getEl().parent()).addClass('x-status-text-panel').createChild({cls:'spacer'});
                    Ext.fly(currentIP.getEl().parent()).addClass('x-status-text-panel').createChild({cls:'spacer'});
                    Ext.fly(clock.getEl().parent()).addClass('x-status-text-panel').createChild({cls:'spacer'});

                    // Kick off the clock timer that updates the clock el every second:
				    Ext.TaskMgr.start({
				        run: function(){
				            Ext.fly(clock.getEl()).update(new Date().format('B/Q/R G:i:s'));
				        },
				        interval: 1000
				    });
                },
                delay: 100
            }
        } : {}
    });
};

Ext.extend(MainPanel, Ext.Panel);

Ext.onReady(function(){

    Ext.QuickTips.init();
	var api;
    if(EURB.menuEnabled) {
	    api = new Ext.Panel({
	    	id: 'eurb-app-menu',
	        region:'east',
	        //margins:'5 0 5 5',
	        split:true,
	        collapsible: true,
	        width: 150
	        //,layout:'accordion',
	        //,items: []
	        //,html: ''
	    });
	    
	    EURB.mainMenu = api;
    }
    
    EURB.mainPanel = new MainPanel();

    var hd = new Ext.Panel({
        border: false,
        layout:'anchor',
        region:'north',
        cls: 'docs-header',
        height:115,
        items: [{
            xtype:'box',
            el:'header',
            border:false,
            anchor: 'none -25'
        },
        new Ext.Toolbar({
            cls:'top-toolbar',
            items:[]
        })]
    });
    var viewPortArr;
	if(EURB.menuEnabled) {
    	viewPortArr = [ hd, api, EURB.mainPanel ];
	} else {
		viewPortArr = [ hd, EURB.mainPanel ];
	}
    if(EURB.westPanel) {
    	viewPortArr.push(EURB.westPanel);
    }
    if(EURB.southPanel) {
    	viewPortArr.push(EURB.southPanel);
    }
    EURB.viewport = new Ext.Viewport({
        layout:'border',
        items: viewPortArr
    });
    
    EURB.viewport.doLayout();

	setTimeout(function(){
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
    }, 250);
});

// return the value of the radio button that is checked
// return an empty string if none are checked, or
// there are no radio buttons
Ext.getCheckedValue = function(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

// set the radio button with the given value as being checked
// do nothing if there are no radio buttons
// if the given value does not exist, all the radio buttons
// are reset to unchecked
Ext.setCheckedValue = function(radioObj, newValue) {
	if(!radioObj)
		return;
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = (radioObj.value == newValue.toString());
		return;
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
		if(radioObj[i].value == newValue.toString()) {
			radioObj[i].checked = true;
		}
	}
}