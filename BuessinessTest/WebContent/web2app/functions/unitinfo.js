	var createUnitInfoFunction = function(parms, parent){
		var m_unitInfoResourceView;
		
		var that = new NosliwAppFunction('UnitInfo', parms, parent, 
		{
			m_doInit : function(){
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
				 		m_unitInfoResourceView = this.getRequestGroupMetaData('uiunitinfo');
						$("#unitinfo").append(m_unitInfoResourceView.getViews());
					},
				});	
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('uiunitinfo', {
					success : function(requestInfo, data){
						this.setRequestGroupMetaData('uiunitinfo', data);
					},
				}));
				
				NosliwRequestManager.startRequestGroup(requestGroup);
			},

			m_doCommand : function(name, data){
				if(name=="setUnitInfo"){
					var faceEntity = data.faceEntity;
					var unitPath = data.unitPath;
					var contextPath = getFaceContextPathByUnitPath(unitPath, faceEntity);
					var contextEle = createContextElement(faceEntity, contextPath);
					
					var serviceContext = createContextElement(new NosliwServiceContext({
						requestChange : function(requestInfo){
							var reqContext = requestInfo.requester.info.context;
							var reqOperations = requestInfo.getRequestOperations();

							var dataId;
							var dataName = getContextPathWraper(reqContext, 'dataconfigure.name').data;
							var dataPath = ".configure|"+dataName;
							var datainputs = this.data.unitEntity.data.configure.data.datainputs.data;
							for (var id in datainputs) {
							    if (datainputs.hasOwnProperty(id)) {
							    	var datainput = datainputs[id];
							    	if(datainput.data.out.data.point.data==dataPath){
							    		dataId = id;
							    		break;
							    	}
							    }
							}
							
							var requestInfo = new NosliwOperationRequestInfo(requestInfo.requester, {});
							
							if(dataId!=undefined){
								requestInfo.addRequestOperation({
									'operation' : 'set',
									'context' : reqContext,
									'contextPath' : getPathInfo('uiunit.configure.datainputs.'+dataId+'.in.point'),
									'data' : reqOperations[0].data,
								});						
								NosliwRequestManager.processRequest(requestInfo);
							}
							else{
								requestInfo.addRequestOperation({
									'operation' : 'newElement',
									'context' : reqContext,
									'contextPath' : getPathInfo('uiunit.configure.datainputs'),
									'data' : reqOperations[0].data,
									'parms' : {
										'in.type' : 'static',
										'in.point' : reqOperations[0].data,
										'out.type' : 'uipath',
										'out.point' : '\\.configure|'+dataName,
									},
								});						
								NosliwRequestManager.processRequest(requestInfo);
							}
							
						},
						
						getData : function(context, path){
							var out;
							if(path=='simple'){
								var dataName = getContextPathWraper(context, 'dataconfigure.name').data;

								var dataPath = ".configure|"+dataName;
								var datainputs = this.data.unitEntity.data.configure.data.datainputs.data;
								for (var id in datainputs) {
								    if (datainputs.hasOwnProperty(id)) {
								    	var datainput = datainputs[id];
								    	if(datainput.data.out.data.point.data==dataPath){
								    		out = datainput.data['in'].data.point;
								    		break;
								    	}
								    }
								}
								
								if(out==undefined){
									out = getContextPathWraper(context, 'dataconfigure.datatypeinfo.datatype.atomtype.defaultvalue');
								}

								return out;
							}
						},
						
						registerContextEvent : function(context, contextPath, action, code){
							if(contextPath.path=='simple'){
								
							}
						},
						
					}, {
						faceEntity : data.faceEntity,
						unitPath : data.unitPath,
						unitEntity : getUiUnitEntityByUnitPath(data.unitPath, data.faceEntity),
					}));
					
					var context = {
						uiunit : contextEle,
						dataservice : serviceContext,
					};
					m_unitInfoResourceView.setContext(context);
				}
			},				
		});

		return that;
	};
