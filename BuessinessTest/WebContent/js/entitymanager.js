var NosliwEntityManager = function(){
	var m_entitys = {};

	var m_requester = {
			sourceType : 'system',
			sourceId : 'entitymanager',
	};

	
	var m_removeEntity = function(ID){
		var entity = m_entitys[ID];
		if(entity!=undefined){
			entity.off();
			delete m_entitys[ID];
		}
	};
	
	var m_getEntity = function(ID){
		return m_entitys[ID];
	};
	
	var m_addEntity = function(ID, entity){
		_.extend(entity, Backbone.Events);
		m_entitys[ID] = entity;
		return entity;
	};
	
	var manager = {
			getRequestInfoGetEntityWrapers : function(ids, handler){
				var reqInfo = new NosliwServiceRequestInfo(m_requester, handler, NosliwServiceManager.getGetEntityWrapersService(ids));
				reqInfo.setRequestExecuteInfo({
					method : this.requestInfoGetEntityWrapers,
					context : this,
				});
				reqInfo.setMetaData('ids', ids);
				return reqInfo;
			},	
			
			requestGetEntityWrapers : function(ids, handlers){
				var requestInfo = this.getRequestInfoGetEntityWrapers(ids, handlers);
				return this.requestInfoGetEntityWrapers(requestInfo);
			},
			
			requestInfoGetEntityWrapers : function(requestInfo){
				var ids = requestInfo.getMetaData('ids');
				var remoteReqs = [];
				var results = {};
				for(var i in ids){
					var entity = m_entitys[ids[i]];
					if(entity==undefined){
						remoteReqs.push(ids[i]);
					}
					else{
						results[ids[i]]=entity;
					}
				}
				requestInfo.setMetaData('result', results);
				
				requestInfo.setMetaHandler( mergeHandler(requestInfo.handler, {
					success : createRequestProcessorHandlerFunction(requestInfo.handler.success, function(entityWrapers, reqInfo){
						var all = requestInfo.getMetaData('result');
						if(entityWrapers!=undefined){
							_.each(entityWrapers, function(entityWraper, ID, list){
								m_addEntity(ID, entityWraper);
								all[ID] = entityWraper;
							}, this);
						}
						requestInfo.setMetaData('result', all);
						return all;
					}), 	
				}));
				
				if(remoteReqs.length==0){
					if(requestInfo.getMetaHandler().success!=undefined){
						return requestInfo.getMetaHandler().success.call(this, requestInfo);
					}
				}
				else{
					var task = NosliwServiceManager.getGetEntityWrapersServiceTask(remoteReqs, requestInfo); 
					NosliwRemoteServiceTaskManager.addServiceTask(task);
				}
			},
			
			
		deleteEntity : function(ID){
			m_removeEntity(ID);
		},
			
		addEntity : function(ID, entity){
			m_addEntity(ID, entity);
		},
		
		getEntity : function(entityID){
			var rootID = getRootEntityID(entityID);
			var rootEntity = m_entitys[rootID];
			if(rootEntity==undefined){
//				var entitys = NosliwServiceManager.getEntityWrapers([entityID]);
//				entity = entitys[0];
//				m_addEntity(entityID, entity);
			}
			
			return getRealEntity(entityID, rootEntity);
		},
		
	};
	return manager;
}();

