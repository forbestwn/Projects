	var createUITemplateCommandInfoFunction = function(parms, parent){
		
		var m_uiTemplateCommandView;
		var m_parentEle;
		
		var that = new NosliwAppFunction('UITemplateCommand', parms, parent, 
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
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('uitemplatecommand', {
					success : function(requestInfo, data){
						m_uiTemplateCommandView = data;
					},
				}));									

				NosliwRequestManager.startRequestGroup(requestGroup);
			},
		
			m_postInit : function(){
				m_parentEle.append(m_uiTemplateCommandView.getViews());
				
				m_uiTemplateCommandView.on('command', function(command){
					var comName = command.name;
					var comParms = command.parms;
					var commandData = {};
					for (var parm in comParms) {
					    if (comParms.hasOwnProperty(parm)) {
					    	var parmDataInfo = comParms[parm];
					    	commandData[parm] = getTemplateData(parmDataInfo.datatype); 
					    }
					}
					document.getElementById('templateSrc').contentWindow.command(comName, commandData);					
				});
				
			},
			
			m_doDestroy : function(){
			},
			
			m_doCommand : function(name, data){
				var that = this;
				
				if(name=='setUITemplateId'){
					var uiTemplateInfo = data;
			 		var templateContext = {};
			 		setContext(templateContext, 'templateinfo', createObjectWraper(uiTemplateInfo));
			 		m_uiTemplateCommandView.setContext(templateContext);
				}
			},
		});
		return that;
	};
	
