	var createUITemplateEventInfoFunction = function(parms, parent){
		
		var m_uiTemplateEventView;
		var m_parentEle;
		
		var that = new NosliwAppFunction('UITemplateEvent', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				
				m_parentEle = this.getParm('parentEle');
				
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
						that.finishInit();
					},
				});		
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('uitemplateevent', {
					success : function(requestInfo, data){
						m_uiTemplateEventView = data;
					},
				}));									

				NosliwRequestManager.startRequestGroup(requestGroup);
			},
		
			m_postInit : function(){
				m_parentEle.append(m_uiTemplateEventView.getViews());
			},
			
			m_doDestroy : function(){
			},
			
			m_doCommand : function(name, data){
				var that = this;
				
				if(name=='showEvent'){
					var templateInfo = data.templateInfo;
					var eventName = data.event.event;
					var eventData = data.event.data;
					
					var dataType = getDataTypeResourceData(eventData, templateInfo.events[eventName].datatype);
					var eventDataInfo = {
						name : eventName,
						data : dataType,
					};
			 		var templateEventContext = {};
			 		setContext(templateEventContext, 'eventdata', createObjectWraper(eventDataInfo));
			 		m_uiTemplateEventView.setContext(templateEventContext);
				}
			},
		});
		return that;
	};
	
