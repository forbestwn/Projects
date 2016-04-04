	var createUITemplateListFunction = function(parms, parent){
		
		var m_uiTemplateInfoFunction;
		var m_uiTemplateEventFunction;
		
		var that = new NosliwAppFunction('UITemplateList', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
						
						m_uiTemplateEventFunction = createUITemplateEventInfoFunction({"parentEle":$("#templateevent")});
						m_uiTemplateInfoFunction = createUITemplateInfoFunction({"parentEle":$("#templateinfo")});
						
				 		var templateListContext = {};
				 		var templateQuery = that.getData('templateQuery');
				 		if(templateQuery!=undefined){
					 		setContext(templateListContext, 'templates', templateQuery);
					 		var templateListResourceView = that.getData('uitemplatelist');
					 		templateListResourceView.setContext(templateListContext);
							$("#templatelist").append(templateListResourceView.getViews());
							
							templateListResourceView.on('selectUITemplate', function(templateId){
								document.getElementById("templateSrc").addEventListener('load', function(){ 
									document.getElementById('templateSrc').contentWindow.setEventFunction(m_uiTemplateEventFunction);					
								});

								m_uiTemplateInfoFunction.command('setUITemplateId', templateId);
								
								var url = "template.html?ID="+templateId;
								$('#templateSrc').attr('src', url);
								
								document.getElementById("templateSrc").contentDocument.location.reload(true);
							});
				 		}
					},
				});	
				
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('templatelists', {
					success : function(requestInfo, data){
						that.setData('uitemplatelist', data);
					},
				}));

				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('datatype', {
					success : function(requestInfo, data){
					},
				}));

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
						that.setData('templateQuery', data);
					},
				}));

				NosliwRequestManager.startRequestGroup(requestGroup);
				
			},
		});

		return that;
	};
