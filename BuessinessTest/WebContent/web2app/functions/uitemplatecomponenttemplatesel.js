
var createUIComponentTemplateSelFunction = function(parms, parent){
		
		var m_templateListResourceView;
		var m_parentEle;
		var m_makeupEle;
		var m_variables;
		var m_templateQuery;
		
		var that = new NosliwAppFunction('UIComponentTemplateSel', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				m_parentEle = this.getParm('parentEle');
				
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
				 		m_templateListResourceView = this.getRequestGroupMetaData('uitemplatelist');
				 		m_templateQuery = this.getRequestGroupMetaData('templateQuery');
					},
				});	
				
				var templateQuery = {
						attributes : ["name", "description"],
						entityTypes : [
						               {
						            	   categary : 'entity',
						            	   type : 'ui.template',
						               }
						               ],
				};
				requestGroup.addRequest(NosliwQueryManager.getRequestInfoQuery(templateQuery, {
					success : function(requestInfo, data){
						this.setRequestGroupMetaData('templateQuery', data);
					},
				}));

				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('uitemplatecomponentsel', {
					success : function(requestInfo, data){
						this.setRequestGroupMetaData('uitemplatelist', data);
					},
				}));
				
				NosliwRequestManager.startRequestGroup(requestGroup);
			},
			
			m_doCommand : function(name, data){
				var that = this;
				
				if(name=='show'){
			 		var templateListContext = {};
			 		if(m_templateQuery!=undefined){
				 		setContext(templateListContext, 'templates', m_templateQuery);
				 		
				 		m_variables = {
				 			selected : '',
				 		};
				 		setContext(templateListContext, 'variables', createObjectWraper(m_variables));
				 		m_templateListResourceView.setContext(templateListContext);
			 		}

			 		var parentEle = m_parentEle;
			 		m_makeupEle = undefined;
			 		if(parentEle==undefined){
			 			m_makeupEle = $("<div></div>");
			 			parentEle = m_makeupEle; 
			 		}
			 		
			 		parentEle.append(m_templateListResourceView.getViews());
					var dialog = parentEle.dialog({
					      autoOpen: false,
					      height: 300,
					      width: 550,
					      modal: true,
					      buttons: {
					        Ok: function(){
						        dialog.dialog( "close" );
						        data.call(this, m_variables.selected);
					        },
					        Cancel: function() {
					          dialog.dialog( "close" );
					        }
					      },
					      close: function() {
					    	  that.destroy();
					    	  m_templateListResourceView.removeViews();
					    	  if(m_makeupEle!=undefined){
					    		  m_makeupEle.remove();
					    	  }
					      }
					 });
					 dialog.dialog( "open" );
					 this.setData('dialog', dialog);
				}
			},
			
		});

		return that;
	};
