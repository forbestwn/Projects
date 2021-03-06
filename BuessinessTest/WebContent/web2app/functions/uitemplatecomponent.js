	var createUITemplateComponentsFunction = function(parms, parent){
		var m_uiTemplateComponentTemplateSelFunction;
		var m_uiTemplateComponentsView;
		var m_parentEle;
		
		var that = new NosliwAppFunction('UITemplateComponents', parms, parent, 
		{
			m_doInit : function(){
				m_uiTemplateComponentTemplateSelFunction = createUIComponentTemplateSelFunction();

				var that = this;
				
				m_parentEle = this.getParm('parentEle');
				
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
						that.finishInit();
					},
				});		
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('uitemplatecomponent', {
					success : function(requestInfo, data){
						m_uiTemplateComponentsView = data;
					},
				}));									

				NosliwRequestManager.startRequestGroup(requestGroup);
			},
		
			m_postInit : function(){
				var that = this;
				m_parentEle.append(m_uiTemplateComponentsView.getViews());
				
				m_uiTemplateComponentsView.on('selectTemplate', function(componentContextEle){
					m_uiTemplateComponentTemplateSelFunction.command('show', function(selectedTemplateId){
						var componentTemplateInfo = MobileServiceManager.getTemplate(selectedTemplateId);
						requestSetObjectContextElePathValue(componentContextEle, 'uiunit', componentTemplateInfo);
						alert(selectedTemplateId);
					});
					
				});
			},
			
			m_doDestroy : function(){
			},
			
			m_doCommand : function(name, data){
				var that = this;
				
				if(name=='setUITemplateId'){
					var uiTemplateInfo = data;
					this.setData('templateInfo', uiTemplateInfo);
			 		var templateContext = {};
			 		setContext(templateContext, 'templateinfo', createObjectWraper(uiTemplateInfo));
			 		m_uiTemplateComponentsView.setContext(templateContext);
				}
			},
		});
		return that;
	};
	
