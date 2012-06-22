EURB.ReportDesign.InfoForm = new Ext.form.FormPanel({
			width: '100%'
			,height: 300
			,border:true
			,stateful:false
			,region: 'center'
        	,split: true
        	,defaultType: 'textfield'
        	,labelWidth: 75
        	,title: EURB.ReportDesign.Info
        	,bodyStyle:'padding:5px 5px 0'
        	,reader: new Ext.data.JsonReader({
                    idProperty: 'id' 
                    ,totalProperty:'totalCount'
                	,root: 'data'             
                    ,fields: [
                         {name:'id', type:'int'}
                         ,{name:'versionId', type:'int'}
                         ,{name:'name', type:'string'}
                         ,{name:'categoryId', type:'int'}
                         ,{name:'description', type:'string'}
                    ]
             }) 
        	,items: [{
			    name: 'id'
			    ,allowBlank:false
			    ,xtype: 'hidden'
			},{
			    name: 'versionId'
				,allowBlank:false
				,xtype: 'hidden'
			},{
			    fieldLabel: EURB.ReportDesign.NameField
			    ,name: 'name'
			    ,anchor:'100%'  // anchor width by percentage
			    ,xtype: 'textfield'
				,allowBlank:false
			},
			EURB.ReportDesign.categoryCombo
			,{
				fieldLabel: EURB.ReportDesign.DescriptionField
			    ,name: 'description'
			    ,anchor:'100%'  // anchor width by percentage
			    ,xtype: 'textarea'
				,allowBlank:true
			}]
			,buttons: [{
		        text:Ext.MessageBox.buttonText.ok,
		        handler: function() {
		        	if(EURB.ReportDesign.InfoForm.getForm().isValid()) {
		        		EURB.ReportDesign.InfoForm.getForm().submit({
		        			url:EURB.ReportDesign.storeAction,
		        			success:function(form, action){
		        				EURB.ReportDesign.selectedVersion = action.result.affectedIds[0][1];
		        				EURB.ReportDesign.InfoForm.getForm().load({url:EURB.ReportDesign.searchAction, params:{reportDesign : EURB.ReportDesign.selectedDesign,reportVersion: EURB.ReportDesign.selectedVersion}} );
		        			}
		        		});
		        	}
		        }
		    }]
	    });
        

