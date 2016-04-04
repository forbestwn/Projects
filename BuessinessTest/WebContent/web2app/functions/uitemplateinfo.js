	var createUITemplateInfoFunction = function(parms, parent){
		
		var m_templateInfoResourceView;
		var m_parentEle;
		var m_templateInfo;
		
		var m_uiTemplateDataFunction;
		var m_uiTemplateCommandFunction;
		var m_uiTemplateComponentFunction;
		
		
		var that = new NosliwAppFunction('UITemplateInfo', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				
				m_parentEle = this.getParm('parentEle');
				
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
				 		m_templateInfoResourceView = that.getData('uitemplateinfo');
						m_parentEle.append(m_templateInfoResourceView.getViews());
						that.finishInit();
					},
				});	
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('uitemplateinfo', {
					success : function(requestInfo, data){
						that.setData('uitemplateinfo', data);
					},
				}));

				NosliwRequestManager.startRequestGroup(requestGroup);
			},
		
			m_postInit : function(){
				var that = this;

				var dataEle = m_templateInfoResourceView.getDomElementByAttributeValue("area", "templatedata");
				m_uiTemplateDataFunction = createUITemplateDataFunction({"parentEle":$(dataEle)});

				var commandEle = m_templateInfoResourceView.getDomElementByAttributeValue("area", "templatecommand");
				m_uiTemplateCommandFunction = createUITemplateCommandInfoFunction({"parentEle":$(commandEle)});

				var componentEle = m_templateInfoResourceView.getDomElementByAttributeValue("area", "templatecomponent");
				m_uiTemplateComponentFunction = createUITemplateComponentsFunction({"parentEle":$(componentEle)});
			
			},
			
			m_doDestroy : function(){
			},
			
			m_doCommand : function(name, data){
				var that = this;
				
				if(name=='setUITemplateId'){
					var uiTemplateId = data;
					m_templateInfo = MobileServiceManager.getTemplate(uiTemplateId);
					
					m_uiTemplateDataFunction.command('setUITemplateId', m_templateInfo);
					m_uiTemplateCommandFunction.command('setUITemplateId', m_templateInfo);
					m_uiTemplateComponentFunction.command('setUITemplateId', m_templateInfo);
				}
			},
		});
		return that;
	};
	
