Ext.onReady(function(){
	
    var Contact = Ext.data.Record.create([
	{name: 'id'},
    {
        name: 'name',
        type: 'string'
    }, {
        name: 'phone',
        type: 'string'
    }, {
        name: 'email',
        type: 'string'
    }, {
        name: 'birthday',
        type: 'date',
        dateFormat: 'B/Q/R'
    }]);
    
    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'view.action',
            create : 'create.action',
            update: 'update.action',
            destroy: 'delete.action'
        },
        listeners: {
        	'exception' : function(proxy, type, action, options, res) {
    	    	Ext.Msg.show({
    	    		title: 'ERROR',
    	    		msg: Ext.util.JSON.decode(res.responseText).message,
    	    		icon: Ext.MessageBox.ERROR,
    	    		buttons: Ext.Msg.OK
    	    	});
    	    }
        }
    });
    
    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, 
    Contact);

 // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: false
    });
    
 // Typical Store collecting the Proxy, Reader and Writer together.
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        reader: reader,
        writer: writer,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    //read the data from simple array
    store.load();

    
    var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    

    // create grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {header: "نام",
             width: 170,
             sortable: true,
             dataIndex: 'name',
             editor: {
                xtype: 'textfield',
                allowBlank: false
            }},
            {header: "شماره تلفن",
             width: 150,
             sortable: true,
             dataIndex: 'phone',
             editor: {
                 xtype: 'textfield',
                 allowBlank: false
            }},
            {header: "رایانامه",
             width: 150,
             sortable: true,
             dataIndex: 'email',
             editor: {
                xtype: 'textfield',
                allowBlank: false
            }},{
                header: "تاریخ تولد",
                dataIndex: 'birthday',
                hidden: false,
                sortable: true,
                resizable: true,
                width: 100,
                renderer : Ext.ux.dateRenderer,
                editor: new Ext.form.DateField({
                	plugins: [Ext.ux.JalaliDatePlugin],
                    format: 'B/Q/R',
                    allowBlank: true
                })
            }
        ],
        plugins: [editor,Ext.ux.grid.DataDrop],
        title: 'گزارشات',
        //height: 300,
        //width:610,
		frame:true,
		tbar: [{
            iconCls: 'icon-user-add',
            text: 'اطلاعات جدید',
            handler: function(){
                var e = new Contact({
                    name: 'New Guy',
                    phone: '(000) 000-0000',
                    email: 'new@blabla.com',
                    birthday: '1376/02/09'
                });
                editor.stopEditing();
                store.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                editor.startEditing(0);
            }
        },{
            iconCls: 'icon-user-delete',
            text: 'حذف اطلاعات',
            handler: function(){
                editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    store.remove(r);
                }
            }
        },{
            iconCls: 'icon-user-save',
            text: 'ذخیره‌سازی همه تغییرات',
            handler: function(){
                store.save();
            }
        }]
    });
    EURB.mainPanel.items.add(grid);
    EURB.mainPanel.doLayout();
});